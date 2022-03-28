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

@WebServlet("/depositPage")
public class DepositPage extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private TransactionServicesImpl transactionServices = new TransactionServicesImpl();

	public DepositPage() {
		super();
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{	
		HttpSession ses = request.getSession();
		Integer accountId = Integer.parseInt(request.getParameter("accountId")); 
		String username = request.getParameter("username");
		String constantsId = request.getParameter("constantsId");
		Constants constants = (Constants) request.getSession().getAttribute(constantsId);
		
		if(request.getParameter("deposit")!=null) {
			if(request.getParameter("depositAmount").isEmpty()) {
				JOptionPane.showMessageDialog(null, "Deposit amount cannot be empty..", 
						"Error",JOptionPane.ERROR_MESSAGE);
				request.getSession().setAttribute(constantsId, constants);
				request.setAttribute("constantsId", constantsId);
				request.setAttribute("username", username);
				request.setAttribute("accountId", accountId);
				request.getRequestDispatcher("DepositPage.jsp").forward(request, response);
			}
			Double depositAmount =  Double.parseDouble(request.getParameter("depositAmount")); 
			if(depositAmount<=0) {
				JOptionPane.showMessageDialog(null, "Deposit amount must be greater than zero..", 
						"Error",JOptionPane.ERROR_MESSAGE);
				request.getSession().setAttribute(constantsId, constants);
				request.setAttribute("constantsId", constantsId);
				request.setAttribute("username", username);
				request.setAttribute("accountId", accountId);
				request.getRequestDispatcher("DepositPage.jsp").forward(request, response);
			}else {
				transactionServices.depositAmount(accountId, depositAmount);
				Double balance = transactionServices.checkBalance(accountId);
				request.setAttribute("balance", String.format("%.2f", balance));
				request.setAttribute("constantsId", constantsId);
				request.setAttribute("username", username);
				request.setAttribute("accountId", accountId);
				request.getRequestDispatcher("CheckBalancePage.jsp").forward(request, response);
			}		
		}else if(request.getParameter("homePage")!=null) {
			request.setAttribute("constantsId", constantsId);
			request.setAttribute("username", username);
			request.setAttribute("accountId", accountId);
			request.getRequestDispatcher("HomePage.jsp").forward(request, response);
		}
	}

}
