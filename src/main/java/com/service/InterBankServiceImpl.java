package com.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.model.AnalyticalStatement;
import com.model.Bank;
import com.model.xml.ClearingSettlementRequest;
import com.model.xml.RTGSRequest;
import com.repository.BankRepository;
import com.repository.CurrencyExchangeRepository;


@Service
public class InterBankServiceImpl implements InterBankService {

	@Autowired
	private BankRepository bankRepository;
	
	@Autowired
	private CurrencyExchangeRepository currencyExchangeRepository;
	
	private static ArrayList<ClearingSettlementRequest> clearingSettlementRequests = new ArrayList<>();
	
	@Override
	public void generateRTGSService(AnalyticalStatement as) {
		System.out.println("YOYOYOYOYOYO");
		RTGSRequest req = new RTGSRequest();
		req.setAnalyticalStatement(as);
		Bank paymentBank = bankRepository.findBankByLeadNumber(as.getOriginatorAccount().substring(0, 3));
		req.setPaymentBank(paymentBank);
		Bank receiverBank = bankRepository.findBankByLeadNumber(as.getRecipientAccount().substring(0, 3));
		req.setRecieverBank(receiverBank);
		
		JAXBContext context;
		try {
			context = JAXBContext.newInstance(RTGSRequest.class);
			Marshaller marshaller = context.createMarshaller();
			
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			System.out.println("[INFO] Marshalling...");
            OutputStream os;
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
		}
		
		
	}

	@Scheduled(fixedRate = 30000)
	@Override
	public void generateClearingService() {
		// TODO Auto-generated method stub
		System.out.println("generating clearing...");
		if(clearingSettlementRequests != null){
			for(int i = 0; i < clearingSettlementRequests.size(); i++){
				JAXBContext context;
				try {
					context = JAXBContext.newInstance(RTGSRequest.class);
					Marshaller marshaller = context.createMarshaller();
					
					marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	
					System.out.println("[INFO] Marshalling...");
		            OutputStream os;
					try {
						String nameOfFile = "MT102" + i + ".xml";
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
			if(csr.getCurrency().equals(as.getCurrency()) && csr.getCurrencyDate().equals(as.getCurrencyDate())
					&& csr.getPaymentBank().getTransactionAccount().substring(0, 3).equals(as.getOriginatorAccount().substring(0, 3))
					&& csr.getRecieverBank().getTransactionAccount().substring(0, 3).equals(as.getRecipientAccount().substring(0, 3))){
				flag = true;
				tempIndex = i;
			}
		}
		if(flag){
			clearingSettlementRequests.get(tempIndex).getAnalyticalStatements().add(as);
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
			csr.getAnalyticalStatements().add(as);
			clearingSettlementRequests.add(csr);
		}
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
}
