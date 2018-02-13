package com.mr.newapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.TimeZone;

import com.mr.datainsert.Dataconn;
import com.mr.newdata.Fibondata;
import com.mr.newdata.StockOtherTechnicals;
import com.mr.newdata.TickData;
import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MAType;
import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.RetCode;

public class Fibonaccidataupdate implements java.lang.Runnable{

	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		ArrayList <String>  stocklist = getnse200fromdb();
		Iterator<String> it = stocklist.iterator();
		Dataconn dataconn =new Dataconn();
		Connection conn = dataconn.getconn();
		PreparedStatement stmt = null;
		String updatestatement = "update fibdata set stochk=?,stochd=?,WillR=?,rsi=?,bh=?,bh1=?,bh2=?,bh3=?,bh4=?,mid1=?,mid2=?,mid3=?,mid4=?,bl=?,bl1=?,bl2=?,sma200=? where stocksymbol=?";		
		String insertstatement = "insert into fibdata (stochk,stochd,willr,rsi,bh,bh1,bh2,bh3,bh4,mid1,mid2,mid3,mid4,bl,bl1,bl2,stocksymbol,sma200) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


while (it.hasNext()) {
			
	        String stocksymbol = (String) it.next();
	        Fibondata st = getfibonaccidata(stocksymbol);
	        if(st.getHighbasic() == 0)
				continue;
	      
	        try {
	        	  stmt = conn.prepareStatement(updatestatement);
	        	  stmt.setFloat(1, st.getStochk());
					stmt.setFloat(2, st.getStochd());
			        stmt.setFloat(3, st.getWpr());
			        stmt.setFloat(4, st.getRsi());
			        stmt.setFloat(5, st.getHighbasic());
			        stmt.setFloat(6, st.getHighbasic1());
			        stmt.setFloat(7, st.getHighbasic2());
			        stmt.setFloat(8, st.getHighbasic3());
			        stmt.setFloat(9, st.getHighbasic4());
			        stmt.setFloat(10, st.getMid1());
			        stmt.setFloat(11, st.getMid2());
			        stmt.setFloat(12, st.getMid3());
			        stmt.setFloat(13, st.getMid4());
			        stmt.setFloat(14, st.getLowbasic());
			        stmt.setFloat(15, st.getLowbasic1());
			        stmt.setFloat(16, st.getLowbasic2());
			        stmt.setFloat(17, (float) st.getSma200());
			        stmt.setString(18, stocksymbol);
					stmt.executeUpdate();
		        int rowsUpdated = stmt.executeUpdate();
		        
		    	if (rowsUpdated == 0) {
					//	System.out.println("Update failed so inserting");
						stmt.close();
						stmt = conn.prepareStatement(insertstatement);
						stmt.setFloat(1, st.getStochk());
						stmt.setFloat(2, st.getStochd());
				        stmt.setFloat(3, st.getWpr());
				        stmt.setFloat(4, st.getRsi());
				        stmt.setFloat(5, st.getHighbasic());
				        stmt.setFloat(6, st.getHighbasic1());
				        stmt.setFloat(7, st.getHighbasic2());
				        stmt.setFloat(8, st.getHighbasic3());
				        stmt.setFloat(9, st.getHighbasic4());
				        stmt.setFloat(10, st.getMid1());
				        stmt.setFloat(11, st.getMid2());
				        stmt.setFloat(12, st.getMid3());
				        stmt.setFloat(13, st.getMid4());
				        stmt.setFloat(14, st.getLowbasic());
				        stmt.setFloat(15, st.getLowbasic1());
				        stmt.setFloat(16, st.getLowbasic2());
				        stmt.setString(17, stocksymbol);
				        stmt.setFloat(18, (float) st.getSma200());
						stmt.executeUpdate();
						
						
					}
					
					stmt.close();
					
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        
}
dataconn.closeconn();

	}

