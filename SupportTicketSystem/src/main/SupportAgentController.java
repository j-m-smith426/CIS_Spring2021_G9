package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SupportAgentController implements Initializable {

    @FXML
    private TableView<ModelTable> table;
    @FXML
    private TableView<ModelTableHistory> historyTable;
    @FXML
    private AnchorPane Homepane;
    @FXML
    private AnchorPane Viewpane;
    @FXML
    private AnchorPane updateHistory;
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
    private TableColumn<ModelTableHistory, String> col_desc2;
    @FXML
    private TableColumn<ModelTableHistory, String> col_Ticketid2;
    @FXML
    private TableColumn<ModelTableHistory, String> col_Agent;
    @FXML
    private TableColumn<ModelTableHistory, String> col_date2;
    @FXML
    private TextField txt_title;
    @FXML
    private TextField txt_search;
    @FXML
    private TextField txt_date;
    @FXML
    private TextField txt_desription;
    @FXML
    private TextField txt_updateHistory;
    @FXML
    private ComboBox category;
    @FXML
    private ComboBox priority;

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    ObservableList<ModelTable> oblist = FXCollections.observableArrayList();
    ObservableList<ModelTableHistory> oblist2 = FXCollections.observableArrayList();
    int index = -1;
    int index22 = -1;
	
    public void HomepaneShow(){
        Homepane.setVisible(true);
        Viewpane.setVisible(false);
	updateHistory.setVisible(false);
	updateTable();
    }
	
    public void ViewpaneShow(){
        Homepane.setVisible(false);
        Viewpane.setVisible(true);
	updateHistory.setVisible(false);
        getSelected();
    }
	
    public void UpdateHistoryShow(){
        Homepane.setVisible(false);
        Viewpane.setVisible(false);
	updateHistory.setVisible(true);
    }
	
	
    @FXML
    public void getSelected(){
        index = table.getSelectionModel().getSelectedIndex();
        if(index <= -1){
            return;
        }   
	    
	oblist2.clear();
	try {
        ConnectToDB connection = new ConnectToDB();
        Connection conn = connection.getConnect();
        User userA = User.getCurrentUser();
        
           
           rs = History.retrieveHistory(Integer.valueOf(oblist.get(index).getId()));
       

        while(rs.next()){
            oblist2.add(new ModelTableHistory(rs.getString("DateSubmitted"), rs.getString("TicketID"), rs.getString("AgentName"), rs.getString("Description")));
        }


        }catch (SQLException e){
            e.printStackTrace();
        }
	    
	col_Ticketid2.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_Agent.setCellValueFactory(new PropertyValueFactory<>("requesterID"));
        col_date2.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_desc2.setCellValueFactory(new PropertyValueFactory<>("description"));

        historyTable.setItems(oblist2);
	    
	//col_Ticketid2.setCellValueFactory(new PropertyValueFactory<>("id"));
        //col_Agent.setCellValueFactory(new PropertyValueFactory<>("requesterID"));
	//col_date2.setCellValueFactory(new PropertyValueFactory<>("date"));
	//col_desc2.setCellValueFactory(new PropertyValueFactory<>("history"));

    }
	
	
    public void updateTable(){
	    oblist.clear();
	    try {
            ConnectToDB connection = new ConnectToDB();
            Connection conn = connection.getConnect();
            User userA = User.getCurrentUser();
           
                rs = conn.createStatement().executeQuery("select * from Ticket");
               
            

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
	

    public void Delete(){
        ConnectToDB connection = new ConnectToDB();
        Connection conn = connection.getConnect();

        String sql = "delete from users where requesterID = ?";
        try{
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_title.getText());
            pst.execute();
            JOptionPane.showMessageDialog(null,"Deleted");
            updateTable();
        } catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        category.getItems().addAll("Student","Professor","G9 Member");
        priority.getItems().addAll("Low","Mild","Emergency");

        try {
            ConnectToDB connection = new ConnectToDB();
            Connection conn = connection.getConnect();
            User userA = User.getCurrentUser();
           
                rs = conn.createStatement().executeQuery("select * from Ticket");

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
	
    public void updateTable22(){
	oblist2.clear();
	try {
        ConnectToDB connection = new ConnectToDB();
        Connection conn = connection.getConnect();
        User userA = User.getCurrentUser();
        
           
           rs = History.retrieveHistory(Integer.valueOf(oblist.get(index).getId()));
       

        while(rs.next()){
            oblist2.add(new ModelTableHistory(rs.getString("DateSubmitted"), rs.getString("TicketID"), rs.getString("AgentName"), rs.getString("Description")));
        }


        }catch (SQLException e){
            e.printStackTrace();
        }
	    
	col_Ticketid2.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_Agent.setCellValueFactory(new PropertyValueFactory<>("requesterID"));
        col_date2.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_desc2.setCellValueFactory(new PropertyValueFactory<>("description"));

        historyTable.setItems(oblist2);
	    
    }

	
    public void addNewHistory(){
		
	ConnectToDB connection = new ConnectToDB();
        Connection conn = connection.getConnect();
        User userA = User.getCurrentUser();
	try {
      
        	History T1History = new History(T1.getTicketID(), T1.getRequesterID(), txt_updateHistory.getText());
		T1History.createHistory(T1.getTicketID(), T1.getRequesterID(), txt_updateHistory.getText());
		
		updateTable22();
	
	}catch (SQLException e){
            e.printStackTrace();
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }


    public void add_ticketSA(ActionEvent event){
        ConnectToDB connection = new ConnectToDB();
        Connection conn = connection.getConnect();
        User userA = User.getCurrentUser();
        try {
        	
			Ticket T1 = new Ticket(userA.getEmail(), txt_title.getText(), category.getValue().toString(), txt_description.getText(), priority.getValue().toString(), txt_date.getText());
			T1.addTicketToDB();
			updateTable();
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
}
