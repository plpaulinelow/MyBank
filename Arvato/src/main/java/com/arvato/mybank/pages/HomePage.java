package com.arvato.mybank.pages;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.arvato.mybank.constants.Constants;
import com.arvato.mybank.impl.services.AccountServicesImpl;
import com.arvato.mybank.impl.services.TransactionServicesImpl;

import static com.arvato.mybank.constants.Constants.CONSTANTS_ID;
import static com.arvato.mybank.constants.Constants.USERNAME;
import static com.arvato.mybank.constants.Constants.ACCOUNT_ID;
import static com.arvato.mybank.constants.Constants.DEPOSIT_PAGE_JSP;
import static com.arvato.mybank.constants.Constants.CHECK_BALANCE_PAGE_JSP;
import static com.arvato.mybank.constants.Constants.WITHDRAWAL_PAGE_JSP;
import static com.arvato.mybank.constants.Constants.TRANSFER_PAGE_JSP;
import static com.arvato.mybank.constants.Constants.DEPOSIT;
import static com.arvato.mybank.constants.Constants.BALANCE;
import static com.arvato.mybank.constants.Constants.CHECK_BALANCE;
import static com.arvato.mybank.constants.Constants.ACCOUNT;
import static com.arvato.mybank.constants.Constants.WITHDRAWAL;
import static com.arvato.mybank.constants.Constants.TRANSFER;
import static com.arvato.mybank.constants.Constants.LOGOUT;
import static com.arvato.mybank.constants.Constants.INDEX_PAGE_JSP;
/**
 * HomePage controller
 * @author paulinelow
 *
 */
@WebServlet("/homePage")
public class HomePage extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	private TransactionServicesImpl transactionServices = new TransactionServicesImpl();
	private AccountServicesImpl accountServices = new AccountServicesImpl();

	public HomePage() {
		super();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{	
		String username = request.getParameter(USERNAME);
		Integer accountId = Integer.parseInt(request.getParameter(ACCOUNT_ID)); 		
		String constantsId = request.getParameter(CONSTANTS_ID);
		Constants constants = (Constants) request.getSession().getAttribute(constantsId);
		
		if(request.getParameter(CHECK_BALANCE)!=null) {
			Double balance = transactionServices.checkBalance(accountId);
			request.setAttribute(BALANCE, String.format("%.2f", balance));
			request.setAttribute(USERNAME, username);
			request.setAttribute(ACCOUNT_ID, accountId);
			request.getSession().setAttribute(constantsId, constants);
			request.setAttribute(CONSTANTS_ID, constantsId);
			request.getRequestDispatcher(CHECK_BALANCE_PAGE_JSP).forward(request, response);
			
		}else if(request.getParameter(DEPOSIT)!=null) {
			request.setAttribute(ACCOUNT, accountServices.retrieveAccountFromAccountId(accountId));
			request.setAttribute(USERNAME, username);
			request.setAttribute(ACCOUNT_ID, accountId);
			request.getSession().setAttribute(constantsId, constants);
			request.setAttribute(CONSTANTS_ID, constantsId);
			request.getRequestDispatcher(DEPOSIT_PAGE_JSP).forward(request, response);
			
		}else if(request.getParameter(WITHDRAWAL)!=null) {
			request.setAttribute(ACCOUNT, accountServices.retrieveAccountFromAccountId(accountId));
			request.setAttribute(USERNAME, username);
			request.setAttribute(ACCOUNT_ID, accountId);
			request.getSession().setAttribute(constantsId, constants);
			request.setAttribute(CONSTANTS_ID, constantsId);
			request.getRequestDispatcher(WITHDRAWAL_PAGE_JSP).forward(request, response);
			
		}else if(request.getParameter(TRANSFER)!=null) {
			request.setAttribute(USERNAME, username);
			request.setAttribute(ACCOUNT_ID, accountId);
			request.getSession().setAttribute(constantsId, constants);
			request.setAttribute(CONSTANTS_ID, constantsId);
			request.getRequestDispatcher(TRANSFER_PAGE_JSP).forward(request, response);
			
		}else if(request.getParameter(LOGOUT)!=null) {
			response.sendRedirect(INDEX_PAGE_JSP);
		}
	}
}
