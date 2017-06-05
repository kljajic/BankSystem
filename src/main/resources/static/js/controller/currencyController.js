var currencyController = angular.module('bankApp.currencyController' ,[]);

currencyController.controller('currencyController', function($scope, $location, $window, $compile, $routeParams,
		currencyService, countryService){
	
	$scope.action = {};
	$scope.currencies = {};
	$scope.countries = {};
	
	function refreshView(){
		currencyService.getAllCurrencies().then(function(response){
			if($routeParams.param > 0){
				var temp = [];
				for(var i = 0; i < response.data.length; i++){
					if(response.data[i].country.id == $routeParams.param){
						temp.push(response.data[i]);
					}
				}
				$scope.currencies = temp;
				$('#selectField').prop('disabled', 'disabled');
				$scope.currency.country = findCountryForSelect($routeParams.param);
				$scope.selectedCountry = findCountryForSelect($routeParams.param);
			} else{
				if(response.data != null){
					$scope.currencies = response.data;
					$('#selectField').removeAttr('disabled');
				}
			}
		});
		countryService.getAllCountries().then(function(response){
			if(response.data != null){
				$scope.countries = response.data;
			}
		});
	}
	
	refreshView();
	

	findCountryForSelect = function(id){
		for(var i = 0; i < $scope.countries.length; i++){
			if($scope.countries[i].id === id){
				return $scope.countries[i];
			}
		}
	}
	
	syncClick = function(currency){
		$scope.selectedCurrency = currency;
		$scope.currency.id = currency.id;
		$scope.currency.officialCode = currency.officialCode;
		$scope.currency.name = currency.name;
		$scope.currency.domicilna = currency.domicilna;
		$scope.currency.country = findCountryForSelect(currency.country.id);
	}
	
	syncButtons = function(selectedCurrency){
		$scope.currency.id = $scope.selectedCurrency.id;
		$scope.currency.officialCode = $scope.selectedCurrency.officialCode;
		$scope.currency.name = $scope.selectedCurrency.name;
		$scope.currency.domicilna = $scope.selectedCurrency.domicilna;
		$scope.currency.country = findCountryForSelect(selectedCurrency.country.id);
	}
	
	$scope.currency = {};
	$scope.selectedCurrency = {};
	$scope.setSelected = function(currency){
		if($scope.selectedCurrency==currency){
			$scope.selectedCurrency = {};
		} else {
			syncClick(currency);
		}
	}
	
	$scope.firstClicked = function(){
		$scope.action = "firstClicked";
		$scope.selectedCurrency = $scope.currencies[0];
		syncButtons($scope.selectedCurrency);
	}
	$scope.lastClicked = function(){
		$scope.action = "lastClicked";
		$scope.selectedCurrency = $scope.currencies[$scope.currencies.length-1];
		syncButtons($scope.selectedCurrency);
	}
	$scope.nextClicked = function(){
		$scope.action = "nextClicked";
		var n = $scope.currencies.indexOf($scope.selectedCurrency)+1;
		n = n % $scope.currencies.length;
		$scope.selectedCurrency = $scope.currencies[n];
		syncButtons($scope.selectedCurrency);
	}
	$scope.prevClicked = function(){
		$scope.action = "prevClicked";
		var n = $scope.currencies.indexOf($scope.selectedCurrency);
		if(n == 0 || n == -1){
			n = $scope.currencies.length;
		}
		$scope.selectedCurrency = $scope.currencies[n-1];
		syncButtons($scope.selectedCurrency);
	}
	$scope.addClicked = function(){
		$scope.action = "addClicked";
	}
	$scope.searchClicked = function(){
		$scope.action = "searchClicked";
	}
	
	$scope.currency.country = {};	
	$scope.submitAction = function(currency){
		if($scope.action == "addClicked"){
			currencyService.addNewCurrency(currency).then(function(response){
				$scope.currencies.push(response.data);
			});
		} else if($scope.action == "searchClicked"){
			if(currency.country == null || typeof currency.country.name == 'undefined' || currency.country.name == ""){
				$scope.currency.country = {};
				$scope.currency.country.name = {};
				currency.country.name = "AHA";
			}
			if(typeof currency.officialCode == 'undefined' || currency.officialCode == ""){
				currency.officialCode = "AHA";
			}
			if(typeof currency.name == 'undefined' || currency.name == ""){
				currency.name = "AHA";
			}
			
			if(currency.domicilna != true){
				currency.domicilna = false;
			}
			currencyService.searchCurrencies(currency.officialCode, currency.name, currency.country.name, currency.domicilna).then(function(response){
				$scope.currencies=response.data;
				currency.officialCode = "";
				currency.name = "";
			});
		}
		else{
			if(Object.keys($scope.selectedCurrency).length > 0){
				currencyService.editCurrency(currency).then(function(response){
					refreshView();
				});
			} else {
				swal({ title:"Selektujte valutu ili operaciju.", type:"error" });
			}
		}
	}
	
	$scope.removeClicked = function(currency){
		$scope.action = "removeClicked";
		$scope.currency = {};
		$scope.selectedCurrency = {};
		if(Object.keys(currency).length > 0){
			swal({
				  title: "Da li ste sigurni?",
				  text: "Necete uspeti da vratite ovo.",
				  type: "warning",
				  showCancelButton: true,
				  confirmButtonColor: "#DD6B55",
				  confirmButtonText: "Da, obrisi.",
				  cancelButtonText: "Ponisti",
				  closeOnConfirm: false,
				  closeOnCancel: false
				},
				function(isConfirm){
				  if (isConfirm) {
						currencyService.deleteCurrency(currency).then(function(response){
							refreshView();
						});
					  swal("Obrisano!", "Uspesno ste obrisali.", "success");
				  } else {
				    swal("Ponisteno", "Ponistili ste operaciju brisanja.", "error");
				  }
				});
		} else {
			swal({ title:"Selektujte valutu.", type:"error" });
		}
	}
	
	$scope.rollbackAction = function(){
		$scope.action = {};
		$scope.currency = {};
		$scope.selectedCurrency = {};
		refreshView();
	}
	
	$scope.showCountries = function(currency){
		$('#combozoomModalCountries').modal('show');
		$scope.selectedCountry = currency.country;
		$scope.setSelectedCountry=function(country){
			if($scope.selectedCountry==country){
				$scope.selectedCountry = {};
			} else {
				$scope.selectedCountry = country;
			}
		}
		
		$scope.confirmCountry = function(){
			$scope.currency.country = findCountryForSelect($scope.selectedCountry.id);
			$('#combozoomModalCountries').modal('hide');
		}
	}
	
	$scope.nextClicked2 = function(selectedCurrency){
		if(Object.keys($scope.selectedCurrency).length > 0 ){
		$('#modalNextMechanism').modal('show');
			$scope.confirmNextMechanism = function(){
				if($scope.radioButtons.group == 'primaryCurrency'){
					$location.path('/currencyExchanges/').search({paramPrimary: selectedCurrency.id});
				} else if($scope.radioButtons.group == 'accordingToCurrency'){
					$location.path('/currencyExchanges/').search({paramAccordingTo: selectedCurrency.id});
				} else if($scope.radioButtons.group == 'account'){
					$location.path('/accounts/').search({param : selectedCurrency.id});
				}
				$('#modalNextMechanism').modal('hide');
			}
		} else {
			swal({ title:"Selektujte drzavu.", type:"error" });
		}
	}
	
	
}) ;