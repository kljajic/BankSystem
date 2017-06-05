var analyticalStatementController = angular.module('bankApp.analyticalStatementController', []);

analyticalStatementController.controller('analyticalStatementController',['$rootScope','$scope','$location','$http',
		'analyticalStatementService', '$routeParams',function($rootScope,$scope,$location,$http,analyticalStatementService,$routeParams, $window) {
	
	$scope.action = {};
	$scope.analyticalStatement = [];
	$scope.analyticalStatements = {};
	$scope.dailyAccountStatuses = [];
	$scope.cities = [];
	$scope.paymentTypes = [];
	$scope.currencies = [];
	$scope.dailyAccountStatusOptions = "dailyAccountStatuse.id for dailyAccountStatuse in dailyAccountStatuses";
	$scope.cityOptions = "city.id for city in cities";
	$scope.paymentTypeOptions = "paymentType.id for paymentType in paymentTypes";
	$scope.currencyOptions = "currency.id for currency in currencies";
	$scope.selectedAnalyticalStatement = {};
	$scope.selectedDailyAccountStatus = {};
	$scope.selectedCity = {};
	$scope.selectedPaymentType = {};
	$scope.selectedCurrency = {};
	$scope.mode = {};
	$scope.mode.current = "Rezim izmene";
	$scope.dateOfReceipt = new Date();
	$scope.currencyDate = new Date();
	
	$scope.getAllAnalyticalStatements = function() {
		analyticalStatementService.getAllAnalyticalStatements().then(function(data) {
			if($routeParams.param > 0){
				var temp = [];
				for(var i = 0; i < data.data.length; i++){
					if(data.data[i].placeOfAcceptance.id == $routeParams.param){
						temp.push(data.data[i]);
					}
				}
				$scope.analyticalStatements = temp;
				$scope.selectedCity = $scope.cities[$routeParams.param-1];
			} else {
				if (data.data != null) {
					$scope.analyticalStatements = data.data;
					$('selectField').removeAttr('disabled');
				}
			}
		});
	}
	
	analyticalStatementService.getAllDailyAccountStatuses().then(function(response) {
		if (response.data != null) {
			$scope.dailyAccountStatuses = response.data;
		}
	});
	
	analyticalStatementService.getAllCities().then(function(response) {
		if (response.data != null) {
			$scope.cities = response.data;
		}
	});
	
	analyticalStatementService.getAllPaymentTypes().then(function(response) {
		if (response.data != null) {
			$scope.paymentTypes = response.data;
		}
	});
	
	analyticalStatementService.getAllCurrencies().then(function(response) {
		if (response.data != null) {
			$scope.currencies = response.data;
		}
	});
	
	$scope.getAllAnalyticalStatements();

	$scope.setSelected = function(analyticalStatement) {
		if ($scope.selectedAnalyticalStatement == analyticalStatement) {
			$scope.selectedAnalyticalStatement = {};
		} else {
			$scope.setParameters(analyticalStatement);
		}
	}

	$scope.firstClicked = function() {
		$scope.action = "firstClicked";
		$scope.setParameters($scope.analyticalStatements[0]);
	}
	
	$scope.lastClicked = function() {
		$scope.action = "lastClicked";
		$scope.setParameters($scope.analyticalStatements[$scope.analyticalStatements.length - 1]);
	}
	
	$scope.nextClicked = function() {
		$scope.action = "nextClicked";
		var n = $scope.analyticalStatements.indexOf($scope.selectedAnalyticalStatement) + 1;
		n = n % $scope.analyticalStatements.length;
		$scope.setParameters($scope.analyticalStatements[n]);
	}
	
	$scope.prevClicked = function() {
		$scope.action = "prevClicked";
		var n = $scope.analyticalStatements.indexOf($scope.selectedAnalyticalStatement);
		if (n == 0 || n == -1) {
			n = $scope.analyticalStatements.length;
		}
		$scope.setParameters($scope.analyticalStatements[n - 1]);
	}
	
	$scope.addClicked = function() {
		$scope.action = "addClicked";
		$scope.mode.current = "Rezim dodavanja";
		$scope.analyticalStatement = {};
		$scope.selectedAnalyticalStatement = {};
		$scope.selectedDailyAccountStatus = {};
		$scope.selectedCity = {};
		$scope.selectedPaymentType = {};
		$scope.selectedCurrency = {};
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
				  analyticalStatementService.deleteAnalyticalStatement($scope.selectedAnalyticalStatement.id).then(function(response){
						var index = $scope.analyticalStatements.indexOf($scope.selectedAnalyticalStatement);
						$scope.analyticalStatements.splice(index,1);
					});
				  swal("Deleted!", "Your data has been deleted.", "success");
			  } else {
			    swal("Cancelled", "Your have canceled delete operation.", "error");
			  }
			});
	}
																			$scope.selectedDailyAccountStatus = {};
																			$scope.selectedCity = {};
																			$scope.selectedPaymentType = {};
																			$scope.selectedCurrency = {};
	$scope.submitAction = function(analyticalStatement) {
		if ($scope.action == "addClicked") {
			analyticalStatementService.createAnalyticalStatement($scope.selectedDailyAccountStatus.id, $scope.selectedCity.id, $scope.selectedPaymentType.id, $scope.selectedCurrency.id, $scope.dateOfReceipt, $scope.currencyDate, analyticalStatement).then(function(response) {
				$scope.analyticalStatements.push(response.data);
				$scope.selectedAnalyticalStatement = response.data;
				$scope.analyticalStatement = response.data;
			});
		} else if ($scope.action == "searchClicked") {
			analyticalStatementService.searchAnalyticalStatement($scope.selectedDailyAccountStatus.id, $scope.selectedCity.id, $scope.selectedPaymentType.id, $scope.selectedCurrency.id, $scope.dateOfReceipt, $scope.currencyDate, analyticalStatement).then(function(response) {
				$scope.analyticalStatements = response.data;
			});
		} else {
			if (Object.keys($scope.selectedAnalyticalStatement).length > 0) {
				analyticalStatementService.updateAnalyticalStatement($scope.selectedDailyAccountStatus.id, $scope.selectedCity.id, $scope.selectedPaymentType.id, $scope.selectedCurrency.id, $scope.dateOfReceipt, $scope.currencyDate, analyticalStatement).then(
						function(response) {
							var index = $scope.analyticalStatements.indexOf($scope.selectedAnalyticalStatement);
							$scope.analyticalStatements.splice(index,1);
							$scope.analyticalStatements.push(response.data);
							$scope.selectedAnalyticalStatement = response.data;
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
		$scope.analyticalStatement = {};
		$scope.getAllAnalyticalStatements();
	}
	
	$scope.setParameters = function(analyticalStatement){
		$scope.selectedAnalyticalStatement = analyticalStatement;
		$scope.analyticalStatement = angular.copy(analyticalStatement);
		for(i=0;i<$scope.dailyAccountStatuses.length;i++){
			if($scope.dailyAccountStatuses[i].id == analyticalStatement.dailyAccountStatus.id){
				$scope.selectedDailyAccountStatus = $scope.dailyAccountStatuses[i];
				break;  
			}
		}
		for(i=0;i<$scope.cities.length;i++){
			if($scope.cities[i].id == analyticalStatement.placeOfAcceptance.id){
				$scope.selectedCity = $scope.cities[i];
				break;  
			}
		}
		for(i=0;i<$scope.paymentTypes.length;i++){
			if($scope.paymentTypes[i].id == analyticalStatement.paymentType.id){
				$scope.selectedPaymentType = $scope.paymentTypes[i];
				break;  
			}
		}
		for(i=0;i<$scope.currencies.length;i++){
			if($scope.currencies[i].id == analyticalStatement.currency.id){
				$scope.selectedCurrency = $scope.currencies[i];
				break;  
			}
		}
		$('#dateOfReceipt').val($scope.getDateForPicker(analyticalStatement.dateOfReceipt, 'YYYY-MM-DD'));
		$('#currencyDate').val($scope.getDateForPicker(analyticalStatement.currencyDate, 'YYYY-MM-DD'));
		$scope.dateOfReceipt = new Date($('#dateOfReceipt').val());
		$scope.currencyDate = new Date($('#currencyDate').val());
	}
	
	$scope.getDateForPicker = function(date, format){
		var check = moment(date, format);
		return check.format('YYYY')+"-"+check.format('MM')+"-"+check.format('DD');
	}
	
	$scope.showDailyAccountStatuses = function(){
		$("#dailyAccountStatusModal").modal('show');
	}
	
	$scope.selectedModalDailyAccountStatus = {};
	$scope.setModalSelectedDailyAccountStatus = function(dailyAccountStatus){
		$scope.selectedModalDailyAccountStatus = dailyAccountStatus;
	}
	
	$scope.confirmDailyAccountStatus = function(){
		$scope.selectedDailyAccountStatus = $scope.selectedModalDailyAccountStatus;
		$("#dailyAccountStatusModal").modal('hide');
		$scope.selectedModalDailyAccountStatus = {};
	}
	
	$scope.showCities = function(){
		$("#cityModal").modal('show');
	}
	
	$scope.selectedModalCity = {};
	$scope.setModalSelectedCity = function(city){
		$scope.selectedModalCity = city;
	}
	
	$scope.confirmCity = function(){
		$scope.selectedCity = $scope.selectedModalCity;
		$("#cityModal").modal('hide');
		$scope.selectedModalCity = {};
	}
	
	$scope.showPaymentTypes = function(){
		$("#paymentTypeModal").modal('show');
	}
	
	$scope.selectedModalPaymentType = {};
	$scope.setModalSelectedPaymentType = function(paymentType){
		$scope.selectedModalPaymentType = paymentType;
	}
	
	$scope.confirmPaymentType = function(){
		$scope.selectedPaymentType = $scope.selectedModalPaymentType;
		$("#paymentTypeModal").modal('hide');
		$scope.selectedModalPaymentType = {};
	}
	
	$scope.showCurrencies = function(){
		$("#currencyModal").modal('show');
	}
	
	$scope.selectedModalCurrency = {};
	$scope.setModalSelectedCurrency = function(currency){
		$scope.selectedModalCurrency = currency;
	}
	
	$scope.confirmCurrency = function(){
		$scope.selectedCurrency = $scope.selectedModalCurrency;
		$("#currencyModal").modal('hide');
		$scope.selectedModalCurrency = {};
	}
	
	$scope.previousForm = function(){
		$("#previousFormsModal").modal('show');
	}
	
	$scope.selectedModalPrevousForm = {};
	$scope.setModalSelectedPreviousForm = function(previousForm){
		$scope.selectedModalPrevousForm = previousForm;
	}
	
	$scope.confirmPreviousForm = function(){
		$("#previousFormsModal").modal('hide');
		$location.path('/'+$scope.selectedModalPrevousForm);
		$scope.selectedModalPrevousForm = {};
	}
	
	$scope.nextForm = function(){
		sweetAlert("Oops...", "There are no next forms for this table!", "error");
	}
	
}]);