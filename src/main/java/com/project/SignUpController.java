package com.project;

import com.project.Models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class SignUpController {

    @FXML
    TextField fieldName;
    @FXML
    TextField fieldEmail;
    @FXML
    TextField fieldPassword;
    @FXML
    TextField fieldConfirmPassword;

    public void goToLogin(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void submit(ActionEvent actionEvent) throws IOException {
        if(fieldPassword.getText().equals(fieldConfirmPassword.getText()))
        {
            AppQuery query = new AppQuery();
            User user = new User(fieldName.getText(), fieldEmail.getText(), fieldPassword.getText());
            query.signUpQuery(user);
            goToLogin(actionEvent);
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Hello, World!");

            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(okButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == okButton){
                alert.close();
            }
        }
    }
}
