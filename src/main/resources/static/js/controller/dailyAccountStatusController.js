var dailyAccountStatusController = angular.module('bankApp.dailyAccountStatusController', []);

dailyAccountStatusController.controller('dailyAccountStatusController',['$rootScope','$scope','$location','$http',
		'dailyAccountStatusService', 'ngNotify', '$routeParams', '$window',function($rootScope,$scope,$location,$http,dailyAccountStatusService, ngNotify, $routeParams, $window) {
	
	$scope.action = {};
	$scope.dailyAccountStatuses = [];
	$scope.dailyAccountStatus = {};
	$scope.accounts = [];
	$scope.accountOptions = "account.id for account in accounts";
	$scope.selectedDailyAccountStatus = {};
	$scope.selectedAccount = {};
	$scope.mode = {};
	$scope.mode.current = "Rezim izmene";
	$scope.date = new Date();
	
	dailyAccountStatusService.getAllAccounts().then(function(response) {
		if (response.data != null) {
			$scope.accounts = response.data;
		}
	});
	
	$scope.initial = function(){
		dailyAccountStatusService.getAllAccounts().then(function(response) {
			if (response.data != null) {
				$scope.accounts = response.data;
			}
		});
	}
	$scope.initial();
	
	$scope.getAllDailyAccountStatuses = function() {
		dailyAccountStatusService.getAllDailyAccountStatuses().then(function(data) {
			if($routeParams.paramAccount > 0){
				var temp = [];
				for(var i = 0; i < data.data.length; i++){
					if(data.data[i].account.id == $routeParams.paramAccount){
						temp.push(data.data[i]);
					}
				}
				$scope.dailyAccountStatuses = temp;
				$('#selectField').prop('disabled', 'disabled');
				$scope.selectedAccount = $scope.findAccount($routeParams.paramAccount);
				
			} else {
				if (data.data != null) {
					$scope.dailyAccountStatuses = data.data;
				}
			}
		});
	}
	
	$scope.findAccount = function(id){
		for(var i = 0;i < $scope.accounts.length;i++){
			if($scope.accounts[i].id == id){
				return $scope.accounts[i];
			}
		}
	}
	
	$scope.getAllDailyAccountStatuses();

	$scope.setSelected = function(dailyAccountStatus) {
		if ($scope.selectedDailyAccountStatus == dailyAccountStatus) {
			$scope.selectedDailyAccountStatus = {};
		} else {
			$scope.setParameters(dailyAccountStatus);
		}
	}

	$scope.firstClicked = function() {
		$scope.action = "firstClicked";
		$scope.setParameters($scope.dailyAccountStatuses[0]);
	}
	
	$scope.lastClicked = function() {
		$scope.action = "lastClicked";
		$scope.setParameters($scope.dailyAccountStatuses[$scope.dailyAccountStatuses.length - 1]);
	}
	
	$scope.nextClicked = function() {
		$scope.action = "nextClicked";
		var n = $scope.dailyAccountStatuses.indexOf($scope.selectedDailyAccountStatus) + 1;
		n = n % $scope.dailyAccountStatuses.length;
		$scope.setParameters($scope.dailyAccountStatuses[n]);
	}
	
	$scope.prevClicked = function() {
		$scope.action = "prevClicked";
		var n = $scope.dailyAccountStatuses.indexOf($scope.selectedDailyAccountStatus);
		if (n == 0 || n == -1) {
			n = $scope.dailyAccountStatuses.length;
		}
		$scope.setParameters($scope.dailyAccountStatuses[n - 1]);
	}
	
	$scope.addClicked = function() {
		$scope.action = "addClicked";
		$scope.mode.current = "Rezim dodavanja";
		$scope.dailyAccountStatus = {};
		$scope.selectedDailyAccountStatus = {};
		$scope.selectedAccount = {};
		$scope.date = new Date();
	}
	
	$scope.searchClicked = function() {
		$scope.action = "searchClicked";
		$scope.mode.current = "Rezim pretrage";
	}

	$scope.removeClicked = function() {
		swal({
			  title: "Are you sure?",
			  text: "You will not be able to recover this!",
			  type: "warning",
			  showCancelButton: true,
			  confirmButtonColor: "#DD6B55",
			  confirmButtonText: "Yes, delete it!",
			  cancelButtonText: "Cancel!",
			  closeOnConfirm: false,
			  closeOnCancel: false
			},
			function(isConfirm){
			  if (isConfirm) {
				  dailyAccountStatusService.deleteDailyAccountStatus($scope.selectedDailyAccountStatus.id).then(function(response){
						var index = $scope.dailyAccountStatuses.indexOf($scope.selectedDailyAccountStatus);
						$scope.dailyAccountStatuses.splice(index,1);
					});
				  swal("Deleted!", "Your data has been deleted.", "success");
			  } else {
			    swal("Cancelled", "Your have canceled delete operation.", "error");
			  }
			});
	}

	$scope.submitAction = function(dailyAccountStatus) {
		if ($scope.action == "addClicked") {
			dailyAccountStatusService.createDailyAccountStatus(dailyAccountStatus, $scope.selectedAccount.id, $scope.date).then(function(response) {
				$scope.dailyAccountStatuses.push(response.data);
				$scope.selectedDailyAccountStatus = response.data;
				$scope.dailyAccountStatus = response.data;
			});
		} else if ($scope.action == "searchClicked") {
			dailyAccountStatusService.searchDailyAccountStatus(dailyAccountStatus, $scope.selectedAccount.id, $scope.date).then(function(response) {
				$scope.dailyAccountStatuses = response.data;
			});
		} else {
			if (Object.keys($scope.selectedDailyAccountStatus).length > 0) {
				dailyAccountStatusService.updateDailyAccountStatus(dailyAccountStatus, $scope.selectedAccount.id, $scope.date).then(
						function(response) {
							var index = $scope.dailyAccountStatuses.indexOf($scope.selectedDailyAccountStatus);
							$scope.dailyAccountStatuses.splice(index,1);
							$scope.dailyAccountStatuses.push(response.data);
							$scope.selectedDailyAccountStatus = response.data;
						});
			} else {
				ngNotify.set('Select payment type first!' , {
					type : 'error',
					duration: 3000,
					theme: 'pitchy'
				});
			}
		}
	}
	
	$scope.rollbackAction = function() {
		$scope.action = "editClicked"
		$scope.mode.current = "Rezim izmene";
		$scope.dailyAccountStatus = {};
		$scope.getAllPaymentTypes();
	}
	
	$scope.setParameters = function(dailyAccountStatus){
		$scope.selectedDailyAccountStatus = dailyAccountStatus;
		$scope.dailyAccountStatus = angular.copy(dailyAccountStatus);
		for(i=0;i<$scope.accounts.length;i++){
			if($scope.accounts[i].id == dailyAccountStatus.account.id){
				$scope.selectedAccount = $scope.accounts[i];
				break;  
			}
		}
		$('#date').val($scope.getDateForPicker(dailyAccountStatus.date, 'YYYY-MM-DD'));
		$scope.date = new Date($('#date').val());
	}
	
	$scope.getDateForPicker = function(date, format){
		var check = moment(date, format);
		return check.format('YYYY')+"-"+check.format('MM')+"-"+check.format('DD');
	}
	
	$scope.showAccounts = function(){
		$("#accountsModal").modal('show');
	}
	
	$scope.selectedModalAccount = {};
	$scope.setModalSelectedAccount = function(account){
		$scope.selectedModalAccount = account;
	}
	
	$scope.confirmAccount = function(){
		$scope.selectedAccount = $scope.selectedModalAccount;
		$("#accountsModal").modal('hide');
		$scope.selectedModalAccount = {};
	}
	
	$scope.previousForm = function(){
		$location.path('/accounts');
	}
	
	$scope.nextForm = function(){
		
	}
	
}]);