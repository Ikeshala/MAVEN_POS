package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Customer;
import model.tm.customerTm;

import java.io.IOException;
import java.sql.*;

public class CustomerFormController {
    public Label lblDate;
    public Label lblTime;
    public JFXTextField txtCustomerID;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerAddress;
    public JFXTextField txtCustomerSalary;
    public JFXButton btnSave;
    public JFXButton btnUpdate;
    public JFXButton btnReload;
    public JFXButton btnBack;
    public AnchorPane customersPane;
    public TableColumn colCustomerID;
    public TableColumn colCustomerName;
    public TableColumn colCustomerAddress;
    public TableColumn colCustomerSalary;
    public TableColumn colOption;
    public TableView<customerTm> tblCustomers;

    public void initialize(){
        colCustomerID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCustomerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCustomerSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));

        loadCustomerTable();
        tblCustomers.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            setData(newValue);
        });
    }

    private void setData(customerTm newValue) {
        if(newValue != null){
            txtCustomerID.setEditable(false);
            txtCustomerID.setText(newValue.getId());
            txtCustomerName.setText(newValue.getName());
            txtCustomerAddress.setText(newValue.getAddress());
            txtCustomerSalary.setText(String.valueOf(newValue.getSalary()));
        }
    }

    private void loadCustomerTable() {
        ObservableList<customerTm> tmList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM customer";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/thogakade", "root", "1993");
            Statement stm = connection.createStatement();
            ResultSet result = stm.executeQuery(sql);
            while (result.next()){
                JFXButton btn = new JFXButton("Delete");
                customerTm custTm = new customerTm(
                        result.getString(1),
                        result.getString(2),
                        result.getString(3),
                        result.getDouble(4),
                        btn
                );
                btn.setOnAction(actionEvent -> {
                    deleteCustomer(custTm.getId());
                });

                tmList.add(custTm);
            }
            connection.close();
            tblCustomers.setItems(tmList);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteCustomer(String id) {
        String sql = "DELETE FROM customer WHERE id = '"+id+"'";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/thogakade", "root", "1993");
            Statement stm = connection.createStatement();
            int result = stm.executeUpdate(sql);
            if (result>0){
                new Alert(Alert.AlertType.INFORMATION,"Customer Deleted!").show();
                loadCustomerTable();
            }else {
                new Alert(Alert.AlertType.WARNING,"Something went wrong!").show();
            }
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void BackButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) customersPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashboardForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ReloadButtonOnAction(ActionEvent actionEvent) {
        loadCustomerTable();
        tblCustomers.refresh();
        clearFields();
    }

    private void clearFields() {
        tblCustomers.refresh();
        txtCustomerID.clear();
        txtCustomerName.clear();
        txtCustomerAddress.clear();
        txtCustomerSalary.clear();
        txtCustomerID.setEditable(true);
    }

    public void SaveButtonOnAction(ActionEvent actionEvent) {
        Customer customer = new Customer(txtCustomerID.getText(),
                txtCustomerName.getText(),
                txtCustomerAddress.getText(),
                Double.parseDouble(txtCustomerSalary.getText())
        );
        String sql = "INSERT INTO customer VALUES('"+customer.getId()+"','"+customer.getName()+"','"+customer.getAddress()+"',"+customer.getSalary()+")";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/thogakade", "root", "1993");
            Statement stm = connection.createStatement();
            int result = stm.executeUpdate(sql);
            if (result>0){
                new Alert(Alert.AlertType.INFORMATION,"Customer Saved!").show();
                loadCustomerTable();
                clearFields();
            }
            connection.close();
        } catch (SQLIntegrityConstraintViolationException ex){
            new Alert(Alert.AlertType.ERROR,"Duplicate Entry!").show();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void UpdateButtonOnAction(ActionEvent actionEvent) {
        Customer customer = new Customer(txtCustomerID.getText(),
                txtCustomerName.getText(),
                txtCustomerAddress.getText(),
                Double.parseDouble(txtCustomerSalary.getText())
        );
        String sql = "UPDATE customer SET name = '"+customer.getName()+"', address = '"+customer.getAddress()+"', salary = "+customer.getSalary()+" WHERE id ='"+customer.getId()+"'";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/thogakade", "root", "1993");
            Statement stm = connection.createStatement();
            int result = stm.executeUpdate(sql);
            if (result>0){
                new Alert(Alert.AlertType.INFORMATION,"Customer "+customer.getId()+" Updated!").show();
                loadCustomerTable();
                clearFields();
            }
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
