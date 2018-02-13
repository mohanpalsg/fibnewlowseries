package com.mr.filter;



import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/AuthenticationFilter")
public class AuthenticationFilter implements Filter {

	private ServletContext context;
	
	public void init(FilterConfig fConfig) throws ServletException {
		this.context = fConfig.getServletContext();
		this.context.log("AuthenticationFilter initialized");
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		String uri = req.getRequestURI();
		this.context.log("Requested Resource::"+uri);
		
		HttpSession session = req.getSession(false);
		
		boolean forcelogout = false;
		
		
		if( session == null && !(uri.contains("Login") || uri.contains("login")))
		{
			System.out.println("entering filter");	
			this.context.log("Unauthorized access request");
			res.sendRedirect("Login.html");
		}
		
		else
		{
			if(session!=null)
			{
			Date logintime = (Date)session.getAttribute("Logintime");
		System.out.println("First Login" + logintime);
		System.out.println("Currenttime" + new Date());
		
		Long timediff = new Date().getTime() - logintime.getTime();
		Long maxsessiontimeprod = 10800000L; //actual config 3 hrs
		
		Long maxsessiontimetest = 300000L; // for test 10mins
		
		if (timediff > maxsessiontimeprod)
		{
			  forcelogout=true;
			   session.invalidate();  
			   //res.sendRedirect("Login.html");
			   request.getRequestDispatcher("Login.html").forward(request, response);
		}

		
			}
		if (!forcelogout)	
		chain.doFilter(request, response);
		}
		
		
	}

	

	public void destroy() {
		//close any resources here
	}

}