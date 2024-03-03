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

public class TrainerManagerScreen implements Initializable {

    @FXML
    private Button BackButton;

    @FXML
    private Button CancelButton;

    @FXML
    private Button DeleteButton;

    @FXML
    private Button DisplayButton;

    @FXML
    private Button EditButton;


    @FXML
    private Button RefrechButton;

    @FXML
    private Label UsernameLable;


    @FXML
    private TextField SearchStudentText;

    @FXML
    private RadioButton TrainarSSNRadioButton;

    @FXML
    private RadioButton TrainerGenderRadioButton;

    @FXML
    private RadioButton TrainerNameRadioButton;

    @FXML
    private TableView<Trainer> TrainerTable;
    @FXML
    private TableColumn<Trainer, String> EmailTrainerColumn;

    @FXML
    private TableColumn<Trainer, String> FirstNameTrainerColumn;

    @FXML
    private TableColumn<Trainer, String> GenderTrainerColumn;
    @FXML
    private TableColumn<Trainer, Integer> SSNTrainerColumn;

    @FXML
    private TableColumn<Trainer, String> BankAccountTrainerColumn;
    @FXML
    private TableColumn<Trainer, String> LastNameTrainerColumn;

    @FXML
    private TableColumn<Trainer, Integer> PhoneTrainercolumn;

    ObservableList<Trainer> List = FXCollections.observableArrayList();

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
        SearchStudentText.setText(" ");
        UsernameLable.setText(User.email);
        ToggleGroup tgStudentTrainer = new ToggleGroup();
        TrainarSSNRadioButton.setToggleGroup(tgStudentTrainer);
        TrainerGenderRadioButton.setToggleGroup(tgStudentTrainer);
        TrainerNameRadioButton.setToggleGroup(tgStudentTrainer);


        //HomeAddressTrainerColumn.setCellValueFactory(new PropertyValueFactory<Trainer,String>("HAddress"));
        //  WorkAddressTrainerColumn.setCellValueFactory(new PropertyValueFactory<Trainer,String>("WAddress"));
        SSNTrainerColumn.setCellValueFactory(new PropertyValueFactory<Trainer,Integer>("SSN"));
        FirstNameTrainerColumn.setCellValueFactory(new PropertyValueFactory<Trainer,String>("FirstName"));
        LastNameTrainerColumn.setCellValueFactory(new PropertyValueFactory<Trainer,String>("LastName"));
        PhoneTrainercolumn.setCellValueFactory(new PropertyValueFactory<Trainer,Integer>("Phone"));
        GenderTrainerColumn.setCellValueFactory(new PropertyValueFactory<Trainer,String>("Gender"));
        EmailTrainerColumn.setCellValueFactory(new PropertyValueFactory<Trainer,String>("Email"));
        BankAccountTrainerColumn.setCellValueFactory(new PropertyValueFactory<Trainer,String>("BankAccount"));
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            String URL = "jdbc:oracle:thin:@localhost:1521:XE";
            Connection con = DriverManager.getConnection(URL, "C##fawar", "654321");
            con.setAutoCommit(false);
            Statement stat = con.createStatement();
            String sqlstetment = "select EMPLOYEESSN , FIRSTNAME ,LASTNAME,GENDER,EMAIL,PHONE,FINANCIALACCOUNT  from employee , trainer where  trainerssn = employeessn    ";
            ResultSet rs = stat.executeQuery(sqlstetment);

            int i =0;
            List.clear();
            while (rs.next()){
                Trainer obj = new Trainer(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(6),rs.getString(7));

                //  sqlstetment = "select FINANCIALACCOUNT from trainer where TRAINERSSN = "+obj.SSN+"";
                //  ResultSet rs2 = stat.executeQuery(sqlstetment);
                //  if (rs2.next()) {
                //     obj.BankAccount = rs2.getString(2);
                //}
              /*  sqlstetment = "select CITYNAME from trainer where ADDRESSSSN = "+obj.SSN+"";
                rs2 = stat.executeQuery(sqlstetment);
                if(rs2.next()){
                    obj.HAddress = rs2.getString(2);
                }
                if(rs.next()){
                    obj.WAddress = rs2.getString(2);
                }*/
                //  obj= editTheObjectWithAddressAndBankAccount(obj);
                List.add(i,obj);
                i++;
            }
            TrainerTable.setItems(List);

