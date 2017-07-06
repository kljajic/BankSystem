var bankService = angular.module('bankApp.bankService', []);

bankService.factory('bankService', function($http){
	
var temp = {};
	
	temp.getAllBanks = function(){
		return $http({
			method : 'GET',
			url: '../banks',
		});
	}
	
	temp.exportToPdf = function(bankId){
		return $http({
			method : 'GET',
			url: '../banks/export/' + bankId,
		});
	}
	
	temp.createBank = function(bank,countryId){
		return $http({
			method : 'POST',
			url: '../banks/'+ countryId,
			data: {
				'name': bank.name,
				'pib': bank.pib,
				'address': bank.address,
				'email': bank.email,
				'web': bank.web,
				'telephone': bank.telephone,
				'fax': bank.fax,
				'swift': bank.swift,
				'transactionAccount': bank.transactionAccount,
				'banka': bank.banka
			}
		});
	}
	
	temp.updateBank = function(bank,countryId){
		return $http({
			method : 'PUT',
			url: '../banks/'+ countryId,
			data: {
				'id': bank.id,
				'name': bank.name,
				'pib': bank.pib,
				'address': bank.address,
				'email': bank.email,
				'web': bank.web,
				'telephone': bank.telephone,
				'fax': bank.fax,
				'swift': bank.swift,
				'transactionAccount': bank.transactionAccount,
				'banka': bank.banka
			}
		});
	}
	
	temp.deleteBank = function(id){
		return $http({
			method : 'DELETE',
			url: '../banks/'+id,
			headers: {
				   'Content-Type': 'application/json',
				   'Accept': 'application/json'
			}
		});
	}
	
	temp.searchBanks = function(bank,countryId){
		return $http({
			method : 'POST',
			url: '../banks/search/'+ countryId,
			data: {
				'id': bank.id,
				'name': bank.name,
				'pib': bank.pib,
				'address': bank.address,
				'email': bank.email,
				'web': bank.web,
				'telephone': bank.telephone,
				'fax': bank.fax,
				'swift': bank.swift,
				'transactionAccount': bank.transactionAccount,
				'banka': bank.banka
			}
		});
	}
	
	return temp;
});