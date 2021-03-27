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
        File file = new File(System.getProperty("user.dir") + "\\key\\fir-testproject-20cf0-firebase-adminsdk-raq32-9ffeb9ca8c.json");               
     

        FileInputStream serviceAccount =
        		new FileInputStream(file);

        FirebaseOptions options = new FirebaseOptions.Builder()
        		.setCredentials(GoogleCredentials.fromStream(serviceAccount))
        		.setDatabaseUrl("https://fir-testproject-20cf0-default-rtdb.firebaseio.com")
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