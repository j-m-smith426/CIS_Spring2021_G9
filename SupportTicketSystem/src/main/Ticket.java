package main;
import java.util.Date;
import java.util.ArrayList;
import java.util.Random;

public class Ticket {
	private String requesterID; 
	private int ticketID;
	private String title;
	private int category;
	private String description;
	private int priority;
	private Date dueDate;
	ArrayList<Integer> history = new ArrayList<Integer>();
	private Random rand = new Random();
	public Ticket(String requesterID, int ticketID, String title, int category, String description, int priority, Date dueDate) {
		super();
		this.requesterID = requesterID;
		this.title = title;
		this.category = category;
		this.description = description;
		this.priority = priority;
		this.dueDate = dueDate;
		this.ticketID = rand.nextInt(10000) + 1;
		while(this.ticketID == usedTicketID(this.ticketID)){
			this.ticketID = rand.NextInt(10000) + 1;
		}
		
		addTicketToDB(this.ticketID, this.title, this.requesterID, this.category, this.description, this.priority, this.dueDate);

	}


 	public void addTicketToDB(int ticketID, String title, String requesterID, int category, String description, int priority, Date dueDate){
	String selectSQL = "INSERT INTO Tickets Values(?,?,?,?,?,?,?);";
	PreparedStatement insert;
	try{
		insert = Conn.prepareStatement(selectSQL);
		insert.setString(1, ticketID);
		insert.setString(2, title);
		insert.setString(3, requesterID);
		insert.setString(4, category);
		insert.setString(5, description);
		insert.setString(6, priority);
		insert.setString(7, dueDate);
		insert.executeQuery();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}


	public int usedTicketID(int ticketID){
		String selectSQL = "SELECT * FROM Tickets WHERE TicketID = ?;";
		PreparedStatement = search = Conn.preparedStatement(selectSQL);
		search.setString(1,ticketID);
		search.executeQuery();
		LarrayList<Integer> intList = new ArrayList<Integer>();
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
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
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
