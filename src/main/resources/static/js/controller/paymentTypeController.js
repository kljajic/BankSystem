var paymentTypeController = angular.module('bankApp.paymentTypeController', []);

paymentTypeController.controller('paymentTypeController',['$rootScope','$scope','$location','$http',
		'paymentTypeService',function($rootScope,$scope,$location,$http,paymentTypeService, ngNotifay) {
	
	$scope.action = {};
	$scope.paymentTypes = [];
	$scope.paymentType = {};
	$scope.selectedPaymentType = {};
	$scope.mode = {};
	$scope.mode.current = "Rezim izmene";
	
	$scope.getAllPaymentTypes = function() {
		paymentTypeService.getAllPaymentTypes().then(function(response) {
			if (response.data != null) {
				$scope.paymentTypes = response.data;
			}
		});
	}
	
	$scope.getAllPaymentTypes();

	$scope.setSelected = function(paymentType) {
		if ($scope.selectedPaymentType == paymentType) {
			$scope.selectedPaymentType = {};
		} else {
			$scope.setParameters(paymentType);
		}
	}

	$scope.firstClicked = function() {
		$scope.action = "firstClicked";
		$scope.setParameters($scope.paymentTypes[0]);
	}
	
	$scope.lastClicked = function() {
		$scope.action = "lastClicked";
		$scope.setParameters($scope.paymentTypes[$scope.paymentTypes.length - 1]);
	}
	
	$scope.nextClicked = function() {
		$scope.action = "nextClicked";
		var n = $scope.paymentTypes.indexOf($scope.selectedPaymentType) + 1;
		n = n % $scope.paymentTypes.length;
		$scope.setParameters($scope.paymentTypes[n]);
	}
	
	$scope.prevClicked = function() {
		$scope.action = "prevClicked";
		var n = $scope.paymentTypes.indexOf($scope.selectedPaymentType);
		if (n == 0 || n == -1) {
			n = $scope.paymentTypes.length;
		}
		$scope.setParameters($scope.paymentTypes[n - 1]);
	}
	
	$scope.addClicked = function() {
		$scope.action = "addClicked";
		$scope.mode.current = "Rezim dodavanja";
		$scope.paymentType={};
		$scope.selectedPaymentType={};
	}
	
	$scope.searchClicked = function() {
		$scope.action = "searchClicked";
		$scope.mode.current = "Rezim pretrage";
	}

	$scope.removeClicked = function(paymentType) {
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
				  paymentTypeService.deletePaymentType($scope.selectedPaymentType.id).then(function(response){
						var index=$scope.paymentTypes.indexOf($scope.selectedPaymentType);
						$scope.prevClicked();
						$scope.paymentTypes.splice(index,1);
					});
				  swal("Deleted!", "Your data has been deleted.", "success");
			  } else {
			    swal("Cancelled", "Your have canceled delete operation.", "error");
			  }
			});
	}

	$scope.submitAction = function(paymentType) {
		if ($scope.action == "addClicked") {
			paymentTypeService.createPaymentType(paymentType).then(function(response) {
				$scope.paymentTypes.push(response.data);
				$scope.selectedPaymentType=response.data;
				$scope.paymentType=response.data;
			});
		} else if ($scope.action == "searchClicked") {
			paymentTypeService.searchPaymentTypes($scope.paymentType).then(function(response) {
				$scope.paymentTypes = response.data;
			});
		} else {
			if (Object.keys($scope.selectedPaymentType).length > 0) {
				paymentTypeService.updatePaymentType(paymentType).then(
						function(response) {
							var index=$scope.paymentTypes.indexOf($scope.selectedPaymentType);
							$scope.paymentTypes.splice(index,1);
							$scope.paymentTypes.push(response.data);
							$scope.selectedPaymentType=response.data;
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
		$scope.paymentType = {};
		$scope.getAllPaymentTypes();
	}
	
	$scope.setParameters = function(paymentType){
		$scope.selectedPaymentType = paymentType;
		$scope.paymentType = angular.copy(paymentType);
	}
	
	$scope.previousForm = function(){
		sweetAlert("Oops...", "There are no previous forms for this table!", "error");
	}
	
	$scope.nextForm = function(){
		
	}
	
}]);