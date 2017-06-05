var loginService = angular.module('bankApp.loginService', []);

loginService.factory('loginService', function($http){
	
	var temp = {};
	
	temp.login = function(logParams){
		
		var jsonLogParams = JSON.stringify({
			email : logParams.email,
			password : logParams.password
		});
		
		return $http.post("/public/login", jsonLogParams);
	}
	
	return temp;
});