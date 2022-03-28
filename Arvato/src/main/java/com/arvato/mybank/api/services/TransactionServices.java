package com.arvato.mybank.api.services;

public interface TransactionServices {
	/**
	 * To check the account balance
	 * @param accountId
	 * @return
	 * 	the balance
	 */
	Double checkBalance(Integer accountId);
	
	/**
	 * To deposit the amount to the account
	 * @param accountId
	 * @param depositAmount
	 */
	void depositAmount(Integer accountId, Double depositAmount);
	
	/**
	 * To withdraw amount from the account
	 * @param accountId
	 * @param withdrawalAmount
	 */
	void withdrawalAmount(Integer accountId, Double withdrawalAmount);
	
	/**
	 * To transfer amount to another account
	 * @param accountId
	 * @param transferAccountId
	 * @param transferAmount
	 */
	void transferAmount(Integer accountId, Integer transferAccountId, Double transferAmount);
	
}
