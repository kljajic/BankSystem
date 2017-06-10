package com.controller;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.model.AnalyticalStatement;
import com.service.AnaltyicalStatementService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/analyticalStatements")
@Api(value = "/analyticalStatements")
public class AnalyticalStatementController {

	private final AnaltyicalStatementService analyticalStatementService;

	@Autowired
	public AnalyticalStatementController(AnaltyicalStatementService analyticalStatementService) {
		this.analyticalStatementService = analyticalStatementService;
	}

	@PostMapping("/create/{currencyId}/{paymentTypeId}/{cityId}/{dailyAccountStatusId}/{dateOfReceipt}/{currencyDate}")
	@ResponseBody
	@ApiOperation(value = "Create a analytical statement.", notes = "Create a single analytical statement.", response = Collection.class)
	public Collection<AnalyticalStatement> createAnalyticalStatement(@PathVariable("currencyId") String currencyId,
			@PathVariable("paymentTypeId") String paymentTypeId, @PathVariable("cityId") String cityId,
			@PathVariable("dailyAccountStatusId") Long dailyAccountStatusId,
			@PathVariable("dateOfReceipt") Date dateOfReceipt, @PathVariable("currencyDate") Date currencyDate,
			@RequestBody AnalyticalStatement analyticalStatement) {
		return analyticalStatementService.createAnalyticalStatement(currencyId, paymentTypeId, cityId,
				dailyAccountStatusId, dateOfReceipt, currencyDate, analyticalStatement);
	}

	@GetMapping
	@ResponseBody
	@ApiOperation(value = "Get analytical statements.", notes = "Get all analytical statements.", response = Collection.class)
	public Collection<AnalyticalStatement> getAnalyticalStatements() {
		return analyticalStatementService.getAnalyticalStatements();
	}

	@GetMapping("/{id}")
	@ResponseBody
	@ApiOperation(value = "Get analytical statement.", notes = "Get analytical statement with given id.", response = AnalyticalStatement.class)
	public AnalyticalStatement getAnalyticalStatement(@PathVariable("id") Long id) {
		return analyticalStatementService.getAnalyticalStatement(id);
	}

	@PutMapping("/update/{currencyId}/{paymentTypeId}/{cityId}/{dailyAccountStatusId}/{dateOfReceipt}/{currencyDate}")
	@ResponseBody
	@ApiOperation(value = "Update a analytical statement.", notes = "Update a single analytical statement.", response = AnalyticalStatement.class)
	public AnalyticalStatement updateAnalyticalStatement(@PathVariable("currencyId") String currencyId,
			@PathVariable("paymentTypeId") String paymentTypeId, @PathVariable("cityId") String cityId,
			@PathVariable("dailyAccountStatusId") Long dailyAccountStatusId,
			@PathVariable("dateOfReceipt") Date dateOfReceipt, @PathVariable("currencyDate") Date currencyDate,
			@RequestBody AnalyticalStatement analyticalStatement) {
		return analyticalStatementService.updateAnalyticalStatement(currencyId, paymentTypeId, cityId,
				dailyAccountStatusId, dateOfReceipt, currencyDate, analyticalStatement);
	}

	@DeleteMapping("/delete/{analyticalStatementId}")
	@ResponseBody
	@ApiOperation(value = "Delete a analytical statement.", notes = "Delete a single analytical statement.")
	public void deleteAnalyticalStatement(@PathVariable("analyticalStatementId") Long analyticalStatementId) {
		analyticalStatementService.deleteAnalyticalStatement(analyticalStatementId);
		;
	}

	@GetMapping("/getByPaymentTypeId/{id}")
	@ResponseBody
	@ApiOperation(value = "Get analytical statements by payment type.", notes = "Get analytical statements by given payment type id.", response = Collection.class)
	public Collection<AnalyticalStatement> getAnalyticalStatementsByPaymentTypeId(@PathVariable("id") Long id) {
		return analyticalStatementService.getAnalyticalStatementsByPaymentTypeId(id);
	}

	@GetMapping("/getByDailyAccountStatusId/{id}")
	@ResponseBody
	@ApiOperation(value = "Get analytical statements by daily account status.", notes = "Get analytical statements by given daily account status id.", response = Collection.class)
	public Collection<AnalyticalStatement> getAnalyticalStatementsByDailyAccountStatusId(@PathVariable("id") Long id) {
		return analyticalStatementService.getAnalyticalStatementsByDailyAccountStatusId(id);
	}

	@PutMapping("/search/{currencyId}/{paymentTypeId}/{cityId}/{dailyAccountStatusId}/{dateOfReceipt}/{currencyDate}")
	@ResponseBody
	@ApiOperation(value = "Search all analytical statements.", notes = "Search all analytical statements by given fields.", response = Collection.class)
	public Collection<AnalyticalStatement> searchAnalyticalStatements(@PathVariable("currencyId") Long currencyId,
			@PathVariable("paymentTypeId") Long paymentTypeId, @PathVariable("cityId") Long cityId,
			@PathVariable("dailyAccountStatusId") Long dailyAccountStatusId,
			@PathVariable("dateOfReceipt") Date dateOfReceipt, @PathVariable("currencyDate") Date currencyDate,
			@RequestBody AnalyticalStatement analyticalStatement) {
		return analyticalStatementService.searchAnalyticalStatements(currencyId, paymentTypeId, cityId,
				dailyAccountStatusId, dateOfReceipt, currencyDate, analyticalStatement);
	}

}
