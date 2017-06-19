package com.webservice.client;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.transform.stream.StreamResult;

import org.springframework.oxm.XmlMappingException;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.bank.wsdl.Mt102Request;
import com.bank.wsdl.Mt103Request;
import com.bank.wsdl.Mt900Response;


public class CentralBankClient extends WebServiceGatewaySupport{
		
	
	public Mt900Response getRtgsResponse(Mt103Request request) throws XmlMappingException, IOException {
		
		final StringWriter out = new StringWriter();
		getWebServiceTemplate().getMarshaller().marshal(request, new StreamResult(out));
		String xml = out.toString();
		
		System.out.println(xml);
		
		
		getWebServiceTemplate().setDefaultUri("http://localhost:8090/ws/centralBankRtgsRequestService");
		Mt900Response response = (Mt900Response) getWebServiceTemplate()
				.marshalSendAndReceive("http://localhost:8090/ws/centralBankRtgsRequestService",
						request, new SoapActionCallback("http://com/xsdSchemas/mt900Response"));
		System.out.println(response);
		return response;
	}
	
	public boolean getMt102Request(Mt102Request req){

		final StringWriter out = new StringWriter();
		try {
			getWebServiceTemplate().getMarshaller().marshal(req, new StreamResult(out));
		} catch (XmlMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String xml = out.toString();
		
		System.out.println(xml);
		
		
		getWebServiceTemplate().setDefaultUri("http://localhost:8090/ws/centralBankClearingService");
		getWebServiceTemplate()
				.marshalSendAndReceive("http://localhost:8090/ws/centralBankClearingService",
						req, new SoapActionCallback("http://com/xsdSchemas/mt102"));
		
		return true;
	}
	
}
