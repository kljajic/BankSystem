var RTGSRequestController = angular.module('bankApp.RTGSRequestController' ,[]);

RTGSRequestController.controller('RTGSRequestController', function($scope, $location, $window, $compile, $routeParams,
		RTGSRequestService){
	
	$scope.rtgsRequests = {};
	$scope.mode = {};
	$scope.mode.current = "Rezim pregleda";
	
	
	function refreshView(){
		RTGSRequestService.getAllRTGSRequests().then(function(response){
			if(response.data != null)
				$scope.rtgsRequests = response.data;
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