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
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


public class CourseManagerScreen implements Initializable {

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
    private RadioButton CNameRadioButton;

    @FXML
    private RadioButton CNumberRadioButton;

    @FXML
    private RadioButton CTypeRadioButton;
    @FXML
    private Label UsernameLable;

    @FXML
    private Button EditButton;
    @FXML
    private Button Report2Button;

    @FXML
    private Button RefrechButton;

    @FXML
    private TextField SearchStudentText;

    @FXML
    private TableView<Course> CourseTable;

    @FXML
    private TableColumn<Course, Integer> CostColumn;

    @FXML
    private TableColumn<Course, String> TimeIntervalColumn;

    @FXML
    private TableColumn<Course, String> TranierNameColumn;

    @FXML
    private TableColumn<Course, String> TypeColumn;

    @FXML
    private TableColumn<Course, Integer> CNamberColumn;

    @FXML
    private TableColumn<Course, String> CNameColumn;

    @FXML
    private TableColumn<Course, String> ForColumn;

    @FXML
    private Button ReportButoon;
    @FXML
    private TableColumn<Course, Integer> MaxStudentColumn;

    @FXML
    private TableColumn<Course, Integer> NumOfStudentColumn;
    ObservableList<Course> List = FXCollections.observableArrayList();
    ObservableList<Student> SList = FXCollections.observableArrayList();

