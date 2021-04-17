package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CPanelController implements Initializable {

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
    private TextField txt_title;
    @FXML
    private TextField txt_date;
    @FXML
    private TextField txt_desription;
    @FXML
    private ComboBox category;
    @FXML
    private ComboBox priority;
    @FXML
    private Button submitTicket;

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public void HomepaneShow(){
        Homepane.setVisible(true);
        Ticketpane.setVisible(false);
    }

    public void TicketpaneShow(){
        Homepane.setVisible(false);
        Ticketpane.setVisible(true);
    }

    ObservableList<ModelTable> oblist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb){

        category.getItems().addAll("Student","Professor","G9 Member");
        priority.getItems().addAll("Low","Mild","Emergency");

        try {
            ConnectToDB connection = new ConnectToDB();
            Connection conn = connection.getConnect();

            ResultSet rs = conn.createStatement().executeQuery("select * from data");

            while(rs.next()){
                oblist.add(new ModelTable(rs.getString("id"), rs.getString("requesterID"), rs.getString("date"), rs.getString("description")));
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_Reqid.setCellValueFactory(new PropertyValueFactory<>("requesterID"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_desc.setCellValueFactory(new PropertyValueFactory<>("description"));

        table.setItems(oblist);

    }

    public void add_ticket(ActionEvent event){
        ConnectToDB connection = new ConnectToDB();
        Connection conn = connection.getConnect();

     /*   try{
        //add ticket
        }catch (){

        //SQLException
        }
	*/


    }
}
