package com.controller;

import java.util.Collection;

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

import com.model.PaymentType;
import com.model.user.Permission;
import com.service.PaymentTypeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/paymentTypes")
@Api(value = "/paymentTypes")
public class PaymentTypeController {

	private final PaymentTypeService paymentTypeService;
	
	@Autowired
	public PaymentTypeController(PaymentTypeService paymentTypeService) {
		this.paymentTypeService = paymentTypeService;
	}
	
	@PostMapping("/add")
	@ResponseBody
	@ApiOperation(value = "Add a peyment type.", notes = "Add a single payment type.", response = PaymentType.class)
	@Permission(permissionName = "writePaymentType")
	public PaymentType createPaymentType(@RequestBody @Valid PaymentType paymentType){
		return paymentTypeService.createPaymentType(paymentType);
	}
	
	@GetMapping
	@ResponseBody
	@ApiOperation(value = "Get all payment types.", notes = "Get all payment types.", response = Collection.class)
	@Permission(permissionName = "readPaymentTypes")
	public Collection<PaymentType> getPaymentTypes(){
		return paymentTypeService.getPaymentTypes();
	}
	
	@GetMapping("/{id}")
	@ResponseBody
	@ApiOperation(value = "Get a payment type.", notes = "Get a payment type with given id.", response = PaymentType.class)
	@Permission(permissionName = "readPaymentType")
	public PaymentType getPaymentType(@PathVariable("id") Long id){
		return paymentTypeService.getPaymentType(id);
	}
	
	@PutMapping("/update")
	@ResponseBody
	@ApiOperation(value = "Update a payment type.", notes = "Update a single payment type.", response = PaymentType.class)
	@Permission(permissionName = "updatePaymentType")
	public PaymentType updatePaymentType(@RequestBody @Valid PaymentType paymentType){
		return paymentTypeService.updatePaymentType(paymentType);
	}
	
	@DeleteMapping("/delete/{id}")
	@ApiOperation(value = "Delete a payment type.", notes = "Delete a payment type with given id.")
	@Permission(permissionName = "removePaymentType")
	public void deletePaymentType(@PathVariable("id") Long id){
		paymentTypeService.deletePaymentType(id);
	}
	
	@PostMapping("/search")
	@ResponseBody
	@ApiOperation(value = "Search a payment types.", notes = "Search a payment types by given field.", response = Collection.class)
	@Permission(permissionName = "searchPaymentTypes")
	public Collection<PaymentType> searchPaymentTypes(@RequestBody PaymentType paymentType){
		return paymentTypeService.searchPaymentTypes(paymentType);
	}
	
}
