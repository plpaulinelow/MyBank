package com.arvato.mybank.pages;

import java.io.IOException;

import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import com.arvato.mybank.constants.Constants;
import com.arvato.mybank.impl.services.AccountServicesImpl;
import com.arvato.mybank.impl.services.LoginServicesImpl;

import static com.arvato.mybank.constants.Constants.CONSTANTS_ID;
import static com.arvato.mybank.constants.Constants.USERNAME;
import static com.arvato.mybank.constants.Constants.ACCOUNT_ID;
import static com.arvato.mybank.constants.Constants.HOME_PAGE_JSP;
import static com.arvato.mybank.constants.Constants.INDEX_PAGE_JSP;
import static com.arvato.mybank.constants.Constants.MESSAGE_INPUT_CANNOT_BE_EMPTY;
import static com.arvato.mybank.constants.Constants.MESSAGE_USER_DOES_NOT_EXIST;
import static com.arvato.mybank.constants.Constants.ERROR;
import static com.arvato.mybank.constants.Constants.PASSWORD;
/**
 * LoginPage controller
 * @author paulinelow
 *
 */
@WebServlet("/loginPage")
public class LoginPage extends HttpServlet{
	
	private LoginServicesImpl loginServices = new LoginServicesImpl();
	
	private AccountServicesImpl accountServices = new AccountServicesImpl();
	
	private static final long serialVersionUID = 1L;

	public LoginPage() {
		super();
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		String username = request.getParameter(USERNAME);
		String password = request.getParameter(PASSWORD);
		
		HttpSession ses = request.getSession();
		Constants constants = new Constants();
		
		if(username.isEmpty() || password.isEmpty()) {
			JOptionPane.showMessageDialog(null, MESSAGE_INPUT_CANNOT_BE_EMPTY, 
					ERROR,JOptionPane.ERROR_MESSAGE);
			response.sendRedirect(INDEX_PAGE_JSP);
		}
		else if(loginServices.validateUser(username,password)) {
			request.setAttribute(USERNAME, username);			
			request.setAttribute(ACCOUNT_ID, accountServices.retrieveAccountFromUserId(username).getAccountId());
			String constantsId = UUID.randomUUID().toString();
			request.getSession().setAttribute(constantsId, constants);
			request.setAttribute(CONSTANTS_ID, constantsId);
			request.getRequestDispatcher(HOME_PAGE_JSP).forward(request, response);
		}else {
			JOptionPane.showMessageDialog(null, MESSAGE_USER_DOES_NOT_EXIST, 
					ERROR,JOptionPane.ERROR_MESSAGE);
			response.sendRedirect(INDEX_PAGE_JSP);
		}
	}

}
