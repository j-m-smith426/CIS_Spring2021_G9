package main;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FireBaseService {


    FirebaseDatabase db;

    public FireBaseService() throws IOException {
        File file = new File("DatabaseKey.json");               
     

        FileInputStream serviceAccount =
        		new FileInputStream(file);

        FirebaseOptions options = new FirebaseOptions.Builder()
        		.setCredentials(GoogleCredentials.fromStream(serviceAccount))
        		.setDatabaseUrl("https://support-ticket-system-b912c-default-rtdb.firebaseio.com/")
        		.build();

		FirebaseApp.initializeApp(options);
		// As an admin, the app has access to read and write all data, regardless of Security Rules
		DatabaseReference ref = FirebaseDatabase.getInstance()
		    .getReference("restricted_access/secret_document");
		ref.addListenerForSingleValueEvent(new ValueEventListener() {
		  @Override
		  public void onDataChange(DataSnapshot dataSnapshot) {
		    Object document = dataSnapshot.getValue();
		    System.out.println(document);
		  }

		  @Override
		  public void onCancelled(DatabaseError error) {
		  }
		});


        db = FirebaseDatabase.getInstance();
    }

    public FirebaseDatabase getDb() {
        return db;
    }

}