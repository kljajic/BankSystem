var legalPersonAccountService = angular.module('bankApp.legalPersonAccountService', []);

legalPersonAccountService.factory('legalPersonAccountService', function($http){
	
	var temp = {};
	
	temp.getAllLegalPersonAccounts = function(){
		return $http.get('/accounts');
	};
	
	temp.deleteLegalPersonAccount = function(legalPersonAccount, transferAccount){
		return $http.post('/accounts/delete/' + legalPersonAccount.id + "/" + transferAccount);
	};
	
	temp.addLegalPersonAccount = function(legalPersonAccount, openingDate, bankId){
		var dT = new Date(openingDate);
		var jsonLegalPersonAccount = JSON.stringify({
			openingDate : dT.valueOf(),
			bank : {
				id : bankId
			}
		});
		return $http.post('/accounts/add/', jsonLegalPersonAccount);
	};
	
	
	temp.editExchangeList = function(legalPersonAccount, openingDate, bankId){
		var dT = new Date(date);
		var sT = new Date(since);
		var jsonLegalPersonAccount = JSON.stringify({
			id : legalPersonAccount.id,
			openingDate : dT.valueOf(),
			bank : {
				id : bankId
			}
		});
		
		return $http.post('/accounts/edit/', jsonLegalPersonAccount);
	};
	
	
	temp.getAllBanks = function(){
		return $http.get('/banks');
	}
	
	temp.searchLegalPersonAccounts = function(legalPersonAccount){
		return;
	};
	
	temp.getAllCurrencies = function(){
		return $http.get('/currencies/getAllCurrencies')
	}
	
	
	return temp;
	
});