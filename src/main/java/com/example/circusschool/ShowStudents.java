package com.example.circusschool;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ShowStudents implements Initializable {

    @FXML
    private TableColumn<Student, String> EmailColumn;

    @FXML
    private TableColumn<Student, String> NameColumn;

    @FXML
    private TableColumn<Student, Integer> PhoneColumn;
    @FXML
    private TableColumn<Student, String> LastNameColumn;
    @FXML
    private TableView<Student> table;
    ObservableList<Student> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EmailColumn.setCellValueFactory(new PropertyValueFactory<Student,String>("Email"));
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        LastNameColumn.setCellValueFactory(new PropertyValueFactory<Student,String>("LastName"));
        PhoneColumn.setCellValueFactory(new PropertyValueFactory<Student,Integer>("Phone"));
        try{
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            String URL ="jdbc:oracle:thin:@localhost:1521:XE";
            Connection con =DriverManager.getConnection(URL,"c##fawar","654321");
            con.setAutoCommit(false);
            Statement stat = con.createStatement();
            String sqlstetment = "select STUDENT.FIRSTNAME,STUDENT.LASTNAME,STUDENT.EMAIL,STUDENT.PHONE from STUDENT,JOIN_TO where  JOIN_TO.COURSENUMBER="+Course.SNumber+"  and STUDENT.STUDENTSSN=JOIN_TO.STUDENTSSN ";
            ResultSet rs =stat.executeQuery(sqlstetment);
            int i=0;
            list.clear();;
            while(rs.next()){
               Student obj=new Student();
               obj.FirstName=rs.getString(1);
               obj.LastName=rs.getString(2);
               obj.Email=rs.getString(3);
               obj.Phone=rs.getInt(4);
               list.add(obj);
               i++;
                }
            table.setItems(list);
            con.commit();
            con.close();

        }
        catch(SQLException ex) {

            JOptionPane.showMessageDialog(null,ex.toString());
        }
    }
}
