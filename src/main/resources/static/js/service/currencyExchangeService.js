var currencyExchangeService = angular.module('bankApp.currencyExchangeService', []);

currencyExchangeService.factory('currencyExchangeService', function($http){
	var temp = {};
	
	//samo privremeno (GADJA EXCHANGE LISTS... nema servisa na frontu jos..
	temp.getAllExchangeLists = function(){
		return $http.get('/exchangeListController/getAllExchangeLists');
	}
	/////////////////////////
	
	temp.getAllCexes = function(){
		return $http.get('/currencyExchange/getAllCurrencyExchanges');
	}
	
	temp.addNewCex = function(cex){
		return $http.post('/currencyExchange/addNewCE', cex);
	}
	
	temp.editCex = function(cex){
		return $http.post('/currencyExchange/editCE', cex);
	}
	
	temp.deleteCex = function(cex){
		return $http.post('/currencyExchange/deleteCE', cex);
	}
	
	temp.searchCexes = function(buy, middle, sell, exch, prim, accord){
		return $http.post('/currencyExchange/searchCE/'+buy+"/"+middle+"/"+sell+"/"+exch+"/"+prim+"/"+accord);
	}
	
	return temp;
});