package com.example.circusschool;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;

public class SignupScreen {

        @FXML
        private Button Back;

        @FXML
        private Button Next;

        @FXML
        private RadioButton StudentRadioButton;

        @FXML
        private RadioButton TrainerRadioButton;

        @FXML
        void BackClick(ActionEvent event) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("welcomeScreen.fxml"));
            Parent root = fxmlLoader.load();
            // Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("The show circus school");
            stage.setScene(new Scene(root));
            stage.show();
        }

        @FXML
        void NextClick(ActionEvent event) throws IOException {

            if (StudentRadioButton.isSelected()) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("signupStudentScreen.fxml"));
                Parent root = fxmlLoader.load();
                // Scene scene = new Scene(fxmlLoader.load());
                Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                stage.setTitle("The show circus school");
                stage.setScene(new Scene(root));
                stage.show();

            } else if (TrainerRadioButton.isSelected()) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("signupTrainerScreen.fxml"));
                Parent root = fxmlLoader.load();
                // Scene scene = new Scene(fxmlLoader.load());
                Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                stage.setTitle("The show circus school");
                stage.setScene(new Scene(root));
                stage.show();

            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("You Dont Select Thing !");
                alert.setHeaderText(null);
                alert.setContentText("Please select Student OR Trainer to complete sign up ");
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();

                alert.showAndWait();

            }

        }

        @FXML
        void StudentSelect(ActionEvent event) {
            ToggleGroup tgStudentTrainer = new ToggleGroup();
            StudentRadioButton.setToggleGroup(tgStudentTrainer);
            TrainerRadioButton.setToggleGroup(tgStudentTrainer);
        }

        @FXML
        void TrainerSelect(ActionEvent event) {
            ToggleGroup tgStudentTrainer = new ToggleGroup();
            StudentRadioButton.setToggleGroup(tgStudentTrainer);
            TrainerRadioButton.setToggleGroup(tgStudentTrainer);
        }


      /*  void SubmitClike(ActionEvent event) throws SQLException {
            try {


                DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
                String url = "jdbc:oracle:thin:@localhost:1521:XE";
                Connection con = DriverManager.getConnection(url, "C##fawar", "654321");
                con.setAutoCommit(false);
                Statement stat = con.createStatement();


                String sqlstetment = "INSERT INTO employee VALUES (" + ssnText.getText() + ",'" + firstNameText.getText() + "','" + firstNameText.getText() + "','F','" + emailText.getText() + "'," + PhoneNumText.getText() + ",'" + passwordText.getText() + "')";
                stat.executeUpdate(sqlstetment);
                con.commit();
                con.close();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.toString());
            }
        }*/
    }
    


