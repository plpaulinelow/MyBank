package com.arvato.mybank.pages;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import com.arvato.mybank.classes.User;
import com.arvato.mybank.constants.Constants;
import com.arvato.mybank.impl.services.AccountServicesImpl;
import com.arvato.mybank.impl.services.LoginServicesImpl;


@WebServlet("/loginPage")
public class LoginPage extends HttpServlet{
	
	private LoginServicesImpl loginServices = new LoginServicesImpl();
	
	private AccountServicesImpl accountServices = new AccountServicesImpl();
	
	private static final long serialVersionUID = 1L;

	public LoginPage() {
		super();
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		HttpSession ses = request.getSession();
		Constants constants = new Constants();
		
		if(username.isEmpty() || password.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Input cannot be empty", 
					"Error",JOptionPane.ERROR_MESSAGE);
			response.sendRedirect("index.jsp");
		}
		else if(loginServices.validateUser(username,password)) {
			request.setAttribute("username", username);			
			request.setAttribute("accountId", accountServices.retrieveAccountFromUserId(username).getAccountId());
			String constantsId = UUID.randomUUID().toString();
			request.getSession().setAttribute(constantsId, constants);
			request.setAttribute("constantsId", constantsId);
			request.getRequestDispatcher("HomePage.jsp").forward(request, response);
		}else {
			JOptionPane.showMessageDialog(null, "User does not exist!", 
					"Error",JOptionPane.ERROR_MESSAGE);
			response.sendRedirect("index.jsp");
		}
	}

}
