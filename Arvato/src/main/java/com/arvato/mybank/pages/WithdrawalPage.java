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

@WebServlet("/withdrawalPage")
public class WithdrawalPage extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private TransactionServicesImpl transactionServices = new TransactionServicesImpl();

	public WithdrawalPage() {
		super();
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{	
		HttpSession ses = request.getSession();
		Integer accountId = Integer.parseInt(request.getParameter("accountId"));
		String username = request.getParameter("username");
		String constantsId = request.getParameter("constantsId");
		Constants constants = (Constants) request.getSession().getAttribute(constantsId);
		Double balance = transactionServices.checkBalance(accountId);
		
		if(request.getParameter("withdraw")!=null) {
			if(request.getParameter("withdrawalAmount").isEmpty()) {
				JOptionPane.showMessageDialog(null, "Withdrawal amount cannot be empty..", 
						"Error",JOptionPane.ERROR_MESSAGE);
				request.getSession().setAttribute(constantsId, constants);
				request.setAttribute("constantsId", constantsId);
				request.setAttribute("username", username);
				request.setAttribute("accountId", accountId);
				request.getRequestDispatcher("WithdrawalPage.jsp").forward(request, response);
			}
			Double withdrawalAmount = Double.parseDouble(request.getParameter("withdrawalAmount")); 
			if(withdrawalAmount<=0) {
				JOptionPane.showMessageDialog(null, "Withdrawal amount must be greater than zero..", 
						"Error",JOptionPane.ERROR_MESSAGE);
				request.getSession().setAttribute(constantsId, constants);
				request.setAttribute("constantsId", constantsId);
				request.setAttribute("username", username);
				request.setAttribute("accountId", accountId);
				request.getRequestDispatcher("WithdrawalPage.jsp").forward(request, response);
			}else if(withdrawalAmount>balance) {
				JOptionPane.showMessageDialog(null, "Insufficient balance to proceed with withdrawal..", 
						"Error",JOptionPane.ERROR_MESSAGE);
				request.getSession().setAttribute(constantsId, constants);
				request.setAttribute("constantsId", constantsId);
				request.setAttribute("username", username);
				request.setAttribute("accountId", accountId);
				request.getRequestDispatcher("WithdrawalPage.jsp").forward(request, response);
			}else {
				transactionServices.withdrawalAmount(accountId, withdrawalAmount);
				request.setAttribute("balance", String.format("%.2f", balance));
				request.setAttribute("constantsId", constantsId);
				request.setAttribute("username", username);
				request.setAttribute("accountId", accountId);
				request.getRequestDispatcher("CheckBalancePage.jsp").forward(request, response);
			}	
		}
		else if(request.getParameter("homePage")!=null) {
			request.setAttribute("constantsId", constantsId);
			request.setAttribute("username", username);
			request.setAttribute("accountId", accountId);
			request.getRequestDispatcher("HomePage.jsp").forward(request, response);
		}
	}

}
