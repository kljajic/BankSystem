var ClearingSettlementController = angular.module('bankApp.ClearingSettlementController' ,[]);

ClearingSettlementController.controller('ClearingSettlementController', function($scope, $location, $window, $compile, $routeParams,
		ClearingSettlementService){

	
	$scope.csRequests = {};
	$scope.mode = {};
	$scope.mode.current = "Rezim pregleda";
	
	
	function refreshView(){
		ClearingSettlementService.getAllClearingSettlementRequests().then(function(response){
			if(response.data != null){
				$scope.csRequests = response.data;
				for(var i = 0;i < $scope.csRequests.length;i++){
					$scope.csRequests[i].currencyDate = moment($scope.csRequests[i].currencyDate).format("DD MMM YYYY");
					$scope.csRequests[i].date = moment($scope.csRequests[i].date).format("DD MMM YYYY");
				}
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
	
	refreshView();
	
	
});