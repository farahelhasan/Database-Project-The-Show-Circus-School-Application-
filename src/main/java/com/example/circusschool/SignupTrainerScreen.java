package com.example.circusschool;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class SignupTrainerScreen {

    @FXML
    private TextField BankAccountNumText;

    @FXML
    private RadioButton BoyRadioButton;

    @FXML
    private PasswordField ConfirmPasswordText;

    @FXML
    private TextField EmailText;

    @FXML
    private TextField FirstNameText;

    @FXML
    private RadioButton GirlRadioButton;

    @FXML
    private TextField HomeAddressText;

    @FXML
    private TextField LastNameText;

    @FXML
    private PasswordField PasswordText;

    @FXML
    private TextField PhoneNumText;

    @FXML
    private TextField SSNText;

    @FXML
    private TextField WorkAddressText;

    @FXML
    private Button SubmitButton;

    @FXML
    void BoyRadioButtonSelect(ActionEvent event) {
        ToggleGroup tgGirlBoy = new ToggleGroup();
        GirlRadioButton.setToggleGroup(tgGirlBoy);
        BoyRadioButton.setToggleGroup(tgGirlBoy);
    }

    @FXML
    void GirlRadioButtonSelect(ActionEvent event) {
        ToggleGroup tgGirlBoy = new ToggleGroup();
        GirlRadioButton.setToggleGroup(tgGirlBoy);
        BoyRadioButton.setToggleGroup(tgGirlBoy);
    }

    @FXML
    void SubmitButtonClick(ActionEvent event) throws IOException {
        try {
            //  String sqlstetment = "INSERT INTO student VALUES (" + SSNText.getText() + ",'" + FirstNameText.getText() + "','" + LastNameText.getText() + "','?','" + EmailText.getText() + "'," + PhonNumberText.getText() + ",'" + AddressText.getText() + "','" + PasswordText.getText() + "')";;

            if ((SSNText.getText().isEmpty() || FirstNameText.getText().isEmpty() || LastNameText.getText().isEmpty() || EmailText.getText().isEmpty() || PhoneNumText.getText().isEmpty() ||BankAccountNumText.getText().isEmpty()|| PasswordText.getText().isEmpty() || (WorkAddressText.getText().isEmpty() && HomeAddressText.getText().isEmpty()) || ConfirmPasswordText.getText().isEmpty()) || (!(GirlRadioButton.isSelected())&& !(BoyRadioButton.isSelected()))   ){
             //   JOptionPane.showMessageDialog(null, " You should enter the empty field :) ");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Sign Up DOESN'T COMPLETE!");
                alert.setHeaderText(null);
                alert.setContentText("Please enter the  the empty field to complete :) ");
                Stage stage2 = (Stage) alert.getDialogPane().getScene().getWindow();
                alert.showAndWait();

            } else if (!(PasswordText.getText().equals(ConfirmPasswordText.getText()))) {
              //  String ssn = SSNText.getText().trim();
              //  String fname = FirstNameText.getText().trim();
              //  String lname = LastNameText.getText().trim();
              //  String Haddress = HomeAddressText.getText().trim();
              //  String Waddress = WorkAddressText.getText().trim();
              //  String email = EmailText.getText().trim();
              //  String Bank = BankAccountNumText.getText().trim();
              //  String phone = PhoneNumText.getText().trim();

               // JOptionPane.showMessageDialog(null, "The password in Password field dosn't the same in ConfirmPassword fiald");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Sign Up DOESN'T COMPLETE!");
                alert.setHeaderText(null);
                alert.setContentText("The password in Password field doesn't the same in ConfirmPassword field , Try again! ");
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                alert.showAndWait();

              //  SSNText.setText(ssn) ;
              //  FirstNameText.setText(fname);
              //  LastNameText.setText(lname);
              //  HomeAddressText.setText(Haddress);
              //  WorkAddressText.setText(Waddress);
              //  EmailText.setText(email);
              //  BankAccountNumText.setText(Bank);
              //  PhoneNumText.setText(phone);
            } else {
                String sqlstetment = " ";
                if (GirlRadioButton.isSelected()) {
                     sqlstetment = "INSERT INTO employee VALUES (" + SSNText.getText().trim() + ",'" + FirstNameText.getText().trim() + "','" + LastNameText.getText().trim() + "','F','" + EmailText.getText().trim() + "'," + PhoneNumText.getText().trim() + ",'" + PasswordText.getText().trim() + "')";
                } else if (BoyRadioButton.isSelected()) {
                     sqlstetment = "INSERT INTO employee VALUES (" + SSNText.getText().trim() + ",'" + FirstNameText.getText().trim() + "','" + LastNameText.getText().trim() + "','M','" + EmailText.getText().trim() + "'," + PhoneNumText.getText().trim() + ",'" + PasswordText.getText().trim() + "')";
                }

                DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
                String url = "jdbc:oracle:thin:@localhost:1521:XE";
                Connection con = DriverManager.getConnection(url, "C##fawar", "654321");
                con.setAutoCommit(false);
                Statement stat = con.createStatement();
                // sqlstetment = "INSERT INTO trainer VALUES (166," + BankAccountNumText.getText() + " )";

                //  String sqlstetment = "INSERT INTO student VALUES (" + SSNText.getText() + ",'" + FirstNameText.getText() + "','" + LastNameText.getText() + "',gengerchar,'" + EmailText.getText() + "'," + PhonNumberText.getText() + ",'" + AddressText.getText() + "','" + PasswordText.getText() + "')";
                stat.executeUpdate(sqlstetment);
                con.commit();
               // con.close();


                 String sqlstetment2 = "INSERT INTO trainer VALUES (" + SSNText.getText().trim() + "," + BankAccountNumText.getText().trim() + " )";
                 stat.executeUpdate(sqlstetment2);
                 con.commit();

                 if(HomeAddressText.getText().trim().equalsIgnoreCase(WorkAddressText.getText().trim())){
                     String sqlstetment3 = "INSERT INTO address VALUES (" + SSNText.getText().trim() + ",'" + HomeAddressText.getText().trim() + "')";
                     stat.executeUpdate(sqlstetment3);
                     con.commit();
                 }else {
                     String sqlstetment3 = "INSERT INTO address VALUES (" + SSNText.getText().trim() + ",'"+ HomeAddressText.getText().trim() + " ')";
                     stat.executeUpdate(sqlstetment3);
                     con.commit();
                     String sqlstetment4 = "INSERT INTO address VALUES (" + SSNText.getText().trim() + ",'" + WorkAddressText.getText().trim() + " ')";
                     stat.executeUpdate(sqlstetment4);
                     con.commit();
                 }

                 con.close();

             //   JOptionPane.showMessageDialog(null, "Done :)");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sign Up");
                alert.setHeaderText(null);
                alert.setContentText("Sign Up DONE ! ");
                Stage stage2 = (Stage) alert.getDialogPane().getScene().getWindow();
                alert.showAndWait();
                SSNText.setText(" ");
                FirstNameText.setText(" ");
                LastNameText.setText(" ");
                BoyRadioButton.setSelected(false);
                GirlRadioButton.setSelected(false);
                EmailText.setText(" ");
                PhoneNumText.setText(" ");
                PasswordText.setText(" ");
                WorkAddressText.setText(" ");
                HomeAddressText.setText(" ");
                ConfirmPasswordText.setText(" ");
                BankAccountNumText.setText((" "));

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("welcomeScreen.fxml"));
                Parent root = fxmlLoader.load();
                // Scene scene = new Scene(fxmlLoader.load());
                Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                stage.setTitle("The show circus school");
                stage.setScene(new Scene(root));
                stage.show();

            }
        }catch(Exception ex){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Sign Up DOESN'T COMPLETE!");
            alert.setHeaderText(null);
            alert.setContentText("Please Check your DATA , the SSN or Email had already exist for some one else ");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            alert.showAndWait();
        }
    }


}

