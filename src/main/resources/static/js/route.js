var app = angular.module("bankApp.route", [ "ngRoute" ]);

app.config(function($routeProvider) {
	$routeProvider.when("/", {
		templateUrl : "html/login.html"
	}).when("/countries", {
		templateUrl : "html/country.html"
	}).when("/currencies", {
		templateUrl : "html/currency.html"
	}).when("/currencyExchanges", {
		templateUrl : "html/currencyExchange.html"
	}).when("/cities", {
		templateUrl : "html/city.html"
	}).when("/exchangeListController", {
		templateUrl : "html/exchangeList.html"
	}).when("/dailyAccountStatuses", {
		templateUrl : "html/dailyAccountStatus.html"
	}).when("/analyticalStatements", {
		templateUrl : "html/analyticalStatement.html"
	}).when("/accounts", {
		templateUrl : "html/legalPersonAccount.html"
	}).when("/login", {
		templateUrl : "html/login.html"
	}).when("/banks", {
		templateUrl : "html/bank.html"
	}).when("/RTGSRequest", {
		templateUrl : "html/RTGSRequest.html"
	}).when("/ClearingSettlement", {
		templateUrl : "html/ClearingSettlement.html"
	}).when("/register", {
		templateUrl : "html/registration.html"
	});
});