var legalPersonAccountController = angular.module('bankApp.legalPersonAccountController',
		[]);

legalPersonAccountController.controller('legalPersonAccountController', function($scope,
		$location, $window, $compile, legalPersonAccountService, ngNotify, exchangeListService) {
	
	$scope.action = {};
	$scope.legalPersonAccounts = [];
	$scope.banks = [];
	$scope.bankOptions = "bank.name for bank in banks";
	$scope.legalPersonAccount= {};
	$scope.selectedBank = {};
	$scope.currencies = [];
	$scope.currencyOptions = "currency.officialCode for currency in currencies";
	$scope.selectedCurrency = {};
	
	$scope.mode = {};
	$scope.mode.current = "Rezim izmene";
	

	$scope.getAllLegalPersonAccounts = function() {
		legalPersonAccountService.getAllLegalPersonAccounts().then(function(data) {
			if (data.data != null) {
				$scope.legalPersonAccounts = data.data;
				for(var i = 0;i < $scope.legalPersonAccounts.length;i++){
					$scope.legalPersonAccounts[i].openingDateView = moment(new Date($scope.legalPersonAccounts[i].openingDate)).format("YYYY MM DD");
					if($scope.legalPersonAccounts[i].active == true){
						$scope.legalPersonAccounts[i].activeView = "AKTIVAN";
					} else {
						$scope.legalPersonAccounts[i].activeView = "NEAKTIVAN";
					}
				}
			}
		});
	}
	
	$scope.getAllBanks = function() {
		legalPersonAccountService.getAllBanks().then(function(data) {
			if (data.data != null) {
				$scope.banks = data.data;
			}
		});
	}
	
	$scope.getAllCurrencies = function() {
		legalPersonAccountService.getAllCurrencies().then(function(data) {
			if (data.data != null) {
				$scope.currencies = data.data;
			}
		});
	}
	
	
	$scope.getAllLegalPersonAccounts();
	$scope.getAllCurrencies();
	$scope.getAllBanks();

	$scope.legalPersonAccount = {};
	$scope.selectedLegalPersonAccount = {};
	$scope.setSelected = function(legalPersonAccount) {
		
		if ($scope.selectedLegalPersonAccount == legalPersonAccount) {
			$scope.LegalPersonAccount = {};
		} else {
			$scope.setParameters(legalPersonAccount);
		}
	}

	$scope.firstClicked = function() {
		$scope.action = "firstClicked";
		$scope.setParameters($scope.legalPersonAccounts[0]);
	}
	$scope.lastClicked = function() {
		$scope.action = "lastClicked";
		$scope.setParameters($scope.legalPersonAccounts[$scope.legalPersonAccounts.length - 1]);
	}
	$scope.nextClicked = function() {
		$scope.action = "nextClicked";
		var n = $scope.legalPersonAccounts.indexOf($scope.selectedLegalPersonAccount) + 1;
		n = n % $scope.legalPersonAccounts.length;
		$scope.setParameters($scope.legalPersonAccounts[n]);
	}
	$scope.prevClicked = function() {
		$scope.action = "prevClicked";
		var n = $scope.legalPersonAccounts.indexOf($scope.selectedLegalPersonAccount);
		if (n == 0 || n == -1) {
			n = $scope.legalPersonAccounts.length;
		}
		$scope.setParameters($scope.legalPersonAccounts[n - 1]);
	}
	$scope.addClicked = function() {
		$scope.action = "addClicked";
		$scope.mode.current = "Rezim dodavanja";
	}
	$scope.searchClicked = function() {
		$scope.action = "searchClicked";
		$scope.mode.current = "Rezim pretrage";
	}

	$scope.removeClicked = function(legalPersonAccount) {
		$scope.action = "removeClicked";
		$scope.mode.current = "Rezim brisanja";
		$("#deleteModal").modal('show');
	}

	$scope.legalPersonAccount = {};
	$scope.submitAction = function(legalPersonAccount) {
		if ($scope.action == "addClicked") {
			/*legalPersonAccountService.addLegalPersonAccount(legalPersonAccount, $("#dateDatePicker").val(), $("#usedSinceDatePicker").val(), $scope.selectedBank.id).then(function(response) {
				$scope.getAllLegalPersonAccounts();
			});*/
		} else if ($scope.action == "searchClicked") {
			//exchangeListService.searchExchangeLists($scope.exchangeList).then(function(response) {
				//$scope.exchangeLists = response.data;
			//});
		} else {
			if (Object.keys($scope.selectedLegalPersonAccount).length > 0) {
				/*
				legalPersonAccountService.editLegalPersonAccount(legalPersonAccount, $("#dateDatePicker").val(), $("#usedSinceDatePicker").val(), $scope.selectedBank.id).then(
						function(response) {
							$scope.getAllLegalPersonAccounts();
						});*/
			} else {
				ngNotify.set('Selektujte naseljeno mesto prvo!' , {
					type : 'error',
					duration: 3000,
					theme: 'pitchy'
				});
			}
		}
	}
	
	

	$scope.rollbackAction = function() {
		$scope.action = "editBank"
		$scope.mode.current = "Rezim izmene";
		$scope.legalPersonAccount= {};
		$scope.selectedBank = {};
		$scope.selectedCurrency = {};
		$scope.getAllLegalPersonAccounts();
		$("#openingDateDatePickerr").val(new Date());
	}
	
	
	$scope.showBanks = function(bank){
		$("#bankModal").modal('show');
		$scope.setModalSelectedBank(bank);
	}
	
	
	$scope.setModalSelectedBank = function(bank){
		$scope.selectedModalBank = bank;
	}
	
	$scope.showCurrencies = function(currency){
		$("#currencyModal").modal('show');
		$scope.setModalSelectedCurrency(currency);
	}
	
	
	$scope.setModalSelectedCurrency = function(currency){
		$scope.selectedModalCurrency = currency;
	}
	
	$scope.selectedStatus = {};
	
	$scope.setParameters = function(legalPersonAccount){
		$scope.selectedLegalPersonAccount = legalPersonAccount;
		$scope.legalPersonAccount = angular.copy(legalPersonAccount);
		for(i=0;i<$scope.banks.length;i++){
			if($scope.banks[i].id == legalPersonAccount.bank.id){
				$scope.selectedBank = $scope.banks[i];
				break;  
			}
		}
		if(legalPersonAccount.active == true){
			$scope.selectedStatus = "Aktivan";
		} else {
			$scope.selectedStatus = "Neaktivan";
		}
		
		for(i=0;i<$scope.currencies.length;i++){
			if($scope.currencies[i].id == legalPersonAccount.currency.id){
				$scope.selectedCurrency = $scope.currencies[i];
				break;  
			}
		}
		var openingDate = $scope.getDateForPicker(legalPersonAccount.openingDateView, 'YYYY MM DD');
		$('#openingDateDatePicker').val(openingDate);
	}
 	$scope.transferAccount = "";
	
	$scope.getDateForPicker = function(date, format){
		var check = moment(date, format);
		var month = check.format('MM');
		var day   = check.format('DD');
		var year  = check.format('YYYY');
		var today = year+"-"+(month)+"-"+(day);
		return today;
	}
	
	$scope.confirmBank = function(){
		$scope.selectedBank = $scope.selectedModalBank;
		$("#bankModal").modal('hide');
		$scope.selectedModalBank = {};
	}
	
	$scope.confirmCurrency = function(){
		$scope.selectedCurrency = $scope.selectedModalCurrency;
		$("#currencyModal").modal('hide');
		$scope.selectedModalCurrency = {};
	}
	
	
	$scope.confirmDelete = function(){
		if($scope.transferAccount != null && $scope.transferAccount != ""){
			if (Object.keys($scope.selectedLegalPersonAccount).length > 0) {
				legalPersonAccountService.deleteLegalPersonAccount($scope.selectedLegalPersonAccount, $scope.transferAccount).then(function(response) {
					$scope.getAllLegalPersonAccounts();
					$scope.transferAccount = "";
					$("#deleteModal").modal('hide');
				});
			}
		} 
	}
	
});