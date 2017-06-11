var analyticalStatementService = angular.module('bankApp.analyticalStatementService', []);

analyticalStatementService.factory('analyticalStatementService', function($http){
	
	var temp = {};
	
	temp.getAllAnalyticalStatements = function(){
		return $http({
			method : 'GET',
			url: '../analyticalStatements',
		});
	}
	
	temp.createAnalyticalStatement = function(cityId, paymentTypeId, currencyId, dateOfReceipt, currencyDate, analyticalStatement){
		return $http({
			method : 'POST',
			url: '../analyticalStatements/create/' + currencyId + '/' + paymentTypeId + '/' + cityId + '/' + dateOfReceipt + '/' + currencyDate,
			data: {
				'originator': analyticalStatement.originator,
				'purpose': analyticalStatement.purpose,
				'recipient': analyticalStatement.recipient,
				'originatorAccount': analyticalStatement.originatorAccount,
				'model': analyticalStatement.model,
				'debitAuthorizationNumber': analyticalStatement.debitAuthorizationNumber,
				'recipientAccount': analyticalStatement.recipientAccount,
				'approvalModel': analyticalStatement.approvalModel,
				'approvalAuthorizationNumber': analyticalStatement.approvalAuthorizationNumber,
				'urgently': analyticalStatement.urgently,
				'amount': analyticalStatement.amount
			}
		});
	}
	
	temp.updateAnalyticalStatement = function(dailyAccountStatusId, cityId, paymentTypeId, currencyId, dateOfReceipt, currencyDate, analyticalStatement){
		return $http({
			method : 'PUT',
			url: '../analyticalStatements/update/' + currencyId + '/' + paymentTypeId + '/' + cityId + '/' + dailyAccountStatusId + '/' + dateOfReceipt + '/' + currencyDate,
			data: {
				'id': analyticalStatement.id,
				'originator': analyticalStatement.originator,
				'purpose': analyticalStatement.purpose,
				'recipient': analyticalStatement.recipient,
				'originatorAccount': analyticalStatement.originatorAccount,
				'model': analyticalStatement.model,
				'debitAuthorizationNumber': analyticalStatement.debitAuthorizationNumber,
				'recipientAccount': analyticalStatement.recipientAccount,
				'approvalModel': analyticalStatement.approvalModel,
				'approvalAuthorizationNumber': analyticalStatement.approvalAuthorizationNumber,
				'urgently': analyticalStatement.urgently,
				'amount': analyticalStatement.amount
			}
		});
	}
	
	temp.searchAnalyticalStatements = function(dailyAccountStatusId, cityId, paymentTypeId, currencyId, dateOfReceipt, currencyDate, analyticalStatement){
		return $http({
			method : 'PUT',
			url: '../analyticalStatements/search/' + currencyId + '/' + paymentTypeId + '/' + cityId + '/' + dailyAccountStatusId + '/' + dateOfReceipt + '/' + currencyDate,
			data: {
				'id': analyticalStatement.id,
				'originator': analyticalStatement.originator,
				'purpose': analyticalStatement.purpose,
				'recipient': analyticalStatement.recipient,
				'originatorAccount': analyticalStatement.originatorAccount,
				'model': analyticalStatement.model,
				'debitAuthorizationNumber': analyticalStatement.debitAuthorizationNumber,
				'recipientAccount': analyticalStatement.recipientAccount,
				'approvalModel': analyticalStatement.approvalModel,
				'approvalAuthorizationNumber': analyticalStatement.approvalAuthorizationNumber,
				'urgently': analyticalStatement.urgently,
				'amount': analyticalStatement.amount
			}
		});
	}
	
	temp.deleteAnalyticalStatement = function(id){
		return $http({
			method : 'DELETE',
			url: '../analyticalStatements/delete/'+id,
			headers: {
				   'Content-Type': 'application/json',
				   'Accept': 'application/json'
			}
		});
	}
	
	temp.getAllDailyAccountStatuses = function(){
		return $http({
			method : 'GET',
			url: '../dailyAccountStatuses',
		});
	}
	
	temp.getAllCities = function(){
		return $http({
			method : 'GET',
			url: '../cities/getCities',
		});
	}
	
	temp.getAllPaymentTypes = function(){
		return $http({
			method : 'GET',
			url: '../paymentTypes',
		});
	}
	
	temp.getAllCurrencies = function(){
		return $http({
			method : 'GET',
			url: '../currencies/getAllCurrencies',
		});
	}
	
	temp.getAnalyticalStatementsByPaymentTypeId = function(id){
		return $http({
			method : 'GET',
			url: '../analyticalStatements/getByPaymentTypeId/'+id,
		});
	}
	
	temp.getAnalyticalStatementsByDailyAccountStatusId = function(id){
		return $http({
			method : 'GET',
			url: '../analyticalStatements/getByDailyAccountStatusId/'+id,
		});
	}
	
	return temp;
	
});