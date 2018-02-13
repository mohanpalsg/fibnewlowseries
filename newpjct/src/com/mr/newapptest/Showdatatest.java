package com.mr.newapptest;

import java.util.Calendar;

public class Showdatatest {

	public static void main(String args[])
	{
		Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        
        System.out.println(year*100+month-1);
	}
}
