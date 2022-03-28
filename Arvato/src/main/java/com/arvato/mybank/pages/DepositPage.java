package com.arvato.mybank.pages;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;
import static com.arvato.mybank.constants.Constants.CONSTANTS_ID;
import static com.arvato.mybank.constants.Constants.USERNAME;
import static com.arvato.mybank.constants.Constants.ACCOUNT_ID;
import static com.arvato.mybank.constants.Constants.HOME_PAGE;
import static com.arvato.mybank.constants.Constants.HOME_PAGE_JSP;
import static com.arvato.mybank.constants.Constants.DEPOSIT_PAGE_JSP;
import static com.arvato.mybank.constants.Constants.CHECK_BALANCE_PAGE_JSP;
import static com.arvato.mybank.constants.Constants.DEPOSIT;
import static com.arvato.mybank.constants.Constants.DEPOSIT_AMOUNT;
import static com.arvato.mybank.constants.Constants.MESSAGE_DEPOSIT_AMOUNT_CANNOT_BE_EMPTY;
import static com.arvato.mybank.constants.Constants.MESSAGE_DEPOSIT_AMOUNT_MUST_BE_GREATER_THAN_ZERO;
import static com.arvato.mybank.constants.Constants.BALANCE;
import static com.arvato.mybank.constants.Constants.ERROR;

import com.arvato.mybank.constants.Constants;
import com.arvato.mybank.impl.services.TransactionServicesImpl;
/**
 * DepositPage controller
 * @author paulinelow
 *
 */
@WebServlet("/depositPage")
public class DepositPage extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private TransactionServicesImpl transactionServices = new TransactionServicesImpl();

	public DepositPage() {
		super();
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{	
		Integer accountId = Integer.parseInt(request.getParameter(ACCOUNT_ID)); 
		String username = request.getParameter(USERNAME);
		String constantsId = request.getParameter(CONSTANTS_ID);
		Constants constants = (Constants) request.getSession().getAttribute(constantsId);
		
		if(request.getParameter(DEPOSIT)!=null) {
			if(request.getParameter(DEPOSIT_AMOUNT).isEmpty()) {
				JOptionPane.showMessageDialog(null, MESSAGE_DEPOSIT_AMOUNT_CANNOT_BE_EMPTY, 
						ERROR,JOptionPane.ERROR_MESSAGE);
				request.getSession().setAttribute(constantsId, constants);
				request.setAttribute(CONSTANTS_ID, constantsId);
				request.setAttribute(USERNAME, username);
				request.setAttribute(ACCOUNT_ID, accountId);
				request.getRequestDispatcher(DEPOSIT_PAGE_JSP).forward(request, response);
			}
			Double depositAmount =  Double.parseDouble(request.getParameter(DEPOSIT_AMOUNT)); 
			if(depositAmount<=0) {
				JOptionPane.showMessageDialog(null, MESSAGE_DEPOSIT_AMOUNT_MUST_BE_GREATER_THAN_ZERO, 
						ERROR,JOptionPane.ERROR_MESSAGE);
				request.getSession().setAttribute(constantsId, constants);
				request.setAttribute(CONSTANTS_ID, constantsId);
				request.setAttribute(USERNAME, username);
				request.setAttribute(ACCOUNT_ID, accountId);
				request.getRequestDispatcher(DEPOSIT_PAGE_JSP).forward(request, response);
			}else {
				transactionServices.depositAmount(accountId, depositAmount);
				Double balance = transactionServices.checkBalance(accountId);
				request.setAttribute(BALANCE, String.format("%.2f", balance));
				request.setAttribute(CONSTANTS_ID, constantsId);
				request.setAttribute(USERNAME, username);
				request.setAttribute(ACCOUNT_ID, accountId);
				request.getRequestDispatcher(CHECK_BALANCE_PAGE_JSP).forward(request, response);
			}		
		}else if(request.getParameter(HOME_PAGE)!=null) {
			request.setAttribute(CONSTANTS_ID, constantsId);
			request.setAttribute(USERNAME, username);
			request.setAttribute(ACCOUNT_ID, accountId);
			request.getRequestDispatcher(HOME_PAGE_JSP).forward(request, response);
		}
	}

}
