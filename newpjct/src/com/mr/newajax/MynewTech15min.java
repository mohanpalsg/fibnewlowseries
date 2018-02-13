package com.mr.newajax;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mr.datainsert.Dataconn;
import com.mr.newapp.Traderspitdownloader;
import com.mr.newdata.Newphpdata;
import com.mr.newdata.Nsetabledata;
import com.mr.newdata.Pivotobject;
import com.mr.newdata.RenderedObject;

/**
 * Servlet implementation class MynewTech15min
 */
@WebServlet("/MynewTech15min")
public class MynewTech15min extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MynewTech15min() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession(false);
		
		
		String Duration = (String) session.getAttribute("chartintervalselected");
		if(Duration == null)
			Duration ="300";
		
		String list = (String) session.getAttribute("nseselected");
		if(list == null)
			list = "nse200";
		
		String highdiff = (String) session.getAttribute("pricediffhighselected");
		if (highdiff == null || highdiff.equals(""))
			if(Duration.equals("300"))
			highdiff = "2.5";
			else if(Duration.equals("600"))
				highdiff = "5";
			else
				highdiff = "1";
		
		String lowdiff = (String) session.getAttribute("pricedifflowselected");
		if (lowdiff == null || lowdiff.equals(""))
			if(Duration.equals("300"))
			lowdiff = "-2.5";
			else if(Duration.equals("600"))
				highdiff = "-5";
			else
				lowdiff = "-1";
		
		String techfilter = (String) session.getAttribute("techfilterselected");
		if (techfilter == null)
			techfilter = "STDOWN";
		
		// techfilter possible values
		// STDOWN,STUP,S1,S2,S3,S4,R1,R2,R3,R4
		
		//default logic if timeinterval <300 use weekly pivot
		//if timeinterval =300 use mothly pivot
		
		if(Duration.equals("600"))
			list = "nse500";
		
		HashMap <String,RenderedObject> output = new HashMap <String,RenderedObject>();
		if(request.getParameter("loadsamedata") == null)
		{
		String tablename = "nse"+Duration+"min";
		
		Dataconn dataconn = new Dataconn();
		Connection conn = dataconn.getconn();

		HashMap <String,Nsetabledata> nsetabdata = gettabledata(conn,tablename);
		
		dataconn.closeconn();
		Traderspitdownloader tddownloader = new Traderspitdownloader();
		HashMap <String,Newphpdata> currentprice= tddownloader.getmarketprice(list); 
		
		
		
		
		ArrayList <String> stocklist = (ArrayList<String>) session.getAttribute(list);
		Iterator<String> it = stocklist.iterator();
		HashMap <String,RenderedObject> fulloutput = new HashMap <String,RenderedObject>();
		
		 
		
		while (it.hasNext()) {
			
	        String stocksymbol = (String) it.next();
	        if(currentprice.containsKey(stocksymbol) && nsetabdata.containsKey(stocksymbol))
	        {
	        	RenderedObject ro = getRenderedObject(currentprice.get(stocksymbol),nsetabdata.get(stocksymbol),lowdiff,highdiff,techfilter,Duration,session,stocksymbol);
	        	if (ro!= null)
	        	{
	        		if( ro.getLowdiff() < Float.valueOf(highdiff) && ro.getLTPdiff() > Float.valueOf(lowdiff))
	        		{
	        		output.put(stocksymbol,ro);
	        		}
	        		
					fulloutput.put(stocksymbol,ro);
	        	}
	        }
	        
		}
		session.setAttribute("fulloutput", fulloutput);
		}
		else if (request.getParameter("loadsamedata").equals("true"))
		{
			HashMap <String,RenderedObject> fulloutput = (HashMap<String, RenderedObject>) session.getAttribute("fulloutput");
			 Iterator it = fulloutput.entrySet().iterator();
			 while (it.hasNext()) {
				 Map.Entry pair = (Map.Entry)it.next();
				 RenderedObject ro = (RenderedObject)pair.getValue();
				 if (ro!= null)
		        	{
		        		if( ro.getLTPdiff() < Float.valueOf(highdiff) && ro.getLTPdiff() > Float.valueOf(lowdiff))
		        		{
		        		output.put(ro.getStocksymbol(),ro);
		        		}
		        	}
				 
		}
		}
		request.setAttribute("stocklist", output);
		
		
		request.setAttribute("Minselect", Duration);
		
		request.setAttribute("highdiff",highdiff );
		request.setAttribute("lowdiff", lowdiff);
		request.setAttribute("condselect", techfilter);
		request.getRequestDispatcher("Technicallive15minnew.jsp").forward(request, response);
		
		
	}

	private RenderedObject getRenderedObject(Newphpdata newphpdata, Nsetabledata nsetabledata, String lowdiff, String highdiff, String techfilter, String duration, HttpSession session, String stocksymbol) {
		// TODO Auto-generated method stub
		RenderedObject ro = new RenderedObject();
		float comparewith = getcritieriadata(nsetabledata,techfilter,duration,session,stocksymbol);
		float diffltp = ((newphpdata.getLastprice() - comparewith)/comparewith)*100;
		float lowpercent = ((newphpdata.getLowprice()- comparewith)/comparewith)*100;
		float highpercent = ((newphpdata.getHighprice()- comparewith)/comparewith)*100;
		
			ro.setStocksymbol(nsetabledata.getStocksymbol());
			ro.setLevel(techfilter);
			ro.setLevelvalue(comparewith);
			ro.setStochk(gettruncatedfloat(nsetabledata.getStochk()));
			ro.setStochd(gettruncatedfloat(nsetabledata.getStochd()));
			ro.setRsi(gettruncatedfloat(nsetabledata.getRsi()));
			ro.setWilliamsr(gettruncatedfloat(nsetabledata.getWillR()));
			ro.setHighdiff(gettruncatedfloat(highpercent));
			ro.setLowdiff(gettruncatedfloat(lowpercent));
			ro.setLTPdiff(gettruncatedfloat(diffltp));
			ro.setTrend(nsetabledata.getTrendstatus());
			ro.setLasttradedprice(newphpdata.getLastprice());
			return ro;
		
		
			
		
	}

	private float gettruncatedfloat(float d) {
		// TODO Auto-generated method stub
		BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
	}

	private float getcritieriadata(Nsetabledata nsetabledata, String techfilter, String duration, HttpSession session, String stocksymbol) {
		// TODO Auto-generated method stub
		if (techfilter.equals("STUP"))
			return nsetabledata.getTREND5down();
		if (techfilter.equals("STDOWN"))
			return nsetabledata.getTREND5up();
		
		HashMap <String,Pivotobject> pivobj = null;
		
		if(duration.equals("300") || duration.equals("600"))
			pivobj = (HashMap<String, Pivotobject>) session.getAttribute("monthpivot");
		else
		pivobj = (HashMap<String, Pivotobject>) session.getAttribute("weekpivot");
		
		
		if (techfilter.equals("CMS1"))
			if(pivobj.containsKey(stocksymbol))
		    return pivobj.get(stocksymbol).getS1();
		if (techfilter.equals("CMS2"))
			if(pivobj.containsKey(stocksymbol))
		    return pivobj.get(stocksymbol).getS2();
		if (techfilter.equals("CMS3"))
			if(pivobj.containsKey(stocksymbol))
		    return pivobj.get(stocksymbol).getS3();
		if (techfilter.equals("CMS4"))
			if(pivobj.containsKey(stocksymbol))
		    return pivobj.get(stocksymbol).getS4();
		if (techfilter.equals("CMR1"))
			if(pivobj.containsKey(stocksymbol))
		    return pivobj.get(stocksymbol).getR1();
		if (techfilter.equals("CMR2"))
			if(pivobj.containsKey(stocksymbol))
		    return pivobj.get(stocksymbol).getR2();
		if (techfilter.equals("CMR3"))
			if(pivobj.containsKey(stocksymbol))
		    return pivobj.get(stocksymbol).getR3();
		if (techfilter.equals("CMR4"))
			if(pivobj.containsKey(stocksymbol))
		    return pivobj.get(stocksymbol).getR4();
		
		
		
		return 1;
	}

	private HashMap<String, Nsetabledata> gettabledata(Connection conn, String tablename) {
		// TODO Auto-generated method stub
		HashMap<String, Nsetabledata> tabdata= new HashMap <String, Nsetabledata>();
		try {
			Statement stmt = conn.createStatement();
			String Query = "select * from "+tablename;
			ResultSet rs = stmt.executeQuery(Query);
			while (rs.next()) {
				Nsetabledata data = new Nsetabledata();
				data.setStocksymbol(rs.getString(1));
				data.setStochk(rs.getFloat(2));
				data.setStochd(rs.getFloat(3));
				data.setWillR(rs.getFloat(4));
				data.setRsi(rs.getFloat(5));
				data.setSma50(rs.getFloat(6));
				data.setSma200(rs.getFloat(7));
				data.setTREND5down(rs.getFloat(8));
				data.setTREND5up(rs.getFloat(9));
				data.setTrendstatus(rs.getString(10));
				tabdata.put(data.getStocksymbol(), data);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tabdata;
	}

	private String gettablename(String duration) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession(false);
		
		session.setAttribute("chartintervalselected", request.getParameter("chartinterval"));
		session.setAttribute("nseselected", request.getParameter("nseselect"));
		session.setAttribute("techfilterselected", request.getParameter("techfilter"));
		session.setAttribute("pricedifflowselected", request.getParameter("pricedifflow"));
		session.setAttribute("pricediffhighselected",request.getParameter("pricediffhigh")) ;
		
		
	
		
		doGet(request, response);
	}

}
