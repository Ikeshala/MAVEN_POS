package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {
    public JFXTextField txtUsername;
    public JFXTextField txtPassword;
    public JFXButton btnLogin;
    public JFXButton btnSignUp;
    public JFXTextField txtUsername1;
    public JFXButton btnBack;
    public AnchorPane loginPane;

    public void LoginButtonOnAction(ActionEvent actionEvent) {
    }

    public void SignUpButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) loginPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/RegisterForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void BackButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) loginPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashboardForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
