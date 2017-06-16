package com.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.model.user.Permission;
import com.model.xml.ClearingSettlementRequest;
import com.service.ClearingSettlementRequestServiceImpl;

@RequestMapping("/clearingSettlementRequests")
@RestController
public class ClearingSettlementRequestController {

	@Autowired
	private ClearingSettlementRequestServiceImpl clearingSettlementRequestServiceImpl;
	
	@RequestMapping(path = "/getAllClearingSettlementRequests", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@Permission(permissionName="readAccounts")
	public ArrayList<ClearingSettlementRequest> getAllClearingSettlementRequests(){
		ArrayList<ClearingSettlementRequest> c = clearingSettlementRequestServiceImpl.getAllClearingSettlementRequests();
		return c;
	}
	
}
