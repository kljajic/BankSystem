var registrationService = angular.module('bankApp.registrationService' , []);

registrationService.factory('registrationService', function($http){
	var temp = {};
	
	temp.registerClient = function(client){
		return $http.post("/clients/registerClient", client);
	}
	
	return temp;
});