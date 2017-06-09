package com.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.AnalyticalStatement;
import com.model.Bank;
import com.model.xml.RTGSRequest;
import com.repository.BankRepository;

@Service
public class InterBankServiceImpl implements InterBankService {

	@Autowired
	private BankRepository bankRepository;
	
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

			System.out.println("[INFO] Marshalling customized address book:");
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

	@Override
	public void generateClearingService(AnalyticalStatement as) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addToClearing(AnalyticalStatement as) {
		// TODO Auto-generated method stub
		
	}

}
