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

public class EditEquipmentManagerScreen implements Initializable {

    @FXML
    private TextField ECountText;

    @FXML
    private TextField ENameText;

   // @FXML
   // private TextField ETypeText;
    @FXML
    private ComboBox<String> ETypeBox;
    @FXML
    private Button SaveButton;
    ObservableList<String> TypeList = FXCollections.observableArrayList("Juggling","Gymnastics");
    @Override
    public void initialize (URL url , ResourceBundle rb) {
        //TrainerBox.setItems(TrainerList);
        ENameText.setText(Equipment.SEName);
            ENameText.setEditable(false);
            ETypeBox.setValue(Equipment.SEType);
            ECountText.setText(Integer.toString(Equipment.SECount));

            ETypeBox.setItems(TypeList);

    }

    @FXML
    void SaveButtonClick(ActionEvent event) {
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            String URL = "jdbc:oracle:thin:@localhost:1521:XE";
            Connection con = DriverManager.getConnection(URL, "C##fawar", "654321");
            con.setAutoCommit(false);
            Statement stat = con.createStatement();
            String sqlstetment = "update EQUIPMENT set EQUIPMENTCOUNT = "+ECountText.getText().trim()+" , EQUIPMENTTYPE = '"+ETypeBox.getValue()+"'where EQUIPMENTNAME = '"+Equipment.SEName+"'";
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
