package com.service;

import java.util.Collection;

import com.model.PaymentType;

public interface PaymentTypeService {
	
	public PaymentType createPaymentType(PaymentType paymentType);
	public Collection<PaymentType> getPaymentTypes();
	public PaymentType updatePaymentType(PaymentType paymentType);
	public void deletePaymentType(Long id);
	
}
