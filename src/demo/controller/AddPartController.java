package demo.controller;

import demo.model.InHouse;
import demo.model.Inventory;
import demo.model.Outsourced;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class AddPartController implements Initializable {

    public Inventory inventory;
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
    public Label machineIdOrCompanyNameLabel;

    /**
     * Constructor for the Add Part Controller
     * @param inventory the current state of the inventory
     */
    public AddPartController(Inventory inventory) {
        this.inventory = new Inventory();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        generateRandomId();
    }

    /**
     * Saves the new Part and transitions back to the Main View
     * @param actionEvent an ActionEvent Object
     */
    public void savePartButton(ActionEvent actionEvent) throws IOException {
        int id;
        String name;
        int inv;
        double priceCost;
        int max;
        int min;
        String machineIdOrCompanyName;

        // Check that TextFields are filled in
        if (!(nameTextField.getText().isEmpty()) && !(inventoryTextField.getText().isEmpty()) &&
                !(priceCostTextField.getText().isEmpty()) && !(maxTextField.getText().isEmpty()) &&
                !(minTextField.getText().isEmpty()) && !(machineIdOrCompanyNameTextField.getText().isEmpty())) {

            // Set variables to their respective TextField values
            id = Integer.parseInt(idTextField.getText());
            name = nameTextField.getText();
            inv = Integer.parseInt(inventoryTextField.getText());
            priceCost = Double.parseDouble(priceCostTextField.getText());
            max = Integer.parseInt(maxTextField.getText());
            min = Integer.parseInt(minTextField.getText());
            machineIdOrCompanyName = machineIdOrCompanyNameTextField.getText();

            if (inv > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error Adding New Part");
                alert.setContentText("The total number in inventory cannot exceed the upper bound (max) allowed in inventory");
                alert.showAndWait();
            }
            else if (inv < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error Adding New Part");
                alert.setContentText("The total number in inventory cannot be less than the lower bound (min) allowed in inventory");
                alert.showAndWait();
            }
            else if (min == max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error Adding New Part");
                alert.setContentText("The upper bound and lower bound allowed in inventory cannot be the same");
                alert.showAndWait();
            }
            else if (min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error Adding New Part");
                alert.setContentText("The lower bound allowed in inventory cannot exceed the upper bound");
                alert.showAndWait();
            }
            else if (max < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error Adding New Part");
                alert.setContentText("The upper bound allowed in inventory cannot be less than the lower bound");
                alert.showAndWait();
            }
            else {

                // Check which radio button is chosen, then add the part in accordance to the chosen radio button
                if (inHouseRadioButton.isSelected()) {
                    InHouse inHouse = new InHouse(id, name, priceCost, inv, min, max, Integer.parseInt(machineIdOrCompanyName));
                    inventory.addPart(inHouse);
                } else if (outsourcedRadioButton.isSelected()) {
                    Outsourced outsourced = new Outsourced(id, name, priceCost, inv, min, max, machineIdOrCompanyName);
                    inventory.addPart(outsourced);
                }

                // Transition back to Main View
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/demo/view/main-view.fxml"));
                MainController controller = new MainController(this.inventory);
                fxmlLoader.setController(controller);

                Parent root = fxmlLoader.load();
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 900, 400);
                stage.setTitle("Inventory Management System!");
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error Adding New Part");
            alert.setContentText("We ran into an issue trying to add your new part request. Please verify that all fields are filled in and try again.");
            alert.showAndWait();
        }
    }

    /**
     * Cancels the Add Part request and transitions back to the Main View
     * @param actionEvent an ActionEvent object
     * @throws IOException when the getResource method cannot find the fxml file to transition back to
     */
    public void onCancel(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/demo/view/main-view.fxml"));
        MainController controller = new MainController(this.inventory);
        //Parent root = FXMLLoader.load(getClass().getResource("/demo/view/main-view.fxml"));
        fxmlLoader.setController(controller);

        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 400);
        stage.setTitle("Inventory Management System!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Sets the text of the Machine ID label when the In House radio button is selected
     * @param mouseEvent a MouseEvent object
     */
    public void inHouseSelected(MouseEvent mouseEvent) {
        machineIdOrCompanyNameLabel.setText("Machine ID");
    }

    /**
     * Sets the text of the Company Name label when the Outsourced radio button is selected
     * @param mouseEvent a MouseEvent object
     */
    public void outsourcedSelected(MouseEvent mouseEvent) {
        machineIdOrCompanyNameLabel.setText("Company Name");
    }

    /**
     * Generates a random ID for the new Part
     * @return the random ID
     */
    private void generateRandomId() {
        Random randomNum = new Random();
        Integer num = randomNum.nextInt(1000);

        if (this.inventory.getAllParts().size() == 0 || this.inventory.lookupPart(num) == null) {
            idTextField.setText(num.toString());
        }
        else {
            generateRandomId();
        }
    }

}
