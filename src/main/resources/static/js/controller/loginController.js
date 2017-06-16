var loginController = angular.module('bankApp.loginController', []);

loginController.controller('loginController', function($scope, $location, $window,
		$compile, loginService){
	
	$scope.logParams = {};
	
	$scope.login = function(logParams){
		loginService.login(logParams).then(function(response){
			$location.path('/countries');
		});
	}
	
});