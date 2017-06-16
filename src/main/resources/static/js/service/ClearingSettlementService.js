var ClearingSettlementService = angular.module('bankApp.ClearingSettlementService', []);

ClearingSettlementService.factory('ClearingSettlementService', function($http){
	
	var temp = {};
	
	temp.getAllClearingSettlementRequests = function(){
		return $http.get("/clearingSettlementRequests/getAllClearingSettlementRequests");
	}
	
	return temp;
});