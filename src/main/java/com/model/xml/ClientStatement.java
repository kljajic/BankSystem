package com.model.xml;

import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.model.Account;
import com.model.AnalyticalStatement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name="clientStatement")
@XmlType(name = "invoice", 
		namespace="http://com/model/clientStatement", 
		propOrder = {"account",
					 "statements",
					})
public class ClientStatement {
	
	public ClientStatement() {}
	
	@XmlElement(name="account", required=true)
	private Account account;
	
	@XmlElement(name="statements", required=true)
	private List<AnalyticalStatement> statements;

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public List<AnalyticalStatement> getStatements() {
		return statements;
	}

	public void setStatements(List<AnalyticalStatement> statements) {
		this.statements = statements;
	}
}


