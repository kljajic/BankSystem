package com.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.model.PaymentType;
import com.repository.PaymentTypeRepository;

@Service
@Transactional
public class PaymentTypeServiceImpl implements PaymentTypeService {

	private final PaymentTypeRepository paymentTypeRepository;
	
	@Autowired
	public PaymentTypeServiceImpl(PaymentTypeRepository paymentTypeRepository) {
		this.paymentTypeRepository = paymentTypeRepository;
	}
	
	@Override
	public PaymentType createPaymentType(PaymentType paymentType) {
		return paymentTypeRepository.save(paymentType);
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<PaymentType> getPaymentTypes() {
		return paymentTypeRepository.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public PaymentType getPaymentType(Long id) {
		return paymentTypeRepository.findOne(id);
	}

	@Override
	public PaymentType updatePaymentType(PaymentType paymentType) {
		PaymentType temp = paymentTypeRepository.findOne(paymentType.getId());
		temp.setPaymentTypeName(paymentType.getPaymentTypeName());
		return paymentTypeRepository.save(temp);
	}

	@Override
	public void deletePaymentType(Long id) {
		paymentTypeRepository.delete(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Collection<PaymentType> searchPaymentTypes(PaymentType paymentType) {
		return paymentTypeRepository.search(paymentType.getPaymentTypeName().toUpperCase());
	}

}
