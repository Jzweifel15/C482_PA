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
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
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
    public TextField minTextField;

    /**
     * Constructor for the Modify Part Controller
     * @param inventory the current state of the inventory
     * @param part the selected Part instance to be modified
     */
    public ModifyPartController(Inventory inventory, Part part) {
        this.inventory = inventory;
        this.part = part;
    }

    /**
     * Check the type (InHouse or Outsourced) that the selected Part is, then set the TextFields with their respective, corresponding data
     * @param location
     * @param resources
     */
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

    /**
     * Save changes made to the selected Part instance and transition back to the Main View
     * @param actionEvent an ActionEvent object
     * @throws IOException when the getResource method cannot find the fxml file to transition back to
     */
    public void saveModificationToPart(ActionEvent actionEvent) throws IOException {
        int index = this.inventory.getAllParts().indexOf(this.part);
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

            try {
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
                    alert.setHeaderText("Error Modifying Part");
                    alert.setContentText("The total number in inventory cannot exceed the upper bound (max) allowed in inventory");
                    alert.showAndWait();
                } else if (inv < min) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error Modifying Part");
                    alert.setContentText("The total number in inventory cannot be less than the lower bound (min) allowed in inventory");
                    alert.showAndWait();
                } else if (min == max) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error Modifying Part");
                    alert.setContentText("The upper bound and lower bound allowed in inventory cannot be the same");
                    alert.showAndWait();
                } else if (min > max) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error Modifying Part");
                    alert.setContentText("The lower bound allowed in inventory cannot exceed the upper bound");
                    alert.showAndWait();
                } else if (max < min) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error Modifying Part");
                    alert.setContentText("The upper bound allowed in inventory cannot be less than the lower bound");
                    alert.showAndWait();
                } else {

                    // Check which radio button is chosen, then add the part in accordance to the chosen radio button
                    if (inHouseRadioButton.isSelected()) {
                        InHouse inHouse = new InHouse(id, name, priceCost, inv, min, max, Integer.parseInt(machineIdOrCompanyName));
                        inventory.updatePart(index, inHouse);
                    } else if (outsourcedRadioButton.isSelected()) {
                        Outsourced outsourced = new Outsourced(id, name, priceCost, inv, min, max, machineIdOrCompanyName);
                        inventory.updatePart(index, outsourced);
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
            catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error Modifying Part");
                alert.setContentText("We ran into an issue while performing your Part modification request. Please verify that all fields are filled in correctly and try again.");
                alert.showAndWait();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error Modifying Part");
            alert.setContentText("We ran into an issue while performing your Part modification request. Please verify that all fields are filled in and try again.");
            alert.showAndWait();
        }
    }

    /**
     * Cancel changes made to the selected Part instance and transition back to Main View
     * @param actionEvent an action event object
     * @throws IOException when the getResource method cannot find the fxml file to transition back to
     */
    public void onCancel(ActionEvent actionEvent) throws IOException {
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
}
