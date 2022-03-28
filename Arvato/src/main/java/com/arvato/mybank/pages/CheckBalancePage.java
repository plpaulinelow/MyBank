package com.arvato.mybank.pages;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static com.arvato.mybank.constants.Constants.CONSTANTS_ID;
import static com.arvato.mybank.constants.Constants.USERNAME;
import static com.arvato.mybank.constants.Constants.ACCOUNT_ID;
import static com.arvato.mybank.constants.Constants.HOME_PAGE;
import static com.arvato.mybank.constants.Constants.HOME_PAGE_JSP;
import com.arvato.mybank.constants.Constants;
/**
 * CheckBalance controller
 * @author paulinelow
 *
 */
@WebServlet("/checkBalancePage")
public class CheckBalancePage extends HttpServlet{

	private static final long serialVersionUID = 1L;

	public CheckBalancePage() {
		super();
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{	
		String constantsId = request.getParameter(CONSTANTS_ID);
		String username = request.getParameter(USERNAME);
		Integer accountId = Integer.parseInt(request.getParameter(ACCOUNT_ID));
		Constants constants = (Constants) request.getSession().getAttribute(constantsId);		
		if(request.getParameter(HOME_PAGE)!=null) {
			request.getSession().setAttribute(constantsId, constants);
			request.setAttribute(CONSTANTS_ID, constantsId);
			request.setAttribute(USERNAME, username);
			request.setAttribute(ACCOUNT_ID, accountId);
			request.getRequestDispatcher(HOME_PAGE_JSP).forward(request, response);
		}
	}
}
