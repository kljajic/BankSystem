var app = angular.module("bankApp.route", [ "ngRoute" ]);

app.config(function($routeProvider) {
	$routeProvider.when("/", {
		templateUrl : "html/country.html"
	}).when("/countries", {
		templateUrl : "html/country.html"
	});
});