package com.arvato.mybank.pages;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import com.arvato.mybank.classes.Account;
import com.arvato.mybank.constants.Constants;
import com.arvato.mybank.impl.services.AccountServicesImpl;
import com.arvato.mybank.impl.services.TransactionServicesImpl;

@WebServlet("/transferPage")
public class TransferPage extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private TransactionServicesImpl transactionServices = new TransactionServicesImpl();
	private AccountServicesImpl accountServices = new AccountServicesImpl();

	public TransferPage() {
		super();
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{	
		HttpSession ses = request.getSession();
		String constantsId = request.getParameter("constantsId");
		String username = request.getParameter("username");
		Integer accountId = request.getParameter("accountId").isEmpty()?0:Integer.parseInt(request.getParameter("accountId"));
		Constants constants = (Constants) request.getSession().getAttribute(constantsId);		
		
		if(request.getParameter("homePage")!=null) {
			request.setAttribute("constantsId", constantsId);
			request.setAttribute("username", username);
			request.setAttribute("accountId", accountId);
			request.getRequestDispatcher("HomePage.jsp").forward(request, response);
		}
		else if(request.getParameter("transfer")!=null) {
			Integer transferAccountId = request.getParameter("transferAccountId").isEmpty()?0: Integer.parseInt(request.getParameter("transferAccountId"));
			Account currentAccount = accountServices.retrieveAccountFromAccountId(accountId);
			Account transferAccount = accountServices.retrieveAccountFromAccountId(transferAccountId);

			if(request.getParameter("transferAmount").isEmpty()) {
				JOptionPane.showMessageDialog(null, "Transfer amount cannot be empty..", 
						"Error",JOptionPane.ERROR_MESSAGE);
				request.getSession().setAttribute(constantsId, constants);
				request.setAttribute("constantsId", constantsId);
				request.setAttribute("username", username);
				request.setAttribute("accountId", accountId);
				request.getRequestDispatcher("TransferPage.jsp").forward(request, response);
			}
			Double transferAmount = Double.parseDouble(request.getParameter("transferAmount"));
			Boolean errorExists=false;
			if(transferAccountId == null || transferAccountId==0) {
				JOptionPane.showMessageDialog(null, "Account id must be entered", 
						"Error",JOptionPane.ERROR_MESSAGE);
				errorExists=true;
			}else if(transferAccount==null) {
				JOptionPane.showMessageDialog(null, "Third Party Account does not exist..", 
						"Error",JOptionPane.ERROR_MESSAGE);
				errorExists=true;
			}else if(currentAccount==null) {
				JOptionPane.showMessageDialog(null, "Current Account does not exist..", 
						"Error",JOptionPane.ERROR_MESSAGE);
				errorExists=true;
			}else if(currentAccount.getAccountId().intValue()==transferAccount.getAccountId().intValue()) {
				JOptionPane.showMessageDialog(null, "Unable to transfer to the same account id..", 
						"Error",JOptionPane.ERROR_MESSAGE);
				errorExists=true;
			}
			else if(currentAccount.getBalance()<transferAmount) {
				JOptionPane.showMessageDialog(null, "Insufficient balance to proceed with transfer..", 
						"Error",JOptionPane.ERROR_MESSAGE);
				errorExists=true;
			}
			request.getSession().setAttribute(constantsId, constants);
			request.setAttribute("constantsId", constantsId);
			request.setAttribute("username", username);
			request.setAttribute("accountId", accountId);
			if(!errorExists) {
				transactionServices.transferAmount(accountId, transferAccountId, transferAmount);
				
				request.getRequestDispatcher("HomePage.jsp").forward(request, response);
			}else {
				request.getRequestDispatcher("TransferPage.jsp").forward(request, response);

			}
		}
		
	}
}
