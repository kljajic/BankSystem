var countryController = angular.module('bankApp.countryController', []);

countryController.controller('countryController', function($scope, $location, $window, $compile, countryService){
	
	$scope.action = {};
	$scope.countries = {};
		
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
	}
	$scope.searchClicked = function(){
		$scope.action = "searchClicked";
	}
	
	$scope.removeClicked = function(country){
		$scope.action = "removeClicked";
		if(Object.keys(country).length > 0){
			countryService.deleteCountry(country).then(function(response){
				countryService.getAllCountries().then(function(response){
					if(response.data != null){
						$scope.countries = response.data;
					}
				});
			});
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
				alert("SELEKTUJ");
			}
		}
	}
	
	$scope.rollbackAction = function(){
		$scope.action = {};
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
			alert("selektuj!");
		}
	}
	
	
});