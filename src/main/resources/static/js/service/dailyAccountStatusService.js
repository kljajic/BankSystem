var dailyAccountStatusService = angular.module('bankApp.dailyAccountStatusService', []);

dailyAccountStatusService.factory('dailyAccountStatusService', function($http){
	
	var temp = {};
	
	temp.getAllDailyAccountStatuses = function(){
		return $http({
			method : 'GET',
			url: '../dailyAccountStatuses',
		});
	}
	
	temp.createDailyAccountStatus = function(dailyAccountStatus, accountId, date){
		return $http({
			method : 'POST',
			url: '../dailyAccountStatuses/add/' + accountId + '/' + date,
			data: {
				'previousAmount': dailyAccountStatus.previousAmount,
				'transferInFavor': dailyAccountStatus.transferInFavor,
				'numberOfChanges': dailyAccountStatus.numberOfChanges,
				'transferExpenses': dailyAccountStatus.transferExpenses,
				'currentAmount': dailyAccountStatus.currentAmount
			}
		});
	}
	
	temp.updateDailyAccountStatus = function(dailyAccountStatus, accountId, date){
		return $http({
			method : 'PUT',
			url: '../dailyAccountStatuses/update/' + accountId + '/'+ date,
			data: {
				'id': dailyAccountStatus.id,
				'previousAmount': dailyAccountStatus.previousAmount,
				'transferInFavor': dailyAccountStatus.transferInFavor,
				'numberOfChanges': dailyAccountStatus.numberOfChanges,
				'transferExpenses': dailyAccountStatus.transferExpenses,
				'currentAmount': dailyAccountStatus.currentAmount
			}
		});
	}
	
	temp.deleteDailyAccountStatus = function(id){
		return $http({
			method : 'DELETE',
			url: '../dailyAccountStatuses/delete/' + id,
			headers: {
				   'Content-Type': 'application/json',
				   'Accept': 'application/json'
			}
		});
	}
	
	temp.searchDailyAccountStatuses = function(dailyAccountStatus, accountId, date){
		return $http({
			method : 'POST',
			url: '../dailyAccountStatuses/search/' + accountId + '/' + date,
			data: {
				'id': dailyAccountStatus.id,
				'previousAmount': dailyAccountStatus.previousAmount,
				'transferInFavor': dailyAccountStatus.transferInFavor,
				'numberOfChanges': dailyAccountStatus.numberOfChanges,
				'transferExpenses': dailyAccountStatus.transferExpenses,
				'currentAmount': dailyAccountStatus.currentAmount
			}
		});
	}
	
	temp.getAllAccounts = function(){
		return $http({
			method : 'GET',
			url: '../accounts'
		});
	}
	
	return temp;
	
});