            con.commit();
            con.close();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }

    @FXML
    void DeleteButtonClick(ActionEvent event) {
        try{  Trainer obj = TrainerTable.getSelectionModel().getSelectedItem();
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            String URL = "jdbc:oracle:thin:@localhost:1521:XE";
            Connection con = DriverManager.getConnection(URL, "C##fawar", "654321");
            con.setAutoCommit(false);
            Statement stat = con.createStatement();
            String sqlstetment = "delete from employee where employeessn = "+obj.SSN+"";
            stat.executeUpdate(sqlstetment);
            con.commit();
            //JOptionPane.showMessageDialog(null, "delete done");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Trainer Page");
            alert.setHeaderText(null);
            alert.setContentText("DELETE DONE ! ");
            Stage stage2 = (Stage) alert.getDialogPane().getScene().getWindow();
            alert.showAndWait();
            con.close();
        }catch(Exception ex){
            //JOptionPane.showMessageDialog(null, ex.toString());
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Trainer Page");
            alert.setHeaderText(null);
            alert.setContentText("Select Trainer who want to delete it  ! ");
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
            sqlstetment = "select EMPLOYEESSN , FIRSTNAME ,LASTNAME,GENDER,EMAIL,PHONE,FINANCIALACCOUNT from employee , trainer where  trainerssn = employeessn   ";
             ResultSet rs = stat.executeQuery(sqlstetment);
            if(TrainerNameRadioButton.isSelected()) {
                while (rs.next()) {
                    String  SearchStudent= SearchStudentText.getText().trim().toLowerCase();
                    String FirstNFromDataBasse =rs.getString(2).toLowerCase();
                    String LastNFromDataBasse = rs.getString(3).toLowerCase();
                    if ( (SearchStudent.contains(FirstNFromDataBasse) && SearchStudent.contains(LastNFromDataBasse) )||( FirstNFromDataBasse.contains(SearchStudent) && LastNFromDataBasse.contains(SearchStudent))){
                        Trainer obj = new Trainer(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(6),rs.getString(7));
                        List.add(i, obj);
                        i++;
                    }else if( (SearchStudent.contains(FirstNFromDataBasse) ) || (FirstNFromDataBasse.contains(SearchStudent)) ){
                        Trainer obj = new Trainer(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(6),rs.getString(7));
                        List.add(i, obj);
                        i++;
                    }else if ((SearchStudent.contains(LastNFromDataBasse)) || (LastNFromDataBasse.contains(SearchStudent))){
                        Trainer obj = new Trainer(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(6),rs.getString(7));
                        List.add(i, obj);
                        i++;
                    }
                }
                TrainerTable.setItems(List);
            }else if(TrainerGenderRadioButton.isSelected()) {
                while (rs.next()) {
                    String  SearchStudent= SearchStudentText.getText().trim().toLowerCase();
                    String GenderFromDataBasse =rs.getString(4).toLowerCase();
                    if(SearchStudent.equalsIgnoreCase(GenderFromDataBasse) ){
                        Trainer obj = new Trainer(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(6),rs.getString(7));
                        List.add(i, obj);
                        i++;
                    }
                }
                TrainerTable.setItems(List);
            }else if(TrainarSSNRadioButton.isSelected()) {
                while (rs.next()) {
                    String  SearchStudent= SearchStudentText.getText().trim()
                            .toLowerCase();
                    String SSNFromDataBasse =rs.getString(1).toLowerCase();
                    if((SearchStudent.contains(SSNFromDataBasse) ) || (SSNFromDataBasse.contains(SearchStudent))){
                        Trainer obj = new Trainer(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(6),rs.getString(7));
                        List.add(i, obj);
                        i++;
                    }
                }
                TrainerTable.setItems(List);
            }
            con.close();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.toString());
        }

    }

    @FXML
    void EditButtonClick(ActionEvent event) throws IOException {
        try {Trainer obj = TrainerTable.getSelectionModel().getSelectedItem();
        Trainer.SFirstName = obj.FirstName;
        Trainer.SLastName = obj.LastName;
        Trainer.SGender = obj.Gender;
        Trainer.SPhone = obj.Phone;
        Trainer.Sssn = obj.SSN;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("editTrainerManagerScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("The show circus school");
        stage.setScene(scene);
        stage.show();} catch (Exception ex){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Trainer Page");
            alert.setHeaderText(null);
            alert.setContentText("Select Trainer ! ");
            Stage stage2 = (Stage) alert.getDialogPane().getScene().getWindow();
            alert.showAndWait();
        }
    }

    @FXML
    void RefrechButtonClick(ActionEvent event) {
        CancelButtonClick(event);
    }


    @FXML
    void searchStudent(KeyEvent event) {

    }
    @Override
    public void initialize (URL url , ResourceBundle rb){
        Color paint = new Color(0.9373, 0.5255, 0.3059, 1.0);
        UsernameLable.setTextFill(paint);
        UsernameLable.setText(User.email);
        ToggleGroup tgStudentTrainer = new ToggleGroup();
        TrainarSSNRadioButton.setToggleGroup(tgStudentTrainer);
        TrainerGenderRadioButton.setToggleGroup(tgStudentTrainer);
        TrainerNameRadioButton.setToggleGroup(tgStudentTrainer);


        //HomeAddressTrainerColumn.setCellValueFactory(new PropertyValueFactory<Trainer,String>("HAddress"));
      //  WorkAddressTrainerColumn.setCellValueFactory(new PropertyValueFactory<Trainer,String>("WAddress"));
        SSNTrainerColumn.setCellValueFactory(new PropertyValueFactory<Trainer,Integer>("SSN"));
        FirstNameTrainerColumn.setCellValueFactory(new PropertyValueFactory<Trainer,String>("FirstName"));
        LastNameTrainerColumn.setCellValueFactory(new PropertyValueFactory<Trainer,String>("LastName"));
        PhoneTrainercolumn.setCellValueFactory(new PropertyValueFactory<Trainer,Integer>("Phone"));
        GenderTrainerColumn.setCellValueFactory(new PropertyValueFactory<Trainer,String>("Gender"));
        EmailTrainerColumn.setCellValueFactory(new PropertyValueFactory<Trainer,String>("Email"));
        BankAccountTrainerColumn.setCellValueFactory(new PropertyValueFactory<Trainer,String>("BankAccount"));
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            String URL = "jdbc:oracle:thin:@localhost:1521:XE";
            Connection con = DriverManager.getConnection(URL, "C##fawar", "654321");
            con.setAutoCommit(false);
            Statement stat = con.createStatement();
            String sqlstetment = "select EMPLOYEESSN , FIRSTNAME ,LASTNAME,GENDER,EMAIL,PHONE,FINANCIALACCOUNT  from employee , trainer where  trainerssn = employeessn    ";
            ResultSet rs = stat.executeQuery(sqlstetment);

            int i =0,flag = 0;
            List.clear();
            while (rs.next()){
                Trainer obj = new Trainer(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(6),rs.getString(7));

               //  sqlstetment = "select FINANCIALACCOUNT from trainer where TRAINERSSN = "+obj.SSN+"";
              //  ResultSet rs2 = stat.executeQuery(sqlstetment);
              //  if (rs2.next()) {
               //     obj.BankAccount = rs2.getString(2);
                //}
              /*  sqlstetment = "select CITYNAME from trainer where ADDRESSSSN = "+obj.SSN+"";
                rs2 = stat.executeQuery(sqlstetment);
                if(rs2.next()){
                    obj.HAddress = rs2.getString(2);
                }
                if(rs.next()){
                    obj.WAddress = rs2.getString(2);
                }*/
                //  obj= editTheObjectWithAddressAndBankAccount(obj);
                List.add(i,obj);
                i++;
            }
            TrainerTable.setItems(List);

            con.commit();
            con.close();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.toString()
            );
        }

        //Student obj =  new Student(111,"DD","D","F","FHFHD",121,"JF");
    }

    }


