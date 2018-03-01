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

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mr.datainsert.Dataconn;
import com.mr.newapp.Traderspitdownloader;
import com.mr.newdata.FibonaccirenderedObject;
import com.mr.newdata.Fibondata;
import com.mr.newdata.Newphpdata;
import com.mr.newdata.Nsetabledata;
import com.mr.newdata.RenderedObject;

/**
 * Servlet implementation class Fibonaccilevel
 */
@WebServlet("/Fibonaccilevel")
public class Fibonaccilevel extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Fibonaccilevel() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		
		String highdiff = (String) session.getAttribute("pricediffhighselected");
		if (highdiff == null || highdiff.equals(""))
			highdiff = "10";
		String lowdiff = (String) session.getAttribute("pricedifflowselected");
		if (lowdiff == null || lowdiff.equals(""))
			lowdiff = "0";
		String duration = (String) session.getAttribute("chartintervalselected");
		
		String tablename="fibdata"+duration;
		
		String tablename2="fibdata_v2"+duration;
		
		HashMap <String,FibonaccirenderedObject> output = new HashMap <String,FibonaccirenderedObject>();
		
		Dataconn dataconn = new Dataconn();
		Connection conn = dataconn.getconn();

		HashMap <String,Fibondata> nsetabdata = gettabledata(conn,tablename);
		HashMap <String,Fibondata> nsetabdata_v2 = gettabledata(conn,tablename2);
		
		
		dataconn.closeconn();
		Traderspitdownloader tddownloader = new Traderspitdownloader();
		HashMap <String,Newphpdata> currentprice= tddownloader.getmarketprice("nse200");
		
		ArrayList <String> stocklist = (ArrayList<String>) session.getAttribute("nse500");
		Iterator<String> it = stocklist.iterator();
		while (it.hasNext()) {
		
			 String stocksymbol = (String) it.next();
		        if(currentprice.containsKey(stocksymbol) && nsetabdata.containsKey(stocksymbol)  && nsetabdata_v2.containsKey(stocksymbol))
		        {
		        	
		        	FibonaccirenderedObject ro = getFibonaccirenderedObject(currentprice.get(stocksymbol),nsetabdata.get(stocksymbol),lowdiff,highdiff,session,stocksymbol);
		        	if (ro!= null)
		        	{
		        		output.put(stocksymbol, ro);
		        	}
		        	
		        	if (session.getAttribute("show2").equals("Y") )
		        	{
		        	ro = getFibonaccirenderedObject(currentprice.get(stocksymbol),nsetabdata_v2.get(stocksymbol),lowdiff,highdiff,session,stocksymbol);
		        	if (ro!= null)
		        	{
		        		output.put(stocksymbol+"v2", ro);
		        	}
		        	}
		        	
		        }
			
		        request.setAttribute("stocklist", output);
		        request.setAttribute("highdiff",highdiff );
				request.setAttribute("lowdiff", lowdiff);
				request.setAttribute("Minselect", duration);
				
			
			
		}
		request.getRequestDispatcher("Fibonaccidatadisplay.jsp").forward(request, response);
	}

	private FibonaccirenderedObject getFibonaccirenderedObject(Newphpdata newphpdata, Fibondata fibondata,
			String lowdiff, String highdiff, HttpSession session, String stocksymbol) {
		// TODO Auto-generated method stub
		FibonaccirenderedObject ro = new FibonaccirenderedObject();
		ro.setStocksymbol(stocksymbol);
		ro.setStochk(fibondata.getStochk());
		ro.setStochd(fibondata.getStochd());
		ro.setRsi(fibondata.getRsi());
		ro.setWilliamsr(fibondata.getWpr());
		ro.setLasttradedprice(newphpdata.getLastprice());
		ro.setMatchfound(false);
		//setprice(ro,lowdiff,highdiff,fibondata.getHighbasic(),"High0",newphpdata);
		//setprice(ro,lowdiff,highdiff,fibondata.getHighbasic1(),"High1",newphpdata);
		//setprice(ro,lowdiff,highdiff,fibondata.getHighbasic2(),"High2",newphpdata);
		//setprice(ro,lowdiff,highdiff,fibondata.getHighbasic3(),"High3",newphpdata);
		//setprice(ro,lowdiff,highdiff,fibondata.getHighbasic4(),"High4",newphpdata);
		//setprice(ro,lowdiff,highdiff,fibondata.getMid1(),"Mid1",newphpdata);
		//setprice(ro,lowdiff,highdiff,fibondata.getMid2(),"Mid2",newphpdata);
		//setprice(ro,lowdiff,highdiff,fibondata.getMid3(),"Mid3",newphpdata);
		//setprice(ro,lowdiff,highdiff,fibondata.getMid4(),"Mid4",newphpdata);
		setprice(ro,lowdiff,highdiff,fibondata.getLowbasic(),"Low0",newphpdata);
		//setprice(ro,lowdiff,highdiff,fibondata.getLowbasic1(),"Low1",newphpdata);
		//setprice(ro,lowdiff,highdiff,fibondata.getLowbasic2(),"Low2",newphpdata);
		//setprice(ro,lowdiff,highdiff,(float) fibondata.getSma200(),"sma200",newphpdata);
		
		if(ro.isMatchfound())
			return ro;
		else
			return null;
		
		
	}

	private void setprice(FibonaccirenderedObject ro, String lowdiff, String highdiff, float comparewith, String levelstring, Newphpdata newphpdata) {
		// TODO Auto-generated method stub
		
		
		float diffltp = ((newphpdata.getLastprice() - comparewith)/comparewith)*100;
		float lowpercent = ((newphpdata.getLowprice()- comparewith)/comparewith)*100;
		float highpercent = ((newphpdata.getHighprice()- comparewith)/comparewith)*100;
		
		if(diffltp >= Float.valueOf(lowdiff) && diffltp <= Float.valueOf(highdiff))
		{
			ro.setLevelvalue(comparewith);
			ro.setLevel(levelstring);
			ro.setHighdiff(gettruncatedfloat(highpercent));
					ro.setLowdiff(gettruncatedfloat(lowpercent));
					ro.setLTPdiff(gettruncatedfloat(diffltp));
					ro.setMatchfound(true);
					
		}
			
		
	}

	private float gettruncatedfloat(float d) {
		// TODO Auto-generated method stub
		BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
	}

	private HashMap<String, Fibondata> gettabledata(Connection conn, String tablename) {
		// TODO Auto-generated method stub
		HashMap<String, Fibondata> fd = new HashMap<String, Fibondata> ();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String Query = "select * from "+tablename;
			ResultSet rs = stmt.executeQuery(Query);
			while (rs.next()) {
				Fibondata fnew = new Fibondata();
				fnew.setStocksymbol(rs.getString(1));
				fnew.setStochk(rs.getFloat(2));
				fnew.setStochd(rs.getFloat(3));
				fnew.setWpr(rs.getFloat(4));
				fnew.setRsi(rs.getFloat(5));
				fnew.setHighbasic(rs.getFloat(6));
				fnew.setHighbasic1(rs.getFloat(7));
				fnew.setHighbasic2(rs.getFloat(8));
				fnew.setHighbasic3(rs.getFloat(9));
				fnew.setHighbasic4(rs.getFloat(10));
				fnew.setMid1(rs.getFloat(11));
				fnew.setMid2(rs.getFloat(12));
				fnew.setMid3(rs.getFloat(13));
				fnew.setMid4(rs.getFloat(14));
				fnew.setLowbasic(rs.getFloat(15));
				fnew.setLowbasic1(rs.getFloat(16));
				fnew.setLowbasic2(rs.getFloat(17));
				fnew.setSma200(rs.getFloat(18));
				fd.put(fnew.getStocksymbol(), fnew);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
		
		return fd;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
HttpSession session = request.getSession(false);
		
	
		
		
		session.setAttribute("pricedifflowselected", request.getParameter("pricedifflow"));
		session.setAttribute("pricediffhighselected",request.getParameter("pricediffhigh")) ;
		session.setAttribute("chartintervalselected",request.getParameter("chartinterval")) ;
		
		
	
		
		doGet(request, response);
	}

}
