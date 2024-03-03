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

public class DisTrainerCourses implements Initializable {

    @FXML
    private TableColumn<Course, Integer> CourseCost;

    @FXML
    private TableColumn<Course, String> CourseName;

    @FXML
    private TableColumn<Course, Integer> CourseNumber;

    @FXML
    private TableColumn<Course, String> CourseType;

    @FXML
    private TableColumn<Course, String> GenderofCourse;
    ObservableList<Course> list = FXCollections.observableArrayList();
    @FXML
    private TableColumn<Course, Integer> MaxNumber;

    @FXML
    private TableColumn<Course, Integer> StudentNumber;

    @FXML
    private TableColumn<Course, String> TimeInterval;

    @FXML
    private Button homeT;
    @FXML
    private Button show;
    @FXML
    private Button refresh;

    @FXML
    private Button sCost;

    @FXML
    private Button sGender;

    @FXML
    private Button sNofS;

    @FXML
    private Button sTime;
    @FXML
    private Label TrainerEM;
    @FXML
    private TextField search;

    @FXML
    private TableView<Course> table;
    Stage stage=new Stage();
    @FXML
    void gotoTrainerPage(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("trainerPage.fxml"));
        Parent root = fxmlLoader.load();
        // Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("The show circus school");
        stage.setScene(new Scene(root));
        stage.show();

    }

    @FXML
    void refreshPage(ActionEvent event) {
        TrainerEM.setText(User.email);
        search.clear();
        CourseNumber.setCellValueFactory(new PropertyValueFactory<Course,Integer>("Number"));
        CourseName.setCellValueFactory(new PropertyValueFactory<Course,String>("Name"));
        TimeInterval.setCellValueFactory(new PropertyValueFactory<Course,String>("Time"));
        CourseCost.setCellValueFactory(new PropertyValueFactory<Course,Integer>("Cost"));
        CourseType.setCellValueFactory(new PropertyValueFactory<Course,String>("Type"));
        GenderofCourse.setCellValueFactory(new PropertyValueFactory<Course,String>("CourseFor"));
        MaxNumber.setCellValueFactory(new PropertyValueFactory<Course,Integer>("MaxNumOfStudent"));
        StudentNumber.setCellValueFactory(new  PropertyValueFactory<Course,Integer>("NumOfStudent"));
        try{
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            String URL ="jdbc:oracle:thin:@localhost:1521:XE";
            Connection con =DriverManager.getConnection(URL,"c##fawar","654321");
            con.setAutoCommit(false);
            Statement stat = con.createStatement();
            String sqlstetment = "select COURSE.CourseNumber,COURSE.CourseName,COURSE.CourseCost,COURSE.CourseType,COURSE.CourseFor,COURSE.TimeInterval,COURSE.MAXNUMOFSTUDENT,COURSE.NUMOFSTUDENT from COURSE,EMPLOYEE,TRAINER,GIVES where COURSE.COURSENUMBER=GIVES.COURSENUMBER and TRAINER.TRAINERSSN=EMPLOYEE.EMPLOYEESSN and GIVES.TRAINERSSN=TRAINER.TRAINERSSN and EMPLOYEE.EMAIL='"+User.email+"'  ";
            ResultSet rs =stat.executeQuery(sqlstetment);
            int i=0;
            list.clear();;
            while(rs.next()){
                Course obj =new Course(rs.getInt(1),rs.getString(2),rs.getString(6),rs.getInt(3),rs.getInt(8),rs.getInt(7),rs.getString(5),rs.getString(4));
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
    void searchby(ActionEvent event) {

    }

    @FXML
    void searchbyCost(ActionEvent event) {
        TrainerEM.setText(User.email);
        CourseNumber.setCellValueFactory(new PropertyValueFactory<Course,Integer>("Number"));
        CourseName.setCellValueFactory(new PropertyValueFactory<Course,String>("Name"));
        TimeInterval.setCellValueFactory(new PropertyValueFactory<Course,String>("Time"));
        CourseCost.setCellValueFactory(new PropertyValueFactory<Course,Integer>("Cost"));
        CourseType.setCellValueFactory(new PropertyValueFactory<Course,String>("Type"));
        GenderofCourse.setCellValueFactory(new PropertyValueFactory<Course,String>("CourseFor"));
        MaxNumber.setCellValueFactory(new PropertyValueFactory<Course,Integer>("MaxNumOfStudent"));
        StudentNumber.setCellValueFactory(new  PropertyValueFactory<Course,Integer>("NumOfStudent"));
        try{
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            String URL ="jdbc:oracle:thin:@localhost:1521:XE";
            Connection con =DriverManager.getConnection(URL,"c##fawar","654321");
            con.setAutoCommit(false);
            Statement stat = con.createStatement();
            String sqlstetment = "select COURSE.CourseNumber,COURSE.CourseName,COURSE.CourseCost,COURSE.CourseType,COURSE.CourseFor,COURSE.TimeInterval,COURSE.MAXNUMOFSTUDENT,COURSE.NUMOFSTUDENT from COURSE,EMPLOYEE,TRAINER,GIVES where COURSE.COURSENUMBER=GIVES.COURSENUMBER and TRAINER.TRAINERSSN=EMPLOYEE.EMPLOYEESSN and GIVES.TRAINERSSN=TRAINER.TRAINERSSN and EMPLOYEE.EMAIL='"+User.email+"'  ";
            ResultSet rs =stat.executeQuery(sqlstetment);
            int i=0;
            list.clear();;
            while(rs.next()){
                String SearchText = search.getText().trim().toLowerCase();
                String CostFromDB = Integer.toString(rs.getInt(3));
                if((SearchText.contains(CostFromDB))||CostFromDB.contains(SearchText)){
                    Course obj =new Course(rs.getInt(1),rs.getString(2),rs.getString(6),rs.getInt(3),rs.getInt(8),rs.getInt(7),rs.getString(5),rs.getString(4));
                    list.add(i,obj);
                i++;
            }}
            table.setItems(list);
            con.commit();
            con.close();

        }
        catch(SQLException ex) {

            JOptionPane.showMessageDialog(null,ex.toString());
        }

    }

    @FXML
    void searchbyGender(ActionEvent event) {
        TrainerEM.setText(User.email);
        CourseNumber.setCellValueFactory(new PropertyValueFactory<Course,Integer>("Number"));
        CourseName.setCellValueFactory(new PropertyValueFactory<Course,String>("Name"));
        TimeInterval.setCellValueFactory(new PropertyValueFactory<Course,String>("Time"));
        CourseCost.setCellValueFactory(new PropertyValueFactory<Course,Integer>("Cost"));
        CourseType.setCellValueFactory(new PropertyValueFactory<Course,String>("Type"));
        GenderofCourse.setCellValueFactory(new PropertyValueFactory<Course,String>("CourseFor"));
        MaxNumber.setCellValueFactory(new PropertyValueFactory<Course,Integer>("MaxNumOfStudent"));
        StudentNumber.setCellValueFactory(new  PropertyValueFactory<Course,Integer>("NumOfStudent"));
        try{
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            String URL ="jdbc:oracle:thin:@localhost:1521:XE";
            Connection con =DriverManager.getConnection(URL,"c##fawar","654321");
            con.setAutoCommit(false);
            Statement stat = con.createStatement();
            String sqlstetment = "select COURSE.CourseNumber,COURSE.CourseName,COURSE.CourseCost,COURSE.CourseType,COURSE.CourseFor,COURSE.TimeInterval,COURSE.MAXNUMOFSTUDENT,COURSE.NUMOFSTUDENT from COURSE,EMPLOYEE,TRAINER,GIVES where COURSE.COURSENUMBER=GIVES.COURSENUMBER and TRAINER.TRAINERSSN=EMPLOYEE.EMPLOYEESSN and GIVES.TRAINERSSN=TRAINER.TRAINERSSN and EMPLOYEE.EMAIL='"+User.email+"'  ";
            ResultSet rs =stat.executeQuery(sqlstetment);
            int i=0;
            list.clear();;
            while(rs.next()){
                String SearchText = search.getText().trim().toLowerCase();
                String GenderFromDB = rs.getString(5);
                if((SearchText.equalsIgnoreCase(GenderFromDB))){
                    Course obj =new Course(rs.getInt(1),rs.getString(2),rs.getString(6),rs.getInt(3),rs.getInt(8),rs.getInt(7),rs.getString(5),rs.getString(4));
                    obj.TrainerName=rs.getString(7)+" "+rs.getString(8);
                    list.add(i,obj);
                    i++;
                }}
            table.setItems(list);
            con.commit();
            con.close();

        }
        catch(SQLException ex) {

            JOptionPane.showMessageDialog(null,ex.toString());
        }
    }

    @FXML
    void searchbyNOofStudent(ActionEvent event) {
        TrainerEM.setText(User.email);
        CourseNumber.setCellValueFactory(new PropertyValueFactory<Course,Integer>("Number"));
        CourseName.setCellValueFactory(new PropertyValueFactory<Course,String>("Name"));
        TimeInterval.setCellValueFactory(new PropertyValueFactory<Course,String>("Time"));
        CourseCost.setCellValueFactory(new PropertyValueFactory<Course,Integer>("Cost"));
        CourseType.setCellValueFactory(new PropertyValueFactory<Course,String>("Type"));
        GenderofCourse.setCellValueFactory(new PropertyValueFactory<Course,String>("CourseFor"));
        MaxNumber.setCellValueFactory(new PropertyValueFactory<Course,Integer>("MaxNumOfStudent"));
        StudentNumber.setCellValueFactory(new  PropertyValueFactory<Course,Integer>("NumOfStudent"));
        try{
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            String URL ="jdbc:oracle:thin:@localhost:1521:XE";
            Connection con =DriverManager.getConnection(URL,"c##fawar","654321");
            con.setAutoCommit(false);
            Statement stat = con.createStatement();
            String sqlstetment = "select COURSE.CourseNumber,COURSE.CourseName,COURSE.CourseCost,COURSE.CourseType,COURSE.CourseFor,COURSE.TimeInterval,COURSE.MAXNUMOFSTUDENT,COURSE.NUMOFSTUDENT from COURSE,EMPLOYEE,TRAINER,GIVES where COURSE.COURSENUMBER=GIVES.COURSENUMBER and TRAINER.TRAINERSSN=EMPLOYEE.EMPLOYEESSN and GIVES.TRAINERSSN=TRAINER.TRAINERSSN and EMPLOYEE.EMAIL='"+User.email+"'  ";
            ResultSet rs =stat.executeQuery(sqlstetment);
            int i=0;
            list.clear();;
            while(rs.next()){
                String SearchText = search.getText().trim().toLowerCase();
                String CostFromDB = Integer.toString(rs.getInt(8));
                if((SearchText.contains(CostFromDB))||CostFromDB.contains(SearchText)){
                    Course obj =new Course(rs.getInt(1),rs.getString(2),rs.getString(6),rs.getInt(3),rs.getInt(8),rs.getInt(7),rs.getString(5),rs.getString(4));
                    list.add(i,obj);
                    i++;
                }}
            table.setItems(list);
            con.commit();
            con.close();

        }
        catch(SQLException ex) {

            JOptionPane.showMessageDialog(null,ex.toString());
        }

    }

    @FXML
    void searchbyTime(ActionEvent event) {
        TrainerEM.setText(User.email);
        CourseNumber.setCellValueFactory(new PropertyValueFactory<Course,Integer>("Number"));
        CourseName.setCellValueFactory(new PropertyValueFactory<Course,String>("Name"));
        TimeInterval.setCellValueFactory(new PropertyValueFactory<Course,String>("Time"));
        CourseCost.setCellValueFactory(new PropertyValueFactory<Course,Integer>("Cost"));
        CourseType.setCellValueFactory(new PropertyValueFactory<Course,String>("Type"));
        GenderofCourse.setCellValueFactory(new PropertyValueFactory<Course,String>("CourseFor"));
        MaxNumber.setCellValueFactory(new PropertyValueFactory<Course,Integer>("MaxNumOfStudent"));
        StudentNumber.setCellValueFactory(new  PropertyValueFactory<Course,Integer>("NumOfStudent"));
        try{
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            String URL ="jdbc:oracle:thin:@localhost:1521:XE";
            Connection con =DriverManager.getConnection(URL,"c##fawar","654321");
            con.setAutoCommit(false);
            Statement stat = con.createStatement();
            String sqlstetment = "select COURSE.CourseNumber,COURSE.CourseName,COURSE.CourseCost,COURSE.CourseType,COURSE.CourseFor,COURSE.TimeInterval,COURSE.MAXNUMOFSTUDENT,COURSE.NUMOFSTUDENT from COURSE,EMPLOYEE,TRAINER,GIVES where COURSE.COURSENUMBER=GIVES.COURSENUMBER and TRAINER.TRAINERSSN=EMPLOYEE.EMPLOYEESSN and GIVES.TRAINERSSN=TRAINER.TRAINERSSN and EMPLOYEE.EMAIL='"+User.email+"'  ";
            ResultSet rs =stat.executeQuery(sqlstetment);
            int i=0;
            list.clear();;
            while(rs.next()){
                String SearchText = search.getText().trim().toLowerCase();
                String TimeFromDB = rs.getString(2).toLowerCase();
                if((SearchText.contains(TimeFromDB))||(TimeFromDB.contains(SearchText))){
                    Course obj =new Course(rs.getInt(1),rs.getString(2),rs.getString(6),rs.getInt(3),rs.getInt(8),rs.getInt(7),rs.getString(5),rs.getString(4));
                    obj.TrainerName=rs.getString(7)+" "+rs.getString(8);
                    list.add(i,obj);
                    i++;
                }}
            table.setItems(list);
            con.commit();
            con.close();

        }
        catch(SQLException ex) {

            JOptionPane.showMessageDialog(null,ex.toString());
        }

    }

    @FXML
    void sortTable(ActionEvent event) {

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TrainerEM.setText(User.email);
        CourseNumber.setCellValueFactory(new PropertyValueFactory<Course,Integer>("Number"));
        CourseName.setCellValueFactory(new PropertyValueFactory<Course,String>("Name"));
        TimeInterval.setCellValueFactory(new PropertyValueFactory<Course,String>("Time"));
        CourseCost.setCellValueFactory(new PropertyValueFactory<Course,Integer>("Cost"));
        CourseType.setCellValueFactory(new PropertyValueFactory<Course,String>("Type"));
        GenderofCourse.setCellValueFactory(new PropertyValueFactory<Course,String>("CourseFor"));
        MaxNumber.setCellValueFactory(new PropertyValueFactory<Course,Integer>("MaxNumOfStudent"));
        StudentNumber.setCellValueFactory(new  PropertyValueFactory<Course,Integer>("NumOfStudent"));
        try{
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            String URL ="jdbc:oracle:thin:@localhost:1521:XE";
            Connection con =DriverManager.getConnection(URL,"c##fawar","654321");
            con.setAutoCommit(false);
            Statement stat = con.createStatement();
            String sqlstetment = "select COURSE.CourseNumber,COURSE.CourseName,COURSE.CourseCost,COURSE.CourseType,COURSE.CourseFor,COURSE.TimeInterval,COURSE.MAXNUMOFSTUDENT,COURSE.NUMOFSTUDENT from COURSE,EMPLOYEE,TRAINER,GIVES where COURSE.COURSENUMBER=GIVES.COURSENUMBER and TRAINER.TRAINERSSN=EMPLOYEE.EMPLOYEESSN and GIVES.TRAINERSSN=TRAINER.TRAINERSSN and EMPLOYEE.EMAIL='"+User.email+"'  ";
            ResultSet rs =stat.executeQuery(sqlstetment);
            int i=0;
            list.clear();;
            while(rs.next()){
                Course obj =new Course(rs.getInt(1),rs.getString(2),rs.getString(6),rs.getInt(3),rs.getInt(8),rs.getInt(7),rs.getString(5),rs.getString(4));
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
    void showStudent(ActionEvent event) throws IOException {
        int Sssn=0;
        Course obj = table.getSelectionModel().getSelectedItem();
        Course.SNumber=obj.Number;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("showStudents.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setTitle("The Show");
        stage.setScene(scene);
        stage.show();

    }
    }

