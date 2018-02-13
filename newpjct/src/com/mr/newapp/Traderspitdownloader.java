package com.mr.newapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import com.mr.newdata.Newphpdata;

public class Traderspitdownloader {

	public static HashMap<String, Newphpdata> getmarketprice(String list) {
		// TODO Auto-generated method stub
		
	   HashMap <String,Newphpdata> resultdata= new HashMap <String,Newphpdata>();
		BufferedReader in = null;
		String inputLine;
		boolean startmapping = false;
		try {
			URL tdlink = null;
			
				tdlink = new URL("https://nsertdata.000webhostapp.com/");
			
			URLConnection conn1 = tdlink.openConnection();
			in = new BufferedReader(new InputStreamReader(conn1.getInputStream()));
			while ((inputLine = in.readLine()) != null)
			{
				inputLine =inputLine.trim().replaceAll("\n ","");
				if(inputLine.isEmpty())
					continue;
			
				if(inputLine.equals("LINEHEADER"))
				{
					Newphpdata currdata = new Newphpdata();
					inputLine = in.readLine();
					currdata.setStocksymbol(inputLine);
					inputLine = in.readLine();
					currdata.setLastprice(Float.valueOf(inputLine));
					inputLine = in.readLine(); // change precentage
					inputLine = in.readLine();
					currdata.setOpenprice(Float.valueOf(inputLine)); //open
					inputLine = in.readLine();
					currdata.setHighprice(Float.valueOf(inputLine)); //high
					inputLine = in.readLine();
					currdata.setLowprice(Float.valueOf(inputLine)); //low
					inputLine = in.readLine();
					currdata.setPrevclose(Float.valueOf(inputLine));
					//inputLine = in.readLine();
					//currdata.setTradevolume(Long.valueOf(inputLine));
					resultdata.put(currdata.getStocksymbol(), currdata);
				System.out.println(currdata.getStocksymbol()+":LTP"+currdata.getLastprice()+":open"+currdata.openprice
							+"high:"+currdata.highprice+"low:"+currdata.lowprice);
					
				}
				
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			
	}
		return resultdata;

		
}
	
	
}
