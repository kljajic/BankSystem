var RTGSRequestService = angular.module('bankApp.RTGSRequestService', []);

RTGSRequestService.factory('RTGSRequestService', function($http){
	
	var temp = {};

	temp.getAllRTGSRequests = function(){
		return $http.get("/RTGSRequests/getAllRTGSRequests");
	}
	
	return temp;
});