package com.arvato.mybank.classes;

import java.io.Serializable;

/**
 * Account Class
 * @author paulinelow
 *
 */
public class Account implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer accountId;
	private Double balance;
	
	public Account(Integer accountId) {
		this.accountId = accountId;
		this.balance = 0.0;
	}
	public Account() {
	}
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
	
}
