package com.arvato.mybank.api.services;
/**
 * Interface for LoginServices
 * @author paulinelow
 *
 */
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
