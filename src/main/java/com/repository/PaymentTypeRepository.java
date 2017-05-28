package com.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.model.PaymentType;

@Repository
public interface PaymentTypeRepository extends JpaRepository<PaymentType, Long> {

	@Query("select pt from PaymentType pt where upper(pt.paymentTypeName) like %?1%")
	Collection<PaymentType> search(String paymentTypeName);
	
}
