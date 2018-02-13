package com.mr.datainsert;

import java.sql.*;
public class Dataconn {
    public String env = "test";
	private String username;
	private String url;
	private String password;
	private Connection connection;
	public Dataconn()
	{
		if ((this.env).equals("test"))
				{
			// local database
			this.url = "jdbc:mysql://localhost:3306/motradedb";
			this.username = "root"; //localsetting
			   //  this.username="SYSTEM"; //openshiftsetting
				 this.password = "jurong123";
				}
		else
		{
			// openshift database
		 this.url = "jdbc:mysql://motradedb:3306/motradedb";
	//	 this.username = "root"; //localsetting
	     this.username="SYSTEM"; //openshiftsetting
		 this.password = "jurong123";
	}
	}
	public Connection getconn()
	{
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.connection = DriverManager.getConnection(this.url,this.username,this.password);
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.connection;
		
	}
	public void closeconn()
	{
		try {
			this.connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
