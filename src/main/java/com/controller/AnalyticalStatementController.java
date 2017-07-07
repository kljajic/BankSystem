package com.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.model.AnalyticalStatement;
import com.model.user.Permission;
import com.service.AnaltyicalStatementService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping("/analyticalStatements")
@Api(value = "/analyticalStatements")
public class AnalyticalStatementController {

	private final AnaltyicalStatementService analyticalStatementService;

	@Autowired
	public AnalyticalStatementController(AnaltyicalStatementService analyticalStatementService) {
		this.analyticalStatementService = analyticalStatementService;
	}

	@PostMapping("/create/{currencyId}/{cityId}/{dateOfReceipt}/{currencyDate}")
	@ResponseBody
	@Permission(permissionName = "writeAnalyticalStatement")
	@ApiOperation(value = "Create a analytical statement.", notes = "Create a single analytical statement.", response = Collection.class)
	public Collection<AnalyticalStatement> createAnalyticalStatement(@PathVariable("currencyId") String currencyId,
			 @PathVariable("cityId") String cityId,
			@PathVariable("dateOfReceipt") Date dateOfReceipt, @PathVariable("currencyDate") Date currencyDate,
			@RequestBody AnalyticalStatement analyticalStatement) {
		return analyticalStatementService.createAnalyticalStatement(currencyId, cityId,
				dateOfReceipt, currencyDate, analyticalStatement);
	}

	@GetMapping
	@ResponseBody
	@Permission(permissionName = "readAnalyticalStatements")
	@ApiOperation(value = "Get analytical statements.", notes = "Get all analytical statements.", response = Collection.class)
	public Collection<AnalyticalStatement> getAnalyticalStatements() {
		return analyticalStatementService.getAnalyticalStatements();
	}

	@GetMapping("/{id}")
	@ResponseBody
	@Permission(permissionName = "readAnalyticalStatement")
	@ApiOperation(value = "Get analytical statement.", notes = "Get analytical statement with given id.", response = AnalyticalStatement.class)
	public AnalyticalStatement getAnalyticalStatement(@PathVariable("id") Long id) {
		return analyticalStatementService.getAnalyticalStatement(id);
	}

	@PutMapping("/update/{currencyId}/{cityId}/{dateOfReceipt}/{currencyDate}")
	@ResponseBody
	@Permission(permissionName = "editAnalyticalStatement")
	@ApiOperation(value = "Update a analytical statement.", notes = "Update a single analytical statement.", response = AnalyticalStatement.class)
	public Collection<AnalyticalStatement> updateAnalyticalStatement(@PathVariable("currencyId") String currencyId,
		    @PathVariable("cityId") String cityId,
			@PathVariable("dateOfReceipt") Date dateOfReceipt, @PathVariable("currencyDate") Date currencyDate,
			@RequestBody AnalyticalStatement analyticalStatement) {
		return analyticalStatementService.updateAnalyticalStatement(currencyId, cityId,
				dateOfReceipt, currencyDate, analyticalStatement);
	}

	@DeleteMapping("/delete/{analyticalStatementId}")
	@ResponseBody
	@Permission(permissionName = "deleteAnalyticalStatement")
	@ApiOperation(value = "Delete a analytical statement.", notes = "Delete a single analytical statement.")
	public Collection<AnalyticalStatement> deleteAnalyticalStatement(@PathVariable("analyticalStatementId") Long analyticalStatementId) {
		return analyticalStatementService.deleteAnalyticalStatement(analyticalStatementId);
	}


	@GetMapping("/getByDailyAccountStatusId/{id}")
	@ResponseBody
	@Permission(permissionName = "readDailyAnalyticalStatement")
	@ApiOperation(value = "Get analytical statements by daily account status.", notes = "Get analytical statements by given daily account status id.", response = Collection.class)
	public Collection<AnalyticalStatement> getAnalyticalStatementsByDailyAccountStatusId(@PathVariable("id") Long id) {
		return analyticalStatementService.getAnalyticalStatementsByDailyAccountStatusId(id);
	}

	@PutMapping("/search/{currencyId}/{cityId}/{dailyAccountStatusId}/{dateOfReceipt}/{currencyDate}")
	@ResponseBody
	@Permission(permissionName = "searchAnalyticalStatements")
	@ApiOperation(value = "Search all analytical statements.", notes = "Search all analytical statements by given fields.", response = Collection.class)
	public Collection<AnalyticalStatement> searchAnalyticalStatements(@PathVariable("currencyId") Long currencyId,
		    @PathVariable("cityId") Long cityId,
			@PathVariable("dailyAccountStatusId") Long dailyAccountStatusId,
			@PathVariable("dateOfReceipt") Date dateOfReceipt, @PathVariable("currencyDate") Date currencyDate,
			@RequestBody AnalyticalStatement analyticalStatement) {
		Collection<AnalyticalStatement> o =analyticalStatementService.searchAnalyticalStatements(currencyId, cityId,
				dailyAccountStatusId, dateOfReceipt, currencyDate, analyticalStatement);
		System.out.println(o.size());
		return o;
	}
	
	  @RequestMapping(value = "/export/{accountId}/{startDate}/{endDate}", method = RequestMethod.GET)
	  @Permission(permissionName = "exportAnalyticalStatement")
	  public void exportToPdf(@PathVariable("accountId") Long accountId,@PathVariable("startDate") Date start,@PathVariable("endDate") Date end,HttpServletResponse response) throws ParseException, JRException, SQLException, IOException {
		  analyticalStatementService.exportToPdf(accountId, start,end,response);
	  }

}
