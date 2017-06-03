var exchangeListService = angular.module('bankApp.exchangeListService', []);

exchangeListService.factory('exchangeListService', function($http){
	
	var temp = {};
	
	temp.getAllExchangeLists = function(){
		return $http.get('/exchangeListController/getAllExchangeLists');
	};
	
	temp.deleteExchangeList = function(exchangeList){
		var jsonExchangeList = JSON.stringify({
			id : exchangeList.id
		});
		return $http.post('/exchangeListController/delete/', jsonExchangeList);
	};
	
	temp.addExchangeList = function(exchangeList, date, since, bankId){
		var dT = new Date(date);
		var sT = new Date(since);
		var jsonExchangeList = JSON.stringify({
			date : dT.valueOf(),
			numberOfExchangeList : exchangeList.numberOfExchangeList,
			usedSince : sT.valueOf(),
			bank : {
				id : bankId
			}
		});
		return $http.post('/exchangeListController/add/', jsonExchangeList);
	};
	
	
	temp.editExchangeList = function(exchangeList, date, since, bankId){
		var dT = new Date(date);
		var sT = new Date(since);
		var jsonExchangeList = JSON.stringify({
			id : exchangeList.id,
			date : dT.valueOf(),
			numberOfExchangeList : exchangeList.numberOfExchangeList,
			usedSince : sT.valueOf(),
			bank : {
				id : bankId
			}
		});
		
		return $http.post('/exchangeListController/edit/', jsonExchangeList);
	};
	
	
	temp.getAllBanks = function(){
		return $http.get('/banks');
	}
	
	temp.searchExchangeLists = function(exchangeList){
		return;
	};
	
	
	return temp;
	
});