    @Override
    public void initialize (URL url , ResourceBundle rb) {
        Color paint = new Color(0.9373, 0.5255, 0.3059, 1.0);
        UsernameLable.setTextFill(paint);
        UsernameLable.setText(User.email);
        ToggleGroup tgCourse = new ToggleGroup();
        CNameRadioButton.setToggleGroup(tgCourse);
        CTypeRadioButton.setToggleGroup(tgCourse);
        CNumberRadioButton.setToggleGroup(tgCourse);


        CNamberColumn.setCellValueFactory(new PropertyValueFactory<Course, Integer>("Number"));
        CNameColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("Name"));
        TimeIntervalColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("Time"));
        CostColumn.setCellValueFactory(new PropertyValueFactory<Course, Integer>("Cost"));
        NumOfStudentColumn.setCellValueFactory(new PropertyValueFactory<Course, Integer>("NumOfStudent"));
        MaxStudentColumn.setCellValueFactory(new PropertyValueFactory<Course, Integer>("MaxNumOfStudent"));
        ForColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("CourseFor"));
        TypeColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("Type"));
        TranierNameColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("TrainerName"));
       try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            String URL = "jdbc:oracle:thin:@localhost:1521:XE";
            Connection con = DriverManager.getConnection(URL, "C##fawar", "654321");
            con.setAutoCommit(false);
            Statement stat = con.createStatement();
            String sqlstetment = "select course.COURSENUMBER,course.COURSENAME,course.TIMEINTERVAL,course.COURSECOST,course.NUMOFSTUDENT,course.MAXNUMOFSTUDENT,course.COURSEFOR,course.COURSETYPE, employee.FIRSTNAME ,employee.LASTNAME from COURSE,employee , gives , trainer where employee.employeessn = trainer.trainerssn and COURSE.COURSENUMBER = gives.COURSENUMBER and trainer.trainerssn = gives.trainerssn  ";
            ResultSet rs = stat.executeQuery(sqlstetment);
            int i =0;
            List.clear();
            while (rs.next()){
                Course obj = new Course(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(5),rs.getInt(6),rs.getString(7) ,rs.getString(8));
               obj.TrainerName = rs.getString(9)+" "+ rs.getString(10);
                List.add(i,obj);
                i++;
            }
            CourseTable.setItems(List);

            con.commit();
            con.close();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }


    @FXML
    void AddButtonClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("addCourseManagerScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("The show circus school");
        stage.setScene(scene);
        stage.show();
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
        ToggleGroup tgCourse = new ToggleGroup();
        CNameRadioButton.setToggleGroup(tgCourse);
        CTypeRadioButton.setToggleGroup(tgCourse);
        CNumberRadioButton.setToggleGroup(tgCourse);


        CNamberColumn.setCellValueFactory(new PropertyValueFactory<Course, Integer>("Number"));
        CNameColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("Name"));
        TimeIntervalColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("Time"));
        CostColumn.setCellValueFactory(new PropertyValueFactory<Course, Integer>("Cost"));
        NumOfStudentColumn.setCellValueFactory(new PropertyValueFactory<Course, Integer>("NumOfStudent"));
        MaxStudentColumn.setCellValueFactory(new PropertyValueFactory<Course, Integer>("MaxNumOfStudent"));
        ForColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("CourseFor"));
        TypeColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("Type"));
        TranierNameColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("TrainerName"));
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            String URL = "jdbc:oracle:thin:@localhost:1521:XE";
            Connection con = DriverManager.getConnection(URL, "C##fawar", "654321");
            con.setAutoCommit(false);
            Statement stat = con.createStatement();
            String sqlstetment = "select course.COURSENUMBER,course.COURSENAME,course.TIMEINTERVAL,course.COURSECOST,course.NUMOFSTUDENT,course.MAXNUMOFSTUDENT,course.COURSEFOR,course.COURSETYPE, employee.FIRSTNAME ,employee.LASTNAME from COURSE,employee , gives , trainer where employee.employeessn = trainer.trainerssn and COURSE.COURSENUMBER = gives.COURSENUMBER and trainer.trainerssn = gives.trainerssn  ";
            ResultSet rs = stat.executeQuery(sqlstetment);
            int i =0;
            List.clear();
            while (rs.next()){
                Course obj = new Course(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(5),rs.getInt(6),rs.getString(7) ,rs.getString(8));
                obj.TrainerName = rs.getString(9)+" "+ rs.getString(10);
                List.add(i,obj);
                i++;
            }
            CourseTable.setItems(List);

            con.commit();
            con.close();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }

    @FXML
    void DeleteButtonClick(ActionEvent event) {
        try{
            Course obj = CourseTable.getSelectionModel().getSelectedItem();
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            String URL = "jdbc:oracle:thin:@localhost:1521:XE";
            Connection con = DriverManager.getConnection(URL, "C##fawar", "654321");
            con.setAutoCommit(false);
            Statement stat = con.createStatement();
            String sqlstetment = "delete from gives where COURSENUMBER = "+obj.Number+"";
            stat.executeUpdate(sqlstetment);
             sqlstetment = "delete from COURSE where COURSENUMBER = "+obj.Number+"";
            stat.executeUpdate(sqlstetment);
          //  JOptionPane.showMessageDialog(null, "delete done");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Course Page");
            alert.setHeaderText(null);
            alert.setContentText("DELETE DONE ! ");
            Stage stage2 = (Stage) alert.getDialogPane().getScene().getWindow();
            alert.showAndWait();
            con.commit();
            con.close();
        }catch(Exception ex){
            //JOptionPane.showMessageDialog(null, ex.toString());
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Course Page");
            alert.setHeaderText(null);
            alert.setContentText("Select Course to delete it  ! ");
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
             sqlstetment = "select course.COURSENUMBER,course.COURSENAME,course.TIMEINTERVAL,course.COURSECOST,course.NUMOFSTUDENT,course.MAXNUMOFSTUDENT,course.COURSEFOR,course.COURSETYPE, employee.FIRSTNAME ,employee.LASTNAME from COURSE,employee , gives , trainer where employee.employeessn = trainer.trainerssn and COURSE.COURSENUMBER = gives.COURSENUMBER and trainer.trainerssn = gives.trainerssn  ";
            ResultSet rs = stat.executeQuery(sqlstetment);
            if(CNameRadioButton.isSelected()) {
                while (rs.next()) {
                    String  SearchStudent= SearchStudentText.getText().trim().toLowerCase();
                    String CNameFromDataBasse =rs.getString(2).toLowerCase();
                    if((SearchStudent.contains(CNameFromDataBasse)) || (CNameFromDataBasse.contains(SearchStudent))){
                        Course obj = new Course(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(5),rs.getInt(6),rs.getString(7) ,rs.getString(8));
                        obj.TrainerName = rs.getString(9)+" "+ rs.getString(10);
                        List.add(i, obj);
                        i++;
                    }
                }
                CourseTable.setItems(List);
            }else if(CTypeRadioButton.isSelected()) {
                while (rs.next()) {
                    String  SearchStudent= SearchStudentText.getText().trim().toLowerCase();
                    String CtypeFromDataBasse =rs.getString(8).toLowerCase();
                    if((SearchStudent.contains(CtypeFromDataBasse) ) || (CtypeFromDataBasse.contains(SearchStudent))){
                        Course obj = new Course(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(5),rs.getInt(6),rs.getString(7) ,rs.getString(8));
                        obj.TrainerName = rs.getString(9)+" "+ rs.getString(10);
                        List.add(i, obj);
                        i++;
                    }
                }
                CourseTable.setItems(List);
            }else if(CNumberRadioButton.isSelected()) {
                while (rs.next()) {
                    String  SearchStudent= SearchStudentText.getText().trim().toLowerCase();
                    String CNumFromDataBasse =Integer.toString(rs.getInt(1));
                    if((SearchStudent.contains(CNumFromDataBasse) ) || (CNumFromDataBasse.contains(SearchStudent))){
                        Course obj = new Course(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(5),rs.getInt(6),rs.getString(7) ,rs.getString(8));
                        obj.TrainerName = rs.getString(9)+" "+ rs.getString(10);
                        List.add(i, obj);
                        i++;
                    }
                }
                CourseTable.setItems(List);
            }
            con.close();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.toString());
        }


    }

    @FXML
    void EditButtonClick(ActionEvent event) throws IOException {
        try {
            Course obj = CourseTable.getSelectionModel().getSelectedItem();
            Course.SNumber = obj.Number;
            Course.SName = obj.Name;
            Course.SType = obj.Type;
            Course.STime = obj.Time;
            Course.SMaxNumOfStudent = obj.MaxNumOfStudent;
            Course.SCost = obj.Cost;
            Course.SCourseFor = obj.CourseFor;


            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("editCourseManagerScreen.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("The show circus school");
            stage.setScene(scene);
            stage.show();
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Course Page");
            alert.setHeaderText(null);
            alert.setContentText("Select Course ! ");
            Stage stage2 = (Stage) alert.getDialogPane().getScene().getWindow();
            alert.showAndWait();
        }
    }
    @FXML
    void ReportButoonClick(ActionEvent event) {
      try{
          DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        String URL = "jdbc:oracle:thin:@localhost:1521:XE";
        Connection con = DriverManager.getConnection(URL, "C##fawar", "654321");
        con.setAutoCommit(false);
         //File file =  new File("Coffee.jrxml");
        InputStream input = new FileInputStream(new File("NameOfStudentReport.jrxml"));
         JasperDesign jd =  JRXmlLoader.load(input);
       JasperReport jr = JasperCompileManager.compileReport(jd);
          JasperPrint jp = JasperFillManager.fillReport(jr,null , con);
         /* JFrame frame = new JFrame("report");
          frame.getContentPane().add(new JRViewer(jp));
          frame.pack();
          frame.setVisible(true);*/
          OutputStream os = new FileOutputStream(new File("NameOfStudentReportPDF.pdf"));
          JasperExportManager.exportReportToPdfStream(jp,os);
          os.close();
          input.close();
        // JasperViewer.viewReport(jp, true);

        //  input.close();

        //  JasperDesign JD = JRXML
        /*Statement stat = con.createStatement();
        String sqlstetment = "delete from gives where COURSENUMBER = "+obj.Number+"";
        stat.executeUpdate(sqlstetment);
        sqlstetment = "delete from COURSE where COURSENUMBER = "+obj.Number+"";
        stat.executeUpdate(sqlstetment);
        JOptionPane.showMessageDialog(null, "delete done");
        con.commit();
        con.close();*/
         // con.commit();
         // con.close();
          Alert alert = new Alert(Alert.AlertType.INFORMATION);
          alert.setTitle("Course Page");
          alert.setHeaderText(null);
          alert.setContentText("The Report done in PDF file :)");
          Stage stage2 = (Stage) alert.getDialogPane().getScene().getWindow();
          alert.showAndWait();
    }catch(Exception ex){
          JOptionPane.showMessageDialog(null,ex.getMessage());   }
    }




    @FXML
    void Report2ButtonClike(ActionEvent event) {
        try{
            Course obj = CourseTable.getSelectionModel().getSelectedItem();
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            String URL = "jdbc:oracle:thin:@localhost:1521:XE";
            Connection con = DriverManager.getConnection(URL, "C##fawar", "654321");
            con.setAutoCommit(false);
            Statement stat = con.createStatement();
            String sqlstetment = "select STUDENT.FIRSTNAME ,STUDENT.LASTNAME from  STUDENT , JOIN_TO where JOIN_TO.COURSENUMBER = "+obj.Number+" AND STUDENT.STUDENTSSN = JOIN_TO.STUDENTSSN  ";
            ResultSet rs = stat.executeQuery(sqlstetment);
            int i =0,j =0;
            List.clear();
            while (rs.next()){
                Student Sobj = new Student();
                Sobj.FirstName = rs.getString(1);
                Sobj.LastName= rs.getString(2);
                SList.add(i,Sobj);
                i++;
            }
            con.commit();
            con.close();
            //JasperDesign jd = JRXmlLoader.load("testtt.jrxml");
          //  String q = "SELECT STUDENT.FIRSTNAME , COURSE.COURSENAME FROM STUDENT , COURSE , JOIN_TO WHERE STUDENT.STUDENTSSN = JOIN_TO.STUDENTSSN AND COURSE.COURSENUMBER = JOIN_TO.COURSENUMBER AND STUDENT.STUDENTNAME = 'nai' AND COURSE.COURSENAME = '"+obj.Name+"'";
         //   JRDesignQuery newQuery = new JRDesignQuery();
         //   newQuery.setText(q);
          //  jd.setQuery(newQuery);
            for (j = 0 ; j < SList.size();j++) {
                JasperReport jr = JasperCompileManager.compileReport("certificate.jrxml");
                JRDataSource C = new JREmptyDataSource();
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("StudentName", SList.get(j).FirstName+" "+SList.get(j).LastName );
                param.put("CourseName", obj.Name);
                param.put("TrainerName", obj.TrainerName);

                JasperPrint jp = JasperFillManager.fillReport(jr, param, C);
                String fileName = "Report" + j + ".pdf";
                OutputStream os = new FileOutputStream(new File(fileName));
                JasperExportManager.exportReportToPdfStream(jp, os);
                os.close();
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Course Page");
            alert.setHeaderText(null);
            alert.setContentText(" Done in PDF files :)");
            Stage stage2 = (Stage) alert.getDialogPane().getScene().getWindow();
            alert.showAndWait();
           // JasperViewer.viewReport(jp, false);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.toString());}
    }
    @FXML
    void RefrechButtonClick(ActionEvent event) {
        CancelButtonClick(event);
    }

    @FXML
    void searchStudent(KeyEvent event) {

    }

}
