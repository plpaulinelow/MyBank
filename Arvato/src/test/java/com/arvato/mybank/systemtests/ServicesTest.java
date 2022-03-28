package com.arvato.mybank.systemtests;

import org.junit.jupiter.api.Test;

import com.arvato.mybank.classes.Account;
import com.arvato.mybank.classes.User;
import com.arvato.mybank.constants.Constants;
import com.arvato.mybank.impl.services.AccountServicesImpl;
import com.arvato.mybank.impl.services.TransactionServicesImpl;

import junit.framework.Assert;

@SuppressWarnings("deprecation")
public class ServicesTest {
	private TransactionServicesImpl transactionServices = new TransactionServicesImpl();
	private AccountServicesImpl accountServices = new AccountServicesImpl();
	
	private Integer USER_1_ACCOUNT_ID = 12341;
	private Integer USER_2_ACCOUNT_ID = 12342;
	private Double USER_1_INITIAL_BALANCE;
	private Double USER_2_INITIAL_BALANCE;
	private Double USER_1_BALANCE = 10.00;
	private Double USER_2_BALANCE = 10.00;
	
	private Account account1;
	private Account account2;
		
	@Test
	public void testTransferMultipleUser() {
		setup();
		
		transactionServices.updateAccountBalance(USER_1_ACCOUNT_ID, USER_1_BALANCE);
		transactionServices.updateAccountBalance(USER_2_ACCOUNT_ID, USER_2_BALANCE);
		Account acc1 = accountServices.retrieveAccountFromAccountId(USER_1_ACCOUNT_ID);
		Account acc2 = accountServices.retrieveAccountFromAccountId(USER_2_ACCOUNT_ID);
		
		Assert.assertEquals(USER_1_BALANCE, acc1.getBalance());
		Assert.assertEquals(USER_2_BALANCE, acc2.getBalance());
		
		transactionServices.transferAmount(USER_1_ACCOUNT_ID, USER_2_ACCOUNT_ID, 5.0);
		acc1 = accountServices.retrieveAccountFromAccountId(USER_1_ACCOUNT_ID);
		acc2 = accountServices.retrieveAccountFromAccountId(USER_2_ACCOUNT_ID);
		
		Assert.assertEquals(5.0, acc1.getBalance());
		Assert.assertEquals(15.0, acc2.getBalance());
		
		reset();
	}
	
	@Test
	public void testDeposit() {
		setup();
		
		transactionServices.updateAccountBalance(USER_1_ACCOUNT_ID, USER_1_BALANCE);
		Account acc1 = accountServices.retrieveAccountFromAccountId(USER_1_ACCOUNT_ID);
		
		Assert.assertEquals(USER_1_BALANCE, acc1.getBalance());
		
		transactionServices.depositAmount(USER_1_ACCOUNT_ID, 5.0);
		acc1 = accountServices.retrieveAccountFromAccountId(USER_1_ACCOUNT_ID);
		
		Assert.assertEquals(15.0, acc1.getBalance());
		
		reset();
	}
	
	@Test
	public void testWithdrawal() {
		setup();
		
		transactionServices.updateAccountBalance(USER_1_ACCOUNT_ID, USER_1_BALANCE);
		Account acc1 = accountServices.retrieveAccountFromAccountId(USER_1_ACCOUNT_ID);
		
		Assert.assertEquals(USER_1_BALANCE, acc1.getBalance());
		
		transactionServices.withdrawalAmount(USER_1_ACCOUNT_ID, 5.0);
		acc1 = accountServices.retrieveAccountFromAccountId(USER_1_ACCOUNT_ID);
		
		Assert.assertEquals(5.0, acc1.getBalance());
		
		reset();
	}
	
	private void setup() {
		account1 = accountServices.retrieveAccountFromAccountId(USER_1_ACCOUNT_ID);
		account2 = accountServices.retrieveAccountFromAccountId(USER_2_ACCOUNT_ID);
		this.USER_1_INITIAL_BALANCE = account1.getBalance();
		this.USER_2_INITIAL_BALANCE = account2.getBalance();
	}
	
	private void reset() {
		transactionServices.updateAccountBalance(USER_1_ACCOUNT_ID, USER_1_INITIAL_BALANCE);
		transactionServices.updateAccountBalance(USER_2_ACCOUNT_ID, USER_2_INITIAL_BALANCE);
	}
	
}
