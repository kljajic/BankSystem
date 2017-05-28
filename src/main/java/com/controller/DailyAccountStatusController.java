package com.controller;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

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

import com.model.DailyAccountStatus;
import com.service.DailyAccountStatusService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/dailyAccountStatuses")
@Api(value = "/dailyAccountStatuses")
public class DailyAccountStatusController {

	private final DailyAccountStatusService dailyAccountStatusService;
	
	@Autowired
	public DailyAccountStatusController(DailyAccountStatusService dailyAccountStatusService) {
		this.dailyAccountStatusService = dailyAccountStatusService;
	}
	
	@PostMapping("/add")
	@ResponseBody
	@ApiOperation(value = "Add a daily account status.", notes = "Add a single daily account status.", response = DailyAccountStatus.class)
	public DailyAccountStatus createDailyAccountStatus(@RequestBody @Valid DailyAccountStatus dailyAccountStatus){
		return dailyAccountStatusService.createDailyAccountStatus(dailyAccountStatus);
	}
	
	@GetMapping
	@ResponseBody
	@ApiOperation(value = "Get all cities.", notes = "Get all cities.", response = Collection.class)
	public Collection<DailyAccountStatus> getDailyAccountStatuses(@RequestBody Date date){
		return dailyAccountStatusService.getDailyAccountStatuses(date);
	}
	
	@PutMapping("/update")
	@ResponseBody
	@ApiOperation(value = "Update a city.", notes = "Update a single city.", response = DailyAccountStatus.class)
	public DailyAccountStatus updateDailyAccountStatus(@RequestBody @Valid DailyAccountStatus dailyAccountStatus){
		return dailyAccountStatusService.updateDailyAccountStatus(dailyAccountStatus);
	}
	
	@DeleteMapping("/delete/{id}")
	@ApiOperation(value = "Delete a daily account status.", notes = "Delete a daily account status with given id.")
	public void deleteDailyAccountStatus(@PathVariable("id") Long id){
		dailyAccountStatusService.deleteDailyAccountStatus(id);
	}
	
}
