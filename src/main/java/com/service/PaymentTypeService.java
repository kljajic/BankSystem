package com.service;

import java.util.Collection;

import com.model.PaymentType;

public interface PaymentTypeService {
	
	PaymentType createPaymentType(PaymentType paymentType);
	Collection<PaymentType> getPaymentTypes();
	PaymentType getPaymentType(Long id);
	PaymentType updatePaymentType(PaymentType paymentType);
	void deletePaymentType(Long id);
	Collection<PaymentType> searchPaymentTypes(PaymentType paymentType);
	
}
