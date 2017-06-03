var paymentTypeController = angular.module('bankApp.paymentTypeController', []);

paymentTypeController.controller('paymentTypeController',['$rootScope','$scope','$location','$http',
		'paymentTypeService',function($rootScope,$scope,$location,$http,paymentTypeService) {
	
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

	$scope.paymentType = {};
	$scope.selectedPaymentType = {};
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
		var n = $scope.paymentTypes.indexOf($scope.selectedPaymenType) + 1;
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
				  countryService.deleteCountry($scope.country.id).then(function(response){
						var index=$scope.countries.indexOf($scope.selectedCountry);
						$scope.previous();
						$scope.countries.splice(index,1);
					});
				  swal("Deleted!", "Your data has been deleted.", "success");
			  } else {
			    swal("Cancelled", "Your have canceled delete operation.", "error");
			  }
			});
	}

	$scope.paymentType = {};
	$scope.submitAction = function(paymentType) {
		if ($scope.action == "addClicked") {
			paymentTypeService.addPaymentType(paymentType).then(function(response) {
				$scope.paymentTypes.push(response.data);
				$scope.selectedPaymentType=response.data;
				$scope.paymentType={};
			});
		} else if ($scope.action == "searchClicked") {
			paymentTypeService.searchPaymentTypes($scope.exchangeList).then(function(response) {
				$scope.paymentTypes = response.data;
			});
		} else {
			if (Object.keys($scope.selectedPaymentType).length > 0) {
				paymentTypeService.editPaymentType(paymentType).then(
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
	
}]);