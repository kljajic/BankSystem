var analyticalStatementService = angular.module('bankApp.analyticalStatementService', []);

analyticalStatementService.factory('analyticalStatementService', function($http){
	
	var temp = {};
	
	temp.getAllAnalyticalStatements = function(){
		return $http({
			method : 'GET',
			url: '../analyticalStatements',
		});
	}
	
	temp.createAnalyticalStatement = function(cityId, currencyId, dateOfReceipt, currencyDate, analyticalStatement){
		return $http({
			method : 'POST',
			url: '../analyticalStatements/create/' + currencyId +  '/' + cityId + '/' + dateOfReceipt + '/' + currencyDate,
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
				'amount': analyticalStatement.amount,
				'analyticalStatementMode': analyticalStatement.analyticalStatementMode
			}
		});
	}
	
	temp.updateAnalyticalStatement = function(cityId, currencyId, dateOfReceipt, currencyDate, analyticalStatement){
		return $http({
			method : 'PUT',
			url: '../analyticalStatements/update/' + currencyId + '/' + cityId + '/' + dateOfReceipt + '/' + currencyDate,
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
				'amount': analyticalStatement.amount,
				'analyticalStatementMode': analyticalStatement.analyticalStatementMode
			}
		});
	}
	
	temp.searchAnalyticalStatements = function(dailyAccountStatusId, cityId, currencyId, dateOfReceipt, currencyDate, analyticalStatement){
		return $http({
			method : 'PUT',
			url: '../analyticalStatements/search/' + currencyId + '/'  + cityId + '/' + dailyAccountStatusId + '/' + dateOfReceipt + '/' + currencyDate,
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
	
	temp.exportToPdf = function(accountId,startDate,endDate){
		return $http({
			method : 'GET',
			url: '../analyticalStatements/export/' + accountId + '/'  + startDate + '/' + endDate,
			responseType: 'blob'
		});
	}
	
	
	temp.exportToXml = function(accountId,startDate,endDate){
		return $http({
			method : 'GET',
			url: '../analyticalStatements/exportxml/' + accountId + '/'  + startDate + '/' + endDate,
			responseType: 'blob'
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