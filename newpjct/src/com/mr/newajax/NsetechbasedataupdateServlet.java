package com.mr.newajax;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mr.newapp.Fibonaccidataupdate;
import com.mr.newapp.NewFibdataCreator;
import com.mr.newapp.Nsebasedatadnldcarmilla;
import com.mr.newapp.Nsebasedownloader;

/**
 * Servlet implementation class NsetechbasedataupdateServlet
 */
@WebServlet("/NsetechbasedataupdateServlet")
public class NsetechbasedataupdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NsetechbasedataupdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		String Duration = (String) session.getAttribute("chartintervalselected");
		String avg1 = (String) session.getAttribute("avg1");
		String avg2 = (String) session.getAttribute("avg2");
		String pdlength = (String) session.getAttribute("pdlength");
		String adddiff = (String) session.getAttribute("adddiff");
		if(Duration == null)
			Duration = "300";
		//Thread thread = new Thread(new Nsebasedatadnldcarmilla(Duration));
		//Thread thread = new Thread(new Fibonaccidataupdate());
		Thread thread = new Thread(new NewFibdataCreator(Duration, avg1, avg2, pdlength,adddiff));
		thread.start();
		doGet(request,response);
	}

}
