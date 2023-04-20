package demo.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AddPartController implements Initializable {

    public AnchorPane addPartFormPane;
    public RadioButton inHouseRadioButton;
    public RadioButton outsourcedRadioButton;
    public TextField idTextField;
    public TextField nameTextField;
    public TextField inventoryTextField;
    public TextField priceCostTextField;
    public TextField maxTextField;
    public TextField machineIdOrCompanyNameTextField;
    public TextField minTextField;
    public Button savePartButton;
    public Button CancelPartButton;

    public AddPartController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

}
