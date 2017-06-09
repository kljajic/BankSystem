package com.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.model.Bank;

@Repository
public interface BankRepository extends JpaRepository<Bank,Long>{
	
	@Query("select b from Bank  as b where b.name like ?1 and b.pib like ?2 and b.address like ?3 and b.email like ?4 and b.web like ?5 and b.telephone like ?6 and b.fax like ?7 and b.swift like ?8 and b.transactionAccount like ?9 and b.country.name = ?10")
	ArrayList<Bank> searchBanks(String name,String pib,String address, String email,String web, String telephone,String fax, String swift, String transactionAccount, String countryName);

	@Query("select b from Bank as b where substring(b.transactionAccount, 1, 3)=?1")
	Bank findBankByLeadNumber(String substring);

}
