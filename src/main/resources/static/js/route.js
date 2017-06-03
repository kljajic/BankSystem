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
	}).when("/cities", {
		templateUrl : "html/city.html"
	}).when("/exchangeLists", {
		templateUrl : "html/exchangeList.html"
	}).when("/paymentTypes", {
		templateUrl : "html/paymentType.html"
	}).when("/dailyAccountStatuses", {
		templateUrl : "html/dailyAccountStatus.html"
	}).when("/analyticalStatements", {
		templateUrl : "html/analyticalStatement.html"
	});
});