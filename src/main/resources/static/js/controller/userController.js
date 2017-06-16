var userController = angular.module('bankApp.userController' ,[]);

userController.controller('userController', function($scope, $rootScope,$location, $window, $compile, $routeParams,
		loginService){
	
	loginService.getLogged(). then(function(response){
		$rootScope.logged = response.data;
	});
	
	$scope.logout = function(){
		loginService.logout().then(function(response){
			$location.path("/");
			$rootScope.logged = null;
		});
	}
	
});