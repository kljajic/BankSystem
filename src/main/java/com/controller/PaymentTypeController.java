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
import com.service.PaymentTypeService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/paymentTypes")
public class PaymentTypeController {

	private final PaymentTypeService paymentTypeService;
	
	@Autowired
	public PaymentTypeController(PaymentTypeService paymentTypeService) {
		this.paymentTypeService = paymentTypeService;
	}
	
	@PostMapping("/add")
	@ResponseBody
	@ApiOperation(value = "Add a peyment type.", notes = "Add a single payment type.", response = PaymentType.class)
	public PaymentType createPaymentType(@RequestBody @Valid PaymentType paymentType){
		return paymentTypeService.createPaymentType(paymentType);
	}
	
	@GetMapping
	@ResponseBody
	@ApiOperation(value = "Get all payment types.", notes = "Get all payment types.", response = Collection.class)
	public Collection<PaymentType> getPaymentTypes(){
		return paymentTypeService.getPaymentTypes();
	}
	
	@PutMapping("/update")
	@ResponseBody
	@ApiOperation(value = "Update a payment type.", notes = "Update a single payment type.", response = PaymentType.class)
	public PaymentType updatePaymentType(@RequestBody @Valid PaymentType paymentType){
		return paymentTypeService.updatePaymentType(paymentType);
	}
	
	@DeleteMapping("/delete/{id}")
	@ApiOperation(value = "Delete a payment type.", notes = "Delete a payment type with given id.")
	public void deletePaymentType(@PathVariable("id") Long id){
		paymentTypeService.deletePaymentType(id);
	}
	
}
