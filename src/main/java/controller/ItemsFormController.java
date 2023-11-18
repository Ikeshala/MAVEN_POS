package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
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
import model.Item;
import model.tm.itemTm;

import java.io.IOException;
import java.sql.*;

public class ItemsFormController {
    public Label lblDate;
    public Label lblTime;
    public JFXTextField txtCode;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQuantity;
    public JFXButton btnSave;
    public JFXButton btnUpdate;
    public TableView<itemTm> tblItems;
    public JFXButton btnReload;
    public JFXButton btnBack;
    public AnchorPane itemsPane;
    public JFXTextField txtDescription;
    public TableColumn colCode;
    public TableColumn colDescription;
    public TableColumn colQuantity;
    public TableColumn colUnitPrice;
    public TableColumn colOption;

    public void initialize(){
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));

        loadItemTable();
        tblItems.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            setData(newValue);
        });
    }

    private void setData(itemTm newValue) {
        if(newValue != null){
            txtCode.setEditable(false);
            txtCode.setText(newValue.getCode());
            txtDescription.setText(newValue.getDescription());
            txtUnitPrice.setText(String.valueOf(newValue.getUnitPrice()));
            txtQuantity.setText(String.valueOf(newValue.getQtyOnHand()));
        }
    }

    private void loadItemTable() {
        ObservableList<itemTm> tmList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM item";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/thogakade","root","1993");
            Statement stm = connection.createStatement();
            ResultSet result = stm.executeQuery(sql);
            while (result.next()){
                JFXButton btn = new JFXButton("Delete");
                itemTm itemTm = new itemTm(
                        result.getString(1),
                        result.getString(2),
                        result.getDouble(3),
                        result.getInt(4),
                        btn
                );
                btn.setOnAction(actionEvent -> {
                    deleteItem(itemTm.getCode());
                });
                
                tmList.add(itemTm);
            }
            connection.close();
            tblItems.setItems(tmList);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteItem(String code) {
        String sql = "DELETE FROM item WHERE code = '"+code+"'";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/thogakade", "root", "1993");
            Statement stm = connection.createStatement();
            int result = stm.executeUpdate(sql);
            if (result>0){
                new Alert(Alert.AlertType.INFORMATION,"Item Deleted!").show();
                loadItemTable();
            }else {
                new Alert(Alert.AlertType.WARNING,"Something went wrong!").show();
            }
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void BackButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) itemsPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashboardForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ReloadButtonOnAction(ActionEvent actionEvent) {
        loadItemTable();
        tblItems.refresh();
        clearFields();
    }

    private void clearFields() {
        tblItems.refresh();
        txtCode.clear();
        txtDescription.clear();
        txtUnitPrice.clear();
        txtQuantity.clear();
        txtCode.setEditable(true);
    }

    public void SaveButtonOnAction(ActionEvent actionEvent) {
        Item item = new Item(txtCode.getText(),
                txtDescription.getText(),
                Double.parseDouble(txtUnitPrice.getText()),
                Integer.parseInt(txtQuantity.getText())
        );
        String sql = "INSERT INTO item VALUES('"+item.getCode()+"','"+item.getDescription()+"','"+item.getUnitPrice()+"',"+item.getQtyOnHand()+")";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/thogakade","root","1993");
            Statement stm = connection.createStatement();
            int result = stm.executeUpdate(sql);
            if (result>0){
                new Alert(Alert.AlertType.INFORMATION,"Item Saved!").show();
                loadItemTable();
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
        Item item = new Item(txtCode.getText(),
                txtDescription.getText(),
                Double.parseDouble(txtUnitPrice.getText()),
                Integer.parseInt(txtQuantity.getText())
        );
        String sql = "UPDATE item SET description = '"+item.getDescription()+"', unitPrice = "+item.getUnitPrice()+", qtyOnHand = "+item.getQtyOnHand()+" WHERE code ='"+item.getCode()+"'";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/thogakade", "root", "1993");
            Statement stm = connection.createStatement();
            int result = stm.executeUpdate(sql);
            if (result>0){
                new Alert(Alert.AlertType.INFORMATION,"Item "+item.getCode()+" Updated!").show();
                loadItemTable();
                clearFields();
            }
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
