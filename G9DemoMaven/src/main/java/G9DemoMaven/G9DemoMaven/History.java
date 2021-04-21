package G9DemoMaven.G9DemoMaven;

import java.util.Date;
import java.sql.*;
public class History{
	private Date today = new Date();
	private java.sql.Date dueDate;
	static Connection Conn = ConnectToDB.getConnect();
	int ticketID;
	String requesterID;

  public History(int ticketID, String requesterID){
	dueDate = new java.sql.Date(today.getTime());
	this.ticketID = ticketID;
	this.requesterID = requesterID;
	
	


  }

  public void createHistory(int ticketID, String requesterID, String Description){
    String selectSQL = "Insert into History Values(?,?,?,?);";
    PreparedStatement insert;
    try{
      insert = Conn.prepareStatement(selectSQL);
      dueDate = new java.sql.Date(today.getTime());
      insert.setDate(1, this.dueDate);
      insert.setInt(2, ticketID);
      insert.setString(3, requesterID);
      insert.setString(4, Description);
      insert.executeUpdate();
    }catch(SQLException e){
      e.printStackTrace();
    }
  }
  public static ResultSet retrieveHistory(int ticketID){
	    String selectSQL = "Select * from History WHERE ticketID = ?;";
	    PreparedStatement insert;
	    ResultSet rs = null;
	    try{
	      insert = Conn.prepareStatement(selectSQL);
	      insert.setInt(1, ticketID);
	      rs = insert.executeQuery();
	    }catch(SQLException e){
	      e.printStackTrace();
	    }
	    return rs;
	  }
  }

 
