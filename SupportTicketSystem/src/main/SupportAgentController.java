package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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
    private TextField txt_desription;
    @FXML
    private ComboBox category;
    @FXML
    private ComboBox priority;

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    ObservableList<ModelTable> oblist = FXCollections.observableArrayList();
    int index = -1;

    public void updateTable(){

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
            if(userA.getTypeOfUser().matches("Support Agent"))
            {
                rs = conn.createStatement().executeQuery("select * from Ticket");
            }else {

                PreparedStatement search = conn.prepareStatement("Select * from Ticket Where requesterID = ?");
                search.setString(1, userA.getEmail());
                rs = search.executeQuery();
            }

            while(rs.next()){
                oblist.add(new ModelTable(rs.getString("TicketID"), rs.getString("requesterID"), rs.getString("DueDate"), rs.getString("Description"), rs.getString("title"), rs.getString("category"), rs.getString("priority")));
            }


        }catch (SQLException e){
            e.printStackTrace();
        }

        updateTable();

    }

    public void edit(){
        try{
            ConnectToDB connection = new ConnectToDB();
            Connection conn = connection.getConnect();

            String value1 = txt_title.getText();
            String value2 = txt_date.getText();
            String value3 = txt_desription.getText();
            String value4 = category.getValue().toString();
            String value5 = priority.getValue().toString();

            String sql = "update users set title= '"+value1+"',DueDate='"+value2+"',description= '"+value3+"',category='"+value4+"',priority= '"+value5+"' where title='"+value1+"' ";
            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null,"Updated Successfully");
            updateTable();
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @FXML
    void getSelected(MouseEvent event){
        index = table.getSelectionModel().getSelectedIndex();
        if(index <= -1){
            return;
        }
        txt_title.setText(col_title.getCellData(index).toString());
        txt_date.setText(col_date.getCellData(index).toString());
        txt_desription.setText(col_desc.getCellData(index).toString());
        category.getValue().toString();
        priority.getValue().toString();

    }



    public void add_ticketSA(ActionEvent event){
        ConnectToDB connection = new ConnectToDB();
        Connection conn = connection.getConnect();

        User userA = User.getCurrentUser();
        try {
            Ticket T1 = new Ticket(userA.getEmail(), txt_title.getText(), category.getVisibleRowCount(), txt_description.getText(), priority.getVisibleRowCount(), txt_date.getText());
            T1.addTicketToDB();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



    }
}
