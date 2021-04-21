package G9DemoMaven.G9DemoMaven;

import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class SupportTicket extends Application {

	    @Override
	    public void start(Stage primaryStage) throws Exception{
	    	
	        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
	        primaryStage.setTitle("G9 Support Ticket System");
	        primaryStage.setScene(new Scene(root, 500, 400));
	        primaryStage.show();
	    }
	    public static void main(String[] args) {
		/* TODO Auto-generated method stub
		try {
			User a = new User("TestUser", "testemail@email.com", "Password");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	    	launch(args);
	}

}
