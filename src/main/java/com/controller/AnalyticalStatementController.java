package com.controller;

import java.util.Collection;

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
	
	public AnalyticalStatementController(AnaltyicalStatementService analyticalStatementService) {
		this.analyticalStatementService = analyticalStatementService;
	}
	
	@PostMapping("/create/{currencyId}/{paymentTypeId}/{cityId}/{dailyAccountStatusId}")
	@ResponseBody
	@ApiOperation(value = "Create a analytical statement.", notes = "Create a single analytical statement.", response = AnalyticalStatement.class)
	public AnalyticalStatement createAnalyticalStatement(@PathVariable("currencyId") String currencyId,
														 @PathVariable("paymentTypeId") String paymentTypeId,
														 @PathVariable("cityId") String cityId,
														 @PathVariable("dailyAccountStatusId") Long dailyAccountStatusId,
														 @RequestBody AnalyticalStatement analyticalStatement){
		return analyticalStatementService.createAnalyticalStatement(currencyId, paymentTypeId, cityId,
																	dailyAccountStatusId, analyticalStatement);
	}
	
	@GetMapping
	@ResponseBody
	@ApiOperation(value = "Get analytical statements.", notes = "Get all analytical statements.", response = Collection.class)
	public Collection<AnalyticalStatement> getAnalyticalStatements(){
		return analyticalStatementService.getAnalyticalStatements();
	}
	
	@GetMapping("/{id}")
	@ResponseBody
	@ApiOperation(value = "Get analytical statement.", notes = "Get analytical statement with given id.", response = AnalyticalStatement.class)
	public AnalyticalStatement getAnalyticalStatement(@PathVariable("id") Long id){
		return analyticalStatementService.getAnalyticalStatement(id);
	}
	
	@PutMapping("/update/{currencyId}/{paymentTypeId}/{cityId}/{dailyAccountStatusId}")
	@ResponseBody
	@ApiOperation(value = "Update a analytical statement.", notes = "Update a single analytical statement.", response = AnalyticalStatement.class)
	public AnalyticalStatement updateAnalyticalStatement(@PathVariable("currencyId") String currencyId,
														 @PathVariable("paymentTypeId") String paymentTypeId,
														 @PathVariable("cityId") String cityId,
														 @PathVariable("dailyAccountStatusId") Long dailyAccountStatusId,
														 @RequestBody AnalyticalStatement analyticalStatement){
		return analyticalStatementService.updateAnalyticalStatement(currencyId, paymentTypeId, cityId,
																	dailyAccountStatusId, analyticalStatement);
	}
	
	@DeleteMapping("/delete/{analyticalStatementId}")
	@ResponseBody
	@ApiOperation(value = "Delete a analytical statement.", notes = "Delete a single analytical statement.")
	public void deleteAnalyticalStatement(@PathVariable("analyticalStatementId") Long analyticalStatementId){
		analyticalStatementService.deleteAnalyticalStatement(analyticalStatementId);;
	}
	
}
