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

public class JuggPage implements Initializable {

    @FXML
    private Button BackS;

    @FXML
    private TableColumn<Course, Integer> CourseCost;

    @FXML
    private TableColumn<Course, String> CourseName;

    @FXML
    private TableColumn<Course, Integer> CourseNumber;

    @FXML
    private TableColumn<Course, String> CourseTrainer;

    @FXML
    private TableColumn<Course, String> CourseType;
    @FXML
    private Label AddStatus;
    @FXML
    private TableColumn<Course, String> GenderofCourse;

    @FXML
    private TableColumn<Course, String> TimeInterval;

    @FXML
    private Button add;

    @FXML
    private Button homeS;

    @FXML
    private Button sCost;

    @FXML
    private Button sGender;

    @FXML
    private Button sTime;

    @FXML
    private Button refresh;
    @FXML
    private Button sTrainer;

    @FXML
    private TextField search;

    @FXML
    private Label studentEM;

    @FXML
    private TableView<Course> table;
    Stage stage=new Stage();
    ObservableList<Course> list = FXCollections.observableArrayList();


    @FXML
    void BackSCTypes(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addCourses.fxml"));
        Parent root = fxmlLoader.load();
        // Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("The show circus school");
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    void refreshPage(ActionEvent event) {
        AddStatus.setText("");
        search.clear();
        studentEM.setText(User.email);
        CourseNumber.setCellValueFactory(new PropertyValueFactory<Course,Integer>("Number"));
        CourseName.setCellValueFactory(new PropertyValueFactory<Course,String>("Name"));
        TimeInterval.setCellValueFactory(new PropertyValueFactory<Course,String>("Time"));
        CourseCost.setCellValueFactory(new PropertyValueFactory<Course,Integer>("Cost"));
        CourseType.setCellValueFactory(new PropertyValueFactory<Course,String>("Type"));
        GenderofCourse.setCellValueFactory(new PropertyValueFactory<Course,String>("CourseFor"));
        CourseTrainer.setCellValueFactory(new PropertyValueFactory<Course,String>("TrainerName"));
        try{
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            String URL ="jdbc:oracle:thin:@localhost:1521:XE";
            Connection con =DriverManager.getConnection(URL,"c##fawar","654321");
            con.setAutoCommit(false);
            Statement stat = con.createStatement();
            String sqlstetment = "select COURSE.CourseNumber,COURSE.CourseName,COURSE.CourseCost,COURSE.CourseType,COURSE.CourseFor,COURSE.TimeInterval,EMPLOYEE.FIRSTNAME,EMPLOYEE.LASTNAME from COURSE,TRAINER,GIVES,employee where COURSE.COURSENUMBER=GIVES.COURSENUMBER and COURSE.COURSETYPE='Juggling'and EMPLOYEE.EMPLOYEESSN=TRAINER.TRAINERSSN and TRAINER.TRAINERSSN=GIVES.TRAINERSSN";
            ResultSet rs =stat.executeQuery(sqlstetment);
            int i=0;
            list.clear();;
            while(rs.next()){
                Course obj =new Course(rs.getInt(1),rs.getString(2),rs.getString(6),rs.getInt(3),0,0,rs.getString(5),rs.getString(4));
                obj.TrainerName=rs.getString(7)+" "+rs.getString(8);
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
    void addCourse(ActionEvent event) {
        AddStatus.setText("");
        int Sssn=0;
        Course obj = table.getSelectionModel().getSelectedItem();
        String Gender="";
        String GenofCourse = obj.CourseFor;
        int Max = 0;
        int Num =0;
        int flag=0;
        try{
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            String URL ="jdbc:oracle:thin:@localhost:1521:XE";
            Connection con =DriverManager.getConnection(URL,"c##fawar","654321");
            con.setAutoCommit(false);
            Statement stat = con.createStatement();
            String sqlstetment= " select MaxNumOfStudent,NumOfStudent from COURSE where COURSE.COURSENUMBER='"+obj.Number+"'";
            ResultSet rs1 = stat.executeQuery(sqlstetment);
            if(rs1.next()){
                Max=rs1.getInt(1);
                Num=rs1.getInt(2);
            }
            sqlstetment= " select STUDENTSSN,GENDER from STUDENT where STUDENT.EMAIL='"+User.email+"'";
            ResultSet rs = stat.executeQuery(sqlstetment);
            if(rs.next()){

                Sssn= rs.getInt(1);
                Gender=rs.getString(2);
            }
            if(Num<Max && (GenofCourse.equals(Gender)||GenofCourse.equals("Both"))){
                stat.executeQuery("INSERT INTO JOIN_TO VALUES ("+Sssn+", "+obj.Number+")");
                flag=1;
            }
            else if(Num>=Max) {
                AddStatus.setText("");
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Full.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                stage.setResizable(false);
                stage.setTitle("The Show");
                stage.setScene(scene);
                stage.show();
            } else if (!GenofCourse.equals(Gender)) {
                AddStatus.setText("");
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("notGender.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                stage.setResizable(false);
                stage.setTitle("The Show");
                stage.setScene(scene);
                stage.show();
            }
            if(flag==1){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("");
                alert.setHeaderText(null);
                alert.setContentText("Join done successfully\n" +
                        " We are happy to have you join us for this course ! ");
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                alert.showAndWait();
               // AddStatus.setText("You Joined The Course Sucessfully");
                Num++;
                stat.executeQuery("UPDATE COURSE SET NUMOFSTUDENT="+Num+" WHERE COURSE.COURSENUMBER="+obj.Number+"");
            }
            con.commit();
            con.close();

        }

        catch(SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("");
            alert.setHeaderText(null);
            alert.setContentText("You Are Already in This Course");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            alert.showAndWait();
          //  AddStatus.setText("You Are Already in This Course");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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

    @FXML
    void searchby(ActionEvent event) {

    }

    @FXML
    void searchbyCost(ActionEvent event) {
        studentEM.setText(User.email);
        CourseNumber.setCellValueFactory(new PropertyValueFactory<Course,Integer>("Number"));
        CourseName.setCellValueFactory(new PropertyValueFactory<Course,String>("Name"));
        TimeInterval.setCellValueFactory(new PropertyValueFactory<Course,String>("Time"));
        CourseCost.setCellValueFactory(new PropertyValueFactory<Course,Integer>("Cost"));
        CourseType.setCellValueFactory(new PropertyValueFactory<Course,String>("Type"));
        GenderofCourse.setCellValueFactory(new PropertyValueFactory<Course,String>("CourseFor"));
        CourseTrainer.setCellValueFactory(new PropertyValueFactory<Course,String>("TrainerName"));
        try{
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            String URL ="jdbc:oracle:thin:@localhost:1521:XE";
            Connection con =DriverManager.getConnection(URL,"c##fawar","654321");
            con.setAutoCommit(false);
            Statement stat = con.createStatement();
            String sqlstetment = "select COURSE.CourseNumber,COURSE.CourseName,COURSE.CourseCost,COURSE.CourseType,COURSE.CourseFor,COURSE.TimeInterval,EMPLOYEE.FIRSTNAME,EMPLOYEE.LASTNAME from COURSE,TRAINER,GIVES,employee where COURSE.COURSENUMBER=GIVES.COURSENUMBER and COURSE.COURSETYPE='Juggling'and EMPLOYEE.EMPLOYEESSN=TRAINER.TRAINERSSN and TRAINER.TRAINERSSN=GIVES.TRAINERSSN ";
            ResultSet rs =stat.executeQuery(sqlstetment);
            int i=0;
            list.clear();;
            while(rs.next()){
                String SearchText = search.getText().trim().toLowerCase();
                String CostFromDB = Integer.toString(rs.getInt(3));
                if((SearchText.contains(CostFromDB))||CostFromDB.contains(SearchText)){
                    Course obj =new Course(rs.getInt(1),rs.getString(2),rs.getString(6),rs.getInt(3),0,0,rs.getString(5),rs.getString(4));
                    obj.TrainerName=rs.getString(7)+" "+rs.getString(8);
                    list.add(i,obj);
                    i++;
                }

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
    void searchbyGender(ActionEvent event) {
        AddStatus.setText("");
        studentEM.setText(User.email);
        CourseNumber.setCellValueFactory(new PropertyValueFactory<Course,Integer>("Number"));
        CourseName.setCellValueFactory(new PropertyValueFactory<Course,String>("Name"));
        TimeInterval.setCellValueFactory(new PropertyValueFactory<Course,String>("Time"));
        CourseCost.setCellValueFactory(new PropertyValueFactory<Course,Integer>("Cost"));
        CourseType.setCellValueFactory(new PropertyValueFactory<Course,String>("Type"));
        GenderofCourse.setCellValueFactory(new PropertyValueFactory<Course,String>("CourseFor"));
        CourseTrainer.setCellValueFactory(new PropertyValueFactory<Course,String>("TrainerName"));
        try{
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            String URL ="jdbc:oracle:thin:@localhost:1521:XE";
            Connection con =DriverManager.getConnection(URL,"c##fawar","654321");
            con.setAutoCommit(false);
            Statement stat = con.createStatement();
            String sqlstetment = "select COURSE.CourseNumber,COURSE.CourseName,COURSE.CourseCost,COURSE.CourseType,COURSE.CourseFor,COURSE.TimeInterval,EMPLOYEE.FIRSTNAME,EMPLOYEE.LASTNAME from COURSE,TRAINER,GIVES,employee where COURSE.COURSENUMBER=GIVES.COURSENUMBER and COURSE.COURSETYPE='Juggling'and EMPLOYEE.EMPLOYEESSN=TRAINER.TRAINERSSN and TRAINER.TRAINERSSN=GIVES.TRAINERSSN ";
            ResultSet rs =stat.executeQuery(sqlstetment);
            int i=0;
            list.clear();;
            while(rs.next()){
                String SearchText = search.getText().trim().toLowerCase();
                String GenderFromDB = rs.getString(5);
                if((SearchText.equalsIgnoreCase(GenderFromDB))){
                    Course obj =new Course(rs.getInt(1),rs.getString(2),rs.getString(6),rs.getInt(3),0,0,rs.getString(5),rs.getString(4));
                    obj.TrainerName=rs.getString(7)+" "+rs.getString(8);
                    list.add(i,obj);
                    i++;
                }

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
        //Name
    void searchbyTime(ActionEvent event) {
        studentEM.setText(User.email);
        CourseNumber.setCellValueFactory(new PropertyValueFactory<Course,Integer>("Number"));
        CourseName.setCellValueFactory(new PropertyValueFactory<Course,String>("Name"));
        TimeInterval.setCellValueFactory(new PropertyValueFactory<Course,String>("Time"));
        CourseCost.setCellValueFactory(new PropertyValueFactory<Course,Integer>("Cost"));
        CourseType.setCellValueFactory(new PropertyValueFactory<Course,String>("Type"));
        GenderofCourse.setCellValueFactory(new PropertyValueFactory<Course,String>("CourseFor"));
        CourseTrainer.setCellValueFactory(new PropertyValueFactory<Course,String>("TrainerName"));
        try{
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            String URL ="jdbc:oracle:thin:@localhost:1521:XE";
            Connection con =DriverManager.getConnection(URL,"c##fawar","654321");
            con.setAutoCommit(false);
            Statement stat = con.createStatement();
            String sqlstetment = "select COURSE.CourseNumber,COURSE.CourseName,COURSE.CourseCost,COURSE.CourseType,COURSE.CourseFor,COURSE.TimeInterval,EMPLOYEE.FIRSTNAME,EMPLOYEE.LASTNAME from COURSE,TRAINER,GIVES,employee where COURSE.COURSENUMBER=GIVES.COURSENUMBER and COURSE.COURSETYPE='Juggling'and EMPLOYEE.EMPLOYEESSN=TRAINER.TRAINERSSN and TRAINER.TRAINERSSN=GIVES.TRAINERSSN ";
            ResultSet rs =stat.executeQuery(sqlstetment);
            int i=0;
            list.clear();;
            while(rs.next()){
                String SearchText = search.getText().trim().toLowerCase();
                String TimeFromDB = rs.getString(2).toLowerCase();
                if((SearchText.contains(TimeFromDB))||(TimeFromDB.contains(SearchText))){
                    Course obj =new Course(rs.getInt(1),rs.getString(2),rs.getString(6),rs.getInt(3),0,0,rs.getString(5),rs.getString(4));
                    obj.TrainerName=rs.getString(7)+" "+rs.getString(8);
                    list.add(i,obj);
                    i++;
                }

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
    void searchbyTrainer(ActionEvent event) {
        studentEM.setText(User.email);
        CourseNumber.setCellValueFactory(new PropertyValueFactory<Course,Integer>("Number"));
        CourseName.setCellValueFactory(new PropertyValueFactory<Course,String>("Name"));
        TimeInterval.setCellValueFactory(new PropertyValueFactory<Course,String>("Time"));
        CourseCost.setCellValueFactory(new PropertyValueFactory<Course,Integer>("Cost"));
        CourseType.setCellValueFactory(new PropertyValueFactory<Course,String>("Type"));
        GenderofCourse.setCellValueFactory(new PropertyValueFactory<Course,String>("CourseFor"));
        CourseTrainer.setCellValueFactory(new PropertyValueFactory<Course,String>("TrainerName"));
        try{
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            String URL ="jdbc:oracle:thin:@localhost:1521:XE";
            Connection con =DriverManager.getConnection(URL,"c##fawar","654321");
            con.setAutoCommit(false);
            Statement stat = con.createStatement();
            String sqlstetment = "select COURSE.CourseNumber,COURSE.CourseName,COURSE.CourseCost,COURSE.CourseType,COURSE.CourseFor,COURSE.TimeInterval,EMPLOYEE.FIRSTNAME,EMPLOYEE.LASTNAME from COURSE,TRAINER,GIVES,employee where COURSE.COURSENUMBER=GIVES.COURSENUMBER and COURSE.COURSETYPE='Juggling'and EMPLOYEE.EMPLOYEESSN=TRAINER.TRAINERSSN and TRAINER.TRAINERSSN=GIVES.TRAINERSSN ";
            ResultSet rs =stat.executeQuery(sqlstetment);
            int i=0;
            list.clear();;
            while(rs.next()){
                String SearchText = search.getText().trim().toLowerCase();
                String TNameFromDB = (rs.getString(7)+" "+rs.getString(8)).toLowerCase();
                if((SearchText.contains(TNameFromDB))||(TNameFromDB.contains(SearchText))){
                    Course obj =new Course(rs.getInt(1),rs.getString(2),rs.getString(6),rs.getInt(3),0,0,rs.getString(5),rs.getString(4));
                    obj.TrainerName=rs.getString(7)+" "+rs.getString(8);
                    list.add(i,obj);
                    i++;
                }

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
    void sortTable(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        studentEM.setText(User.email);
        CourseNumber.setCellValueFactory(new PropertyValueFactory<Course,Integer>("Number"));
        CourseName.setCellValueFactory(new PropertyValueFactory<Course,String>("Name"));
        TimeInterval.setCellValueFactory(new PropertyValueFactory<Course,String>("Time"));
        CourseCost.setCellValueFactory(new PropertyValueFactory<Course,Integer>("Cost"));
        CourseType.setCellValueFactory(new PropertyValueFactory<Course,String>("Type"));
        GenderofCourse.setCellValueFactory(new PropertyValueFactory<Course,String>("CourseFor"));
        CourseTrainer.setCellValueFactory(new PropertyValueFactory<Course,String>("TrainerName"));
        try{
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            String URL ="jdbc:oracle:thin:@localhost:1521:XE";
            Connection con =DriverManager.getConnection(URL,"c##fawar","654321");
            con.setAutoCommit(false);
            Statement stat = con.createStatement();
            String sqlstetment = "select COURSE.CourseNumber,COURSE.CourseName,COURSE.CourseCost,COURSE.CourseType,COURSE.CourseFor,COURSE.TimeInterval,EMPLOYEE.FIRSTNAME,EMPLOYEE.LASTNAME from COURSE,TRAINER,GIVES,employee where COURSE.COURSENUMBER=GIVES.COURSENUMBER and COURSE.COURSETYPE='Juggling'and EMPLOYEE.EMPLOYEESSN=TRAINER.TRAINERSSN and TRAINER.TRAINERSSN=GIVES.TRAINERSSN";
            ResultSet rs =stat.executeQuery(sqlstetment);
            int i=0;
            list.clear();;
            while(rs.next()){
                Course obj =new Course(rs.getInt(1),rs.getString(2),rs.getString(6),rs.getInt(3),0,0,rs.getString(5),rs.getString(4));
                obj.TrainerName=rs.getString(7)+" "+rs.getString(8);
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