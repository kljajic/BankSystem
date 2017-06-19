var registrationController = angular.module('bankApp.registrationController' ,[]);

registrationController.controller('registrationController', function($scope, $location, $window, $compile, $routeParams,
		registrationService){
	
	$scope.client = {};
	$scope.submitAction = function(client){
		registrationService.registerClient(client).then(function(response){
			swal({ title:"Uspesno ste registrovali klijenta.", type:"success" });
		});
	}
	
	$scope.rollbackAction = function(){
		$scope.client = {};
	}
	
});