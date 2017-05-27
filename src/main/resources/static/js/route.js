var app = angular.module("bankApp.route", [ "ngRoute" ]);

app.config(function($routeProvider) {
	$routeProvider.when("/", {
		templateUrl : "html/country.html"
	}).when("/country", {
		templateUrl : "html/country.html"
	});
});