package com.example.circusschool;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class EquipmentManagerScreen implements Initializable {

    @FXML
    private Button AddButton;

    @FXML
    private Button BackButton;

    @FXML
    private Button CancelButton;

    @FXML
    private Button DeleteButton;

    @FXML
    private Button DisplayButton;
    @FXML
    private TableView<Equipment> EquipmentTable;
    @FXML
    private TableColumn<Equipment, Integer> ECountColumn;
    @FXML
    private TableColumn<Equipment, String> ENameColumn;
    @FXML
    private TableColumn<Equipment, String> ETypeColumn;
    @FXML
    private RadioButton ENameRadioButton;



    @FXML
    private RadioButton ETypeRadioButton;

    @FXML
    private Button EditButton;



    @FXML
    private Button RefrechButton;

    @FXML
    private TextField SearchStudentText;

    @FXML
    private Label UsernameLable;

    ObservableList<Equipment> List = FXCollections.observableArrayList();


    @Override
    public void initialize (URL url , ResourceBundle rb){
        Color paint = new Color(0.9373, 0.5255, 0.3059, 1.0);
        UsernameLable.setTextFill(paint);
        UsernameLable.setText(User.email);
        ToggleGroup tgNameType = new ToggleGroup();
        ENameRadioButton.setToggleGroup(tgNameType);
        ETypeRadioButton.setToggleGroup(tgNameType);

        ENameColumn.setCellValueFactory(new PropertyValueFactory<Equipment,String>("EName"));
        ECountColumn.setCellValueFactory(new PropertyValueFactory<Equipment,Integer>("ECount"));
        ETypeColumn.setCellValueFactory(new PropertyValueFactory<Equipment,String>("EType"));

        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            String URL = "jdbc:oracle:thin:@localhost:1521:XE";
            Connection con = DriverManager.getConnection(URL, "C##fawar", "654321");
            con.setAutoCommit(false);
            Statement stat = con.createStatement();
            String sqlstetment = "select * from EQUIPMENT";
            ResultSet rs = stat.executeQuery(sqlstetment);
            int i =0;
            List.clear();
            while (rs.next()){
                Equipment obj = new Equipment(rs.getString(1),rs.getInt(2),rs.getString(3));
                List.add(i,obj);
                i++;
            }
            EquipmentTable.setItems(List);
            con.commit();
            con.close();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }
    @FXML
    void BackButtonClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("welcomeManagerScreen.fxml"));
        Parent root = fxmlLoader.load();
        // Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("The show circus school");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void CancelButtonClick(ActionEvent event) {
        UsernameLable.setText(User.email);
        SearchStudentText.setText(" ");
        ToggleGroup tgNameType = new ToggleGroup();
        ENameRadioButton.setToggleGroup(tgNameType);
        ETypeRadioButton.setToggleGroup(tgNameType);

        ENameColumn.setCellValueFactory(new PropertyValueFactory<Equipment,String>("EName"));
        ECountColumn.setCellValueFactory(new PropertyValueFactory<Equipment,Integer>("ECount"));
        ETypeColumn.setCellValueFactory(new PropertyValueFactory<Equipment,String>("EType"));

        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            String URL = "jdbc:oracle:thin:@localhost:1521:XE";
            Connection con = DriverManager.getConnection(URL, "C##fawar", "654321");
            con.setAutoCommit(false);
            Statement stat = con.createStatement();
            String sqlstetment = "select * from EQUIPMENT";
            ResultSet rs = stat.executeQuery(sqlstetment);
            int i =0;
            List.clear();
            while (rs.next()){
                Equipment obj = new Equipment(rs.getString(1),rs.getInt(2),rs.getString(3));
                List.add(i,obj);
                i++;
            }
            EquipmentTable.setItems(List);
            con.commit();
            con.close();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }

    @FXML
    void DeleteButtonClick(ActionEvent event) {
        try{  Equipment obj = EquipmentTable.getSelectionModel().getSelectedItem();
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            String URL = "jdbc:oracle:thin:@localhost:1521:XE";
            Connection con = DriverManager.getConnection(URL, "C##fawar", "654321");
            con.setAutoCommit(false);
            Statement stat = con.createStatement();
            String sqlstetment = "delete from EQUIPMENT where EQUIPMENTNAME = '"+obj.EName+"'";
            stat.executeUpdate(sqlstetment);

           // JOptionPane.showMessageDialog(null, "delete done");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Equipment Page");
            alert.setHeaderText(null);
            alert.setContentText("DELETE DONE ! ");
            Stage stage2 = (Stage) alert.getDialogPane().getScene().getWindow();
            alert.showAndWait();
            con.commit();
            con.close();
        }catch(Exception ex){
           // JOptionPane.showMessageDialog(null, ex.toString());
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Equipment Page");
            alert.setHeaderText(null);
            alert.setContentText("Select Equipment to delete it  ! ");
            Stage stage2 = (Stage) alert.getDialogPane().getScene().getWindow();
            alert.showAndWait();
        }
    }

    @FXML
    void DisplayButton(ActionEvent event) {
        try {
            String sqlstetment = "";
            int i = 0;
            List.clear();

            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            String URL = "jdbc:oracle:thin:@localhost:1521:XE";
            Connection con = DriverManager.getConnection(URL, "C##fawar", "654321");
            con.setAutoCommit(false);
            Statement stat = con.createStatement();
            sqlstetment = "select * from EQUIPMENT";
            ResultSet rs = stat.executeQuery(sqlstetment);
            if(ENameRadioButton.isSelected()) {
                while (rs.next()) {
                    String  SearchStudent= SearchStudentText.getText().trim().toLowerCase();
                    String ENameFromDataBasse =rs.getString(1).toLowerCase();
                    if((SearchStudent.contains(ENameFromDataBasse)) || (ENameFromDataBasse.contains(SearchStudent))){
                        Equipment obj = new Equipment(rs.getString(1),rs.getInt(2),rs.getString(3));
                        List.add(i, obj);
                        i++;
                    }
                }
                EquipmentTable.setItems(List);
            }else if(ETypeRadioButton.isSelected()) {
                while (rs.next()) {
                    String  SearchStudent= SearchStudentText.getText().trim().toLowerCase();
                    String EtypeFromDataBasse =rs.getString(3).toLowerCase();
                    if((SearchStudent.contains(EtypeFromDataBasse) ) || (EtypeFromDataBasse.contains(SearchStudent))){
                        Equipment obj = new Equipment(rs.getString(1),rs.getInt(2),rs.getString(3));
                        List.add(i, obj);
                        i++;
                    }
                }
                EquipmentTable.setItems(List);
            }
            con.close();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }

    @FXML
    void EditButtonClick(ActionEvent event) throws IOException {
     try{   Equipment obj = EquipmentTable.getSelectionModel().getSelectedItem();
        Equipment.SEName = obj.EName;
        Equipment.SEType = obj.EType;
        Equipment.SECount = obj.ECount;

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("editEquipmentManagerScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("The show circus school");
        stage.setScene(scene);
        stage.show();} catch (Exception ex){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Equipment Page");
        alert.setHeaderText(null);
        alert.setContentText("Select Equipment ! ");
        Stage stage2 = (Stage) alert.getDialogPane().getScene().getWindow();
        alert.showAndWait();
    }

    }
    @FXML
    void AddButtonClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("addEquipmentManagerScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("The show circus school");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void RefrechButtonClick(ActionEvent event) {
        CancelButtonClick(event);
    }

    @FXML
    void searchStudent(KeyEvent event) {

    }

}
