package com.arvato.mybank.pages;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import com.arvato.mybank.constants.Constants;
import com.arvato.mybank.impl.services.TransactionServicesImpl;

import static com.arvato.mybank.constants.Constants.CONSTANTS_ID;
import static com.arvato.mybank.constants.Constants.USERNAME;
import static com.arvato.mybank.constants.Constants.ACCOUNT_ID;
import static com.arvato.mybank.constants.Constants.HOME_PAGE;
import static com.arvato.mybank.constants.Constants.HOME_PAGE_JSP;
import static com.arvato.mybank.constants.Constants.CHECK_BALANCE_PAGE_JSP;
import static com.arvato.mybank.constants.Constants.WITHDRAWAL_PAGE_JSP;
import static com.arvato.mybank.constants.Constants.BALANCE;
import static com.arvato.mybank.constants.Constants.WITHDRAWAL;
import static com.arvato.mybank.constants.Constants.WITHDRAWAL_AMOUNT;
import static com.arvato.mybank.constants.Constants.MESSAGE_WITHDRAWAL_AMOUNT_CANNOT_BE_EMPTY;
import static com.arvato.mybank.constants.Constants.MESSAGE_WITHDRAWAL_AMOUNT_MUST_BE_GREATER_THAN_ZERO;
import static com.arvato.mybank.constants.Constants.MESSAGE_INSUFFICIENT_BALANCE_FOR_WITHDRAWAL;
import static com.arvato.mybank.constants.Constants.ERROR;


/**
 * WithdrawalPage controller
 * @author paulinelow
 *
 */
@WebServlet("/withdrawalPage")
public class WithdrawalPage extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private TransactionServicesImpl transactionServices = new TransactionServicesImpl();

	public WithdrawalPage() {
		super();
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{	
		HttpSession ses = request.getSession();
		Integer accountId = Integer.parseInt(request.getParameter(ACCOUNT_ID));
		String username = request.getParameter(USERNAME);
		String constantsId = request.getParameter(CONSTANTS_ID);
		Constants constants = (Constants) request.getSession().getAttribute(constantsId);
		Double balance = transactionServices.checkBalance(accountId);
		
		if(request.getParameter(WITHDRAWAL)!=null) {
			if(request.getParameter(WITHDRAWAL_AMOUNT).isEmpty()) {
				JOptionPane.showMessageDialog(null, MESSAGE_WITHDRAWAL_AMOUNT_CANNOT_BE_EMPTY, 
						ERROR,JOptionPane.ERROR_MESSAGE);
				request.getSession().setAttribute(constantsId, constants);
				request.setAttribute(CONSTANTS_ID, constantsId);
				request.setAttribute(USERNAME, username);
				request.setAttribute(ACCOUNT_ID, accountId);
				request.getRequestDispatcher(WITHDRAWAL_PAGE_JSP).forward(request, response);
			}
			Double withdrawalAmount = Double.parseDouble(request.getParameter(WITHDRAWAL_AMOUNT)); 
			if(withdrawalAmount<=0) {
				JOptionPane.showMessageDialog(null, MESSAGE_WITHDRAWAL_AMOUNT_MUST_BE_GREATER_THAN_ZERO, 
						ERROR,JOptionPane.ERROR_MESSAGE);
				request.getSession().setAttribute(constantsId, constants);
				request.setAttribute(CONSTANTS_ID, constantsId);
				request.setAttribute(USERNAME, username);
				request.setAttribute(ACCOUNT_ID, accountId);
				request.getRequestDispatcher(WITHDRAWAL_PAGE_JSP).forward(request, response);
			}else if(withdrawalAmount>balance) {
				JOptionPane.showMessageDialog(null, MESSAGE_INSUFFICIENT_BALANCE_FOR_WITHDRAWAL, 
						ERROR,JOptionPane.ERROR_MESSAGE);
				request.getSession().setAttribute(constantsId, constants);
				request.setAttribute(CONSTANTS_ID, constantsId);
				request.setAttribute(USERNAME, username);
				request.setAttribute(ACCOUNT_ID, accountId);
				request.getRequestDispatcher(WITHDRAWAL_PAGE_JSP).forward(request, response);
			}else {
				transactionServices.withdrawalAmount(accountId, withdrawalAmount);
				request.setAttribute(BALANCE, String.format("%.2f", balance));
				request.setAttribute(CONSTANTS_ID, constantsId);
				request.setAttribute(USERNAME, username);
				request.setAttribute(ACCOUNT_ID, accountId);
				request.getRequestDispatcher(CHECK_BALANCE_PAGE_JSP).forward(request, response);
			}	
		}
		else if(request.getParameter(HOME_PAGE)!=null) {
			request.setAttribute(CONSTANTS_ID, constantsId);
			request.setAttribute(USERNAME, username);
			request.setAttribute(ACCOUNT_ID, accountId);
			request.getRequestDispatcher(HOME_PAGE_JSP).forward(request, response);
		}
	}

}
