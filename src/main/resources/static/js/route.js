var app = angular.module("bankApp.route", [ "ngRoute" ]);

app.config(function($routeProvider) {
	$routeProvider.when("/", {
		templateUrl : "html/country.html"
	}).when("/countries", {
		templateUrl : "html/country.html"
	}).when("/currencies", {
		templateUrl : "html/currency.html"
	}).when("/currencyExchanges", {
		templateUrl : "html/currencyExchange.html"
	});
});