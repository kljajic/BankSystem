package com.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.oxm.XmlMappingException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.bank.wsdl.Mt103Request;
import com.bank.wsdl.Mt900Response;
import com.model.AnalyticalStatement;
import com.model.Bank;
import com.model.xml.ClearingSettlementRequest;
import com.model.xml.RTGSRequest;
import com.model.xml.RTGSResponse;
import com.model.xml.RTGSResponseType;
import com.repository.BankRepository;
import com.repository.ClearingAndSettlementRequestRepository;
import com.repository.CurrencyExchangeRepository;
import com.repository.RTGSRequestRepository;
import com.webservice.client.CentralBankClient;



@Service
public class InterBankServiceImpl implements InterBankService {

	@Autowired
	private BankRepository bankRepository;
	
	@Autowired
	private CurrencyExchangeRepository currencyExchangeRepository;
	
	private ArrayList<ClearingSettlementRequest> clearingSettlementRequests = new ArrayList<>();
	
	@Autowired
	private RTGSRequestRepository rtgsRequestRepository;
	
	@Autowired
	private ClearingAndSettlementRequestRepository clearingAndSettlementRequestRepository;
	
	@Autowired
	private ApplicationContext con;
	
	@Autowired
	private RTGSResponseService rtgsResponseService;
	
