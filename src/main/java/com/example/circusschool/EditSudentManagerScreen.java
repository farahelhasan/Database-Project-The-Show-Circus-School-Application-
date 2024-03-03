package com.example.circusschool;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class EditSudentManagerScreen implements Initializable {

    @FXML
    private TextField addressText;

    @FXML
    private RadioButton boyRadiobutton;

    @FXML
    private TextField firstNameText;

    @FXML
    private RadioButton girlRadiobutton;

    @FXML
    private TextField lastNameText;

    @FXML
    private TextField phoneText;




    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ToggleGroup tgBoyGirl = new ToggleGroup();
        girlRadiobutton.setToggleGroup(tgBoyGirl);
        boyRadiobutton.setToggleGroup(tgBoyGirl);
        firstNameText.setText(Student.SFirstName);
        lastNameText.setText(Student.SLastName);
        phoneText.setText(Integer.toString(Student.SPhone));
        addressText.setText(Student.SAddress);
        if (Student.SGender.equalsIgnoreCase("F")) {
            girlRadiobutton.setSelected(true);
        } else {
            boyRadiobutton.setSelected(true);
        }
    }

    @FXML
    void SaveButtonClick(ActionEvent event) {
        try {
            char G;
            if(girlRadiobutton.isSelected()){
                G = 'F';
            }else{
                G = 'M';
            }

            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            String URL = "jdbc:oracle:thin:@localhost:1521:XE";
            Connection con = DriverManager.getConnection(URL, "C##fawar", "654321");
            con.setAutoCommit(false);
            Statement stat = con.createStatement();
            String sqlstetment = "update student set   firstname= '"+firstNameText.getText().trim()+"' , lastname = '"+lastNameText.getText().trim()+"' ,gender ='"+G+"' , address = '"+addressText.getText().trim()+"', phone = "+phoneText.getText().trim()+" where studentssn = "+Student.Sssn+" ";
            stat.executeUpdate(sqlstetment);
            con.commit();
            con.close();
           // JOptionPane.showMessageDialog(null, "Edit Done");
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