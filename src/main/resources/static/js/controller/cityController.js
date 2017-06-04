var cityController = angular.module('bankApp.cityController', []);

cityController.controller('cityController', function($scope, $location,
		$window, $compile, $routeParams, cityService, countryService, ngNotify) {

	$scope.action = {};
	$scope.cities = [];
	$scope.countries = [];
	$scope.countryOptions = "country.name for country in countries";
	$scope.selectedCity = {};
	$scope.selectedCountry = {};
	$scope.mode = {};
	$scope.mode.current = "Rezim izmene";
	
	$scope.getAllCities = function() {
		cityService.getAllCities().then(function(data) {
			if($routeParams.param > 0){
				var temp = [];
				for(var i = 0; i < data.data.length; i++){
					if(data.data[i].country.id == $routeParams.param){
						temp.push(data.data[i]);
					}
				}
				$scope.cities = temp;
				$scope.selectedCountry = $scope.countries[$routeParams.param-1];
			} else {
				if (data.data != null) {
					$scope.cities = data.data;
				}
			}
		});
	}

	$scope.getAllCountries = function() {
		countryService.getAllCountries().then(function(data) {
			if (data.data != null) {
				$scope.countries = data.data;
			}
		});
	}

	$scope.getAllCities();
	$scope.getAllCountries();

	$scope.city = {};
	$scope.selectedCity = {};
	$scope.setSelected = function(city) {
		if ($scope.selectedCity == city) {
			$scope.selectedCity = {};
		} else {
			$scope.setParameters(city);
		}
	}

	$scope.firstClicked = function() {
		$scope.action = "firstClicked";
		$scope.setParameters($scope.cities[0]);
	}
	$scope.lastClicked = function() {
		$scope.action = "lastClicked";
		$scope.setParameters($scope.cities[$scope.cities.length - 1]);
	}
	$scope.nextClicked = function() {
		$scope.action = "nextClicked";
		var n = $scope.cities.indexOf($scope.selectedCity) + 1;
		n = n % $scope.cities.length;
		$scope.setParameters($scope.cities[n]);
	}
	$scope.prevClicked = function() {
		$scope.action = "prevClicked";
		var n = $scope.cities.indexOf($scope.selectedCity);
		if (n == 0 || n == -1) {
			n = $scope.cities.length;
		}
		$scope.setParameters($scope.cities[n - 1]);
	}
	$scope.addClicked = function() {
		$scope.action = "addClicked";
		$scope.mode.current = "Rezim dodavanja";
	}
	$scope.searchClicked = function() {
		$scope.action = "searchClicked";
		$scope.mode.current = "Rezim pretrage";
	}

	$scope.removeClicked = function(city) {
		$scope.action = "removeClicked";
		$scope.mode.current = "Rezim brisanja";
		if (Object.keys(city).length > 0) {
			cityService.deleteCity(city.id).then(function(response) {
				$scope.getAllCities();
			});
		}
	}

	$scope.city = {};
	$scope.submitAction = function(city) {
		if ($scope.action == "addClicked") {
			cityService.addNewCity(city, $scope.selectedCountry.id).then(function(response) {
				$scope.cities.push(response.data);
			});
		} else if ($scope.action == "searchClicked") {
			cityService.searchCities($scope.city, $scope.selectedCountry).then(function(response) {
				$scope.cities = response.data;
			});
		} else {
			if (Object.keys($scope.selectedCity).length > 0) {
				cityService.editCity(city, $scope.selectedCountry.id).then(
						function(response) {
							$scope.getAllCities();
						});
			} else {
				ngNotify.set('Selektujte naseljeno mesto prvo!' , {
					type : 'error',
					duration: 3000,
					theme: 'pitchy'
				});
			}
		}
	}
	
	

	$scope.rollbackAction = function() {
		$scope.action = "editCity"
		$scope.mode.current = "Rezim izmene";
		$scope.city = {};
		$scope.selectedCountry = {};
		$scope.getAllCities();
	}
	
	
	$scope.showCountries = function(country){
		$("#countryModal").modal('show');
		$scope.setModalSelectedCountry(country);
	}
	
	
	$scope.setModalSelectedCountry = function(country){
		$scope.selectedModalCountry = country;
	}
	
	$scope.setParameters = function(city){
		$scope.selectedCity = city;
		$scope.city = angular.copy(city);
		for(i=0;i<$scope.countries.length;i++){
			if($scope.countries[i].id == city.country.id){
				$scope.selectedCountry = $scope.countries[i];
				break;  
			}
		}
	}
	
	$scope.confirmCountry = function(){
		$scope.selectedCountry = $scope.selectedModalCountry;
		$("#countryModal").modal('hide');
		$scope.selectedModalCountry = {};
	}

});