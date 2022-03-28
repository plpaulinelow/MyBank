package com.arvato.mybank.pages;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.arvato.mybank.constants.Constants;

@WebServlet("/checkBalancePage")
public class CheckBalancePage extends HttpServlet{

	private static final long serialVersionUID = 1L;

	public CheckBalancePage() {
		super();
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{	
		HttpSession ses = request.getSession();
		String constantsId = request.getParameter("constantsId");
		String username = request.getParameter("username");
		Integer accountId = Integer.parseInt(request.getParameter("accountId"));
		Constants constants = (Constants) request.getSession().getAttribute(constantsId);		
		if(request.getParameter("homePage")!=null) {
			request.getSession().setAttribute(constantsId, constants);
			request.setAttribute("constantsId", constantsId);
			request.setAttribute("username", username);
			request.setAttribute("accountId", accountId);
			request.getRequestDispatcher("HomePage.jsp").forward(request, response);
		}
	}
}
