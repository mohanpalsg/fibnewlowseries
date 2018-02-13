package com.mr.newajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String userID = "admin@trade.com";
	private final String password = "password";

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// get request parameters for userID and password
		String user = request.getParameter("uname");
		String pwd = request.getParameter("pwd");
		System.out.println("enterted loginserv");
		if(userID.equals(user) && password.equals(pwd)){
			HttpSession session = request.getSession(true);
			session.setAttribute("user", "Pankaj");
			session.setAttribute("Logintime", new Date());
			//setting session to expiry in 30 mins
			session.setMaxInactiveInterval(30*60);
			Cookie userName = new Cookie("user", user);
			userName.setMaxAge(30*60);
			response.addCookie(userName);
			System.out.println(request.getRequestURI());
			session.setAttribute("userauthenticated", "yes");
			response.sendRedirect(request.getContextPath()+"/home");
		}else{
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/Login.html");
			PrintWriter out= response.getWriter();
			out.println("<font color=red>Either user name or password is wrong.</font>");
			rd.include(request, response);
		}

	}

}