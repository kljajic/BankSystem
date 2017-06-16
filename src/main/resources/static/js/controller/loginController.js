var loginController = angular.module('bankApp.loginController', []);

loginController.controller('loginController', function($scope, $location, $window,
		$compile,$rootScope, loginService){
	
	$scope.logParams = {};
	
	$scope.login = function(logParams){
		loginService.login(logParams).then(function(response){
			$location.path('/countries');
			$rootScope.logged = true;
		});
	}
	
});