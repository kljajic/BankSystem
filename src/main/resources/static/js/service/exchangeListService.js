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
	
	temp.searchExchangeLists = function(exchangeList, date, since, bank){
		var dT = new Date(date);
		var sT = new Date(since);
		var number = -1;
		var bname = "";
		if(exchangeList.numberOfExchangeList != undefined || exchangeList.numberOfExchangeList != null){
			number = exchangeList.numberOfExchangeList;
		}
		if(bank != null || bank != undefined){
			if(bank.name != null || bank.name != undefined){
				bname = bank.name;
			}
		} 
		
		var jsonExchangeList = JSON.stringify({
			date : dT.valueOf(),
			numberOfExchangeList : number,
			usedSince : sT.valueOf(),
			bank : {
				name : bname
			}
		});
		return $http.post('/exchangeListController/search/', jsonExchangeList);
	};
	
	return temp;
});