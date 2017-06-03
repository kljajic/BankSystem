var currencyExchangeController = angular.module('bankApp.currencyExchangeController', []);

currencyExchangeController.controller('currencyExchangeController', function($scope, $location, $window, $compile, currencyExchangeService, currencyService){
	
	$scope.action = {};
	$scope.cexes = {};
	
	$scope.currencies = {};
	$scope.exchangeLists = {};
	
	$scope.cex = {};
	$scope.selectedCex = {};
	
	function refreshView(){
		currencyExchangeService.getAllCexes().then(function(response){
			if(response.data != null){
				$scope.cexes = response.data;
			}
		});
		currencyService.getAllCurrencies().then(function(response){
			if(response.data != null){
				$scope.currencies = response.data;
			}
		});
		currencyExchangeService.getAllExchangeLists().then(function(response){
			if(response.data != null){
				$scope.exchangeLists = response.data;
			}
		});
	}
	
	refreshView();
	
	findExchangeListForSelect = function(id){
		for(var i = 0; i < $scope.exchangeLists.length; i++){
			if($scope.exchangeLists[i].id === id){
				return $scope.exchangeLists[i];
			}
		}
	}
	
	findCurrencyForSelect = function(id){
		for(var i = 0; i < $scope.currencies.length; i++){
			if($scope.currencies[i].id === id){
				return $scope.currencies[i];
			}
		}
	}
	
	syncClick = function(cex){
		$scope.selectedCex = cex;
		$scope.cex.id = cex.id;
		$scope.cex.buyRate = cex.buyRate;
		$scope.cex.middleRate = cex.middleRate;
		$scope.cex.sellRate = cex.sellRate;
		$scope.cex.exchangeList = findExchangeListForSelect(cex.exchangeList.id);
		$scope.cex.primaryCurrency = findCurrencyForSelect(cex.primaryCurrency.id);
		$scope.cex.accordingToCurrency = findCurrencyForSelect(cex.accordingToCurrency.id);
	}
	
	syncButtons = function(selectedCex){
		$scope.cex.id = $scope.selectedCex.id;
		$scope.cex.buyRate = $scope.selectedCex.buyRate;
		$scope.cex.middleRate = $scope.selectedCex.middleRate;
		$scope.cex.sellRate = $scope.selectedCex.sellRate;
		$scope.cex.exchangeList = findExchangeListForSelect(selectedCex.exchangeList.id);
		$scope.cex.primaryCurrency = findCurrencyForSelect(selectedCex.primaryCurrency.id);
		$scope.cex.accordingToCurrency = findCurrencyForSelect(selectedCex.accordingToCurrency.id);
	}
	

	$scope.setSelected = function(cex){
		if($scope.selectedCex==cex){
			$scope.selectedCex = {};
		} else {
			syncClick(cex);
		}
	}
	
	$scope.firstClicked = function(){
		$scope.action = "firstClicked";
		$scope.selectedCex = $scope.cexes[0];
		syncButtons($scope.selectedCex);
	}
	$scope.lastClicked = function(){
		$scope.action = "lastClicked";
		$scope.selectedCex = $scope.cexes[$scope.cexes.length-1];
		syncButtons($scope.selectedCex);
	}
	$scope.nextClicked = function(){
		$scope.action = "nextClicked";
		var n = $scope.cexes.indexOf($scope.selectedCex)+1;
		n = n % $scope.cexes.length;
		$scope.selectedCex = $scope.cexes[n];
		syncButtons($scope.selectedCex);
	}
	$scope.prevClicked = function(){
		$scope.action = "prevClicked";
		var n = $scope.cexes.indexOf($scope.selectedCex);
		if(n == 0 || n == -1){
			n = $scope.cexes.length;
		}
		$scope.selectedCex = $scope.cexes[n-1];
		syncButtons($scope.selectedCex);
	}
	$scope.addClicked = function(){
		$scope.action = "addClicked";
	}
	$scope.searchClicked = function(){
		$scope.action = "searchClicked";
	}
	
	$scope.cex.exchangeList = {};
	$scope.cex.primaryCurrency = {};
	$scope.cex.accordingToCurrency = {};
	$scope.submitAction = function(cex){
		if($scope.action == "addClicked"){
			currencyExchangeService.addNewCex(cex).then(function(response){
				$scope.cexes.push(response.data);
			});
		} else if($scope.action == "searchClicked"){
			if(cex.exchangeList == null || typeof cex.exchangeList.numberOfExchangeList == 'undefined' || cex.exchangeList.numberOfExchangeList == ""){
				$scope.cex.exchangeList = {};
				$scope.cex.exchangeList.numberOfExchangeList = {};
				cex.exchangeList.numberOfExchangeList = 123;
			}
			if(cex.primaryCurrency == null || typeof cex.primaryCurrency.officialCode == 'undefined' || cex.primaryCurrency.officialCode == ""){
				$scope.cex.primaryCurrency = {};
				$scope.cex.primaryCurrency.officialCode = {};
				cex.primaryCurrency.officialCode = "AHA";
			}
			if(cex.accordingToCurrency == null || typeof cex.accordingToCurrency.officialCode == 'undefined' || cex.accordingToCurrency.officialCode == ""){
				$scope.cex.accordingToCurrency = {};
				$scope.cex.accordingToCurrency.officialCode = {};
				cex.accordingToCurrency.officialCode = "AHA";
			}
			if(typeof cex.buyRate == 'undefined' || cex.buyRate == ""){
				cex.buyRate = 454916231.8487;
			}
			if(typeof cex.sellRate == 'undefined' || cex.sellRate == ""){
				cex.sellRate = 454916231.8487;
			}
			if(typeof cex.sellRate == 'undefined' || cex.sellRate == ""){
				cex.sellRate = 454916231.8487;
			}
			
			
			currencyExchangeService.searchCexes(cex.buyRate, cex.sellRate, cex.middleRate, cex.exchangeList.numberOfExchangeList, cex.primaryCurrency.officialCode, cex.accordingToCurrency.officialCode).then(function(response){
				$scope.cexes=response.data;
			});
		}
		else{
			if(Object.keys($scope.selectedCex).length > 0){
				currencyExchangeService.editCex(cex).then(function(response){
					alert("izmenjeno");
					refreshView();
				});
			} else {
				alert("SELEKTUJ");
			}
		}
	}
	
	$scope.removeClicked = function(cex){
		$scope.action = "removeClicked";
		$scope.cex = {};
		$scope.selectedCex = {};
		if(Object.keys(cex).length > 0){
			currencyExchangeService.deleteCex(cex).then(function(response){
				refreshView();
			});
		}
	}
	
	$scope.rollbackAction = function(){
		$scope.action = {};
		$scope.cex = {};
		$scope.selectedCex = {};
		refreshView();
	}
	
});