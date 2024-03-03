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

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class StudentManagerScreen implements Initializable {
    @FXML
    private Button RefrechButton;

    @FXML
    private Button BackButton;

    @FXML
    private Button CancelButton;

    @FXML
    private Button DeleteStudentButton;

    @FXML
    private Button DisplayStudentButton;

    @FXML
    private Button EditStudentButton;

    @FXML
    private TextField SearchStudentText;

    @FXML
    private RadioButton StudenAddressRadioButton;

    @FXML
    private RadioButton StudenGenderRadioButton;

    @FXML
    private RadioButton StudenNameRadioButton;


    @FXML
    private Label UsernameLable;

    @FXML
    private TableView<Student> StudentTable;

    @FXML
    private TableColumn<Student, String> AddressStudentColumn;

    @FXML
    private TableColumn<Student, String> EmailStudentColumn;

    @FXML
    private TableColumn<Student, String> FirstNameStudentColumn;

    @FXML
    private TableColumn<Student, String> GenderStudentColumn;

    @FXML
    private TableColumn<Student, String> LastNameStudentColumn;

    @FXML
    private TableColumn<Student, Integer> PhoneStudentColumn;

    @FXML
    private TableColumn< Student, Integer> SSNStudentColumn;

      ObservableList<Student> List = FXCollections.observableArrayList();


    @Override
    public void initialize (URL url , ResourceBundle rb){
        Color paint = new Color(0.9373, 0.5255, 0.3059, 1.0);
        UsernameLable.setTextFill(paint);
        UsernameLable.setText(User.email);
        ToggleGroup tgStudentTrainer = new ToggleGroup();
        StudenAddressRadioButton.setToggleGroup(tgStudentTrainer);
        StudenNameRadioButton.setToggleGroup(tgStudentTrainer);
        StudenGenderRadioButton.setToggleGroup(tgStudentTrainer);


        AddressStudentColumn.setCellValueFactory(new PropertyValueFactory<Student,String>("Address"));
        SSNStudentColumn.setCellValueFactory(new PropertyValueFactory<Student,Integer>("SSN"));
        FirstNameStudentColumn.setCellValueFactory(new PropertyValueFactory<Student,String>("FirstName"));
        LastNameStudentColumn.setCellValueFactory(new PropertyValueFactory<Student,String>("LastName"));
        PhoneStudentColumn.setCellValueFactory(new PropertyValueFactory<Student,Integer>("Phone"));
        GenderStudentColumn.setCellValueFactory(new PropertyValueFactory<Student,String>("Gender"));
        EmailStudentColumn.setCellValueFactory(new PropertyValueFactory<Student,String>("Email"));
 try {
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        String URL = "jdbc:oracle:thin:@localhost:1521:XE";
        Connection con = DriverManager.getConnection(URL, "C##fawar", "654321");
        con.setAutoCommit(false);
        Statement stat = con.createStatement();
        String sqlstetment = "select * from student";
        ResultSet rs = stat.executeQuery(sqlstetment);
        int i =0;
        List.clear();
        while (rs.next()){
         Student obj = new Student(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(6),rs.getString(7));
            List.add(i,obj);
            i++;
        }
        StudentTable.setItems(List);

        con.commit();
        con.close();
    }catch(Exception ex){
        JOptionPane.showMessageDialog(null, ex.toString());
 }

        //Student obj =  new Student(111,"DD","D","F","FHFHD",121,"JF");
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
        SearchStudentText.clear();
        AddressStudentColumn.setCellValueFactory(new PropertyValueFactory<Student,String>("Address"));
        SSNStudentColumn.setCellValueFactory(new PropertyValueFactory<Student,Integer>("SSN"));
        FirstNameStudentColumn.setCellValueFactory(new PropertyValueFactory<Student,String>("FirstName"));
        LastNameStudentColumn.setCellValueFactory(new PropertyValueFactory<Student,String>("LastName"));
        PhoneStudentColumn.setCellValueFactory(new PropertyValueFactory<Student,Integer>("Phone"));
        GenderStudentColumn.setCellValueFactory(new PropertyValueFactory<Student,String>("Gender"));
        EmailStudentColumn.setCellValueFactory(new PropertyValueFactory<Student,String>("Email"));
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            String URL = "jdbc:oracle:thin:@localhost:1521:XE";
            Connection con = DriverManager.getConnection(URL, "C##fawar", "654321");
            con.setAutoCommit(false);
            Statement stat = con.createStatement();
            String sqlstetment = "select * from student";
            ResultSet rs = stat.executeQuery(sqlstetment);
            int i =0;
            List.clear();
            while (rs.next()){
                Student obj = new Student(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(6),rs.getString(7));
                List.add(i,obj);
                i++;
            }
            StudentTable.setItems(List);

            con.commit();
            con.close();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }

    @FXML
    void DeleteStudentButtonClick(ActionEvent event) {
      try{  Student obj = StudentTable.getSelectionModel().getSelectedItem();
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        String URL = "jdbc:oracle:thin:@localhost:1521:XE";
        Connection con = DriverManager.getConnection(URL, "C##fawar", "654321");
        con.setAutoCommit(false);
        Statement stat = con.createStatement();
        String sqlstetment = "delete from student where studentssn = "+obj.SSN+"";
        stat.executeUpdate(sqlstetment);

        //  JOptionPane.showMessageDialog(null, "delete done");
          Alert alert = new Alert(Alert.AlertType.INFORMATION);
          alert.setTitle("Student Page");
          alert.setHeaderText(null);
          alert.setContentText("DELETE DONE ! ");
          Stage stage2 = (Stage) alert.getDialogPane().getScene().getWindow();
          alert.showAndWait();
          con.commit();
          con.close();
      }catch(Exception ex){
        //JOptionPane.showMessageDialog(null, ex.toString());
          Alert alert = new Alert(Alert.AlertType.WARNING);
          alert.setTitle("Student Page");
          alert.setHeaderText(null);
          alert.setContentText("Select Trainer who want to delete it ! ");
          Stage stage2 = (Stage) alert.getDialogPane().getScene().getWindow();
          alert.showAndWait();
      }
    }

    @FXML
    void DisplayStudentButton(ActionEvent event) {
        try {
            String sqlstetment = "";
            int i = 0;
            List.clear();

            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            String URL = "jdbc:oracle:thin:@localhost:1521:XE";
            Connection con = DriverManager.getConnection(URL, "C##fawar", "654321");
            con.setAutoCommit(false);
            Statement stat = con.createStatement();
            sqlstetment = "select * from student";
            ResultSet rs = stat.executeQuery(sqlstetment);
            if(StudenNameRadioButton.isSelected()) {
                while (rs.next()) {
                    String  SearchStudent= SearchStudentText.getText().trim().toLowerCase();
                    String FirstNFromDataBasse =rs.getString(2).toLowerCase();
                    String LastNFromDataBasse = rs.getString(3).toLowerCase();
                    if ( (SearchStudent.contains(FirstNFromDataBasse) && SearchStudent.contains(LastNFromDataBasse) )||( FirstNFromDataBasse.contains(SearchStudent) && LastNFromDataBasse.contains(SearchStudent))){
                        Student obj = new Student(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(6),rs.getString(7));
                        List.add(i, obj);
                        i++;
                    }else if( (SearchStudent.contains(FirstNFromDataBasse) ) || (FirstNFromDataBasse.contains(SearchStudent)) ){
                        Student obj = new Student(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(6),rs.getString(7));
                        List.add(i, obj);
                        i++;
                    }else if ((SearchStudent.contains(LastNFromDataBasse)) || (LastNFromDataBasse.contains(SearchStudent))){
                        Student obj = new Student(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(6),rs.getString(7));
                        List.add(i, obj);
                        i++;
                    }
                }
                StudentTable.setItems(List);
            }else if(StudenAddressRadioButton.isSelected()) {
                while (rs.next()) {
                    String  SearchStudent= SearchStudentText.getText().trim().toLowerCase();
                    String AddressFromDataBasse =rs.getString(7).toLowerCase();
                    if((SearchStudent.contains(AddressFromDataBasse)) || (AddressFromDataBasse.contains(SearchStudent))){
                        Student obj = new Student(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(6),rs.getString(7));
                        List.add(i, obj);
                        i++;
                    }
                }
                StudentTable.setItems(List);
            }else if(StudenGenderRadioButton.isSelected()) {
                while (rs.next()) {
                    String  SearchStudent= SearchStudentText.getText().trim().toLowerCase();
                    String GenderFromDataBasse =rs.getString(4).toLowerCase();
                    if(SearchStudent.equalsIgnoreCase(GenderFromDataBasse) ){
                        Student obj = new Student(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(6),rs.getString(7));
                        List.add(i, obj);
                        i++;
                    }
                }
                StudentTable.setItems(List);
            }
            con.close();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }

    @FXML
    void EditStudentButtonClick(ActionEvent event) throws IOException {
     try{   Student obj = StudentTable.getSelectionModel().getSelectedItem();
        Student.Sssn = obj.SSN;
        Student.SFirstName = obj.FirstName;
        Student.SLastName = obj.LastName;
        Student.SGender = obj.Gender;
        Student.SEmail = obj.Email;
        Student.SPhone = obj.Phone;
        Student.SAddress = obj.Address;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("editSudentManagerScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("The show circus school");
        stage.setScene(scene);
        stage.show();
    } catch (Exception ex){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Student Page");
        alert.setHeaderText(null);
        alert.setContentText("Select Student ! ");
        Stage stage2 = (Stage) alert.getDialogPane().getScene().getWindow();
        alert.showAndWait();
    }
    }

    @FXML
    void RefrechButtonClick(ActionEvent event) {
        CancelButtonClick(event);
    }

    @FXML
    void StudenAddressRadioButtonSelect(ActionEvent event) {

    }

       @FXML
    void StudenGenderRadioButtonSelect(ActionEvent event) {

    }

    @FXML
    void StudenNameRadioButtonSelect(ActionEvent event) {

    }

    @FXML
    void searchStudent(KeyEvent event) {

    }

}
