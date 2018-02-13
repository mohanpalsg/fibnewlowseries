package com.mr.newapptest;

import java.net.MalformedURLException;
import java.sql.Connection;
import java.text.ParseException;

import com.mr.datainsert.Dataconn;
import com.mr.newapp.Fibonaccidataupdate;
import com.mr.newapp.Nsebasedatadnldcarmilla;
import com.mr.newapp.Nsebasedownloader;

public class Datainserttest {

	public static void main(String args[])
	{
		//Thread thread = new Thread(new Nsebasedownloader("300"));
		//thread.start();
		
		Fibonaccidataupdate crm = new Fibonaccidataupdate();
		crm.getfibonaccidata("INFY");
		
	}
}
