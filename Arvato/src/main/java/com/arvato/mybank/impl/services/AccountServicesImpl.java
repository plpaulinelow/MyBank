package com.arvato.mybank.impl.services;

import com.arvato.mybank.api.services.AccountServices;
import com.arvato.mybank.classes.Account;
import com.arvato.mybank.classes.User;
import static com.arvato.mybank.constants.Constants.INT_ZERO_VALUE;
import com.arvato.mybank.constants.Constants;
/**
 * AccountServices implementation
 * @author paulinelow
 *
 */
public class AccountServicesImpl implements AccountServices{
	
	public AccountServicesImpl() {
		super();
	}
	
	public Account retrieveAccountFromUserId(String username) {
		Constants constants = new Constants();
		Integer accountId = INT_ZERO_VALUE;
		for(User user:constants.getUserList()) {
			if(username.equals(user.getUsername())){
				accountId= user.getAccountId();
				break;
			}
		}		
		return retrieveAccountFromAccountId(accountId);
	}
	
	public Account retrieveAccountFromAccountId(Integer accountId) {
		Constants constants = new Constants();
		if(accountId>INT_ZERO_VALUE) {
			for(Account account:constants.getAccountList()) {
				if(accountId.equals(account.getAccountId())) {
					return account;
				}
			}
		}
		return null;
	}
}
