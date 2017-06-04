var legalPersonAccountService = angular.module('bankApp.legalPersonAccountService', []);

legalPersonAccountService.factory('legalPersonAccountService', function($http){
	
	var temp = {};
	
	temp.getAllLegalPersonAccounts = function(){
		return $http.get('/accounts');
	};
	
	temp.deleteLegalPersonAccount = function(legalPersonAccount, transferAccount){
		return $http.post('/accounts/delete/' + legalPersonAccount.id + "/" + transferAccount);
	};
	
	temp.getAllClients = function(){
		return $http.get('/clients');
	};
	
	temp.addLegalPersonAccount = function(legalPersonAccount, openingDate, bankId, clientId, currencyId){
		var dT = new Date(openingDate);
		var jsonLegalPersonAccount = JSON.stringify({
			accountNumber : legalPersonAccount.accountNumber,
			openingDate : dT.valueOf(),
			bank : {
				id : bankId
			},
			client : {
				id : clientId
			},
			currency : {
				id : currencyId
			},
			active : true
		});
		return $http.post('/accounts/', jsonLegalPersonAccount);
	};
	
	
	temp.editLegalPersonAccount = function(legalPersonAccount, status, openingDate, bankId, currencyId, clientId){
		var dT = new Date(openingDate);
		dT = dT.getTime();
		var active = true;
		if(status == "Aktivan"){
			active = true;
		} else if (status == "Neaktivan"){
			active = false;
		} else {
			active = legalPersonAccount.active;
		}
		var jsonLegalPersonAccount = JSON.stringify({
			id : legalPersonAccount.id,
			openingDate : dT,
			active : active,
			accountNumber : legalPersonAccount.accountNumber,
			bank : {
				id : bankId
			},
			client : {
				id : clientId
			},
			currency : {
				id : currencyId
			}
		});
		
		return $http.put('/accounts/edit/', jsonLegalPersonAccount);
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