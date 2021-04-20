package main;

import java.util.Date;
import java.sql.*;
public class History{
	private java.sql.Date dueDate;
	ConnectToDB connection = new ConnectToDB();
	Connection Conn = connection.getConnect();

  public void createHistory(int ticketID, String requesterID){
    String selectSQL = "Insert into History Values(?,?,?,?);";
    PreparedStatement insert;
    try{
      insert = Conn.prepareStatement(selectSQL);
      dueDate = new java.sql.Date(new Date().getTime());
      insert.setDate(1,dueDate);
      insert.setInt(2, ticketID);
      insert.setString(3, requesterID);
      insert.setString(4, "Ticket Created");
      insert.executeUpdate();
    }catch(SQLException e){
      e.printStackTrace();
    }
  }
  public void retrieveHistory(int ticketID){
    String selectSQL = "Select * from Ticket WHERE ticketID = ?;";
    PreparedStatement insert;
    try{
      insert = Conn.prepareStatement(selectSQL);
      insert.setInt(1, ticketID);
      insert.executeQuery();
    }catch(SQLException e){
      e.printStackTrace();
    }
  }
}