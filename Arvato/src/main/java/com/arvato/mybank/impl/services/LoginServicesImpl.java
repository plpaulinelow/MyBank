package com.arvato.mybank.impl.services;

import java.util.List;

import com.arvato.mybank.api.services.LoginServices;
import com.arvato.mybank.classes.User;
import com.arvato.mybank.constants.Constants;

public class LoginServicesImpl implements LoginServices {
	
	public LoginServicesImpl() {
		super();
	}

	public Boolean validateUser(String username, String password) {
		Constants constants = new Constants();
		List<User> userList = constants.getUserList();
		if(userList.stream().anyMatch(u->u.getUsername().equals(username) && u.getPassword().equals(password))) {
			return true;
		}
		return false;
	}
}
