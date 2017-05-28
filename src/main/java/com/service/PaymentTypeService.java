package com.service;

import java.util.Collection;

import com.model.PaymentType;

public interface PaymentTypeService {
	
	public PaymentType createPaymentType(PaymentType paymentType);
	public Collection<PaymentType> getPaymentTypes();
	public PaymentType getPaymentType(Long id);
	public PaymentType updatePaymentType(PaymentType paymentType);
	public void deletePaymentType(Long id);
	public Collection<PaymentType> searchPaymentTypes(PaymentType paymentType);
	
}
