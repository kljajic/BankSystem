package com.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.model.Account;
import com.model.AnalyticalStatement;
import com.model.AnalyticalStatementMode;
import com.model.DailyAccountStatus;
import com.repository.AnalyticalStatementRepository;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Service
@Transactional
public class AnaltyicalStatementServiceImpl implements AnaltyicalStatementService {
	
	private final AnalyticalStatementRepository analyticalStatementRepository;
	private final CurrencyService currencyService;
	private final CityService cityService;
	private final DailyAccountStatusService dailyAccountStatusService;
	private final InterBankService interBankService;
	private final AccountService accountService;
	
    @Autowired
    DataSource dataSource;
	
	@Autowired
	public AnaltyicalStatementServiceImpl(AnalyticalStatementRepository analyticalStatementRepository,
										  CurrencyService currencyService,
										  CityService cityService,
										  DailyAccountStatusService dailyAccountStatusService,
										  InterBankService interBankService,
										  AccountService accountService
										  ){
		this.analyticalStatementRepository = analyticalStatementRepository;
		this.currencyService = currencyService;
		this.cityService = cityService;
		this.dailyAccountStatusService = dailyAccountStatusService;
		this.interBankService = interBankService;
		this.accountService = accountService;
	}

	@Override
	public Collection<AnalyticalStatement> createAnalyticalStatement(String currencyId, String cityId, 
														  Date dateOfReceipt, Date currencyDate, AnalyticalStatement analyticalStatement) {
		if(currencyId != null && !currencyId.trim().equals("NOT_ENTERED"))
			analyticalStatement.setCurrency(currencyService.getCurrency(new Long(currencyId)));
		if(cityId != null && !cityId.trim().equals("NOT_ENTERED"))
			analyticalStatement.setPlaceOfAcceptance(cityService.getCity(new Long(cityId)));
		analyticalStatement.setDateOfReceipt(dateOfReceipt);
		analyticalStatement.setCurrencyDate(currencyDate);
		analyticalStatement.setUplata(false);
		return this.doTransaction(analyticalStatement);
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<AnalyticalStatement> getAnalyticalStatements() {
		return analyticalStatementRepository.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public AnalyticalStatement getAnalyticalStatement(Long id) {
		return analyticalStatementRepository.findOne(id);
	}

	@Override
	public Collection<AnalyticalStatement> updateAnalyticalStatement(String currencyId, String cityId, 
														 Date dateOfReceipt, Date currencyDate, AnalyticalStatement analyticalStatement) {
		Collection<AnalyticalStatement> analyticalStatements = new ArrayList<>();
		AnalyticalStatement temp = analyticalStatementRepository.findOne(analyticalStatement.getId());
		analyticalStatements.addAll(this.undoTransaction(temp));
		temp.setAmount(analyticalStatement.getAmount());
		temp.setApprovalAuthorizationNumber(analyticalStatement.getApprovalAuthorizationNumber());
		temp.setApprovalModel(analyticalStatement.getApprovalModel());
		if(currencyId != null && !currencyId.trim().equals("NOT_ENTERED"))
			temp.setCurrency(currencyService.getCurrency(new Long(currencyId)));
		if(cityId != null && !cityId.trim().equals("NOT_ENTERED"))
			temp.setPlaceOfAcceptance(cityService.getCity(new Long(cityId)));
		temp.setCurrencyDate(currencyDate);
		temp.setDateOfReceipt(dateOfReceipt);
		temp.setDebitAuthorizationNumber(analyticalStatement.getDebitAuthorizationNumber());
		temp.setErrorType(analyticalStatement.getErrorType());
		temp.setModel(analyticalStatement.getModel());
		temp.setOriginator(analyticalStatement.getOriginator());
		temp.setOriginatorAccount(analyticalStatement.getOriginatorAccount());
		temp.setPurpose(analyticalStatement.getPurpose());
		temp.setRecipient(analyticalStatement.getRecipient());
		temp.setRecipientAccount(analyticalStatement.getRecipientAccount());
		temp.setUplata(analyticalStatement.isUplata());
		temp.setUrgently(analyticalStatement.isUrgently());
		analyticalStatements.addAll(this.doTransaction(cloneAnalyticalStatement(temp)));
		return analyticalStatements;
	}

	@Override
	public Collection<AnalyticalStatement> deleteAnalyticalStatement(Long id) {
		Collection<AnalyticalStatement> analyticalStatements = new ArrayList<>();
		AnalyticalStatement analyticalStatement = this.getAnalyticalStatement(id);
		analyticalStatements.addAll(this.undoTransaction(analyticalStatement));
		return analyticalStatements;
	}


	@Override
	@Transactional(readOnly = true)
	public Collection<AnalyticalStatement> getAnalyticalStatementsByDailyAccountStatusId(Long id) {
		return analyticalStatementRepository.findAnalyticalStatementsByDailyAccountStatusId(id);
	}

	@Override
	public Collection<AnalyticalStatement> searchAnalyticalStatements(Long currencyId,
			Long cityId, Long dailyAccountStatusId, Date dateOfReceipt, Date currencyDate,
			AnalyticalStatement analyticalStatement) {
		String currencyCode = "";
		if(currencyId != null && currencyId >= 0){
			currencyCode = currencyService.getCurrency(currencyId).getOfficialCode();
		}
		String cityPttNumber = "";
		if(cityId != null && cityId >= 0){
			cityPttNumber = cityService.getCity(cityId).getPttNumber();
		}
		String accountNumber = "";
		if(dailyAccountStatusId != null && dailyAccountStatusId >= 0){
			accountNumber = dailyAccountStatusService.getDailyAccountStatus(dailyAccountStatusId).getAccount().getAccountNumber();
		}
		String originator = "";
		if(analyticalStatement.getOriginator() != null && !analyticalStatement.getOriginator().trim().equals("")){
			originator = analyticalStatement.getOriginator();
		}
		String purpose = "";
		if(analyticalStatement.getPurpose() != null && !analyticalStatement.getPurpose().trim().equals("")){
			purpose = analyticalStatement.getPurpose();
		}
		String recipient = "";
		if(analyticalStatement.getRecipient() != null && !analyticalStatement.getRecipient().trim().equals("")){
			recipient = analyticalStatement.getRecipient();
		}
		if(dateOfReceipt == null){
			dateOfReceipt = new Date(Long.MAX_VALUE);
		}
		if(currencyDate == null){
			currencyDate = new Date(Long.MAX_VALUE);
		}
		String originatorAccount = "";
		if(analyticalStatement.getOriginatorAccount() != null && !analyticalStatement.getOriginatorAccount().trim().equals("")){
			originatorAccount = analyticalStatement.getOriginatorAccount();
		}
		String model = "";
		if(analyticalStatement.getModel() > 0){
			model = analyticalStatement.getModel() + "";
		}
		String debitAuthorizationNumber = "";
		if(analyticalStatement.getDebitAuthorizationNumber() != null && !analyticalStatement.getDebitAuthorizationNumber().trim().equals("")){
			debitAuthorizationNumber = analyticalStatement.getDebitAuthorizationNumber();
		}
		String recipientAccount = "";
		if(analyticalStatement.getRecipientAccount() != null && !analyticalStatement.getRecipientAccount().trim().equals("")){
			recipientAccount = analyticalStatement.getRecipientAccount();
		}
		String approvalModel = "";
		if(analyticalStatement.getApprovalModel() > 0){
			approvalModel = analyticalStatement.getApprovalModel() + "";
		}
		String approvalAuthorizationNumber = "";
		if(analyticalStatement.getApprovalAuthorizationNumber() != null && !analyticalStatement.getApprovalAuthorizationNumber().trim().equals("")){
			approvalAuthorizationNumber = analyticalStatement.getApprovalAuthorizationNumber();
		}
		boolean urgently = analyticalStatement.isUrgently();
		Double amount = new Double(Double.MAX_VALUE);
		if(analyticalStatement.getAmount() > 0){
			amount = new Double(analyticalStatement.getAmount());
		}
		Date minimumDate = new Date(Long.MIN_VALUE);
		
		Calendar cal =Calendar.getInstance();
		cal.setTime(dateOfReceipt);
		cal.add(Calendar.HOUR_OF_DAY, 24);
		dateOfReceipt =cal.getTime();
		
		cal.setTime(currencyDate);
		cal.add(Calendar.HOUR_OF_DAY, 24);
		currencyDate =cal.getTime();
		
		return analyticalStatementRepository.searchAnalyticalStatements(currencyCode,
								cityPttNumber, accountNumber, originator, purpose, recipient,
								minimumDate, dateOfReceipt, currencyDate, originatorAccount, model,
								debitAuthorizationNumber, recipientAccount, approvalModel, 
								approvalAuthorizationNumber, urgently, amount);
	}
	
	public Collection<AnalyticalStatement> doTransaction(AnalyticalStatement analyticalStatement){
		ArrayList<AnalyticalStatement> analyticalStatements = new ArrayList<>();
		if(analyticalStatement.getAnalyticalStatementMode() == AnalyticalStatementMode.TRANSFER){
			if(!analyticalStatement.getOriginatorAccount().substring(0, 3).equals(analyticalStatement.getRecipientAccount().substring(0, 3))){
				analyticalStatements.add(this.updateOriginatorStatus(analyticalStatement));
				interBankService.RTGSOrClearing(analyticalStatement);
			}else{
				analyticalStatements.add(this.updateOriginatorStatus(analyticalStatement));
				analyticalStatements.add(this.updateRecipientStatus(analyticalStatement));
			}
		}else if(analyticalStatement.getAnalyticalStatementMode() == AnalyticalStatementMode.PAYMENT){
			analyticalStatements.add(this.updateRecipientStatus(analyticalStatement));
		}else{
			analyticalStatements.add(this.updateOriginatorStatus(analyticalStatement));
		}
		return analyticalStatements;
	}
	
	public Collection<AnalyticalStatement> undoTransaction(AnalyticalStatement analyticalStatement){
		ArrayList<AnalyticalStatement> analyticalStatements = new ArrayList<>();
		
		AnalyticalStatement undoAnalyticalStatement = cloneAnalyticalStatement(analyticalStatement);
		undoAnalyticalStatement.setRecipient(analyticalStatement.getOriginator());
		undoAnalyticalStatement.setRecipientAccount(analyticalStatement.getOriginatorAccount());
		undoAnalyticalStatement.setApprovalModel(analyticalStatement.getModel());
		undoAnalyticalStatement.setApprovalAuthorizationNumber(analyticalStatement.getDebitAuthorizationNumber());
		undoAnalyticalStatement.setOriginator(analyticalStatement.getRecipient());
		undoAnalyticalStatement.setOriginatorAccount(analyticalStatement.getRecipientAccount());
		undoAnalyticalStatement.setModel(analyticalStatement.getApprovalModel());
		undoAnalyticalStatement.setDebitAuthorizationNumber(analyticalStatement.getApprovalAuthorizationNumber());
		
		if(analyticalStatement.getAnalyticalStatementMode() == AnalyticalStatementMode.TRANSFER){
			if(!analyticalStatement.getOriginatorAccount().substring(0, 3).equals(analyticalStatement.getRecipientAccount().substring(0, 3))){
				
			}else{
				analyticalStatements.add(this.updateOriginatorStatus(undoAnalyticalStatement));
				analyticalStatements.add(this.updateRecipientStatus(undoAnalyticalStatement));
			}
		}else if(analyticalStatement.getAnalyticalStatementMode() == AnalyticalStatementMode.PAYMENT){
			analyticalStatements.add(this.updateRecipientStatus(analyticalStatement));
		}else{
			analyticalStatements.add(this.updateOriginatorStatus(analyticalStatement));
		}
		return analyticalStatements;
	}
	
	private AnalyticalStatement updateOriginatorStatus(AnalyticalStatement analyticalStatement){
		DailyAccountStatus originatorDailyAccountStatus = dailyAccountStatusService.updateOriginatorDailyAccountStatus(analyticalStatement);
		analyticalStatement.setDailyAccountStatus(originatorDailyAccountStatus);
		return analyticalStatementRepository.save(analyticalStatement);
	}
	
	private AnalyticalStatement updateRecipientStatus(AnalyticalStatement analyticalStatement){
		DailyAccountStatus recipientDailyAccountStatus = dailyAccountStatusService.updateRecipiantDailyAccountStatus(analyticalStatement);
		AnalyticalStatement recipientAnalyticalStatement = cloneAnalyticalStatement(analyticalStatement); //shallow copy
		recipientAnalyticalStatement.setDailyAccountStatus(recipientDailyAccountStatus);
		recipientAnalyticalStatement.setUplata(true);
		return analyticalStatementRepository.save(recipientAnalyticalStatement);
	}

	@Override
	public void exportToPdf(Long accountId, Date startDate,Date endDate,HttpServletResponse response) throws JRException, SQLException, IOException {
		Account a = accountService.getAccount(accountId);
	    Map<String,Object> params = new HashMap<>();
	    params.put("bankAccount", a.getAccountNumber());
	    params.put("startDate", startDate);
	    params.put("endDate", endDate);
	    params.put("client", a.getClient().getName() + " " +  a.getClient().getSurname());
	    URL url = this.getClass().getClassLoader().getResource("jasper/logo.png");
	    params.put("logo", url);
	    FileInputStream fileInputStream;
	    params.put("address", a.getClient().getAddress());
		JasperPrint jp  = JasperFillManager.fillReport(getClass().getResource("/jasper/BankReport.jasper").openStream(),params, dataSource.getConnection());
	    File pdf = new File(System.getProperty("user.home") + "/Downloads/" + "izvestaj-"+a.getAccountNumber()+".pdf");
		FileOutputStream out = new FileOutputStream(pdf);
		JasperExportManager.exportReportToPdfStream(jp, out);
		fileInputStream = new FileInputStream(pdf);
		response.setContentLengthLong(pdf.length());
		IOUtils.copy(fileInputStream, response.getOutputStream());
		fileInputStream.close();
		out.close();
		response.flushBuffer();
	}
	
	public AnalyticalStatement cloneAnalyticalStatement(AnalyticalStatement as) {
		AnalyticalStatement clone = new AnalyticalStatement();
		clone.setAmount(as.getAmount());
		clone.setApprovalAuthorizationNumber(as.getApprovalAuthorizationNumber());
		clone.setApprovalModel(as.getApprovalModel());
		clone.setCurrency(as.getCurrency());
		clone.setCurrencyDate(as.getCurrencyDate());
		clone.setDebitAuthorizationNumber(as.getDebitAuthorizationNumber());
		clone.setErrorType(as.getErrorType());
		clone.setModel(as.getModel());
		clone.setOriginator(as.getOriginator());
		clone.setOriginatorAccount(as.getOriginatorAccount());
		clone.setPlaceOfAcceptance(as.getPlaceOfAcceptance());
		clone.setPurpose(as.getPurpose());
		clone.setDateOfReceipt(as.getDateOfReceipt());
		clone.setRecipient(as.getRecipient());
		clone.setRecipientAccount(as.getRecipientAccount());
		clone.setUrgently(as.isUrgently());
		clone.setAnalyticalStatementMode(as.getAnalyticalStatementMode());
		return clone;
	}

	@Override
	public List<AnalyticalStatement> getAnalyticalStatementsForDailyAccountStatusId(Pageable pageable,
			Long dailyAccountStatusId) {
		return this.analyticalStatementRepository.findAnalyticalStatementsByDailyAccountStatusId(pageable, dailyAccountStatusId);
	}
	
	@Override
	public void receiveClearingOrRtgs(AnalyticalStatement analyticalStatement) {
		DailyAccountStatus recipientDailyAccountStatus = dailyAccountStatusService.updateRecipiantDailyAccountStatus(analyticalStatement);
		analyticalStatement.setDailyAccountStatus(recipientDailyAccountStatus);
		analyticalStatementRepository.save(analyticalStatement);
	}
	
}
