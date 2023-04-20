package demo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AddPartController implements Initializable {

    public AnchorPane addPartFormPane;
    public RadioButton inHouseRadioButton;      // isSelected() is the method used for checking if a radio button is selected
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

    /**
     * The action event that is triggered when the user tries to save a new Part
     * @param actionEvent an event Object
     */
    public void savePartButton(ActionEvent actionEvent) {
    }

    public void onCancelButton(ActionEvent actionEvent) {
    }
}
