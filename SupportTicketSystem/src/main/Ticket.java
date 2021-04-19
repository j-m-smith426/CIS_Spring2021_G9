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
	private int category;
	private String description;
	private int priority;
	private DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
	private java.sql.Date dueDate;
	ConnectToDB connection = new ConnectToDB();
	Connection Conn = connection.getConnect();
	private static Ticket currentTicket;
	private ArrayList<Integer> history = new ArrayList<Integer>();
	private Random rand = new Random();
	public Ticket(String requesterID, String title, int category, String description, int priority, String dueDate) throws SQLException {
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
		insert.setInt(4, this.category);
		insert.setString(5, this.description);
		insert.setInt(6, this.priority);
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
	}

	public int getPriority() {
    		return priority;
		
	}
	public void setPriority(int priority) {
		this.priority = priority;
    		setHistory(priority);
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
	public int getCategory() {
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
