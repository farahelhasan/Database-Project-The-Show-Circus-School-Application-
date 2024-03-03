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
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AddCourseManagerScreen implements Initializable {

    //@FXML
    //private TextField TrainerNameText;
    @FXML
    private TextField CostText;

  //  @FXML
   // private TextField ENameText;

   // @FXML
   // private TextField ForText;

    @FXML
    private TextField MaxNumTEXT;

    @FXML
    private TextField NameText;

    @FXML
    private TextField NumberText;

    @FXML
    private Button SaveButton;

    @FXML
    private TextField TimeText;

   // @FXML
    //private TextField TypeText;

    @FXML
    private ComboBox<String> TrainerBox;
    @FXML
    private ComboBox<String> EquipmentBox;
    @FXML
    private ComboBox<String> ForBox;
    @FXML
    private ComboBox<String> TypeBox;
    ObservableList<String> TrainerList = FXCollections.observableArrayList();
    ObservableList<String> EquipmentList = FXCollections.observableArrayList();
    ObservableList<String> TypeList = FXCollections.observableArrayList("Juggling","Gymnastics");
    ObservableList<String> ForList = FXCollections.observableArrayList("F","M","Both");

    @Override
    public void initialize (URL url , ResourceBundle rb) {
        //TrainerBox.setItems(TrainerList);
       try{ DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        String URL = "jdbc:oracle:thin:@localhost:1521:XE";
        Connection con = DriverManager.getConnection(URL, "C##fawar", "654321");
        con.setAutoCommit(false);
        Statement stat = con.createStatement();
        String sqlstetment = " select FIRSTNAME , LASTNAME from employee , trainer where employee.employeessn = trainer.trainerssn  ";
        ResultSet rs = stat.executeQuery(sqlstetment);
        int i =0;
           TrainerList.clear();
        while (rs.next()){
           String Tname = rs.getString(1)+" "+ rs.getString(2);
            TrainerList.add(i,Tname);
            i++;
        }
           TrainerBox.setItems(TrainerList);
        sqlstetment = "select EQUIPMENTNAME from EQUIPMENT   ";
        ResultSet rs2 = stat.executeQuery(sqlstetment);
        int j =0;
           EquipmentList.clear();
           while (rs2.next()){
               String Ename = rs2.getString(1);
               EquipmentList.add(j,Ename);
               j++;
           }
           EquipmentBox.setItems(EquipmentList);

           TypeBox.setItems(TypeList);//

           ForBox.setItems(ForList);

        con.commit();
        con.close();
    }catch(Exception ex){
        JOptionPane.showMessageDialog(null, ex.toString());
    }
    }
    @FXML
    void SaveButtonClick(ActionEvent event) {
        try { int zero = 0;
              int Mssn = 123000000;
            int Tssn =0;
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            String URL = "jdbc:oracle:thin:@localhost:1521:XE";
            Connection con = DriverManager.getConnection(URL, "C##fawar", "654321");
            con.setAutoCommit(false);
            Statement stat = con.createStatement();
            String sqlstetment = "insert into  course values ( "+NumberText.getText().trim()+" ,'"+NameText.getText().trim()+"', '"+TimeText.getText().trim()+"' , "+CostText.getText().trim()+" , "+zero+" , "+MaxNumTEXT.getText().trim()+" , '"+ForBox.getValue()+"' , "+Mssn+"  , '"+TypeBox.getValue()+"') ";
            stat.executeUpdate(sqlstetment);



            sqlstetment = "insert into  need values ('"+EquipmentBox.getValue()+"', "+NumberText.getText().trim()+") ";
            stat.executeUpdate(sqlstetment);
            String Split []= TrainerBox.getValue().split(" ");
            sqlstetment = "select EMPLOYEESSN  from employee  where  FIRSTNAME = '"+Split[0]+"' and LASTNAME = '"+Split[1]+"'   ";
            ResultSet rs = stat.executeQuery(sqlstetment);
            if(rs.next()){
                 Tssn = rs.getInt(1);
            }
            sqlstetment = "insert into  gives values ("+Tssn+", "+NumberText.getText().trim()+") ";
            stat.executeUpdate(sqlstetment);

            con.commit();
            con.close();
          //  JOptionPane.showMessageDialog(null, "Add Done");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add");
            alert.setHeaderText(null);
            alert.setContentText("Add DONE ! ");
            Stage stage2 = (Stage) alert.getDialogPane().getScene().getWindow();
            alert.showAndWait();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }

}
