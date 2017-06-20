package com.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.bank.wsdl.GetUserRequest;
import com.bank.wsdl.GetUserResponse;
import com.model.user.Role;
import com.model.user.User;
import com.service.UserService;

@Endpoint
public class UserEndpoint {
		
	private static final String NAMESPACE = "http://certificate.com/user";
	@Autowired
	private UserService userService;

	
	@PayloadRoot(namespace=NAMESPACE , localPart="getUserResponse")
	@ResponsePayload
	public GetUserResponse getUserResponse(@RequestPayload GetUserRequest userRequest){
		
		GetUserResponse response = new GetUserResponse();
		
		User u = userService.getUser(userRequest.getEmail());
		System.out.println("usao");
		
		if(u != null){
			for(Role r : u.getRoles()){
				if(r.getName().equals("BANK_ADMIN")){
					response.setVerified(true);
					return response;
				}
			}
			
		}
		response.setVerified(false);
		return response;

	}
	
}

