var countryService = angular.module('bankApp.countryService', []);

countryService.factory('countryService', function($http){
	
	var temp = {};
	
	temp.getAllCountries = function(){
		return $http.get('/countries/getAllCountries');
	};
	
	temp.deleteCountry = function(country){
		return $http.post('/countries/deleteCountry', country);
	};
	
	temp.addNewCountry = function(name){
		return $http.post('/countries/addNewCountry/'+name);
	};
	
	temp.searchCountries = function(name){
		return $http.post('/countries/searchCountries/'+name)
	};
	
	temp.editCountry = function(id, name){
		return $http.post('/countries/editCountry/'+id+"/"+name);
	};
	
	return temp;
});