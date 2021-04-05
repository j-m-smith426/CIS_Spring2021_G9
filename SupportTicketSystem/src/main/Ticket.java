package main;
import java.util.Date;
import java.util.ArrayList;

public class Ticket {
	private String requesterID; 
	private int ticketID;
	private String title;
	private int category;
	private String description;
	private int priority;
	private Date dueDate;
	ArrayList<Integer> history = new ArrayList<Integer>();

	public Ticket(String requesterID, int ticketID, String title, int category, String description, int priority, Date dueDate) {
		super();
		this.requesterID = requesterID;
		this.ticketID = ticketID;
		this.title = title;
		this.category = category;
		this.description = description;
		this.priority = priority;
		this.dueDate = dueDate;
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
