package main;

import java.sql.*;

public class ConnectToDB {
	Connection Conn;
	
	public ConnectToDB(Connection conn) {
	try {
		DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
	String dbURL = "jdbc:sqlserver://192.168.0.96:1433;databaseName=SupportTicketDB;";
	//local
	//	String dbURL = "jdbc:sqlserver://localhost:1433;databaseName=SupportTicketDB;integratedSecurity=true;";
	String user = "pass";
	String pass = "pass";
	conn = null;
	try {
		conn = DriverManager.getConnection(dbURL, user, pass);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	if (conn != null) {
	    System.out.println("Connected");
	    Conn = conn;
	}
	}
	public Connection getConnect() {
		return Conn;
	}
}
