package com.arvato.mybank.api.services;

import com.arvato.mybank.classes.Account;
import com.arvato.mybank.constants.Constants;

public interface AccountServices {

	/**
	 * To retrieve the account from the user id
	 * @param username
	 * @return
	 * the user account
	 */
	Account retrieveAccountFromUserId(String username);
}
