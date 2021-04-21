package G9DemoMaven.G9DemoMaven;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

public class CPanelController implements Initializable {

    
    //define all the FX buttons, text boxes, etc
    @FXML
    private AnchorPane Homepane;
    @FXML
    private AnchorPane Ticketpane;
    @FXML
    private TableView<ModelTable> table;
    @FXML
    private TableColumn<ModelTable, String> col_id;
    @FXML
    private TableColumn<ModelTable, String> col_Reqid;
    @FXML
    private TableColumn<ModelTable, String> col_date;
    @FXML
    private TableColumn<ModelTable, String> col_desc;
    @FXML
    private TableColumn<ModelTable, String> col_title;
    @FXML
    private TableColumn<ModelTable, String> col_category;
    @FXML
    private TableColumn<ModelTable, String> col_priority;
    @FXML
    private TextField txt_title;
    @FXML
    private TextField txt_date;
    @FXML
    private TextField txt_description;
    @FXML
    private ComboBox category;
    @FXML
    private ComboBox priority;
    @FXML
    private Button btn_logout;

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    
    //shows the home view
    public void HomepaneShow(){
        Homepane.setVisible(true);
        Ticketpane.setVisible(false);
	updateTable();
    }

    //shows the ticket view
    public void TicketpaneShow(){
        Homepane.setVisible(false);
        Ticketpane.setVisible(true);
    }

    //defines the array list used to show the tickets in the table
    ObservableList<ModelTable> oblist = FXCollections.observableArrayList();
	
	
    //updates the table on the home page with the new ticket created
    public void updateTable(){
	    oblist.clear();
	    try {
            ConnectToDB connection = new ConnectToDB();
            Connection conn = connection.getConnect();
            User userA = User.getCurrentUser();
            if(userA.getTypeOfUser().matches("Support Agent"))
            {
                rs = conn.createStatement().executeQuery("select * from Ticket");
            }else {

                PreparedStatement search = conn.prepareStatement("Select * from Ticket Where requesterID = ?");
                search.setString(1, userA.getEmail());
                rs = search.executeQuery();
            }

            while(rs.next()){
                oblist.add(new ModelTable(rs.getString("TicketID"), rs.getString("requesterID"), rs.getString("DueDate"), rs.getString("Description"), rs.getString("Title"), rs.getString("Catagory"), rs.getString("Priority")));
            }


        }catch (SQLException e){
            e.printStackTrace();
        }

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_Reqid.setCellValueFactory(new PropertyValueFactory<>("requesterID"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_desc.setCellValueFactory(new PropertyValueFactory<>("description"));
        col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_category.setCellValueFactory(new PropertyValueFactory<>("category"));
        col_priority.setCellValueFactory(new PropertyValueFactory<>("priority"));

        table.setItems(oblist);
    }

	
    //sets the combo boxes with its values, adds data to the table if available
    @Override
    public void initialize(URL url, ResourceBundle rb){

        category.getItems().addAll("Student","Professor","G9 Member");
        priority.getItems().addAll("Low","Mild","Emergency");

        try {
            ConnectToDB connection = new ConnectToDB();
            Connection conn = connection.getConnect();
            User userA = User.getCurrentUser();
            if(userA.getTypeOfUser().matches("Support Agent"))
            {
            	rs = conn.createStatement().executeQuery("select * from Ticket");
            }else {
            	
            	PreparedStatement search = conn.prepareStatement("Select * from Ticket Where requesterID = ?");
            	search.setString(1, userA.getEmail());
            	rs = search.executeQuery();
            }

            while(rs.next()){
            	Integer id = rs.getInt(1);
                oblist.add(new ModelTable(id.toString(), rs.getString("requesterID"), rs.getDate(7).toString(), rs.getString("Description"), rs.getString("Title"), rs.getString("Catagory"), rs.getString("Priority")));
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
        
	col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_Reqid.setCellValueFactory(new PropertyValueFactory<>("requesterID"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_desc.setCellValueFactory(new PropertyValueFactory<>("description"));
        col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_category.setCellValueFactory(new PropertyValueFactory<>("category"));
        col_priority.setCellValueFactory(new PropertyValueFactory<>("priority"));

        table.setItems(oblist);

    }

	
    //creates a ticket when the submit ticket button is pressed, also creates history for the ticket
    public void add_ticket(ActionEvent event){
        ConnectToDB connection = new ConnectToDB();
        Connection conn = connection.getConnect();
        User userA = User.getCurrentUser();
        try {
        	
			Ticket T1 = new Ticket(userA.getEmail(), txt_title.getText(), category.getValue().toString(), txt_description.getText(), priority.getValue().toString(), txt_date.getText());
			T1.addTicketToDB();
			updateTable();
			JOptionPane.showMessageDialog(null, "Ticket Created");
			HomepaneShow();
			History T1History = new History(T1.getTicketID(), T1.getRequesterID());
			T1History.createHistory(T1.getTicketID(), T1.getRequesterID(), "Ticket Created");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e);
		}
    
    }
	
	
    //logs the current user out and displays the login page
    @FXML
    private void logoutPressed(ActionEvent event) throws Exception{
    	 Parent root;
    	try{
                btn_logout.getScene().getWindow().hide();
               	
                root = FXMLLoader.load(getClass().getResource("Login.fxml"));
                Stage mainStage2 = new Stage();
                Scene scene = new Scene(root);
                mainStage2.setScene(scene);
                mainStage2.show();
		
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
	
}
