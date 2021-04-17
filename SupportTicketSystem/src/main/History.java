import java.util.Date;
import java.sql.*;
public class History{
  private java.sql.Date dueDate;

  public void createHistory(int ticketID, String requesterID, String Description){
    String selectSQL = "Insert into History Values(?,?,?,?);";
    PreparedStatement insert;
    try{
      insert = Conn.prepareStatement(selectSQL);
      Date date = new java.sql.Date(df.parse(dueDate).gettime());
      insert.setDate(1,date);
      insert.setInt(2, ticketID);
      insert.setString(3, requesterID);
      insert.setString(4, Description);
      insert.executeQuery();
    }catch(SQLException e){
      e.printStackTrace();
    }
  }
  public void retrieveHistory(int ticketID){
    String selectSQL = "Select * from Tickets WHERE ticketID = ?;";
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