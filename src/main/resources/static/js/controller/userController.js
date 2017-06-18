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
	
	$scope.user = {};
	var ime = {};
	function getUser(){
		loginService.getCurrentlyLoggedUser().then(function(response){
			$scope.user = response.data;
			ime = response.data;
		});
	}
	
	getUser();
	
	$scope.password = {};
	$scope.changePassword = function(){
		getUser();
		$('#changePasswordModal').show();
		$scope.confirmNewPassword = function(password){
			if(password.newFirstTime != password.newSecondTime){
				swal("Greska", "Niste dobro ukucali novu sifru.", "error")
			} else if(password.old == "" || password.newFirstTime == "" || password.newSecondTime ==""){
				swal("Greska", "Sva polja moraju biti popunjena.", "error")
			}
			alert($scope.user);
			username = $scope.user;
			last4Chars = $scope.user.substr($scope.user.length - 4);
			if(last4Chars == ".com"){
				username = username.replace(".com", "tackacom");
			}
			loginService.changePassword(password.old, password.newFirstTime, username).then(function(response){
				if(response.data == true){
					swal("Uspesno", "Uspesno promenjena sifra.", "success")
				} else {
					swal("Neuspesno", "Neuspesno promenjena sifra.", "error")
				}
			});
			$('#changePasswordModal').hide();
		}
	}
	
});