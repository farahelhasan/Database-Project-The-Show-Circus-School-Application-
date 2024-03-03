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
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class DisStudentCourses implements Initializable {
Stage stage=new Stage();
    @FXML
    private TableColumn<Course,Integer> CourseCost;

    @FXML
    private TableColumn<Course,String> CourseName;

    @FXML
    private TableColumn<Course,Integer> CourseNumber;

    @FXML
    private TableColumn<Course,String> CourseType;

    @FXML
    private Button refresh;
    @FXML
    private TableColumn<Course,String> GenderofCourse;

    @FXML
    private Button Remove;

    @FXML
    private Button SHome;

    @FXML
    void refreshPage(ActionEvent event) {
        RemoveStatus.setText("");
        studentEM.setText(User.email);
        CourseNumber.setCellValueFactory(new PropertyValueFactory<Course,Integer>("Number"));
        CourseName.setCellValueFactory(new PropertyValueFactory<Course,String>("Name"));
        TimeInterval.setCellValueFactory(new PropertyValueFactory<Course,String>("Time"));
        CourseCost.setCellValueFactory(new PropertyValueFactory<Course,Integer>("Cost"));
        CourseType.setCellValueFactory(new PropertyValueFactory<Course,String>("Type"));
        GenderofCourse.setCellValueFactory(new PropertyValueFactory<Course,String>("CourseFor"));
        try{
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            String URL ="jdbc:oracle:thin:@localhost:1521:XE";
            Connection con =DriverManager.getConnection(URL,"c##fawar","654321");
            con.setAutoCommit(false);
            Statement stat = con.createStatement();
            String sqlstetment = "select COURSE.CourseNumber,COURSE.CourseName,COURSE.CourseCost,COURSE.CourseType,COURSE.CourseFor,COURSE.TimeInterval from COURSE,STUDENT,JOIN_TO where COURSE.COURSENUMBER=JOIN_TO.COURSENUMBER and STUDENT.STUDENTSSN=JOIN_TO.STUDENTSSN and STUDENT.EMAIL='"+User.email+"'  ";
            ResultSet rs =stat.executeQuery(sqlstetment);
            int i=0;
            list.clear();;
            while(rs.next()){
                Course obj =new Course(rs.getInt(1),rs.getString(2),rs.getString(6),rs.getInt(3),0,0,rs.getString(5),rs.getString(4));
                list.add(i,obj);
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
    @FXML
    private TableColumn<Course,String> TimeInterval;
    @FXML
    private Label RemoveStatus;
    @FXML
    private Label studentEM;

    @FXML
    private TableView<Course> table;

    @FXML
    void RemoveCourse(ActionEvent event) {
        RemoveStatus.setText("");
        int flag =0 ;
        int Sssn=0;
    Course obj = table.getSelectionModel().getSelectedItem();
    int Num=0;
        try{
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            String URL ="jdbc:oracle:thin:@localhost:1521:XE";
            Connection con =DriverManager.getConnection(URL,"c##fawar","654321");
            con.setAutoCommit(false);
            Statement stat = con.createStatement();
            String sqlstetment= " select NumOfStudent from COURSE where COURSE.COURSENUMBER='"+obj.Number+"'";
            ResultSet rs1 = stat.executeQuery(sqlstetment);
            if(rs1.next()){
                Num=rs1.getInt(1);
            }
            sqlstetment= " select STUDENTSSN from STUDENT where STUDENT.EMAIL='"+User.email+"'";
            ResultSet rs = stat.executeQuery(sqlstetment);
            if(rs.next()){
                Sssn= rs.getInt(1);
            }
            if(Num>0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("");
                alert.setHeaderText(null);
                alert.setContentText("You Are Not in The Course Anymore");
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                alert.showAndWait();
             //   RemoveStatus.setText("You Are Not in The Course Anymore");
                stat.executeQuery("DELETE FROM JOIN_TO where JOIN_TO.COURSENUMBER=" + obj.Number + " and JOIN_TO.STUDENTSSN =" + Sssn + "");
            Num--;
            stat.executeQuery("UPDATE COURSE SET NUMOFSTUDENT="+Num+" WHERE COURSE.COURSENUMBER="+obj.Number+"");

            }
            con.commit();
            con.close();

        }
        catch(SQLException ex) {

            JOptionPane.showMessageDialog(null,ex.toString());
        }

    }


    ObservableList<Course> list = FXCollections.observableArrayList();
    @FXML
    void gotoStudentPage(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("studentPage.fxml"));
        Parent root = fxmlLoader.load();
        // Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("The show circus school");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        RemoveStatus.setText("");
        studentEM.setText(User.email);
        CourseNumber.setCellValueFactory(new PropertyValueFactory<Course,Integer>("Number"));
        CourseName.setCellValueFactory(new PropertyValueFactory<Course,String>("Name"));
        TimeInterval.setCellValueFactory(new PropertyValueFactory<Course,String>("Time"));
        CourseCost.setCellValueFactory(new PropertyValueFactory<Course,Integer>("Cost"));
        CourseType.setCellValueFactory(new PropertyValueFactory<Course,String>("Type"));
        GenderofCourse.setCellValueFactory(new PropertyValueFactory<Course,String>("CourseFor"));
        try{
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            String URL ="jdbc:oracle:thin:@localhost:1521:XE";
            Connection con =DriverManager.getConnection(URL,"c##fawar","654321");
            con.setAutoCommit(false);
            Statement stat = con.createStatement();
            String sqlstetment = "select COURSE.CourseNumber,COURSE.CourseName,COURSE.CourseCost,COURSE.CourseType,COURSE.CourseFor,COURSE.TimeInterval from COURSE,STUDENT,JOIN_TO where COURSE.COURSENUMBER=JOIN_TO.COURSENUMBER and STUDENT.STUDENTSSN=JOIN_TO.STUDENTSSN and STUDENT.EMAIL='"+User.email+"'  ";
            ResultSet rs =stat.executeQuery(sqlstetment);
            int i=0;
            list.clear();;
            while(rs.next()){
                Course obj =new Course(rs.getInt(1),rs.getString(2),rs.getString(6),rs.getInt(3),0,0,rs.getString(5),rs.getString(4));
                list.add(i,obj);
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
