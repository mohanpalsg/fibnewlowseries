package com.mr.newajax;

import java.io.IOException;
import java.util.HashMap;

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
import com.mr.newdata.Settingobj;

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
		HashMap<String,Settingobj> setting = (HashMap<String,Settingobj>)  session.getAttribute("setting");
		if(Duration == null)
			Duration = "600";
		Settingobj st = (Settingobj)setting.get(Duration);
		String avg1 = st.getAvg1();
		String avg2 = st.getAvg2();
		String pdlength = st.getBarlength();
		String adddiff =st.getAdjustment();
		
		Settingobj st_v2 = (Settingobj)setting.get(Duration+"v2");
		String avg1_v2 = st_v2.getAvg1();
		String avg2_v2 = st_v2.getAvg2();
		String pdlength_v2 = st_v2.getBarlength();
		String adddiff_v2 =st_v2.getAdjustment();
		
		
		//Thread thread = new Thread(new Nsebasedatadnldcarmilla(Duration));
		//Thread thread = new Thread(new Fibonaccidataupdate());
		
		Thread thread_v2 = new Thread(new NewFibdataCreator(Duration, avg1_v2, avg2_v2, pdlength_v2,adddiff_v2,"v2"));
		thread_v2.start();
		Thread thread = new Thread(new NewFibdataCreator(Duration, avg1, avg2, pdlength,adddiff,"v1"));
		thread.start();
		doGet(request,response);
	}

}
