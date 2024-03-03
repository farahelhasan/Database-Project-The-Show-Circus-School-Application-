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
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeManagerScreen implements Initializable {

    @FXML
    private Button CoursesButton;

    @FXML
    private Button EquipmentButton;

    @FXML
    private Button LogoutButton;

    @FXML
    private Button StudentButton;

    @FXML
    private Button TrainersButton;

    @FXML
    private Label UsernameLable;

    @Override
    public void initialize (URL url , ResourceBundle rb){
        Color paint = new Color(0.9373, 0.5255, 0.3059, 1.0);
        UsernameLable.setTextFill(paint);
        UsernameLable.setText(User.email);
    }

    @FXML
    void CoursesButtonClick(ActionEvent event) throws IOException {
        User.email = UsernameLable.getText();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("courseManagerScreen.fxml"));
        Parent root = fxmlLoader.load();
       // Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("The show circus school");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void EquipmentButtonClick(ActionEvent event) throws IOException {
        User.email = UsernameLable.getText();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("equipmentManagerScreen.fxml"));
        Parent root = fxmlLoader.load();
        // Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("The show circus school");
        stage.setScene(new Scene(root));
        stage.show();
    }


    @FXML
    void StudentButtonClick(ActionEvent event) throws IOException {
        User.email = UsernameLable.getText();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("studentManagerScreen.fxml"));
        Parent root = fxmlLoader.load();
        // Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("The show circus school");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void TrainersButtonClick(ActionEvent event) throws IOException {
        User.email = UsernameLable.getText();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("trainerManagerScreen.fxml"));
        Parent root = fxmlLoader.load();
        // Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("The show circus school");
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    void LogoutButtonClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("welcomeScreen.fxml"));
        Parent root = fxmlLoader.load();
        // Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("The show circus school");
        stage.setScene(new Scene(root));
        stage.show();
    }


}
