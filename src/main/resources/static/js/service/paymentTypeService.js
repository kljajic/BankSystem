var paymentTypeService = angular.module('bankApp.paymentTypeService', []);

paymentTypeService.factory('paymentTypeService', function($http){
	
	var temp = {};
	
	temp.getAllPaymentTypes = function(){
		return $http({
			method : 'GET',
			url: '../paymentTypes'
		});
	}
	
	temp.createPaymentType = function(paymentType){
		return $http({
			method : 'POST',
			url: '../paymentTypes/add',
			data: {
				'paymentTypeName': paymentType.paymentTypeName
			}
		});
	}
	
	temp.updatePaymentType = function(paymentType){
		return $http({
			method : 'PUT',
			url: '../paymentTypes/update',
			data: {
				'id': paymentType.id,
				'paymentTypeName': paymentType.paymentTypeName
			}
		});
	}
	
	temp.deletePaymentType = function(id){
		return $http({
			method : 'DELETE',
			url: '../paymentTypes/delete/'+id,
			headers: {
				   'Content-Type': 'application/json',
				   'Accept': 'application/json'
			}
		});
	}
	
	temp.searchPaymentTypes = function(paymentType){
		return $http({
			method : 'POST',
			url: '../paymentTypes/search',
			data: {
				'paymentTypeName': paymentType.paymentTypeName
			}
		});
	}
	
	return temp;
	
});