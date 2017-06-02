var currencyService = angular.module('bankApp.currencyService' , []);

currencyService.factory('currencyService', function($http){
	var temp = {};
	
	temp.getAllCurrencies = function(){
		return $http.get('/currencies/getAllCurrencies')
	}
	
	temp.addNewCurrency = function(currency){
		return $http.post("/currencies/addNewCurrency", currency);
	}
	
	temp.editCurrency = function(currency){
		return $http.post("/currencies/editCurrency", currency);
	}
	
	temp.deleteCurrency = function(currency){
		return $http.post("/currencies/deleteCurrency", currency);
	}
	
	temp.searchCurrencies = function(code, name, country, domicilna){
		return $http.post("/currencies/searchCurrencies/"+code+"/"+name+"/"+country+"/"+domicilna);
	}
	
	return temp;
});