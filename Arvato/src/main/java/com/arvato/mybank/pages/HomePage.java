package com.arvato.mybank.pages;

import java.io.IOException;
import java.io.ObjectInputFilter.Config;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.arvato.mybank.classes.Account;
import com.arvato.mybank.constants.Constants;
import com.arvato.mybank.impl.services.AccountServicesImpl;
import com.arvato.mybank.impl.services.TransactionServicesImpl;

@WebServlet("/homePage")
public class HomePage extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	private TransactionServicesImpl transactionServices = new TransactionServicesImpl();
	private AccountServicesImpl accountServices = new AccountServicesImpl();

	public HomePage() {
		super();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{	
		HttpSession ses = request.getSession();
		String username = request.getParameter("username");
		Integer accountId = Integer.parseInt(request.getParameter("accountId")); 		
		String constantsId = request.getParameter("constantsId");
		Constants constants = (Constants) request.getSession().getAttribute(constantsId);

		//System.out.println("Constants: " + constants.getUserList().get(0).getUsername());
		
		if(request.getParameter("checkBalance")!=null) {
			Double balance = transactionServices.checkBalance(accountId);
			request.setAttribute("balance", String.format("%.2f", balance));
			request.setAttribute("username", username);
			request.setAttribute("accountId", accountId);
			request.getSession().setAttribute(constantsId, constants);
			request.setAttribute("constantsId", constantsId);
			request.getRequestDispatcher("CheckBalancePage.jsp").forward(request, response);
			
		}else if(request.getParameter("deposit")!=null) {
			request.setAttribute("account", accountServices.retrieveAccountFromAccountId(accountId));
			request.setAttribute("username", username);
			request.setAttribute("accountId", accountId);
			request.getSession().setAttribute(constantsId, constants);
			request.setAttribute("constantsId", constantsId);
			request.getRequestDispatcher("DepositPage.jsp").forward(request, response);
			
		}else if(request.getParameter("withdrawal")!=null) {
			request.setAttribute("account", accountServices.retrieveAccountFromAccountId(accountId));
			request.setAttribute("username", username);
			request.setAttribute("accountId", accountId);
			request.getSession().setAttribute(constantsId, constants);
			request.setAttribute("constantsId", constantsId);
			request.getRequestDispatcher("WithdrawalPage.jsp").forward(request, response);
			
		}else if(request.getParameter("transfer")!=null) {
			request.setAttribute("username", username);
			request.setAttribute("accountId", accountId);
			request.getSession().setAttribute(constantsId, constants);
			request.setAttribute("constantsId", constantsId);
			request.getRequestDispatcher("TransferPage.jsp").forward(request, response);
			
		}else if(request.getParameter("logout")!=null) {
			response.sendRedirect("index.jsp");
		}
	}
}
