package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

public class DashboardFormController {
    public JFXButton btnRegister;
    public Label lblTime;
    public Label lblDate;
    public JFXButton btnLogin;
    public JFXButton btnCustomers;
    public JFXButton btnOrders;
    public JFXButton btnItems;
    public JFXButton btnPlaceOrder;
    public AnchorPane dashboard;

    public void LoginButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) dashboard.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/LoginForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void RegisterButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) dashboard.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/RegisterForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void CustomersButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) dashboard.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/CustomerForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void OrdersButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) dashboard.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/OrderForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ItemsButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) dashboard.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ItemsForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void PlaceOrderButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) dashboard.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/PlaceOrderForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
