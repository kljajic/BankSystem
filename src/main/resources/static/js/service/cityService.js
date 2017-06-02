var cityService = angular.module('bankApp.cityService', []);

cityService.factory('cityService', function($http){
	
	var temp = {};
	
	temp.getAllCities = function(){
		return $http.get('/cities/getCities/');
	};
	
	temp.deleteCity = function(id){
		return $http.delete('/cities/delete/' + id);
	};
	
	temp.addNewCity = function(city, countryId){
		var jsonCity = JSON.stringify({
			name : city.name,
			pttNumber : city.pttNumber
		});
		return $http.post('/cities/add/' + countryId, jsonCity);
	};
	
	temp.searchCities = function(city, country){
		if(country != null){
			var jsonCity = JSON.stringify({
				id : city.id,
				name : city.name,
				pttNumber : city.pttNumber,
				country : {
					id : country.id,
					name : country.name
				}
			}); 
		}else {
			var jsonCity = JSON.stringify({
				id : city.id,
				name : city.name,
				pttNumber : city.pttNumber,
			});
		}
		
		
		return $http.post('/cities/search/', jsonCity)
	};
	
	temp.editCity = function(city, countryId){
		var jsonCity = JSON.stringify({
			id : city.id,
			name : city.name,
			pttNumber : city.pttNumber,
			country : {
				id : countryId
			}
		});
		
		return $http.put('/cities/update/' + countryId, jsonCity);
	};
	
	return temp;
});