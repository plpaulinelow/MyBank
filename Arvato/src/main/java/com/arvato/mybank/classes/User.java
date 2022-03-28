package com.arvato.mybank.classes;

import java.io.Serializable;
/**
 * User Class
 * @author paulinelow
 *
 */
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private Integer accountId;
	
	public User() {
	}
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	

}
