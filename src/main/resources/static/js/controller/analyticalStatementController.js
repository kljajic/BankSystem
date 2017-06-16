var analyticalStatementController = angular.module('bankApp.analyticalStatementController', []);

analyticalStatementController.controller('analyticalStatementController',['$rootScope','$scope','$location','$http',
		'analyticalStatementService', '$routeParams',function($rootScope,$scope,$location,$http,analyticalStatementService,$routeParams, $window) {
	
	$scope.action = 'searchClicked';
	$scope.analyticalStatement = {};
	$scope.analyticalStatements = [];
	$scope.dailyAccountStatuses = [];
	$scope.cities = [];
	$scope.currencies = [];
	$scope.dailyAccountStatusOptions = "dailyAccountStatuse.id for dailyAccountStatuse in dailyAccountStatuses";
	$scope.cityOptions = "city.id for city in cities";
	$scope.currencyOptions = "currency.id for currency in currencies";
	$scope.selectedAnalyticalStatement = {};
	$scope.selectedDailyAccountStatus = {};
	$scope.selectedCity = {};
	$scope.selectedCurrency = {};
	$scope.mode = {};
	$scope.mode.current = "Rezim pretrage";
	$scope.dateOfReceipt = new Date();
	$scope.currencyDate = new Date();
	$scope.nextDailyAccountStatus = false;
	$scope.nextCity = false;
	
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
	
	
	analyticalStatementService.getAllCurrencies().then(function(response) {
		if (response.data != null) {
			$scope.currencies = response.data;
		}
	});
	
	$scope.getAllAnalyticalStatements = function() {
		analyticalStatementService.getAllAnalyticalStatements().then(function(data) {
			if($routeParams.cityId > 0){
				var temp = [];
				for(var i = 0; i < data.data.length; i++){
					if(data.data[i].placeOfAcceptance.id == $routeParams.cityId){
						temp.push(data.data[i]);
					}
				}
				$scope.analyticalStatements = temp;
				$scope.selectedCity = $scope.findCityFromNext($routeParams.cityId);
				$scope.nextCity = true;
			} else if($rootScope.nextDailyAccountStatus != null && $rootScope.nextDailyAccountStatus != undefined) {
				analyticalStatementService.getAnalyticalStatementsByDailyAccountStatusId($rootScope.nextDailyAccountStatus.id).then(function(response){
					$scope.analyticalStatements = response.data;
				});
				$scope.dailyAccountStatuses = [];
				$scope.dailyAccountStatuses.push($rootScope.nextDailyAccountStatus);
				$scope.selectedDailyAccountStatus = $scope.dailyAccountStatuses[0];
				$scope.nextDailyAccountStatus = true;
			} else {
				if (data.data != null) {
					$scope.analyticalStatements = data.data;
					$('selectField').removeAttr('disabled');
				}
			}
		});
	}
	
	$scope.findCityFromNext = function(id){
		for(var i = 0;i < $scope.cities.length;i++){
			if($scope.cities[i].id == id){
				return $scope.cities[i];
			}
		}
	}
	
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

	$scope.selectedDailyAccountStatus = {};
	$scope.selectedCity = {};
	$scope.selectedCurrency = {};
	$scope.submitAction = function(analyticalStatement) {
			var dailyAccountStatusId = -1;
			if($scope.selectedDailyAccountStatus != null && $scope.selectedDailyAccountStatus.id != undefined)
				dailyAccountStatusId = $scope.selectedDailyAccountStatus.id;
			var cityId = -1;
			if($scope.selectedCity != null && $scope.selectedCity.id != undefined)
				cityId = $scope.selectedCity.id;
			var currencyId = -1;
			if($scope.selectedCurrency != null && $scope.selectedCurrency.id != undefined)
				currencyId = $scope.selectedCurrency.id;
			if($scope.dateOfReceipt == undefined)
				$scope.dateOfReceipt = null;
			if($scope.currencyDate == undefined)
				$scope.currencyDate = null;
			analyticalStatementService.searchAnalyticalStatements(dailyAccountStatusId, cityId, currencyId, $scope.dateOfReceipt, $scope.currencyDate, analyticalStatement).then(function(response) {
				$scope.analyticalStatements = response.data;
			});
	}
	
	$scope.rollbackAction = function() {
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