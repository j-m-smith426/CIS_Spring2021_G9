package main;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import com.google.firebase.database.*;

public class addToDatabase {

	public addToDatabase(Object o) throws IOException, InterruptedException, ExecutionException {
		FirebaseDatabase database;
		
			database = new FireBaseService().getDb();
			DatabaseReference ref = database.getReference();
			
			DatabaseReference ClassRef = ref.child(o.getClass().toString());
			
			ClassRef.push().setValueAsync(o).get();
			
			
		
	}
	
}
