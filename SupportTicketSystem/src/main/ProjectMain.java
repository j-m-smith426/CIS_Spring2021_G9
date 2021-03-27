package main;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.firebase.database.*;

public class ProjectMain {

	public static void main(String[] args) {
		FirebaseDatabase database;
		try {
			database = new FireBaseService().getDb();
			DatabaseReference ref = database.getReference();
			DatabaseReference TicketRef = ref.child("NewTickets");
			
			TicketRef.child("0002").setValueAsync(new Ticket("User1", 1, "Test Ticket", 0, "Testing if the ticket updates", 1, new Date())).get();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
