package demo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ModifyPartController implements Initializable {


    public RadioButton inHouseRadioButton;
    public RadioButton outsourcedRadioButton;
    public TextField idTextField;
    public TextField nameTextField;
    public TextField inventoryTextField;
    public TextField priceCostTextField;
    public TextField maxTextField;
    public TextField machineIdOrCompanyNameTextField;
    public Label minLabel;
    public TextField minTextField;
    public Button savePartButton;
    public Button CancelPartButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) { }

    public void onCancel(ActionEvent actionEvent) {
    }
}
