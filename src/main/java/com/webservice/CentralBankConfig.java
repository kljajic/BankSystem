package com.webservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.bank.wsdl.Mt103Request;
import com.model.xml.RTGSRequest;
//import com.centralbank.wsdl.RtgsRequest;
//import com.centralbank.wsdl.RtgsResponseOriginator;
import com.webservice.client.CentralBankClient;

@Configuration
public class CentralBankConfig {
	
	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPath("com.bank.wsdl");
		return marshaller;
	}

	@Bean
	public CentralBankClient centralBankClient(Jaxb2Marshaller marshaller) {
		CentralBankClient client = new CentralBankClient();
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}

}
