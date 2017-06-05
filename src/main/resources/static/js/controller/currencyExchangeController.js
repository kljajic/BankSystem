var currencyExchangeController = angular.module('bankApp.currencyExchangeController', []);

currencyExchangeController.controller('currencyExchangeController', function($scope, $location, $window, 
		$compile, $routeParams, currencyExchangeService, currencyService){
	
	$scope.action = {};
	$scope.cexes = {};
	
	$scope.currencies = {};
	$scope.exchangeLists = {};
	
	$scope.cex = {};
	$scope.selectedCex = {};
	
	function refreshView(){
		currencyExchangeService.getAllCexes().then(function(response){
			if($routeParams.paramPrimary > 0){
				var temp = [];
				for(var i = 0; i < response.data.length; i++){
					if(response.data[i].primaryCurrency.id === $routeParams.paramPrimary){
						temp.push(response.data[i]);
					}
				}
				$scope.cexes = temp;
				$('#selectFieldPrimary').prop('disabled', 'disabled');
				$scope.cex.primaryCurrency = findCurrencyForSelect($routeParams.paramPrimary);
				$scope.selectedPrimaryCurrency = findCurrencyForSelect($routeParams.paramPrimary);
			} else if($routeParams.paramAccordingTo > 0){
				var temp = [];
				for(var i = 0; i < response.data.length; i++){
					if(response.data[i].accordingToCurrency.id === $routeParams.paramAccordingTo){
						temp.push(response.data[i]);
					}
				}
				$scope.cexes = temp;
				$('#selectFieldAccordingTo').prop('disabled', 'disabled');
				$scope.cex.accordingToCurrency = findCurrencyForSelect($routeParams.paramAccordingTo);
				$scope.selectedAccordingToCurrency = findCurrencyForSelect($routeParams.paramAccordingTo);
			} else if($routeParams.paramEL > 0){
				var temp = [];
				for(var i = 0; i < response.data.length; i++){
					if(response.data[i].exchangeList.id === $routeParams.paramEL){
						temp.push(response.data[i]);
					}
				}
				$scope.cexes = temp;
				$('#selectFieldAccordingTo').prop('disabled', 'disabled');
				$scope.cex.exchangeList = findExchangeListForSelect($routeParams.paramEL);
				$scope.selectedExchangeList = findExchangeListForSelect($routeParams.paramEL);
			}
			else {
				if(response.data != null){
					$scope.cexes = response.data;
					$('#selectFieldPrimary').removeAttr('disabled');
					$('#selectFieldAccordingTo').removeAttr('disabled');
				}
			}
		});
		currencyService.getAllCurrencies().then(function(response){
			if(response.data != null){
				$scope.currencies = response.data;
			}
		});
		currencyExchangeService.getAllExchangeLists().then(function(response){
			if(response.data != null){
				$scope.exchangeLists = response.data;
				for(var i = 0;i < $scope.exchangeLists.length;i++){
					$scope.exchangeLists[i].dateView = moment($scope.exchangeLists[i].date).format("DD MMM YYYY");
					$scope.exchangeLists[i].usedSinceView = moment($scope.exchangeLists[i].usedSince).format("DD MMM YYYY");
				}
			}
		});
	}
	
	refreshView();
	
	findExchangeListForSelect = function(id){
		for(var i = 0; i < $scope.exchangeLists.length; i++){
			if($scope.exchangeLists[i].id === id){
				return $scope.exchangeLists[i];
			}
		}
	}
	
	findCurrencyForSelect = function(id){
		for(var i = 0; i < $scope.currencies.length; i++){
			if($scope.currencies[i].id === id){
				return $scope.currencies[i];
			}
		}
	}
	
	syncClick = function(cex){
		$scope.selectedCex = cex;
		$scope.cex.id = cex.id;
		$scope.cex.buyRate = cex.buyRate;
		$scope.cex.middleRate = cex.middleRate;
		$scope.cex.sellRate = cex.sellRate;
		$scope.cex.exchangeList = findExchangeListForSelect(cex.exchangeList.id);
		$scope.cex.primaryCurrency = findCurrencyForSelect(cex.primaryCurrency.id);
		$scope.cex.accordingToCurrency = findCurrencyForSelect(cex.accordingToCurrency.id);
	}
	
	syncButtons = function(selectedCex){
		$scope.cex.id = $scope.selectedCex.id;
		$scope.cex.buyRate = $scope.selectedCex.buyRate;
		$scope.cex.middleRate = $scope.selectedCex.middleRate;
		$scope.cex.sellRate = $scope.selectedCex.sellRate;
		$scope.cex.exchangeList = findExchangeListForSelect(selectedCex.exchangeList.id);
		$scope.cex.primaryCurrency = findCurrencyForSelect(selectedCex.primaryCurrency.id);
		$scope.cex.accordingToCurrency = findCurrencyForSelect(selectedCex.accordingToCurrency.id);
	}
	

	$scope.setSelected = function(cex){
		if($scope.selectedCex==cex){
			$scope.selectedCex = {};
		} else {
			syncClick(cex);
		}
	}
	
	$scope.firstClicked = function(){
		$scope.action = "firstClicked";
		$scope.selectedCex = $scope.cexes[0];
		syncButtons($scope.selectedCex);
	}
	$scope.lastClicked = function(){
		$scope.action = "lastClicked";
		$scope.selectedCex = $scope.cexes[$scope.cexes.length-1];
		syncButtons($scope.selectedCex);
	}
	$scope.nextClicked = function(){
		$scope.action = "nextClicked";
		var n = $scope.cexes.indexOf($scope.selectedCex)+1;
		n = n % $scope.cexes.length;
		$scope.selectedCex = $scope.cexes[n];
		syncButtons($scope.selectedCex);
	}
	$scope.prevClicked = function(){
		$scope.action = "prevClicked";
		var n = $scope.cexes.indexOf($scope.selectedCex);
		if(n == 0 || n == -1){
			n = $scope.cexes.length;
		}
		$scope.selectedCex = $scope.cexes[n-1];
		syncButtons($scope.selectedCex);
	}
	$scope.addClicked = function(){
		$scope.action = "addClicked";
	}
	$scope.searchClicked = function(){
		$scope.action = "searchClicked";
	}
	
	$scope.cex.exchangeList = {};
	$scope.cex.primaryCurrency = {};
	$scope.cex.accordingToCurrency = {};
	$scope.submitAction = function(cex){
		if($scope.action == "addClicked"){
			currencyExchangeService.addNewCex(cex).then(function(response){
				$scope.cexes.push(response.data);
			});
		} else if($scope.action == "searchClicked"){
			if(cex.exchangeList == null || typeof cex.exchangeList.numberOfExchangeList == 'undefined' || cex.exchangeList.numberOfExchangeList == ""){
				$scope.cex.exchangeList = {};
				$scope.cex.exchangeList.numberOfExchangeList = {};
				cex.exchangeList.numberOfExchangeList = -1;
			}
			if(cex.primaryCurrency == null || typeof cex.primaryCurrency.officialCode == 'undefined' || cex.primaryCurrency.officialCode == ""){
				$scope.cex.primaryCurrency = {};
				$scope.cex.primaryCurrency.officialCode = {};
				cex.primaryCurrency.officialCode = "AHA";
			}
			if(cex.accordingToCurrency == null || typeof cex.accordingToCurrency.officialCode == 'undefined' || cex.accordingToCurrency.officialCode == ""){
				$scope.cex.accordingToCurrency = {};
				$scope.cex.accordingToCurrency.officialCode = {};
				cex.accordingToCurrency.officialCode = "AHA";
			}
			if(cex.buyRate == null || typeof cex.buyRate == 'undefined' || cex.buyRate == ""){
				cex.buyRate = -1;
			}
			if(cex.sellRate == null || typeof cex.sellRate == 'undefined' || cex.sellRate == ""){
				cex.sellRate = -1;
			}
			if(cex.middleRate == null || typeof cex.middleRate == 'undefined' || cex.middleRate == ""){
				cex.middleRate = -1;
			}
			
			
			currencyExchangeService.searchCexes(cex.buyRate, cex.middleRate, cex.sellRate, cex.exchangeList.numberOfExchangeList, cex.primaryCurrency.officialCode, cex.accordingToCurrency.officialCode).then(function(response){
				$scope.cexes=response.data;
				$scope.cex.buyRate = "";
				$scope.cex.middleRate = "";
				$scope.cex.sellRate = "";
			});
		}
		else{
			if(Object.keys($scope.selectedCex).length > 0){
				currencyExchangeService.editCex(cex).then(function(response){
					refreshView();
				});
			} else {
				swal({ title:"Selektujte kurs u valuti ili operaciju.", type:"error" });
			}
		}
	}
	
	$scope.removeClicked = function(cex){
		$scope.action = "removeClicked";
		$scope.cex = {};
		$scope.selectedCex = {};
		if(Object.keys(cex).length > 0){
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
						currencyExchangeService.deleteCex(cex).then(function(response){
							refreshView();
						});
					  swal("Obrisano!", "Uspesno ste obrisali.", "success");
				  } else {
				    swal("Ponisteno", "Ponistili ste operaciju brisanja.", "error");
				  }
				});
		} else {
			swal({ title:"Selektujte kurs u valuti.", type:"error" });
		}
	}
	
	$scope.rollbackAction = function(){
		$scope.action = {};
		$scope.cex = {};
		$scope.selectedCex = {};
		refreshView();
	}
	
	$scope.showExchangeLists = function(cex){
		$('#combozoomModalExchangeLists').modal('show');
		$scope.selectedExchangeList = cex.exchangeList;
		$scope.setSelectedExchangeList = function(exchangeList) {
			if ($scope.selectedExchangeList == exchangeList) {
				$scope.selectedExchangeList = {};
			} else {
				$scope.selectedExchangeList = exchangeList;
			}
		}
		
		$scope.confirmExchangeList = function(){
			$scope.cex.exchangeList = findExchangeListForSelect($scope.selectedExchangeList.id);
			$('#combozoomModalExchangeLists').modal('hide');
		}
	}
	
	$scope.showPrimaryCurrency = function(cex){
		$('#combozoomModalPrimaryCurrency').modal('show');
		$scope.selectedPrimaryCurrency = cex.primaryCurrency;
		$scope.setSelectedPrimaryCurrency = function(currency) {
			if ($scope.selectedPrimaryCurrency == currency) {
				$scope.selectedPrimaryCurrency = {};
			} else {
				$scope.selectedPrimaryCurrency = currency;
			}
		}
		
		$scope.confirmPrimaryCurrency = function(){
			$scope.cex.primaryCurrency = findCurrencyForSelect($scope.selectedPrimaryCurrency.id);
			$('#combozoomModalPrimaryCurrency').modal('hide');
		}
	}
	
	$scope.showAccordingToCurrency = function(cex){
		$('#combozoomModalAccordingToCurrency').modal('show');
		$scope.selectedAccordingToCurrency = cex.accordingToCurrency;
		$scope.setSelectedAccordingToCurrency = function(currency) {
			if ($scope.selectedAccordingToCurrency == currency) {
				$scope.selectedAccordingToCurrency = {};
			} else {
				$scope.selectedAccordingToCurrency = currency;
			}
		}
		
		$scope.confirmAccordingToCurrency = function(){
			$scope.cex.accordingToCurrency = findCurrencyForSelect($scope.selectedAccordingToCurrency.id);
			$('#combozoomModalAccordingToCurrency').modal('hide');
		}
	}
	
	
});