	public Fibondata getfibonaccidata(String stocksymbol) {
		// TODO Auto-generated method stub
		String strt = null;
        SimpleDateFormat sf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss z");  
        SimpleDateFormat sf1 = new SimpleDateFormat("dd MMM yyyy HH:mm:ss"); 
        
        //currentdate in SGT.
        ArrayList<Float> low = new ArrayList<Float>();
        ArrayList<Float> high = new ArrayList<Float>();
        ArrayList<Float> obvvol = new ArrayList<Float>();
        ArrayList<Float> close = new ArrayList<Float>();
        ArrayList<Float> highlow2 = new ArrayList<Float>();
        Calendar c = Calendar.getInstance();
		String Stocksymbol = stocksymbol;
		double indick=0,indicd=0,wpr=0,rsi=0;
		Float openprice,closeprice = 0f,highprice,lowprice;
		Float fopen=0f,fclose=0f,fhigh=0f,flow=0f;
		double sma50 =0;
		Float C_openprice = 0f,C_closeprice =0f,C_highprice =0f,C_lowprice =0f;
		Float obvvolume = 0f ,currentvolume,C_currentvolume;
		Long timestamp = null;
		Date tickstart = null,tickend = null;
		
		URL tdlink = null;
		try {
			tdlink = new URL("https://finance.google.com/finance/getprices?q="+stocksymbol+"&x=NSE&p=5Y&f=d,o,h,l,c,v");
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		URLConnection conn1 =null;
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			conn1 = tdlink.openConnection();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(conn1.getInputStream()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		ArrayList<TickData> tickhash = new ArrayList<TickData>();
		String inputLine;
		boolean start =false;
        try {
			while ((inputLine = in.readLine()) != null)
				{
		//System.out.println(inputLine);
				if (!start && inputLine.startsWith("a"))
				{
                    start=true;
					StringTokenizer st = new StringTokenizer(inputLine,",");
					String timestampstring = (String) st.nextElement();
					timestamp = Long.valueOf(timestampstring.substring(1));
					
					Calendar tickdate = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
					tickdate.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
					tickdate.setTimeInMillis((Long)timestamp*1000L) ;
					closeprice=Float.valueOf((String) st.nextElement());
					highprice=Float.valueOf((String) st.nextElement());
					lowprice=Float.valueOf((String) st.nextElement());
					openprice=Float.valueOf((String) st.nextElement());
					currentvolume= Float.valueOf((String) st.nextElement());
					
					sf.setTimeZone(TimeZone.getTimeZone("Asia/Singapore"));
					strt = sf.format(tickdate.getTime());
					//
					//tickend = tickdate.getTime();
					strt = strt.replaceAll("SGT", "");
					//
					//tickend = tickdate.getTime();
					try {
						tickend = sf1.parse(strt);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//System.out.println(tickend);
					Calendar tickstartdate = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
					tickstartdate.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
					
						tickstartdate.setTimeInMillis((Long)(timestamp-86400)*1000L) ;
					//tickstart = tickstartdate.getTime();
					
					sf.setTimeZone(TimeZone.getTimeZone("Asia/Singapore"));
					strt = sf.format(tickstartdate.getTime());
					//
					//tickend = tickdate.getTime();
					strt = strt.replaceAll("SGT", "");
					//
					//tickend = tickdate.getTime();
					try {
						tickstart = sf1.parse(strt);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					TickData  tickd = new TickData();
					tickd.setOpenprice(openprice);
					tickd.setHighprice(highprice);
					tickd.setLowprice(lowprice);
					tickd.setCloseprice(closeprice);
					tickd.setTickstart(tickstart);
					tickd.setTickend(tickend);
					tickd.setVolume(currentvolume);
					
					tickhash.add(tickd);
					
					
					continue;
					//System.out.println(obvvolume+"::"+currentvolume);
				}
				else if (start )
				{
					
					if (!inputLine.isEmpty())
					{
						Long currenttimestamp = 0L;
						StringTokenizer st = new StringTokenizer(inputLine,",");
						String timevar = (String)st.nextElement();
						if(timevar.startsWith("a"))
						{
							timevar = timevar.substring(1);
							timestamp = Long.valueOf(timevar);
							currenttimestamp = timestamp;
						}
						else
						{
						Long rg = Long.valueOf(timevar);
						//if(!this.actualinterval.equals("300") && !this.actualinterval.equals("600"))
						
							currenttimestamp = timestamp + (rg*86400);
						}
						Calendar tickdate = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
						tickdate.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
						tickdate.setTimeInMillis((Long)currenttimestamp*1000L) ;
						
						
						sf.setTimeZone(TimeZone.getTimeZone("Asia/Singapore"));
						strt = sf.format(tickdate.getTime());
						//System.out.println(strt);
						strt = strt.replaceAll("SGT", "");
						//
						//tickend = tickdate.getTime();
						try {
							tickend = sf1.parse(strt);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					//	System.out.println(tickend);
						
						C_closeprice=Float.valueOf((String) st.nextElement());
						C_highprice=Float.valueOf((String) st.nextElement());
						C_lowprice=Float.valueOf((String) st.nextElement());
						C_openprice=Float.valueOf((String) st.nextElement());
						C_currentvolume= Float.valueOf((String) st.nextElement());
						
						Calendar tickstartdate = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
						tickstartdate.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
						//if(!this.actualinterval.equals("300") && !this.actualinterval.equals("600"))
						
								tickstartdate.setTimeInMillis((Long)(timestamp-86400)*1000L) ;
						
	
						
						sf.setTimeZone(TimeZone.getTimeZone("Asia/Singapore"));
						strt = sf.format(tickstartdate.getTime());
						//
						//tickend = tickdate.getTime();
						strt = strt.replaceAll("SGT", "");
						//
						//tickend = tickdate.getTime();
						try {
							tickstart = sf1.parse(strt);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						
						
						TickData  tickd = new TickData();
						tickd.setOpenprice(C_openprice);
						tickd.setHighprice(C_highprice);
						tickd.setLowprice(C_lowprice);
						tickd.setCloseprice(C_closeprice);
						tickd.setTickstart(tickstart);
						tickd.setTickend(tickend);
						tickd.setVolume(C_currentvolume);
						
						tickhash.add(tickd);
						
						

						
						
						
						
					}
				
				
				
				}
				}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        ArrayList<TickData> Converttick = new ArrayList<TickData>();
        Converttick = tickhash;
        
        
        for (int ik =0;ik < Converttick.size();ik++)
        {
        	float prevclose = 0f;
        	TickData Currtickdata = Converttick.get(ik);
        	if (ik > 0)
        	{
        		TickData prevtickdata = Converttick.get(ik-1);
        		prevclose = prevtickdata.getCloseprice();
        		obvvolume=getobvvol(Currtickdata.getHighprice(),Currtickdata.getLowprice(),Currtickdata.getCloseprice(),Currtickdata.getVolume(),obvvolume,prevclose);
        	}
        	else
        	{
        		prevclose = Currtickdata.getCloseprice();
        		obvvolume = Currtickdata.getVolume();
        	}
        	
        	fopen= Currtickdata.getOpenprice();
        	fhigh= Currtickdata.getHighprice();
        	flow=Currtickdata.getLowprice();
        	fclose=Currtickdata.getCloseprice();
        	
        	low.add(Currtickdata.getLowprice());
        	high.add(Currtickdata.getHighprice());
        	close.add(Currtickdata.getCloseprice());
        	
        	obvvol.add(obvvolume);
        	
        	
        }
        
        
        float[] lowArray = new float[low.size()];
        int k = 0;

        for (Float f : low) {
        	lowArray[k++] = (f != null ? f : Float.NaN); // Or whatever default you want.
        }
        
        float[] highArray = new float[high.size()];
        k = 0;

        for (Float f : high) {
        	highArray[k++] = (f != null ? f : Float.NaN); // Or whatever default you want.
        }
        
        float[] closeArray = new float[close.size()];
        k = 0;

        for (Float f : close) {
        	closeArray[k++] = (f != null ? f : Float.NaN); // Or whatever default you want.
        }
        
        
        
        float[] obvArray = new float[obvvol.size()];
       k = 0;

        for (Float f : obvvol) {
        	obvArray[k++] = f; // Or whatever default you want.
        }
        
        Float lowdiff;
        Float low_lowdiff =1000000000f;
        Float high_lowdiff=0f;
        
        Float highdiff;
        Float low_highdiff =1000000000f;
        Float high_highdiff=0f;
        
        Integer Length = low.size();
        Core c1 = new Core();
        double[] closePrice = new double[5000];
        double[] out = new double[5000];
        MInteger begin = new MInteger();
        MInteger length = new MInteger();
        
        ArrayList<Float> lowdiffarr = new ArrayList<Float>();
        ArrayList<Float> highdiffarr = new ArrayList<Float>();
        
        	
        		 RetCode retCode =c1.wma(0, lowArray.length-1, lowArray, 50, begin, length, out) ;
        		 if (retCode == RetCode.Success) {
        			 for (int i = begin.value; i < length.value+begin.value; i++) {
        				 lowdiff = lowArray[i] - Float.valueOf(String.valueOf(out[i-begin.value]));
            			// System.out.println(i+":"+lowdiff+";"+lowArray[i]+"::"+out[i-begin.value]);
            			 lowdiffarr.add(lowdiff);
        			 }
        		 }
        			
        			 
        		 retCode =c1.wma(0, highArray.length-1, highArray, 50, begin, length, out) ;
        		 if (retCode == RetCode.Success) {
        			 for (int i = begin.value; i < length.value+begin.value; i++) {
        				 highdiff = highArray[i] - Float.valueOf(String.valueOf(out[i-begin.value]));
            			 
            			 highdiffarr.add(highdiff);
        			 }
        		 }
        			
        		 
        		
        		
        		 
        	
        	
        	float[] highdiffArray = new float[highdiffarr.size()];
            k = 0;

            for (Float f : highdiffarr) {
            	highdiffArray[k++] = (f != null ? f : Float.NaN); // Or whatever default you want.
            } 
            
        	float[] lowdiffArray = new float[lowdiffarr.size()];
            k = 0;

            for (Float f : lowdiffarr) {
            	lowdiffArray[k++] = (f != null ? f : Float.NaN); // Or whatever default you want.
            } 
            
            
          
            
        // lowdiffArray, highdiffarray, obvArray,lowarray ,closeArrayready
        	
            DecimalFormat df1 = new DecimalFormat("#.##"); 
        
          
          double [] vals = getstochvals(lowdiffArray,highdiffArray,obvArray,lowArray,retCode,c1);
         
          indick = Double.valueOf(df1.format(vals[0]));
          indicd=Double.valueOf(df1.format(vals[1]));
		 
           retCode = c1.willR(0, closeArray.length-1, highArray, lowArray, closeArray, 21, begin, length, out);
           if (retCode == RetCode.Success) {
        	   try{
        	   wpr= Double.valueOf(df1.format(out[length.value-1]));
        	   }
        	   catch(Exception e)
        	   {
        		   wpr=75;
        	   }
           }
		 
           retCode = c1.rsi(0, closeArray.length-1, closeArray, 14, begin, length, out);
  if (retCode == RetCode.Success) {
	  try{
	  rsi= Double.valueOf(df1.format(out[length.value-1]));
	  }
	  catch(Exception e)
	  {
		  rsi=75;
	  }
        	   
           }
  double sma200 = 0;
  retCode = c1.sma(0, closeArray.length-1, closeArray, 200, begin, length, out);

  if (retCode == RetCode.Success) {
	  
	try{
	  sma200= Double.valueOf(df1.format(out[length.value-1]));
	  }
	  catch(Exception e)
	  {
		  
		  sma200=(double) 0;
	  }
        	   
           }
  
  System.out.println(stocksymbol+":"+":"+wpr);
  Fibondata fd = new Fibondata();
  
  fd = getfibondata(stocksymbol,closeArray,highArray,c1,retCode,begin,length,out);
  fd.setStochk((float) indick);
  fd.setStochd((float) indicd);
  fd.setRsi((float) rsi);
  fd.setWpr((float) wpr);
  fd.setSma200(sma200);
  
  return fd;
 
        
	}

	private Fibondata getfibondata(String stocksymbol, float[] closeArray, float[] highArray, Core c1, RetCode retCode, MInteger begin, MInteger length, double[] out) {
		// TODO Auto-generated method stub
		
		double lw = 0;
		double hg = 0;
        float dev =0;
        ArrayList<Double> bh1 = new ArrayList<Double> (),bh2 = new ArrayList<Double> (),bh3 = new ArrayList<Double> (),bh4 = new ArrayList<Double> (),bh = new ArrayList<Double> (),mid1 = new ArrayList<Double> (),mid2 = new ArrayList<Double> (),mid3 = new ArrayList<Double> (),mid4 = new ArrayList<Double> (),bl= new ArrayList<Double> (),bl1=new ArrayList<Double> (),bl2=new ArrayList<Double> ();
        float f_bh1;
float f_bh2,f_bh3,f_bh4,f_bh,f_mid1,f_mid2,f_mid3,f_mid4,f_bl,f_bl1,f_bl2;
       

for (int i=200 ; i< closeArray.length;i++)
{
	hg = 0;
	lw = 0;
	
	retCode = c1.min(i-200, i, closeArray, 200, begin, length, out);
	if (retCode == RetCode.Success) {
		lw =  out[length.value-1];
	}
	retCode = c1.max(i-200, i, highArray, 200, begin, length, out);
	if (retCode == RetCode.Success) {
		hg = out[length.value-1];
	}
	dev = (float) (hg-lw);
	
	
double basish_h1 = (float) (hg + (0.236*dev));
bh1.add(basish_h1);
double basish_h2 = (float) (hg + (0.414*dev));
bh2.add(basish_h2);
double basish_h3 = (float) (hg + (0.618*dev));
bh3.add(basish_h3);
double basish_h4 = (float) (hg + (1.001*dev));
bh4.add(basish_h4);
double basish = hg;
bh.add(basish);
double mid_1 = (float) (hg - (0.382*dev));
mid1.add(mid_1);
double mid_2 = (float) (hg - (0.618*dev));
mid2.add(mid_2);
double mid_3 = (float) (hg - (0.764*dev));
mid3.add(mid_3);
double mid_4 = (float) (hg - (0.236*dev));
mid4.add(mid_4);
double basisl = lw;
bl.add(basisl);
double basis_l1 = (float) (lw- (0.236*dev));
bl1.add(basis_l1);
double basis_l2 = (float) (lw- (0.414*dev));
bl2.add(basis_l2);
	
 
}		
		
f_bh1 = getsma23(bh1,c1,begin, length, out,retCode);
f_bh2 = getsma23(bh2,c1,begin, length, out,retCode);
f_bh3 = getsma23(bh3,c1,begin, length, out,retCode);
f_bh4 = getsma23(bh4,c1,begin, length, out,retCode);
f_bh = getsma23(bh,c1,begin, length, out,retCode);
f_mid1 =  getsma23(mid1,c1,begin, length, out,retCode);
f_mid2 =  getsma23(mid2,c1,begin, length, out,retCode);
f_mid3 =  getsma23(mid3,c1,begin, length, out,retCode);
f_mid4 =  getsma23(mid4,c1,begin, length, out,retCode);
f_bl = getsma23(bl,c1,begin, length, out,retCode);
f_bl1 = getsma23(bl1,c1,begin, length, out,retCode);
f_bl2 = getsma23(bl2,c1,begin, length, out,retCode);
		
Fibondata fd = new Fibondata();		
fd.setStocksymbol(stocksymbol);		
fd.setHighbasic(f_bh);
fd.setHighbasic1(f_bh1);
fd.setHighbasic2(f_bh2);
fd.setHighbasic3(f_bh3);
fd.setHighbasic4(f_bh4);
fd.setLowbasic(f_bl);
fd.setLowbasic1(f_bl1);
fd.setLowbasic2(f_bl2);
fd.setMid1(f_mid1);
fd.setMid2(f_mid2);
fd.setMid3(f_mid3);
fd.setMid4(f_mid4);
	
		
		
		
		
		
		
		
		return fd;
	}

	private float getsma23(ArrayList<Double> inarray, Core c1, MInteger begin, MInteger length, double[] out, RetCode retCode) {
		// TODO Auto-generated method stub
		
		 double[] closeArray = new double[inarray.size()];
	        int k = 0;

	        for (Double f : inarray) {
	        	closeArray[k++] = (f != null ? f : Double.NaN); // Or whatever default you want.
	        }
	        
		float sma200 =0.1f ,sma300 = 0.1f;
		retCode = c1.sma(0, closeArray.length-1, closeArray, 200, begin, length, out);
		if (retCode == RetCode.Success) {
			if(length.value == 0)
				return 0;
			sma200 = (float) out[length.value-1];
		}
		retCode = c1.sma(0, closeArray.length-1, closeArray, 300, begin, length, out);
		if (retCode == RetCode.Success) {
			if(length.value == 0)
				return sma200;
			sma300 = (float) out[length.value-1];
		}
		
		return (sma200+sma300)/2;
		
		
	}

	private ArrayList<String> getnse200fromdb() {
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

	private static Float getobvvol(Float c_highprice, Float c_lowprice, Float c_closeprice, Float c_currentvolume,
			Float obvvolume, Float closeprice) {
		// TODO Auto-generated method stub
		
		Float range = (c_highprice+c_lowprice)/2;
		if (c_closeprice >= range)
		{
			if (c_closeprice >= closeprice)
			{
				return (float) (obvvolume + c_currentvolume);
			}
			else
			{
				return obvvolume;
			}
		}
		else
		{
			if (c_closeprice >= closeprice)
			{
				return obvvolume ;
			}
			else
			{
				return obvvolume - c_currentvolume;
			}
		}
		
	}
	
	private static double[] getstochvals(float[] lowdiffArray, float[] highdiffArray, float[] obvArray, float[] lowArray,
			RetCode retCode, Core c) {
		 double[] outFastK = new double[5000];
			double[] outFastD = new double[5000];
			MInteger outNBElement = new MInteger(); 
			MInteger outBegIdx = new MInteger(); 
			
			double lowk=0,lowd=0,highk=0,highd=0,volk = 0,vold=0,pricek = 0,priced=0,finalk = 0,finald = 0;
			/*double[] outMin = null;
			double[] outMax = null;
			retCode = c.minMax(0, lowdiffArray.length-1, lowdiffArray, 14, outBegIdx, outNBElement, outMin, outMax);
			*/
			retCode = c.stochF(0, lowdiffArray.length-1, lowdiffArray, lowdiffArray, lowdiffArray, 14, 3, MAType.Wma, outBegIdx, outNBElement, outFastK, outFastD);
			 if (retCode == RetCode.Success) {
				 try{
				 lowk=outFastK[outNBElement.value-1] ;
				 lowd=outFastD[outNBElement.value-1];
				 }
				 catch(Exception e)
				 {
					 lowk=75;
					 lowd=75;
				 }
				// System.out.println(outFastK[outNBElement.value-1]+"::"+outFastD[outNBElement.value-1]);
			 }
			 
			 retCode = c.stochF(0, highdiffArray.length-1, highdiffArray, highdiffArray, highdiffArray, 14, 3, MAType.Wma, outBegIdx, outNBElement, outFastK, outFastD);
			 if (retCode == RetCode.Success) {
				 try{
				 highk= outFastK[outNBElement.value-1] ;
				 highd=outFastD[outNBElement.value-1];
				 }
				 catch(Exception e)
				 {
					 highk=75;
					 highd=75;
				 }
				 //System.out.println(outFastK[outNBElement.value-1]+"::"+outFastD[outNBElement.value-1]);
			 }
			 
			 retCode = c.stochF(0, obvArray.length-1, obvArray, obvArray, obvArray, 14, 3, MAType.Wma, outBegIdx, outNBElement, outFastK, outFastD);
		
			 if (retCode == RetCode.Success) {
				 try
				 {
				 volk=outFastK[outNBElement.value-1] ;
				 vold=outFastD[outNBElement.value-1];
				 }
				 catch(Exception e)
				 {
					 volk=75;
					 vold=75;
				 }
			//	System.out.println(outFastK[outNBElement.value-1]+"::"+outFastD[outNBElement.value-1]);
			 }
			 
			 retCode = c.stochF(0, lowArray.length-1, lowArray, lowArray, lowArray, 14, 5, MAType.Wma, outBegIdx, outNBElement, outFastK, outFastD);
				
			 if (retCode == RetCode.Success) {
				 try
				 {
				pricek=outFastK[outNBElement.value-1];
				priced=outFastD[outNBElement.value-1];
				 }
				 catch(Exception e)
				 {
					 pricek=75;
					 priced=75;
				 }
				// System.out.println(outFastK[outNBElement.value-1]+"::"+outFastD[outNBElement.value-1]);
			 }
		
			 finalk = (volk+pricek)/2;
			 finald= (vold+((lowk+lowd+highk+highd+pricek+priced)/6))/2;
			 
			 
			 return (new double[]{finalk,finald});
			 
			 
			 
	}

}
