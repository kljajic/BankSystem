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
	
	temp.logout = function(){
		
		return $http.get("/public/logout");
	}
	
	temp.getLogged = function(){
		
		return $http.get("/public/getLogged");
	}
	
	return temp;
});