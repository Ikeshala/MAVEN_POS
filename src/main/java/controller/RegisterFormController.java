package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterFormController {
    public JFXTextField txtFirstName;
    public JFXTextField txtLastName;
    public JFXTextField txtUserName;
    public JFXTextField txtContactNumber;
    public JFXTextField txtEmail;
    public JFXTextField txtPassword;
    public JFXTextField txtConfirmPassword;
    public JFXButton btnSignUp;
    public AnchorPane customerPane;
    public JFXButton btnBack;

    public void SignUpButtonOnAction(ActionEvent actionEvent) {
    }

    public void BackButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) btnBack.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashboardForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
