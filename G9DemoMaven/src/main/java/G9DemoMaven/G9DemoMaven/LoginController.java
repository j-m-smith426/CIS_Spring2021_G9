package G9DemoMaven.G9DemoMaven;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;



public class LoginController implements Initializable {

    //defines the JavaFX buttons, text fields, anchor panes, etc
    @FXML
    private AnchorPane pane_login;
    @FXML
    private ImageView img = new ImageView();
    @FXML
    private TextField txt_username;
    @FXML
    private PasswordField txt_password;
    @FXML
    private ComboBox type;
    @FXML
    private Button btn_login;
    @FXML
    private AnchorPane pane_signup;
    @FXML
    private TextField txt_username_up;
    @FXML
    private PasswordField txt_password_up;
    @FXML
    private TextField email_up;
    @FXML
    private ComboBox type_up;

    ConnectToDB connection = new ConnectToDB();
    Connection conn = connection.getConnect();
    ResultSet rs = null;
    PreparedStatement pst = null;


    //displays the login screen
    public void LoginpaneShow(){
        pane_login.setVisible(true);
        pane_signup.setVisible(false);
    }

	
    //displays the sign up screen
    public void SignuppaneShow(){
        pane_login.setVisible(false);
        pane_signup.setVisible(true);
    }

	
    //when the login button is pressed, it authenticates the user and directs them to the correct screen
    @FXML
    private void Login (ActionEvent event) throws Exception{
        
        String sql = "Select * from Users where Username = ? and Password = ? and AccountType = ?";
        User userA = new User(txt_username.getText(),txt_username.getText(),txt_password.getText(),type.getValue().toString());
        try{
            pst = conn.prepareStatement(sql);
            pst.setString(1, userA.hashUserAccount(userA.getUserName()));
            pst.setString(2, userA.hashUserAccount(userA.getPassword()));
            pst.setString(3, userA.getTypeOfUser());
            rs = pst.executeQuery();
            
           

            if(rs.next()){
            	String email = rs.getString("Email");
                userA.setEmail(email);
                JOptionPane.showMessageDialog(null, "Username and password is correct");
                User.setCurrentUser(userA);
                btn_login.getScene().getWindow().hide();
                Parent root;
                if(userA.getTypeOfUser().matches("Support Agent"))
                {
                	 root = FXMLLoader.load(getClass().getResource("SupportAgent.fxml"));
                }else
                	root = FXMLLoader.load(getClass().getResource("CPanel.fxml"));
                Stage mainStage = new Stage();
                Scene scene = new Scene(root);
                mainStage.setScene(scene);
                mainStage.show();

            } else {
                JOptionPane.showMessageDialog(null, "Invalid Username or password");
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

	
    // adds a new user to the database with the given credentials
    public void add_users(ActionEvent event){

	try {
		User user = new User(txt_username_up.getText(), email_up.getText(), txt_password_up.getText(), type_up.getValue().toString());
		user.addUserToDB();
		JOptionPane.showMessageDialog(null, "Account created, Please Login.");
		LoginpaneShow();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

    }


    //sets the comboboxes with the given values
    @Override
    public void initialize(URL url, ResourceBundle rb){
        type_up.getItems().addAll("User","Support Agent");
        type.getItems().addAll("User","Support Agent");
       

    }
}
