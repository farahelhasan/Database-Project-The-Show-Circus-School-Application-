package com.example.circusschool;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ResourceBundle;

public class EditCourseManagerScreen implements Initializable {

    @FXML
    private TextField CostText;

  //  @FXML
    //private TextField ForText;

    @FXML
    private TextField MaxNumTEXT;

    @FXML
    private TextField NamtText;

    @FXML
    private Button SaveButton;

    @FXML
    private TextField TimeText;
    @FXML
    private ComboBox<String> TypeBox;

    @FXML
    private ComboBox<String> ForBox;

    //@FXML
   // private TextField TypeText;
    ObservableList<String> TypeList = FXCollections.observableArrayList("Juggling","Gymnastics");
    ObservableList<String> ForList = FXCollections.observableArrayList("F","M","Both");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        NamtText.setText(Course.SName);
        TypeBox.setValue(Course.SType);
        TimeText.setText(Course.STime);
        MaxNumTEXT.setText(Integer.toString(Course.SMaxNumOfStudent));
      ForBox.setValue(Course.SCourseFor);
        CostText.setText(Integer.toString(Course.SCost));


        TypeBox.setItems(TypeList);//
        ForBox.setItems(ForList);

    }
    @FXML
    void SaveButtonClick(ActionEvent event) {
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            String URL = "jdbc:oracle:thin:@localhost:1521:XE";
            Connection con = DriverManager.getConnection(URL, "C##fawar", "654321");
            con.setAutoCommit(false);
            Statement stat = con.createStatement();
            String sqlstetment = "update COURSE set COURSENAME = '"+NamtText.getText().trim()+"' , COURSETYPE = '"+TypeBox.getValue()+"', MAXNUMOFSTUDENT = "+MaxNumTEXT.getText().trim()+" , TIMEINTERVAL = '"+TimeText.getText().trim()+"', COURSECOST = "+CostText.getText().trim() +" , COURSEFOR = '"+ForBox.getValue()+"'where COURSENUMBER = "+Course.SNumber+" ";
            stat.executeUpdate(sqlstetment);
            con.commit();
            con.close();
          //  JOptionPane.showMessageDialog(null, "Edit Done");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Edit");
            alert.setHeaderText(null);
            alert.setContentText("Edit DONE ! ");
            Stage stage2 = (Stage) alert.getDialogPane().getScene().getWindow();
            alert.showAndWait();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }

}
