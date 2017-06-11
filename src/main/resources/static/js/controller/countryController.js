var countryController = angular.module('bankApp.countryController', []);

countryController.controller('countryController', function($scope, $location, $window,
		$compile, countryService){
	
	$scope.action = {};
	$scope.countries = {};
	$scope.mode = {};
	$scope.mode.current = "Rezim izmene";
		
	countryService.getAllCountries().then(function(data){
		if(data.data != null){
			$scope.countries = data.data;
		}
	});
	
	$scope.country = {};
	$scope.selectedCountry = {};
	$scope.setSelected = function(country){
		if($scope.selectedCountry==country){
			$scope.selectedCountry = {};
		} else {
			$scope.selectedCountry = country;
			$scope.country.name = country.name;
			$scope.country.id = country.id;
		}
	}
	
	$scope.firstClicked = function(){
		$scope.action = "firstClicked";
		$scope.selectedCountry = $scope.countries[0];
		$scope.country.name = $scope.selectedCountry.name;
		$scope.country.id = $scope.selectedCountry.id;
	}
	$scope.lastClicked = function(){
		$scope.action = "lastClicked";
		$scope.selectedCountry = $scope.countries[$scope.countries.length-1];
		$scope.country.name = $scope.selectedCountry.name;
		$scope.country.id = $scope.selectedCountry.id;
	}
	$scope.nextClicked = function(){
		$scope.action = "nextClicked";
		var n = $scope.countries.indexOf($scope.selectedCountry)+1;
		n = n % $scope.countries.length;
		$scope.selectedCountry = $scope.countries[n];
		$scope.country.name = $scope.selectedCountry.name;
		$scope.country.id = $scope.selectedCountry.id;
	}
	$scope.prevClicked = function(){
		$scope.action = "prevClicked";
		var n = $scope.countries.indexOf($scope.selectedCountry);
		if(n == 0 || n == -1){
			n = $scope.countries.length;
		}
		$scope.selectedCountry = $scope.countries[n-1];
		$scope.country.name = $scope.selectedCountry.name;
		$scope.country.id = $scope.selectedCountry.id;
	}
	$scope.addClicked = function(){
		$scope.action = "addClicked";
		$scope.mode.current = "Rezim dodavanja";
	}
	$scope.searchClicked = function(){
		$scope.action = "searchClicked";
		$scope.mode.current = "Rezim pretrage";
	}
	
	$scope.removeClicked = function(country){
		$scope.action = "removeClicked";
		$scope.mode.current = "Rezim brisanja";
		if(Object.keys(country).length > 0){
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
					  countryService.deleteCountry(country).then(function(response){
							countryService.getAllCountries().then(function(response){
								if(response.data != null){
									$scope.countries = response.data;
								}
							});
						});
					  swal("Obrisano!", "Uspesno ste obrisali.", "success");
				  } else {
				    swal("Ponisteno", "Ponistili ste operaciju brisanja.", "error");
				  }
				});
			
			
		} else {
			swal({ title:"Selektujte drzavu.", type:"error" });
		}
	}
	
	$scope.country = {};
	$scope.submitAction = function(country){
		if($scope.action == "addClicked"){
			countryService.addNewCountry(country.name).then(function(response){
				$scope.countries.push(response.data);
			});
		} else if($scope.action == "searchClicked"){
			countryService.searchCountries(country.name).then(function(response){
				$scope.countries=response.data;
			});
		} else{
			if(Object.keys($scope.selectedCountry).length > 0){
				countryService.editCountry(country.id, country.name).then(function(response){
					countryService.getAllCountries().then(function(data){
						if(data.data != null){
							$scope.countries = data.data;
						}
					});
				});
			} else {
				swal({ title:"Selektujte drzavu ili operaciju.", type:"error" });
			}
		}
	}
	
	$scope.rollbackAction = function(){
		$scope.action = {};
		$scope.mode.current = "Rezim izmene";
		$scope.country = {};
		$scope.selectedCountry = {};
		countryService.getAllCountries().then(function(data){
			if(data.data != null){
				$scope.countries = data.data;
			}
		});
	}

	$scope.radioButtons = {};
	$scope.nextClicked2 = function(selectedCountry){
		if(Object.keys($scope.selectedCountry).length > 0){
			$('#modalNextMechanism').modal('show');
			$scope.confirmNextMechanism = function(){
				if($scope.radioButtons.group == 'cities'){
					$location.path('/cities/').search({param: selectedCountry.id});
				} else if($scope.radioButtons.group == 'currencies'){
					$location.path('/currencies/').search({param: selectedCountry.id});
				}
				
				$('#modalNextMechanism').modal('hide');
			}
		} else {
			swal({ title:"Selektujte drzavu.", type:"error" });
		}
	}
	
	$scope.confirmPreviousForm = function(){
		$("#previousFormsModal").modal('hide');
		$location.path('/'+$scope.selectedModalPrevousForm);
		$scope.selectedModalPrevousForm = {};
	}
	
	$scope.previousForm = function(){
		$("#previousFormsModal").modal('show');
	}
	
	$scope.selectedModalPrevousForm = {};
	$scope.setModalSelectedPreviousForm = function(previousForm){
		$scope.selectedModalPrevousForm = previousForm;
	}
	
});