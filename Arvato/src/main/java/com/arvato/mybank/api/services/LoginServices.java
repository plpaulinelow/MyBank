package com.arvato.mybank.api.services;

import com.arvato.mybank.constants.Constants;

public interface LoginServices {
	/**
	 * To validate if the user is in the database/system
	 * @param username
	 * @param password
	 * @return 
	 *	true if user exists, else false
	 */
	Boolean validateUser(String username, String password);
}
