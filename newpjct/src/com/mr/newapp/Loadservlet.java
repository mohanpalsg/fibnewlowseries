package com.mr.newapp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.mr.datainsert.Dataconn;
import com.mr.newdata.Pivotobject;
import com.mysql.jdbc.PreparedStatement;




/**
 * Servlet implementation class Loadservlet
 */
@WebServlet("/Loadservlet")
public class Loadservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Loadservlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	

		HttpSession session = request.getSession(false);
		session.setAttribute("nse200", getnse200());
		session.setAttribute("nse200", getnse200fromdb());
		session.setAttribute("nse500", getnse500());
		session.setAttribute("chartintervalselected", getchartinterval());
		session.setAttribute("avg1", getAvg1());
		
		session.setAttribute("avg2",getAvg2());
		session.setAttribute("pdlength",getpd());
		session.setAttribute("adddiff", getadddiff());
		
		
		//session.setAttribute("weekpivot", getweekpivot());
		//session.setAttribute("monthpivot", getmonthpivot());
		
	request.getRequestDispatcher("./LoginHome/dashboardhome.jsp").forward(request, response);
		//request.getRequestDispatcher("angtest.html").forward(request, response);
	}

	private Object getadddiff() {
		// TODO Auto-generated method stub
		Dataconn dataconn =new Dataconn();
		Connection conn = dataconn.getconn();
		String val  =new String();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select value from defaultsetting where setting=\'adddiff\'");
			while (rs.next()) {

				val=(rs.getString(1));
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return val;
	}

	private Object getpd() {
		// TODO Auto-generated method stub
		Dataconn dataconn =new Dataconn();
		Connection conn = dataconn.getconn();
		String val  =new String();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select value from defaultsetting where setting=\'pdlength\'");
			while (rs.next()) {

				val=(rs.getString(1));
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return val;
	}

	private Object getAvg2() {
		// TODO Auto-generated method stub
		Dataconn dataconn =new Dataconn();
		Connection conn = dataconn.getconn();
		String val  =new String();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select value from defaultsetting where setting=\'avg2\'");
			while (rs.next()) {

				val=(rs.getString(1));
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return val;
	}

	private Object getAvg1() {
		// TODO Auto-generated method stub
		Dataconn dataconn =new Dataconn();
		Connection conn = dataconn.getconn();
		String val  =new String();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select value from defaultsetting where setting=\'avg1\'");
			while (rs.next()) {

				val=(rs.getString(1));
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return val;
	}

	private Object getchartinterval() {
		// TODO Auto-generated method stub
		Dataconn dataconn =new Dataconn();
		Connection conn = dataconn.getconn();
		String val  =new String();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select value from defaultsetting where setting=\'DF_PD\'");
			while (rs.next()) {

				val=(rs.getString(1));
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return val;
	}

	private HashMap<String,Pivotobject>  getmonthpivot() {
		// TODO Auto-generated method stub
		HashMap<String,Pivotobject> pivot = new HashMap <String,Pivotobject> ();
		Dataconn dataconn = new Dataconn();
		Connection conn = dataconn.getconn();
		java.sql.PreparedStatement ps =null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("select stocksymbol,s1,s2,s3,s4,r1,r2,r3,r4 from Camarillamonthlypivot");
			 rs = ps.executeQuery();
			while (rs.next())
			{
				Pivotobject po = new Pivotobject();
				po.setStocksymbol(rs.getString(1));
				po.setS1(rs.getFloat(2));
				po.setS2(rs.getFloat(3));
				po.setS3(rs.getFloat(4));
				po.setS4(rs.getFloat(5));
				po.setR1(rs.getFloat(6));
				po.setR2(rs.getFloat(7));
				po.setR3(rs.getFloat(8));
				po.setR4(rs.getFloat(9));
				pivot.put(po.getStocksymbol(), po);
				
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
					ps.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			e.printStackTrace();
		}
		try {
			rs.close();
			ps.close();
			dataconn.closeconn();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return pivot;
		
	}

	private HashMap<String,Pivotobject> getweekpivot() {
		// TODO Auto-generated method stub
		HashMap<String,Pivotobject> pivot = new HashMap <String,Pivotobject> ();
		Dataconn dataconn = new Dataconn();
		Connection conn = dataconn.getconn();
		java.sql.PreparedStatement ps =null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("select stocksymbol,s1,s2,s3,s4,r1,r2,r3,r4 from Camarillaweekpivot");
			 rs = ps.executeQuery();
			while (rs.next())
			{
				Pivotobject po = new Pivotobject();
				po.setStocksymbol(rs.getString(1));
				po.setS1(rs.getFloat(2));
				po.setS2(rs.getFloat(3));
				po.setS3(rs.getFloat(4));
				po.setS4(rs.getFloat(5));
				po.setR1(rs.getFloat(6));
				po.setR2(rs.getFloat(7));
				po.setR3(rs.getFloat(8));
				po.setR4(rs.getFloat(9));
				pivot.put(po.getStocksymbol(), po);
				
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {

				ps.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			e.printStackTrace();
		}
		try {

			ps.close();
			dataconn.closeconn();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return pivot;
		
		
	}

	private Object getnse200fromdb() {
		// TODO Auto-generated method stub
		Dataconn dataconn =new Dataconn();
		Connection conn = dataconn.getconn();
		ArrayList <String> nse200  =new ArrayList <String>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select STOCKSYMBOL from nse200");
			while (rs.next()) {

				nse200.add(rs.getString(1));
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return nse200;
	}

	private ArrayList<String> getnse500() {
		// TODO Auto-generated method stub
		ArrayList<String> nse500 = new ArrayList<String> ();
		
		nse500.add("3MINDIA");
		nse500.add("8KMILES");
		nse500.add("ABB");
		nse500.add("ACC");
		nse500.add("AIAENG");
		nse500.add("APLAPOLLO");
		nse500.add("AARTIIND");
		nse500.add("ABAN");
		nse500.add("ADANIENT");
		nse500.add("ADANIPORTS");
		nse500.add("ADANIPOWER");
		nse500.add("ADANITRANS");
		nse500.add("ABFRL");
		nse500.add("ADVENZYMES");
		nse500.add("AEGISCHEM");
		nse500.add("AHLUCONT");
		nse500.add("AJANTPHARM");
		nse500.add("AKZOINDIA");
		nse500.add("APLLTD");
		nse500.add("ALKEM");
		nse500.add("ALBK");
		nse500.add("ALLCARGO");
		nse500.add("AMARAJABAT");
		nse500.add("AMBUJACEM");
		nse500.add("AMTEKAUTO");
		nse500.add("ANANTRAJ");
		nse500.add("ANDHRABANK");
		nse500.add("APARINDS");
		nse500.add("APOLLOHOSP");
		nse500.add("APOLLOTYRE");
		nse500.add("ARVIND");
		nse500.add("ASAHIINDIA");
		nse500.add("ASHOKLEY");
		nse500.add("ASHOKA");
		nse500.add("ASIANPAINT");
		nse500.add("ASTRAZEN");
		nse500.add("ASTRAL");
		nse500.add("ATUL");
		nse500.add("AUROPHARMA");
		nse500.add("AVANTIFEED");
		nse500.add("AXISBANK");
		nse500.add("BASF");
		nse500.add("BEML");
		nse500.add("BFUTILITIE");
		nse500.add("BGRENERGY");
		nse500.add("BAJAJ-AUTO");
		nse500.add("BAJAJCORP");
		nse500.add("BAJAJELEC");
		nse500.add("BAJFINANCE");
		nse500.add("BAJAJFINSV");
		nse500.add("BAJAJHIND");
		nse500.add("BAJAJHLDNG");
		nse500.add("BALKRISIND");
		nse500.add("BALLARPUR");
		nse500.add("BALMLAWRIE");
		nse500.add("BALRAMCHIN");
		nse500.add("BANKBARODA");
		nse500.add("BANKINDIA");
		nse500.add("BATAINDIA");
		nse500.add("BERGEPAINT");
		nse500.add("BEL");
		nse500.add("BHARATFIN");
		nse500.add("BHARATFORG");
		nse500.add("BHEL");
		nse500.add("BPCL");
		nse500.add("BHARTIARTL");
		nse500.add("INFRATEL");
		nse500.add("BHUSANSTL");
		nse500.add("BIOCON");
		nse500.add("BIRLACORPN");
		nse500.add("BLISSGVS");
		nse500.add("BLUEDART");
		nse500.add("BLUESTARCO");
		nse500.add("BBTC");
		nse500.add("BOMDYEING");
		nse500.add("BOSCHLTD");
		nse500.add("BRITANNIA");
		nse500.add("CARERATING");
		nse500.add("CCL");
		nse500.add("CESC");
		nse500.add("CGPOWER");
		nse500.add("CRISIL");
		nse500.add("CADILAHC");
		nse500.add("CANFINHOME");
		nse500.add("CANBK");
		nse500.add("CAPF");
		nse500.add("CAPLIPOINT");
		nse500.add("CARBORUNIV");
		nse500.add("CASTROLIND");
		nse500.add("CEATLTD");
		nse500.add("CENTRALBK");
		nse500.add("CENTURYPLY");
		nse500.add("CENTURYTEX");
		nse500.add("CERA");
		nse500.add("CHAMBLFERT");
		nse500.add("CHENNPETRO");
		nse500.add("CHOLAFIN");
		nse500.add("CIPLA");
		nse500.add("CUB");
		nse500.add("COALINDIA");
		nse500.add("COFFEEDAY");
		nse500.add("COLPAL");
		nse500.add("CONCOR");
		nse500.add("COROMANDEL");
		nse500.add("CORPBANK");
		//nse500.add("COX&KINGS");
		nse500.add("CROMPTON");
		nse500.add("CUMMINSIND");
		nse500.add("CYIENT");
		nse500.add("DBREALTY");
		nse500.add("DBCORP");
		nse500.add("DCBBANK");
		nse500.add("DCMSHRIRAM");
		nse500.add("DLF");
		nse500.add("DABUR");
		nse500.add("DALMIABHA");
		nse500.add("DEEPAKFERT");
		nse500.add("DELTACORP");
		nse500.add("DEN");
		nse500.add("DENABANK");
		nse500.add("DHFL");
		nse500.add("DHANUKA");
		nse500.add("DBL");
		nse500.add("DISHTV");
		nse500.add("DIVISLAB");
		nse500.add("LALPATHLAB");
		nse500.add("DRREDDY");
		nse500.add("DREDGECORP");
		nse500.add("EIDPARRY");
		nse500.add("EIHOTEL");
		nse500.add("EDELWEISS");
		nse500.add("EICHERMOT");
		nse500.add("EMAMILTD");
		nse500.add("ENDURANCE");
		nse500.add("ENGINERSIN");
		nse500.add("EQUITAS");
		nse500.add("EROSMEDIA");
		nse500.add("ESCORTS");
		nse500.add("ESSELPACK");
		nse500.add("EVEREADY");
		nse500.add("EXIDEIND");
		nse500.add("FDC");
		nse500.add("FEDERALBNK");
		nse500.add("FMGOETZE");
		nse500.add("FINCABLES");
		nse500.add("FINPIPE");
		nse500.add("FSL");
		nse500.add("FORTIS");
		nse500.add("FCONSUMER");
		nse500.add("FRETAIL");
		nse500.add("GAIL");
		nse500.add("GEPIL");
		//nse500.add("GET&D");
		nse500.add("GHCL");
		nse500.add("GMRINFRA");
		nse500.add("GVKPIL");
		nse500.add("GDL");
		nse500.add("GATI");
		nse500.add("GAYAPROJ");
		nse500.add("GILLETTE");
		nse500.add("GSKCONS");
		nse500.add("GLAXO");
		nse500.add("GLENMARK");
		nse500.add("GODFRYPHLP");
		nse500.add("GODREJCP");
		nse500.add("GODREJIND");
		nse500.add("GODREJPROP");
		nse500.add("GRANULES");
		nse500.add("GESHIP");
		nse500.add("GREAVESCOT");
		nse500.add("GREENPLY");
		nse500.add("GRINDWELL");
		nse500.add("GRUH");
		nse500.add("GUJALKALI");
		nse500.add("GUJFLUORO");
		nse500.add("GUJGASLTD");
		nse500.add("GMDCLTD");
		nse500.add("GNFC");
		nse500.add("GPPL");
		nse500.add("GSFC");
		nse500.add("GSPL");
		nse500.add("GULFOILLUB");
		nse500.add("HCL-INSYS");
		nse500.add("HCLTECH");
		nse500.add("HDFCBANK");
		nse500.add("HSIL");
		nse500.add("HTMEDIA");
		nse500.add("HATHWAY");
		nse500.add("HAVELLS");
		nse500.add("HEIDELBERG");
		nse500.add("HEROMOTOCO");
		nse500.add("HEXAWARE");
		nse500.add("HFCL");
		nse500.add("HIMATSEIDE");
		nse500.add("HINDALCO");
		nse500.add("HCC");
		nse500.add("HINDCOPPER");
		nse500.add("HMVL");
		nse500.add("HINDPETRO");
		nse500.add("HINDUNILVR");
		nse500.add("HINDZINC");
		nse500.add("HONAUT");
		nse500.add("HDFC");
		nse500.add("HDIL");
		nse500.add("ITC");
		nse500.add("ICICIBANK");
		nse500.add("ICICIPRULI");
		nse500.add("ICRA");
		nse500.add("IDBI");
		nse500.add("IDFCBANK");
		nse500.add("IDFC");
		nse500.add("IFCI");
	//	nse500.add("IL&FSTRANS");
		nse500.add("IRB");
		nse500.add("ITDCEM");
		nse500.add("IDEA");
		nse500.add("IGARASHI");
		nse500.add("INDIACEM");
		nse500.add("IBULHSGFIN");
		nse500.add("IBREALEST");
		nse500.add("INDIANB");
		nse500.add("INDHOTEL");
		nse500.add("IOC");
		nse500.add("IOB");
		nse500.add("ICIL");
		nse500.add("INDOCO");
		nse500.add("IGL");
		nse500.add("INDUSINDBK");
		nse500.add("INFIBEAM");
		nse500.add("NAUKRI");
		nse500.add("INFY");
		nse500.add("INOXLEISUR");
		nse500.add("INOXWIND");
		nse500.add("INTELLECT");
		nse500.add("INDIGO");
		nse500.add("IPCALAB");
		nse500.add("JBCHEPHARM");
		nse500.add("JKCEMENT");
		nse500.add("JKIL");
		nse500.add("JBFIND");
		nse500.add("JKLAKSHMI");
		nse500.add("JKTYRE");
		nse500.add("JMFINANCIL");
		nse500.add("JMTAUTOLTD");
		nse500.add("JSWENERGY");
		nse500.add("JSWSTEEL");
		nse500.add("JAGRAN");
		nse500.add("JAICORPLTD");
		nse500.add("JISLJALEQS");
		nse500.add("JPASSOCIAT");
	//	nse500.add("J&KBANK");
		nse500.add("JETAIRWAYS");
		nse500.add("JINDALPOLY");
		nse500.add("JINDALSTEL");
		nse500.add("JCHAC");
		nse500.add("JUBLFOOD");
		nse500.add("JUBILANT");
		nse500.add("JUSTDIAL");
		nse500.add("JYOTHYLAB");
		nse500.add("KPRMILL");
		nse500.add("KNRCON");
		nse500.add("KPIT");
		nse500.add("KRBL");
		nse500.add("KAJARIACER");
		nse500.add("KALPATPOWR");
		nse500.add("KANSAINER");
		nse500.add("KTKBANK");
		nse500.add("KARURVYSYA");
		nse500.add("KSCL");
		nse500.add("KEC");
		nse500.add("KESORAMIND");
		nse500.add("KITEX");
		nse500.add("KOLTEPATIL");
		nse500.add("KOTAKBANK");
		nse500.add("KWALITY");
		//nse500.add("L&TFH");
		nse500.add("LTTS");
		nse500.add("LICHSGFIN");
		nse500.add("LAXMIMACH");
		nse500.add("LAKSHVILAS");
		nse500.add("LTI");
		nse500.add("LT");
		nse500.add("LAURUSLABS");
		nse500.add("LUPIN");
		nse500.add("MMTC");
		nse500.add("MOIL");
		nse500.add("MRF");
		nse500.add("MAGMA");
		nse500.add("MGL");
		nse500.add("MTNL");
		////nse500.add("M&MFIN");
	//	nse500.add("M&M");
		nse500.add("MAHINDCIE");
		nse500.add("MHRIL");
		nse500.add("MANAPPURAM");
		nse500.add("MRPL");
		nse500.add("MANPASAND");
		nse500.add("MARICO");
		nse500.add("MARKSANS");
		nse500.add("MARUTI");
		nse500.add("MAXINDIA");
		nse500.add("MCLEODRUSS");
		nse500.add("MERCK");
		nse500.add("MINDTREE");
		nse500.add("MINDACORP");
		nse500.add("MINDAIND");
		nse500.add("MONSANTO");
		nse500.add("MOTHERSUMI");
		nse500.add("MOTILALOFS");
		nse500.add("MPHASIS");
		nse500.add("MUTHOOTFIN");
		nse500.add("NATCOPHARM");
		nse500.add("NBCC");
		nse500.add("NCC");
		nse500.add("NHPC");
		nse500.add("NIITTECH");
		nse500.add("NLCINDIA");
		nse500.add("NMDC");
		nse500.add("NTPC");
		nse500.add("NH");
		nse500.add("NATIONALUM");
		nse500.add("NBVENTURES");
		nse500.add("NAVINFLUOR");
		nse500.add("NAVKARCORP");
		nse500.add("NAVNETEDUL");
		nse500.add("NETWORK18");
		nse500.add("NILKAMAL");
		nse500.add("NITINFIRE");
		nse500.add("OBEROIRLTY");
		nse500.add("ONGC");
		nse500.add("OIL");
		nse500.add("OMAXE");
		nse500.add("OFSS");
		nse500.add("ORIENTCEM");
		nse500.add("ORIENTBANK");
		nse500.add("ORISSAMINE");
		nse500.add("PCJEWELLER");
		nse500.add("PIIND");
		nse500.add("PNBHOUSING");
		nse500.add("PNCINFRA");
		nse500.add("PFS");
		nse500.add("PTC");
		nse500.add("PVR");
		nse500.add("PAGEIND");
		nse500.add("PARAGMILK");
		nse500.add("PERSISTENT");
		nse500.add("PETRONET");
		nse500.add("PFIZER");
		nse500.add("PHOENIXLTD");
		nse500.add("PIDILITIND");
		nse500.add("PEL");
		nse500.add("POLARIS");
		nse500.add("PFC");
		nse500.add("POWERGRID");
		nse500.add("PRAJIND");
		nse500.add("PRESTIGE");
		nse500.add("PRISMCEM");
		nse500.add("PGHH");
		nse500.add("PUNJLLOYD");
		nse500.add("PNB");
		nse500.add("QUESS");
		nse500.add("RBLBANK");
		nse500.add("RADICO");
		nse500.add("RAIN");
		nse500.add("RAJESHEXPO");
		nse500.add("RALLIS");
		nse500.add("RAMCOSYS");
		nse500.add("RKFORGE");
		nse500.add("RCF");
		nse500.add("RATNAMANI");
		nse500.add("RTNPOWER");
		nse500.add("RAYMOND");
		nse500.add("REDINGTON");
		nse500.add("RELAXO");
		nse500.add("RELCAPITAL");
		nse500.add("RCOM");
		nse500.add("RDEL");
		nse500.add("RIIL");
		nse500.add("RELIANCE");
		nse500.add("RELINFRA");
		nse500.add("RPOWER");
		nse500.add("RELIGARE");
		nse500.add("REPCOHOME");
		nse500.add("ROLTA");
		nse500.add("RUCHISOYA");
		nse500.add("RECLTD");
		nse500.add("SHK");
		nse500.add("SJVN");
		nse500.add("SKFINDIA");
		nse500.add("SMLISUZU");
		nse500.add("SREINFRA");
		nse500.add("SRF");
		nse500.add("SADBHAV");
		nse500.add("SANOFI");
		nse500.add("SCHAEFFLER");
		nse500.add("SCHNEIDER");
		nse500.add("SHARDACROP");
		nse500.add("SFL");
		nse500.add("SHILPAMED");
		nse500.add("SHILPI");
		nse500.add("SCI");
		nse500.add("SHREECEM");
		nse500.add("RENUKA");
		nse500.add("SHRIRAMCIT");
		nse500.add("SRTRANSFIN");
		nse500.add("SIEMENS");
		nse500.add("SNOWMAN");
		nse500.add("SOBHA");
		nse500.add("SOLARINDS");
		nse500.add("SOMANYCERA");
		nse500.add("SONATSOFTW");
		nse500.add("SOUTHBANK");
		nse500.add("SBIN");
		nse500.add("STCINDIA");
		nse500.add("SAIL");
		nse500.add("STRTECH");
		nse500.add("STAR");
		nse500.add("SUDARSCHEM");
		nse500.add("SPARC");
		nse500.add("SUNPHARMA");
		nse500.add("SUNTV");
		nse500.add("SUNDARMFIN");
		nse500.add("SUNDRMFAST");
		nse500.add("SUNTECK");
		nse500.add("SUPREMEIND");
		nse500.add("SUVEN");
		nse500.add("SUZLON");
		nse500.add("SWANENERGY");
		nse500.add("SYMPHONY");
		nse500.add("SYNDIBANK");
		nse500.add("SYNGENE");
		nse500.add("TTKPRESTIG");
		nse500.add("TVTODAY");
		nse500.add("TV18BRDCST");
		nse500.add("TVSMOTOR");
		nse500.add("TVSSRICHAK");
		nse500.add("TAKE");
		nse500.add("TNPL");
		nse500.add("TATACHEM");
		nse500.add("TATACOFFEE");
		nse500.add("TATACOMM");
		nse500.add("TCS");
		nse500.add("TATAELXSI");
		nse500.add("TATAGLOBAL");
		nse500.add("TATAINVEST");
		nse500.add("TATAMTRDVR");
		nse500.add("TATAMOTORS");
		nse500.add("TATAPOWER");
		nse500.add("TATASPONGE");
		nse500.add("TATASTEEL");
		nse500.add("TECHM");
		nse500.add("TECHNO");
		nse500.add("TEXRAIL");
		nse500.add("RAMCOCEM");
		nse500.add("THERMAX");
		nse500.add("THOMASCOOK");
		nse500.add("THYROCARE");
		nse500.add("TIDEWATER");
		nse500.add("TIMKEN");
		nse500.add("TITAN");
		nse500.add("TORNTPHARM");
		nse500.add("TORNTPOWER");
		nse500.add("TRENT");
		nse500.add("TRIDENT");
		nse500.add("TRITURBINE");
		nse500.add("UCOBANK");
		nse500.add("UFLEX");
		nse500.add("UPL");
		nse500.add("UJJIVAN");
		nse500.add("ULTRACEMCO");
		nse500.add("UNICHEMLAB");
		nse500.add("UNIONBANK");
		nse500.add("UNITECH");
		nse500.add("UBL");
		nse500.add("MCDOWELL-N");
		nse500.add("VGUARD");
		nse500.add("VIPIND");
		nse500.add("VRLLOG");
		nse500.add("VSTIND");
		nse500.add("WABAG");
		nse500.add("VAKRANGEE");
		nse500.add("VTL");
		nse500.add("VBL");
		nse500.add("VEDL");
		nse500.add("VIDEOIND");
		nse500.add("VIJAYABANK");
		nse500.add("VINATIORGA");
		nse500.add("VOLTAS");
		nse500.add("WABCOINDIA");
		nse500.add("WELCORP");
		nse500.add("WELSPUNIND");
		nse500.add("WHIRLPOOL");
		nse500.add("WIPRO");
		nse500.add("WOCKPHARMA");
		nse500.add("WONDERLA");
		nse500.add("YESBANK");
		nse500.add("ZEEL");
		nse500.add("ZEELEARN");
		nse500.add("ZENSARTECH");
		nse500.add("ZYDUSWELL");
		nse500.add("ECLERX");

		
		return nse500;
	}

	private ArrayList<String> getnse200() {
		// TODO Auto-generated method stub
		ArrayList<String> nse200 = new ArrayList<String> ();
		
		nse200.add("ABB");
		nse200.add("ACC");
		nse200.add("AIAENG");
		nse200.add("ADANIENT");
		nse200.add("ADANIPORTS");
		nse200.add("ADANIPOWER");
		nse200.add("ABFRL");
		nse200.add("AJANTPHARM");
		nse200.add("APLLTD");
		nse200.add("ALKEM");
		nse200.add("AMARAJABAT");
		nse200.add("AMBUJACEM");
		nse200.add("APOLLOHOSP");
		nse200.add("APOLLOTYRE");
		nse200.add("ARVIND");
		nse200.add("ASHOKLEY");
		nse200.add("ASIANPAINT");
		nse200.add("AUROPHARMA");
		nse200.add("AXISBANK");
		nse200.add("BAJAJ-AUTO");
		nse200.add("BAJFINANCE");
		nse200.add("BAJAJFINSV");
		nse200.add("BANKBARODA");
		nse200.add("BANKINDIA");
		nse200.add("BATAINDIA");
		nse200.add("BERGEPAINT");
		nse200.add("BEL");
		nse200.add("BHARATFIN");
		nse200.add("BHARATFORG");
		nse200.add("BHEL");
		nse200.add("BPCL");
		nse200.add("BHARTIARTL");
		nse200.add("INFRATEL");
		nse200.add("BIOCON");
		nse200.add("BOSCHLTD");
		nse200.add("BRITANNIA");
		nse200.add("CESC");
		nse200.add("CRISIL");
		nse200.add("CADILAHC");
		nse200.add("CANBK");
		nse200.add("CASTROLIND");
		nse200.add("CENTRALBK");
		nse200.add("CENTURYTEX");
		nse200.add("CHOLAFIN");
		nse200.add("CIPLA");
		nse200.add("COALINDIA");
		nse200.add("COLPAL");
		nse200.add("CONCOR");
		nse200.add("COROMANDEL");
		nse200.add("CROMPTON");
		nse200.add("CUMMINSIND");
		nse200.add("DLF");
		nse200.add("DABUR");
		nse200.add("DALMIABHA");
		nse200.add("DHFL");
		nse200.add("DISHTV");
		nse200.add("DIVISLAB");
		nse200.add("LALPATHLAB");
		nse200.add("DRREDDY");
		nse200.add("EDELWEISS");
		nse200.add("EICHERMOT");
		nse200.add("EMAMILTD");
		nse200.add("ENDURANCE");
		nse200.add("ENGINERSIN");
		nse200.add("EXIDEIND");
		nse200.add("FEDERALBNK");
		nse200.add("FORTIS");
		nse200.add("GAIL");
		nse200.add("GMRINFRA");
		nse200.add("GSKCONS");
		nse200.add("GLAXO");
		nse200.add("GLENMARK");
		nse200.add("GODREJCP");
		nse200.add("GODREJIND");
		nse200.add("GRUH");
		nse200.add("GPPL");
		nse200.add("GSPL");
		nse200.add("HCLTECH");
		nse200.add("HDFCBANK");
		nse200.add("HAVELLS");
		nse200.add("HEROMOTOCO");
		nse200.add("HEXAWARE");
		nse200.add("HINDALCO");
		nse200.add("HINDPETRO");
		nse200.add("HINDUNILVR");
		nse200.add("HINDZINC");
		nse200.add("HDFC");
		nse200.add("ITC");
		nse200.add("ICICIBANK");
		nse200.add("ICICIPRULI");
		nse200.add("IDBI");
		nse200.add("IDFCBANK");
		nse200.add("IDFC");
		nse200.add("IRB");
		nse200.add("IDEA");
		nse200.add("IBULHSGFIN");
		nse200.add("INDIANB");
		nse200.add("INDHOTEL");
		nse200.add("IOC");
		nse200.add("IGL");
		nse200.add("INDUSINDBK");
		nse200.add("NAUKRI");
		nse200.add("INFY");
		nse200.add("INDIGO");
		nse200.add("IPCALAB");
		nse200.add("JSWENERGY");
		nse200.add("JSWSTEEL");
		nse200.add("JINDALSTEL");
		nse200.add("JUBLFOOD");
		nse200.add("JUBILANT");
		nse200.add("KARURVYSYA");
		nse200.add("KOTAKBANK");
	//	nse200.add("L&TFH");
		nse200.add("LTTS");
		nse200.add("LICHSGFIN");
		nse200.add("LT");
		nse200.add("LUPIN");
		nse200.add("MRF");
		nse200.add("MGL");
	//	nse200.add("M&MFIN");
	//	nse200.add("M&M");
		nse200.add("MANAPPURAM");
		nse200.add("MARICO");
		nse200.add("MARUTI");
		nse200.add("MINDTREE");
		nse200.add("MOTHERSUMI");
		nse200.add("MPHASIS");
		nse200.add("MUTHOOTFIN");
		nse200.add("NATCOPHARM");
		nse200.add("NBCC");
		nse200.add("NHPC");
		nse200.add("NMDC");
		nse200.add("NTPC");
		nse200.add("NATIONALUM");
		nse200.add("OBEROIRLTY");
		nse200.add("ONGC");
		nse200.add("OIL");
		nse200.add("OFSS");
		nse200.add("PCJEWELLER");
		nse200.add("PIIND");
		nse200.add("PNBHOUSING");
		nse200.add("PAGEIND");
		nse200.add("PETRONET");
		nse200.add("PIDILITIND");
		nse200.add("PEL");
		nse200.add("PFC");
		nse200.add("POWERGRID");
		nse200.add("PRESTIGE");
		nse200.add("PGHH");
		nse200.add("PNB");
		nse200.add("QUESS");
		nse200.add("RBLBANK");
		nse200.add("RAJESHEXPO");
		nse200.add("RELCAPITAL");
		nse200.add("RCOM");
		nse200.add("RELIANCE");
		nse200.add("RELINFRA");
		nse200.add("RPOWER");
		nse200.add("RECLTD");
		nse200.add("SRF");
		nse200.add("SHREECEM");
		nse200.add("SRTRANSFIN");
		nse200.add("SIEMENS");
		nse200.add("SBIN");
		nse200.add("SAIL");
		nse200.add("STAR");
		nse200.add("SPARC");
		nse200.add("SUNPHARMA");
		nse200.add("SUNTV");
		nse200.add("SUZLON");
		nse200.add("SYNDIBANK");
		nse200.add("SYNGENE");
		nse200.add("TV18BRDCST");
		nse200.add("TVSMOTOR");
		nse200.add("TATACHEM");
		nse200.add("TATACOMM");
		nse200.add("TCS");
		nse200.add("TATAGLOBAL");
		nse200.add("TATAMTRDVR");
		nse200.add("TATAMOTORS");
		nse200.add("TATAPOWER");
		nse200.add("TATASTEEL");
		nse200.add("TECHM");
		nse200.add("RAMCOCEM");
		nse200.add("THERMAX");
		nse200.add("TITAN");
		nse200.add("TORNTPHARM");
		nse200.add("TORNTPOWER");
		nse200.add("UPL");
		nse200.add("ULTRACEMCO");
		nse200.add("UNIONBANK");
		nse200.add("UBL");
		nse200.add("MCDOWELL-N");
		nse200.add("VAKRANGEE");
		nse200.add("VEDL");
		nse200.add("VOLTAS");
		nse200.add("WELSPUNIND");
		nse200.add("WIPRO");
		nse200.add("WOCKPHARMA");
		nse200.add("YESBANK");
		nse200.add("ZEEL");

		return nse200;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
