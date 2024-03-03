package com.example.circusschool;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import oracle.jdbc.driver.OracleDriver;

import javax.swing.*;
import java.io.IOException;
import java.sql.*;

public class LoginScreen {

    @FXML
    private Button Back;

    @FXML
    private Button ForgitPasswordButton;

    @FXML
    private Label MassageForWrongEmailandPassword;

    @FXML
    private Label MassageForWrongPassword;

    @FXML
    private Button LoginButton;

    @FXML
    private TextField emailText;

    @FXML
    private PasswordField passwordText;

    @FXML
    void BackClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("welcomeScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("The show circus school");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void loginButtonClick(ActionEvent event) throws SQLException, IOException {
        int flag =0 ;
        String type = " ";
        if (passwordText.getText().isEmpty() || emailText.getText().isEmpty()) {
            Color paint = new Color(0.851, 0.0549, 0.0549, 1.0);
            MassageForWrongEmailandPassword.setText("inter your email & password");
            MassageForWrongEmailandPassword.setTextFill(paint);
            MassageForWrongEmailandPassword.setVisible(true);
            MassageForWrongPassword.setVisible(false);
        } else {
            try {
                DriverManager.registerDriver(new OracleDriver());
                String url = "jdbc:oracle:thin:@localhost:1521:XE";
                Connection con = DriverManager.getConnection(url, "C##fawar", "654321");
                con.setAutoCommit(false);
                Statement stat = con.createStatement();
                String sqlstetment = "select * from users";
                ResultSet rs = stat.executeQuery(sqlstetment);
                String email = emailText.getText().trim();
                String password = passwordText.getText().trim();
                //sqlstetment = "select email ,password from employee where employeessn = "+ssnManagerint+"";
                //   rs = stat.executeQuery(sqlstetment);
                while (rs.next()) {
                    if (rs.getString(1).equals(email) && rs.getString(2).equals(password)) {
                        flag = 2;
                        if (rs.getString(3).equals("manager")) {
                            MassageForWrongEmailandPassword.setVisible(false);
                            MassageForWrongPassword.setVisible(false);
                            User.email = emailText.getText();
                            type = "manager";


                        } else if (rs.getString(3).equals("trainer")) {
                            MassageForWrongEmailandPassword.setVisible(false);

                            MassageForWrongPassword.setVisible(false);
                            User.email = emailText.getText();
                            type = "trainer";
                        } else if (rs.getString(3).equals("student")) {
                            MassageForWrongEmailandPassword.setVisible(false);
                            MassageForWrongPassword.setVisible(false);
                            User.email = emailText.getText();
                            type = "student";
                        }
                    } else if ((email.equals(rs.getString(1))) && !(password.equals(rs.getString(2)))) {

                        flag =1;
                    }

                }
                con.close();
                if(flag == 0){
                    Color paint = new Color(0.851, 0.0549, 0.0549, 1.0);
                    MassageForWrongEmailandPassword.setTextFill(paint);
                    MassageForWrongEmailandPassword.setText("Couldn't find your email, please Try again !");
                    MassageForWrongEmailandPassword.setVisible(true);
                    MassageForWrongPassword.setVisible(false);
                    passwordText.clear();
                    emailText.clear();
                }else if(flag == 1){
                    Color paint = new Color(0.851, 0.0549, 0.0549, 1.0);
                    MassageForWrongPassword.setTextFill(paint);
                    MassageForWrongEmailandPassword.setVisible(false);
                    MassageForWrongPassword.setVisible(true);
                    passwordText.clear();
                }else if (flag == 2){
                    if (type.equalsIgnoreCase("manager")){
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("welcomeManagerScreen.fxml"));
                        Parent root = fxmlLoader.load();
                        // Scene scene = new Scene(fxmlLoader.load());
                        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                        stage.setTitle("The show circus school");
                        stage.setScene(new Scene(root));
                        stage.show();
                    }else if(type.equalsIgnoreCase("trainer")){
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("trainerPage.fxml"));
                        Parent root = fxmlLoader.load();
                        // Scene scene = new Scene(fxmlLoader.load());
                        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                        stage.setTitle("The show circus school");
                        stage.setScene(new Scene(root));
                        stage.show();

                    }else if(type.equalsIgnoreCase("student")){
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("studentPage.fxml"));
                        Parent root = fxmlLoader.load();
                        // Scene scene = new Scene(fxmlLoader.load());
                        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                        stage.setTitle("The show circus school");
                        stage.setScene(new Scene(root));
                        stage.show();
                    }
                }
                //con.commit();
               // con.close();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.toString());
            }

        }
    }
    @FXML
    void ForgitPasswordButtonClick(ActionEvent event) {

    }



}




