package com.arvato.mybank.api.services;

import com.arvato.mybank.classes.Account;
/**
 * Interface for Account Services
 * @author paulinelow
 *
 */
public interface AccountServices {

	/**
	 * To retrieve the account from the user id
	 * @param username
	 * @return
	 * the user account
	 */
	Account retrieveAccountFromUserId(String username);
}
