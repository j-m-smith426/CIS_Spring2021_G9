package main;
import java.util.Date;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class Ticket {
	private String requesterID; 
	private int ticketID;
	private String title;
	private String category;
	private String description;
	private String priority;
	private DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
	private java.sql.Date dueDate;
	
	static Connection Conn = ConnectToDB.getConnect();
	private static Ticket currentTicket;
	private ArrayList<Integer> history = new ArrayList<Integer>();
	private Random rand = new Random();
	public Ticket(String requesterID, String title, String category, String description, String priority, String dueDate) throws Exception {
		super();
		this.requesterID = requesterID;
		this.title = title;
		this.category = category;
		this.description = description;
		this.priority = priority;
		try {
			if(dueDate.matches("\\d{2}-\\d{2}-\\d{4}")){
			this.dueDate = new java.sql.Date(df.parse(dueDate).getTime());
			}else{
			    throw new Exception("Incorrect Due Date Format");
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		this.ticketID = rand.nextInt(10000) + 1;
		while(this.ticketID == usedTicketID(this.ticketID)){
			this.ticketID = rand.nextInt(10000) + 1;
		}
		
		

	}


public void addTicketToDB(){
 
	
	String selectSQL = "INSERT INTO Ticket Values(?,?,?,?,?,?,?);";
	PreparedStatement insert;
	
	try{
		insert = Conn.prepareStatement(selectSQL);
		insert.setInt(1, this.ticketID);
		insert.setString(2, this.title);
		insert.setString(3, this.requesterID);
		insert.setString(4, this.category);
		insert.setString(5, this.description);
		insert.setString(6, this.priority);
		insert.setDate(7, this.dueDate);
		insert.executeUpdate();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
 
}



	public int usedTicketID(int ticketID) throws SQLException{
		String selectSQL = "SELECT * FROM Ticket WHERE TicketID = ?;";
		PreparedStatement search = Conn.prepareStatement(selectSQL);
		search.setInt(1,this.ticketID);
		ResultSet rs = search.executeQuery();
		ArrayList<Integer> intList = new ArrayList<Integer>();
		while(rs.next()){
			int name = rs.getInt("TicketID");
			intList.add(name);
	}
		
		if(intList.isEmpty()){
			return 0;
		}else{
			return ticketID;
	}

		}


	public static ResultSet searchForTicket(int ticketID) throws SQLException{
		String selectSQL = "Select * FROM Ticket WHERE TicketID = ?;";
		PreparedStatement search = Conn.prepareStatement(selectSQL);
		search.setInt(1,ticketID);
		ResultSet rs = search.executeQuery();
		return rs;


	}


	 public static ResultSet dbSort(String category) throws SQLException{
	String selectSQL = "Select * FROM Ticket Where Catagory = ?;";
	PreparedStatement search = Conn.prepareStatement(selectSQL);
	search.setString(1,category);
	ResultSet rs = search.executeQuery();
	return rs;


}
	

	public String getPriority() {
    		return priority;
		
	}
	public void setPriority(String priority) {
		this.priority = priority;
    		//setHistory(priority);
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) throws ParseException {
		this.dueDate = new java.sql.Date(df.parse(dueDate).getTime());
	}
	public String getRequesterID() {
		return requesterID;
	}
	public int getTicketID() {
		return ticketID;
	}
	public String getTitle() {
		return title;
	}
	public String getCategory() {
		return category;
	}
	public String getDescription() {
		return description;
	}
  	public ArrayList getHistory(){
    		return history;
  }
  	public void setHistory(int priority){
    		history.add(priority);
  }

}
