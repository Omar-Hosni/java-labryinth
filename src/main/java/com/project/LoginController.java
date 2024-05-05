package com.project;

import com.project.AppQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginController{

    @FXML
    TextField fieldEmail;
    @FXML
    TextField fieldPassword;

    @FXML
    public void goToSignup(ActionEvent actionEvent) throws IOException{
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    public void submit(ActionEvent actionEvent) throws IOException {
        AppQuery query = new AppQuery();
        query.loginUserQuery(actionEvent, fieldEmail.getText(), fieldPassword.getText());

    }
}
