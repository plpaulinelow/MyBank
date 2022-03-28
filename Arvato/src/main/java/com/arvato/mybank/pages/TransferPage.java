package com.arvato.mybank.pages;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import com.arvato.mybank.classes.Account;
import com.arvato.mybank.constants.Constants;
import com.arvato.mybank.impl.services.AccountServicesImpl;
import com.arvato.mybank.impl.services.TransactionServicesImpl;

import static com.arvato.mybank.constants.Constants.CONSTANTS_ID;
import static com.arvato.mybank.constants.Constants.USERNAME;
import static com.arvato.mybank.constants.Constants.ACCOUNT_ID;
import static com.arvato.mybank.constants.Constants.HOME_PAGE;
import static com.arvato.mybank.constants.Constants.HOME_PAGE_JSP;
import static com.arvato.mybank.constants.Constants.TRANSFER_PAGE_JSP;
import static com.arvato.mybank.constants.Constants.TRANSFER;
import static com.arvato.mybank.constants.Constants.MESSAGE_TRANSFER_AMOUNT_CANNOT_BE_EMPTY;
import static com.arvato.mybank.constants.Constants.MESSAGE_ACCOUNT_ID_MUST_BE_ENTERED;
import static com.arvato.mybank.constants.Constants.MESSAGE_THIRD_PARTY_ACCOUNT_DOES_NOT_EXISTS;
import static com.arvato.mybank.constants.Constants.MESSAGE_CURRENT_ACCOUNT_DOES_NOT_EXIST;
import static com.arvato.mybank.constants.Constants.MESSAGE_UNABLE_TO_TRANSFER_TO_SAME_ACOUNT;
import static com.arvato.mybank.constants.Constants.MESSAGE_INSUFFICENT_BALANCE_TO_TRANSFER;
import static com.arvato.mybank.constants.Constants.MESSAGE_TRANSFER_AMOUNT_MUST_BE_GREATER_THAN_ZERO;
import static com.arvato.mybank.constants.Constants.ERROR;
import static com.arvato.mybank.constants.Constants.TRANSFER_ACCOUNT_ID;
import static com.arvato.mybank.constants.Constants.TRANSFER_AMOUNT;

/**
 * TransferPage controller
 * @author paulinelow
 *
 */
@WebServlet("/transferPage")
public class TransferPage extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private TransactionServicesImpl transactionServices = new TransactionServicesImpl();
	private AccountServicesImpl accountServices = new AccountServicesImpl();

	public TransferPage() {
		super();
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{	
		String constantsId = request.getParameter(CONSTANTS_ID);
		String username = request.getParameter(USERNAME);
		Integer accountId = request.getParameter(ACCOUNT_ID).isEmpty()?0:Integer.parseInt(request.getParameter(ACCOUNT_ID));
		Constants constants = (Constants) request.getSession().getAttribute(constantsId);		
		
		if(request.getParameter(HOME_PAGE)!=null) {
			request.setAttribute(CONSTANTS_ID, constantsId);
			request.setAttribute(USERNAME, username);
			request.setAttribute(ACCOUNT_ID, accountId);
			request.getRequestDispatcher(HOME_PAGE_JSP).forward(request, response);
		}
		else if(request.getParameter(TRANSFER)!=null) {
			Integer transferAccountId = request.getParameter(TRANSFER_ACCOUNT_ID).isEmpty()?0: Integer.parseInt(request.getParameter(TRANSFER_ACCOUNT_ID));
			Account currentAccount = accountServices.retrieveAccountFromAccountId(accountId);
			Account transferAccount = accountServices.retrieveAccountFromAccountId(transferAccountId);

			if(request.getParameter(TRANSFER_AMOUNT).isEmpty()) {
				JOptionPane.showMessageDialog(null, MESSAGE_TRANSFER_AMOUNT_CANNOT_BE_EMPTY, 
						ERROR,JOptionPane.ERROR_MESSAGE);
				request.getSession().setAttribute(constantsId, constants);
				request.setAttribute(CONSTANTS_ID, constantsId);
				request.setAttribute(USERNAME, username);
				request.setAttribute(ACCOUNT_ID, accountId);
				request.getRequestDispatcher(TRANSFER_PAGE_JSP).forward(request, response);
			}
			Double transferAmount = Double.parseDouble(request.getParameter(TRANSFER_AMOUNT));
			Boolean errorExists=false;
			if(transferAmount<=0) {
				JOptionPane.showMessageDialog(null, MESSAGE_TRANSFER_AMOUNT_MUST_BE_GREATER_THAN_ZERO, 
						ERROR,JOptionPane.ERROR_MESSAGE);
				errorExists=true;
			}
			if(transferAccountId == null || transferAccountId==0) {
				JOptionPane.showMessageDialog(null, MESSAGE_ACCOUNT_ID_MUST_BE_ENTERED, 
						ERROR,JOptionPane.ERROR_MESSAGE);
				errorExists=true;
			}else if(transferAccount==null) {
				JOptionPane.showMessageDialog(null, MESSAGE_THIRD_PARTY_ACCOUNT_DOES_NOT_EXISTS, 
						ERROR,JOptionPane.ERROR_MESSAGE);
				errorExists=true;
			}else if(currentAccount==null) {
				JOptionPane.showMessageDialog(null, MESSAGE_CURRENT_ACCOUNT_DOES_NOT_EXIST, 
						ERROR,JOptionPane.ERROR_MESSAGE);
				errorExists=true;
			}else if(currentAccount.getAccountId().intValue()==transferAccount.getAccountId().intValue()) {
				JOptionPane.showMessageDialog(null, MESSAGE_UNABLE_TO_TRANSFER_TO_SAME_ACOUNT, 
						ERROR,JOptionPane.ERROR_MESSAGE);
				errorExists=true;
			}
			else if(currentAccount.getBalance()<transferAmount) {
				JOptionPane.showMessageDialog(null,MESSAGE_INSUFFICENT_BALANCE_TO_TRANSFER, 
						ERROR,JOptionPane.ERROR_MESSAGE);
				errorExists=true;
			}
			request.getSession().setAttribute(constantsId, constants);
			request.setAttribute(CONSTANTS_ID, constantsId);
			request.setAttribute(USERNAME, username);
			request.setAttribute(ACCOUNT_ID, accountId);
			if(!errorExists) {
				transactionServices.transferAmount(accountId, transferAccountId, transferAmount);
				
				request.getRequestDispatcher(HOME_PAGE_JSP).forward(request, response);
			}else {
				request.getRequestDispatcher(TRANSFER_PAGE_JSP).forward(request, response);

			}
		}
		
	}
}
