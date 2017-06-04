var exchangeListController = angular.module('bankApp.exchangeListController',
		[]);

exchangeListController.controller('exchangeListController', function($scope,
		$location, $window, $compile, exchangeListService, ngNotify) {
	
	$scope.action = {};
	$scope.exchangeLists = [];
	$scope.banks = [];
	$scope.bankOptions = "bank.name for bank in banks";
	$scope.exchangeList = {};
	$scope.selectedBank = {};
	$scope.mode = {};
	$scope.mode.current = "Rezim izmene";
	

	$scope.getAllExchangeLists = function() {
		exchangeListService.getAllExchangeLists().then(function(data) {
			if (data.data != null) {
				$scope.exchangeLists = data.data;
				for(var i = 0;i < $scope.exchangeLists.length;i++){
					$scope.exchangeLists[i].dateView = moment($scope.exchangeLists[i].date).format("DD MMM YYYY");
					$scope.exchangeLists[i].usedSinceView = moment($scope.exchangeLists[i].usedSince).format("DD MMM YYYY");
				}
			}
		});
	}
	
	$scope.getAllBanks = function() {
		exchangeListService.getAllBanks().then(function(data) {
			if (data.data != null) {
				$scope.banks = data.data;
			}
		});
	}
	
	
	$scope.getAllExchangeLists();
	$scope.getAllBanks();

	$scope.exchangeList = {};
	$scope.selectedExchangeList = {};
	$scope.setSelected = function(exchangeList) {
		if ($scope.selectedExchangeList == exchangeList) {
			$scope.selectedExchangeList = {};
		} else {
			$scope.setParameters(exchangeList);
		}
	}

	$scope.firstClicked = function() {
		$scope.action = "firstClicked";
		$scope.setParameters($scope.exchangeLists[0]);
	}
	$scope.lastClicked = function() {
		$scope.action = "lastClicked";
		$scope.setParameters($scope.exchangeLists[$scope.exchangeLists.length - 1]);
	}
	$scope.nextClicked = function() {
		$scope.action = "nextClicked";
		var n = $scope.exchangeLists.indexOf($scope.selectedExchangeList) + 1;
		n = n % $scope.exchangeLists.length;
		$scope.setParameters($scope.exchangeLists[n]);
	}
	$scope.prevClicked = function() {
		$scope.action = "prevClicked";
		var n = $scope.exchangeLists.indexOf($scope.selectedExchangeList);
		if (n == 0 || n == -1) {
			n = $scope.exchangeLists.length;
		}
		$scope.setParameters($scope.exchangeLists[n - 1]);
	}
	$scope.addClicked = function() {
		$scope.action = "addClicked";
		$scope.mode.current = "Rezim dodavanja";
	}
	$scope.searchClicked = function() {
		$scope.action = "searchClicked";
		$scope.mode.current = "Rezim pretrage";
	}

	$scope.removeClicked = function(exchangeList) {
		$scope.action = "removeClicked";
		$scope.mode.current = "Rezim brisanja";
		if (Object.keys(exchangeList).length > 0) {
			exchangeListService.deleteExchangeList(exchangeList).then(function(response) {
				$scope.getAllExchangeLists();
			});
		}
	}

	$scope.exchangeList = {};
	$scope.submitAction = function(exchangeList) {
		if ($scope.action == "addClicked") {
			exchangeListService.addExchangeList(exchangeList, $("#dateDatePicker").val(), $("#usedSinceDatePicker").val(), $scope.selectedBank.id).then(function(response) {
				$scope.getAllExchangeLists();
			});
		} else if ($scope.action == "searchClicked") {
			exchangeListService.searchExchangeLists(exchangeList, $("#dateDatePicker").val(), $("#usedSinceDatePicker").val(), $scope.selectedBank).then(function(response) {
				if(response.data != null){
					$scope.exchangeLists = response.data;
					for(var i = 0;i < $scope.exchangeLists.length;i++){
						$scope.exchangeLists[i].dateView = moment($scope.exchangeLists[i].date).format("DD MMM YYYY");
						$scope.exchangeLists[i].usedSinceView = moment($scope.exchangeLists[i].usedSince).format("DD MMM YYYY");
					}
				}
			});
		} else {
			if (Object.keys($scope.selectedExchangeList).length > 0) {
				exchangeListService.editExchangeList(exchangeList, $("#dateDatePicker").val(), $("#usedSinceDatePicker").val(), $scope.selectedBank.id).then(
						function(response) {
							$scope.getAllExchangeLists();
						});
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
		$scope.exchangeList = {};
		$scope.selectedBank = {};
		$scope.getAllExchangeLists();
		$("#dateDatePicker").val(new Date());
		$('#usedSinceDatePicker').val(new Date());
	}
	
	
	$scope.showBanks = function(bank){
		$("#bankModal").modal('show');
		$scope.setModalSelectedBank(bank);
	}
	
	
	$scope.setModalSelectedBank = function(bank){
		$scope.selectedModalBank = bank;
	}
	
	$scope.setParameters = function(exchangeList){
		$scope.selectedExchangeList = exchangeList;
		$scope.exchangeList = angular.copy(exchangeList);
		for(i=0;i<$scope.banks.length;i++){
			if($scope.banks[i].id == exchangeList.bank.id){
				$scope.selectedBank = $scope.banks[i];
				break;  
			}
		}
		var date = $scope.getDateForPicker(exchangeList.dateView, 'DD MMM YYYY');
		$('#dateDatePicker').val(date);
		
		var usedSince = $scope.getDateForPicker(exchangeList.usedSinceView, 'DD MMM YYYY');
		$('#usedSinceDatePicker').val(usedSince);
		
		
		
	}
	
	
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
	
	
});