package demo.controller;

import demo.model.InHouse;
import demo.model.Inventory;
import demo.model.Outsourced;
import demo.model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyPartController implements Initializable {

    private Inventory inventory;
    private Part part;

    public RadioButton inHouseRadioButton;
    public RadioButton outsourcedRadioButton;
    public TextField idTextField;
    public TextField nameTextField;
    public TextField inventoryTextField;
    public TextField priceCostTextField;
    public TextField maxTextField;
    public Label machineIdOrCompanyNameLabel;
    public TextField machineIdOrCompanyNameTextField;
    public Label minLabel;
    public TextField minTextField;
    public Button savePartButton;
    public Button CancelPartButton;

    public ModifyPartController(Inventory inventory, Part part) {
        this.inventory = inventory;
        this.part = part;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (this.part instanceof InHouse) {
            InHouse p = (InHouse) part;
            inHouseRadioButton.setSelected(true);
            machineIdOrCompanyNameLabel.setText("Machine ID");
            nameTextField.setText(p.getName());
            idTextField.setText((Integer.toString(p.getId())));
            inventoryTextField.setText((Integer.toString(p.getStock())));
            priceCostTextField.setText((Double.toString(p.getPrice())));
            minTextField.setText((Integer.toString(p.getMin())));
            maxTextField.setText((Integer.toString(p.getMax())));
            machineIdOrCompanyNameTextField.setText((Integer.toString(p.getMachineId())));
        }
        else if (this.part instanceof Outsourced) {
            Outsourced p = (Outsourced) part;
            outsourcedRadioButton.setSelected(true);
            machineIdOrCompanyNameLabel.setText("Company Name");
            nameTextField.setText(p.getName());
            idTextField.setText((Integer.toString(p.getId())));
            inventoryTextField.setText((Integer.toString(p.getStock())));
            priceCostTextField.setText((Double.toString(p.getPrice())));
            minTextField.setText((Integer.toString(p.getMin())));
            maxTextField.setText((Integer.toString(p.getMax())));
            machineIdOrCompanyNameTextField.setText(p.getCompanyName());
        }
    }

    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/demo/view/main-view.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 400);
        stage.setTitle("Inventory Management System!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