	@Override
	public void generateRTGSService(AnalyticalStatement as) {
		System.out.println("YOYOYOYOYOYO");
		RTGSRequest req = new RTGSRequest();
		req.setAnalyticalStatement(as);
		Bank paymentBank = bankRepository.findBankByLeadNumber(as.getOriginatorAccount().substring(0, 3));
		req.setPaymentBank(paymentBank);
		Bank receiverBank = bankRepository.findBankByLeadNumber(as.getRecipientAccount().substring(0, 3));
		req.setRecieverBank(receiverBank);
		req.setMessageId("MT103");
		
		rtgsRequestRepository.save(req);

		JAXBContext context;
		try {
			context = JAXBContext.newInstance(RTGSRequest.class);
			Marshaller marshaller = context.createMarshaller();
			
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			System.out.println("[INFO] Marshalling...");
            OutputStream os;
			CentralBankClient cbc = (CentralBankClient) con.getBean(CentralBankClient.class);
			Mt900Response mt;
			Mt103Request mt103Request = mapAnalyticalStatementToMt103Request(as, paymentBank, receiverBank);
			
			try {
				mt = cbc.getRtgsResponse(mt103Request);
				System.out.println(mt.getAmount());
				
				RTGSResponse rtgsResponse = new RTGSResponse();
				rtgsResponse.setMessageId(mt.getMessageId());
				rtgsResponse.setBank(paymentBank);
				rtgsResponse.setCurrencyCode(mt.getCurrency());
				rtgsResponse.setCurrencyDate(mt.getCurrencyDate().toGregorianCalendar().getTime());
				rtgsResponse.setRequestId(mt.getRequestMessageId());
				rtgsResponse.setValue(mt.getAmount().doubleValue());
				rtgsResponse.setResponseType(RTGSResponseType.PaymentAccount);
				rtgsResponseService.save(rtgsResponse);
				
			} catch (XmlMappingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            
			try {
				os = new FileOutputStream("MT103.xml");
				marshaller.marshal(req, os);
				os.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//marshaller.marshal(req, System.out);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatatypeConfigurationException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		
	}

	@Scheduled(fixedRate = 120000)
	@Override
	public void generateClearingService() {
		// TODO Auto-generated method stub
		System.out.println("generating clearing...");
		if(clearingSettlementRequests != null){
			for(int i = 0; i < clearingSettlementRequests.size(); i++){
				JAXBContext context;
				try {
					context = JAXBContext.newInstance(ClearingSettlementRequest.class);
					Marshaller marshaller = context.createMarshaller();
					
					marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
					ClearingSettlementRequest csr = clearingSettlementRequests.get(i);
					clearingAndSettlementRequestRepository.save(csr);
					System.out.println("[INFO] Marshalling...");
		            OutputStream os;
					try {
						String nameOfFile = "MT102_" + i + ".xml";
						os = new FileOutputStream(nameOfFile);
						marshaller.marshal(clearingSettlementRequests.get(i), os);
						os.close();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (JAXBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
			}
		}
		clearingSettlementRequests.clear();
	}

	@Override
	public void addToClearing(AnalyticalStatement as) {
		boolean flag = false;
		int tempIndex = 0;
		for(int i = 0; i < clearingSettlementRequests.size(); i++){
			ClearingSettlementRequest csr = clearingSettlementRequests.get(i);
			if(csr.getCurrency().getOfficialCode().equals(as.getCurrency().getOfficialCode()) && csr.getCurrencyDate().equals(as.getCurrencyDate())
					&& csr.getPaymentBank().getTransactionAccount().substring(0, 3).equals(as.getOriginatorAccount().substring(0, 3))
					&& csr.getRecieverBank().getTransactionAccount().substring(0, 3).equals(as.getRecipientAccount().substring(0, 3))){
				flag = true;
				tempIndex = i;
			}
		}
		if(flag){
			clearingSettlementRequests.get(tempIndex).getAnalyticalStatements().add(as);
			clearingSettlementRequests.get(tempIndex).setTotalAmount(clearingSettlementRequests.get(tempIndex).getTotalAmount() + as.getAmount());
			System.out.println("flag");
		} else {
			ClearingSettlementRequest csr = new ClearingSettlementRequest();
			Bank paymentBank = bankRepository.findBankByLeadNumber(as.getOriginatorAccount().substring(0, 3));
			csr.setPaymentBank(paymentBank);
			Bank receiverBank = bankRepository.findBankByLeadNumber(as.getRecipientAccount().substring(0, 3));
			csr.setRecieverBank(receiverBank);
			csr.setTotalAmount(as.getAmount());
			csr.setCurrency(as.getCurrency());
			csr.setCurrencyDate(as.getCurrencyDate());
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date date = new Date();
			csr.setDate(date);
			HashSet<AnalyticalStatement> tempAS = new HashSet<>();
			tempAS.add(as);
			csr.setAnalyticalStatements(tempAS);
			clearingSettlementRequests.add(csr);
			System.out.println("nije flag");
		}
		System.out.println("VELICINAAAAAAA " + clearingSettlementRequests.size());
	}

	@Override
	public void RTGSOrClearing(AnalyticalStatement as) {
		double sum;
		if(!as.getCurrency().getOfficialCode().equals("DIN")){
			sum = as.getAmount() * currencyExchangeRepository.findMiddleRateAccordingToDinars(as.getCurrency().getOfficialCode());
		} else {
			sum = as.getAmount();
		}
		if(sum >= 250000 || as.isUrgently()){
			System.out.println("RATATAATATA");
			generateRTGSService(as);
			} else {
				addToClearing(as);
			}
		}
	
	
	private Mt103Request mapAnalyticalStatementToMt103Request(AnalyticalStatement as, Bank originatorBank, Bank recieverBank) throws DatatypeConfigurationException{
		GregorianCalendar c = new GregorianCalendar();
		
		Mt103Request mt103Request = new Mt103Request();
		mt103Request.setMessageId("mt103");
		mt103Request.setAmount(new BigDecimal(as.getAmount()));
		mt103Request.setChargeDialNumber(as.getDebitAuthorizationNumber());
		mt103Request.setChargeModel(as.getModel());
		mt103Request.setClearanceDialNumber(as.getApprovalAuthorizationNumber());
		mt103Request.setClearanceModel(as.getApprovalModel());
		mt103Request.setCurrency(as.getCurrency().getOfficialCode());
		
		c.setTime(as.getCurrencyDate());
		XMLGregorianCalendar currencyDateXml = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		mt103Request.setCurrencyDate(currencyDateXml);
		
		mt103Request.setOriginator(as.getOriginator());
		mt103Request.setOriginatorAccountNumber(as.getOriginatorAccount());
		mt103Request.setOriginatorBankSwiftCode(originatorBank.getSwift());
		mt103Request.setOriginatorBankTransactionAccount(originatorBank.getTransactionAccount());
		mt103Request.setPaymentPurpose(as.getPurpose());
		mt103Request.setReciever(as.getRecipient());
		mt103Request.setRecieverAccountNumber(as.getRecipientAccount());
		mt103Request.setRecieverBankSwiftCode(recieverBank.getSwift());
		mt103Request.setRecieverBankTransactionAccount(recieverBank.getTransactionAccount());
		
		c.setTime(as.getDateOfReceipt());
		XMLGregorianCalendar dateOfReceit = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		mt103Request.setStatementDate(dateOfReceit);
		
		return mt103Request;
	}
}
