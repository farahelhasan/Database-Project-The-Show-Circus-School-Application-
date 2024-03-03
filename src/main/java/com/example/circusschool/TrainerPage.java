package com.example.circusschool;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class TrainerPage implements Initializable {
    Stage stage=new Stage();

    @FXML
    private TextField SALARY;

    @FXML
    private Label TrainerEM;

    @FXML
    private Button disTC;

    @FXML
    private Button logb;

    @FXML
    void calc_salary(ActionEvent event) {

    }

    @FXML
    void gotoDisTrainerCourses(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("disTrainerCourses.fxml"));
        Parent root = fxmlLoader.load();
        // Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("The show circus school");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loginScreen.fxml"));
        Parent root = fxmlLoader.load();
        // Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("The show circus school");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TrainerEM.setText(User.email);
        int mul=1;
        int sum=0;
        int Tssn=0;
            try {
                DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            String URL ="jdbc:oracle:thin:@localhost:1521:XE";
        Connection con =DriverManager.getConnection(URL,"c##fawar","654321");
        con.setAutoCommit(false);
                Statement stat = con.createStatement();
                String sqlstetment = "select EMPLOYEE.EMPLOYEESSN from EMPLOYEE where  EMPLOYEE.EMAIL='"+User.email+"'  ";
                ResultSet rs1 =stat.executeQuery(sqlstetment);
                if(rs1.next()){
                    Tssn=rs1.getInt(1);
                }
         sqlstetment = "select COURSE.CourseCost,COURSE.NUMOFSTUDENT from COURSE,EMPLOYEE,TRAINER,GIVES where COURSE.COURSENUMBER=GIVES.COURSENUMBER and TRAINER.TRAINERSSN=EMPLOYEE.EMPLOYEESSN and GIVES.TRAINERSSN=TRAINER.TRAINERSSN and EMPLOYEE.EMAIL='"+User.email+"'  ";
        ResultSet rs =stat.executeQuery(sqlstetment);
        while (rs.next()){
            mul=rs.getInt(1)*rs.getInt(2);
            sum=sum+mul;
        }
        double Slr =(double)sum * 0.7;
        SALARY.setText(Double.toString(Slr));
            }
            catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"SQLLLLLLLLLLL");
        }
    }

}
