var legalPersonAccountController = angular.module('bankApp.legalPersonAccountController',
		[]);

legalPersonAccountController.controller('legalPersonAccountController', function($scope,
		$location, $window, $compile, $routeParams,analyticalStatementService,legalPersonAccountService, ngNotify, exchangeListService) {
	
	$scope.action = {};
	$scope.legalPersonAccounts = [];
	$scope.legalPersonAccount= {};
	$scope.banks = [];
	$scope.bankOptions = "bank.name for bank in banks";
	$scope.selectedBank = {};
	$scope.currencies = [];
	$scope.currencyOptions = "currency.officialCode for currency in currencies";
	$scope.selectedCurrency = {};
	$scope.clients = [];
	$scope.clientOptions = "client.name + space + client.surname for client in clients";
	$scope.selectedClient = {};
	$scope.space = " ";
	$scope.transferAccount = "";
	
	$scope.mode = {};
	$scope.mode.current = "Rezim izmene";
	
	$scope.references = ['Banks', 'Clients', ''];

	$scope.getAllLegalPersonAccounts = function() {
		legalPersonAccountService.getAllLegalPersonAccounts().then(function(data) {
			if($routeParams.param > 0){
				var temp = [];
				for(var i = 0; i<data.data.length; i++){
					if(data.data[i].currency.id === $routeParams.param > 0){
						temp.push(data.data[i]);
					}
				}
				$scope.legalPersonAccounts = temp;
				$('#selectFieldCurrency').prop('disabled', 'disabled');
				$scope.selectedCurrency = $scope.currencies[$routeParams.param - 1];
			} 
			else{
				if (data.data != null) {
					$scope.legalPersonAccounts = data.data;
					for(var i = 0;i < $scope.legalPersonAccounts.length;i++){
						if($scope.legalPersonAccounts[i].active == true){
							$scope.legalPersonAccounts[i].activeView = "AKTIVAN";
						} else {
							$scope.legalPersonAccounts[i].activeView = "NEAKTIVAN";
						}
					}
				}
				$('#selectFieldCurrency').removeAttr('disabled');
			}
		},
		function(reason) {
			  if(reason.status == 401){
				  $location.path('/unauthorized')
			  } else if(reason.status == 403){
				  $location.path('/');
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
	
	$scope.getAllClients = function() {
		legalPersonAccountService.getAllClients().then(function(data) {
			if (data.data != null) {
				$scope.clients = data.data;
			}
		});
	}
	
	
	$scope.getAllLegalPersonAccounts();
	$scope.getAllBanks();
	$scope.getAllCurrencies();
	$scope.getAllClients();

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
		if (Object.keys($scope.selectedLegalPersonAccount).length > 0){
			$scope.action = "removeClicked";
			$scope.mode.current = "Rezim brisanja";
			$("#deleteModal").modal('show');
		} else {
			swal("Izvrsite selekciju", "Selektujte racun!", "error");
		}
	}

	$scope.legalPersonAccount = {};
	$scope.submitAction = function(legalPersonAccount) {
		if ($scope.action == "addClicked") {
			legalPersonAccountService.addLegalPersonAccount(legalPersonAccount, $("#openingDateDatePicker").val(), $scope.selectedBank.id, $scope.selectedClient.id, $scope.selectedCurrency.id).then(function(response) {
				$scope.getAllLegalPersonAccounts();
			});
		} else if ($scope.action == "searchClicked") {
			legalPersonAccountService.searchLegalPersonAccounts(legalPersonAccount, $scope.selectedStatus, $scope.selectedBank, $scope.selectedClient, $scope.selectedCurrency, $("#openingDateDatePicker").val()).then(function(response) {
				$scope.legalPersonAccounts = response.data;
				for(var i = 0;i < $scope.legalPersonAccounts.length;i++){
					if($scope.legalPersonAccounts[i].active == true){
						$scope.legalPersonAccounts[i].activeView = "AKTIVAN";
					} else {
						$scope.legalPersonAccounts[i].activeView = "NEAKTIVAN";
					}
				}
			});
		} else {
			if (Object.keys($scope.selectedLegalPersonAccount).length > 0) {
				legalPersonAccountService.editLegalPersonAccount(legalPersonAccount,$scope.selectedStatus, $("#openingDateDatePicker").val(), $scope.selectedBank.id, $scope.selectedClient.id, $scope.selectedCurrency.id).then(
						function(response) {
							$scope.getAllLegalPersonAccounts();
				});
			} else {
				swal("Izvrsite selekciju", "Selektujte racun!", "error");
			}
		}
	}

	$scope.rollbackAction = function() {
		$scope.action = "editBank"
		$scope.mode.current = "Rezim izmene";
		$scope.legalPersonAccount= {};
		$scope.selectedLegalPersonAccount= {};
		$scope.selectedBank = {};
		$scope.selectedCurrency = {};
		$scope.selectedClient = {};
		$scope.getAllLegalPersonAccounts();
		$("#openingDateDatePicker").val(new Date());
	}
	
	
	$scope.showBanks = function(bank){
		$("#bankModal").modal('show');
		$scope.setModalSelectedBank(bank);
	}
	
	$scope.pdf = function(account){
		$("#pdfModal").modal('show');
		$scope.pdfExportAccount = account;
	}
	
	$scope.exportToPdf = function(){
		console.log($scope.pdfExportAccount);
		analyticalStatementService.exportToPdf($scope.pdfExportAccount.id,$scope.pdfExportAccount.startDate,$scope.pdfExportAccount.endDate).then(function(response){
			$("#pdfModal").modal('show');
			var name = $scope.pdfExportAccount.accountNumber + ' - IZVESTAJ - ' + $scope.pdfExportAccount.startDate.toString().substring(4, 15) + ' - ' + $scope.pdfExportAccount.startDate.toString().substring(4, 15) + '.pdf';
			var file = new Blob([response.data], {type: "application/pdf;charset=utf-8"});
	            saveAs(file, name);
		});
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
	
	$scope.showClients = function(client){
		$("#clientModal").modal('show');
		$scope.setModalSelectedClient(client);
	}
	
	
	$scope.setModalSelectedClient = function(client){
		$scope.selectedModalClient = client;
	}
	
	
	$scope.selectedStatus = {};
	
	$scope.setParameters = function(legalPersonAccount){
		$scope.selectedLegalPersonAccount = legalPersonAccount;
		$scope.legalPersonAccount = angular.copy(legalPersonAccount);
		
		if(legalPersonAccount.active == true){
			$scope.selectedStatus = "Aktivan";
		} else {
			$scope.selectedStatus = "Neaktivan";
		}
		
		for(i=0;i<$scope.banks.length;i++){
			if($scope.banks[i].id == legalPersonAccount.bank.id){
				$scope.selectedBank = $scope.banks[i];
				break;  
			}
		}
		
		for(i=0;i<$scope.currencies.length;i++){
			if($scope.currencies[i].id == legalPersonAccount.currency.id){
				$scope.selectedCurrency = $scope.currencies[i];
				break;  
			}
		}
		
		for(i=0;i<$scope.clients.length;i++){
			if($scope.clients[i].id == legalPersonAccount.client.id){
				$scope.selectedClient = $scope.clients[i];
				break;  
			}
		}
		$("#openingDateDatePicker").val(legalPersonAccount.openingDate.substring(0, 10));
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
	
	$scope.confirmClient = function(){
		$scope.selectedClient = $scope.selectedModalClient;
		$("#clientModal").modal('hide');
		$scope.selectedModalClient = {};
	}
	
	
	$scope.confirmDelete = function(){
		if($scope.transferAccount != null && $scope.transferAccount != ""){
			if (Object.keys($scope.selectedLegalPersonAccount).length > 0) {
				swal({
					  title: "Da li ste sigurni?",
					  text: "Necete uspeti da vratite ovo.",
					  type: "warning",
					  showCancelButton: true,
					  confirmButtonColor: "#DD6B55",
					  confirmButtonText: "Da, obrisi.",
					  cancelButtonText: "Ponisti",
					  closeOnConfirm: false,
					  closeOnCancel: false
					},
					function(isConfirm){
					  if (isConfirm) {
						  legalPersonAccountService.deleteLegalPersonAccount($scope.selectedLegalPersonAccount, $scope.transferAccount).then(function(response) {
								$scope.getAllLegalPersonAccounts();
								$scope.transferAccount = "";
								$("#deleteModal").modal('hide');
							});
						  swal("Obrisano!", "Uspesno ste obrisali.", "success");
					  } else {
					    swal("Ponisteno", "Ponistili ste operaciju brisanja.", "error");
					  }
					});
			} else {
				swal({ title:"Selektujte zeljeni nalog.", type:"error" });
			}
		} else {
			swal({ title:"Unesite racun za transfer!.", type:"error" });
		} 
	}
	
	$scope.chain = function(selectedLegalPersonAccount){
		if(Object.keys($scope.selectedLegalPersonAccount).length > 0){
				$location.path('/dailyAccountStatuses/').search({paramAccount: $scope.selectedLegalPersonAccount.id});
		} else {
			swal({ title:"Selektujte racun!", type:"error" });
		}
	}
	
	$scope.confirmPreviousForm = function(){
		$("#previousFormsModal").modal('hide');
		$location.path('/'+$scope.selectedModalPrevousForm);
		$scope.selectedModalPrevousForm = {};
	}
	
	$scope.previousForm = function(){
		$("#previousFormsModal").modal('show');
	}
	
	$scope.selectedModalPrevousForm = {};
	$scope.setModalSelectedPreviousForm = function(previousForm){
		$scope.selectedModalPrevousForm = previousForm;
	}
	
});