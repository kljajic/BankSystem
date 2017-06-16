package com.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.model.user.Permission;
import com.model.xml.RTGSRequest;
import com.service.RTGSRequestServiceImpl;

@RequestMapping("/RTGSRequests")
@RestController
public class RTGSRequestController {
	
	@Autowired
	RTGSRequestServiceImpl rtgsRequestServiceImpl;
	
	@RequestMapping(path = "/getAllRTGSRequests", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@Permission(permissionName="readAccounts")
	public ArrayList<RTGSRequest> getAllRTGSRequests(){
		ArrayList<RTGSRequest> r = rtgsRequestServiceImpl.getAllRTGSRequestServices();
		return r;
	}

}
