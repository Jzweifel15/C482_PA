package demo.controller;

import demo.model.Inventory;
import demo.model.Part;
import demo.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyProductController implements Initializable {

    private Inventory inventory;
    private Product product;
    private ObservableList<Part> allParts;
    private ObservableList<Part> associatedParts;

    public TextField nameTextField;
    public TextField idTextField;
    public TextField inventoryTextField;
    public TextField priceCostTextField;
    public TextField maxTextField;
    public TextField searchPartTextField;
    public TextField minTextField;

    public TableView partTableView1;
    public TableColumn partIdTable1Column;
    public TableColumn partNameTable1Column;
    public TableColumn inventoryLevelTable1Column;
    public TableColumn pricePerUnitTable1Column;

    public TableView partTableView2;
    public TableColumn partIdTable2Column;
    public TableColumn partNameTable2Column;
    public TableColumn inventoryLevelTable2Column;
    public TableColumn pricePerUnitTable2Column;

    public Button addAssociatedPartButton;
    public Button removeAssociatedPartButton;
    public Button saveProductButton;
    public Button cancelProductButton;

    /**
     * Constructor for the Modify Product Controller
     * @param inventory the current state of the inventory
     * @param product the selected Product instance to be modified
     */
    public ModifyProductController(Inventory inventory, Product product) {
        this.inventory = inventory;
        this.product = product;

        // Copy lists to new ones as to not change the originals
        this.allParts = FXCollections.observableArrayList(inventory.getAllParts());
        this.associatedParts = FXCollections.observableArrayList(product.getAssociatedParts());

        // Add the remaining Parts that are not associated with the passed Product
        // instance to the allParts List for the top TableView (All Parts)
        for (int i = 0; i < allParts.size(); i++) {
            for (int j = 0; j < associatedParts.size(); j++) {
                if (associatedParts.get(j).getId() == allParts.get(i).getId()) {
                    allParts.remove(associatedParts.get(j));
                }
            }
        }
     }

    /**
     * Set the TextFields with their respective, corresponding data. Also, add the correct data to the top (All Parts)
     * and bottom (Associated Parts) TableViews
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idTextField.setText(Integer.toString(this.product.getId()));
        nameTextField.setText(this.product.getName());
        inventoryTextField.setText(Integer.toString(this.product.getStock()));
        priceCostTextField.setText(Double.toString(this.product.getPrice()));
        maxTextField.setText(Integer.toString(this.product.getMax()));
        minTextField.setText(Integer.toString(this.product.getMin()));

        partTableView1.setItems(this.allParts);
        partIdTable1Column.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameTable1Column.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryLevelTable1Column.setCellValueFactory(new PropertyValueFactory<>("stock"));
        pricePerUnitTable1Column.setCellValueFactory(new PropertyValueFactory<>("price"));

        partTableView2.setItems(this.associatedParts);
        partIdTable2Column.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameTable2Column.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryLevelTable2Column.setCellValueFactory(new PropertyValueFactory<>("stock"));
        pricePerUnitTable2Column.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Save changes made to the Product instance and transition back to the Main View
     * @param actionEvent an ActionEvent object
     * @throws IOException when the getResource method cannot find the fxml file to transition back to
     */
    public void saveProduct(ActionEvent actionEvent) throws IOException {
        int index = this.inventory.getAllProducts().indexOf(this.product);
        int id;
        String name;
        int inv;
        double priceCost;
        int max;
        int min;

        if (!(nameTextField.getText().isEmpty()) && !(inventoryTextField.getText().isEmpty()) &&
                !(priceCostTextField.getText().isEmpty()) && !(maxTextField.getText().isEmpty()) &&
                !(minTextField.getText().isEmpty())) {

            // Set variables to their respective TextField values
            id = Integer.parseInt(idTextField.getText());
            name = nameTextField.getText();
            inv = Integer.parseInt(inventoryTextField.getText());
            priceCost = Double.parseDouble(priceCostTextField.getText());
            max = Integer.parseInt(maxTextField.getText());
            min = Integer.parseInt(minTextField.getText());

            if (inv > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error Modifying Product");
                alert.setContentText("The total number in inventory cannot exceed the upper bound (max) allowed in inventory");
                alert.showAndWait();
            }
            else if (inv < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error Modifying Product");
                alert.setContentText("The total number in inventory cannot be less than the lower bound (min) allowed in inventory");
                alert.showAndWait();
            }
            else if (min == max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error Modifying Product");
                alert.setContentText("The upper bound and lower bound allowed in inventory cannot be the same");
                alert.showAndWait();
            }
            else if (min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error Modifying Product");
                alert.setContentText("The lower bound allowed in inventory cannot exceed the upper bound");
                alert.showAndWait();
            }
            else if (max < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error Modifying Product");
                alert.setContentText("The upper bound allowed in inventory cannot be less than the lower bound");
                alert.showAndWait();
            }
            else {
                Product updatedProduct = new Product(id, name, priceCost, inv, min, max);

                for (Part associatedPart : this.associatedParts) {
                    updatedProduct.addAssociatedPart(associatedPart);
                }

                inventory.updateProduct(index, updatedProduct);

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
            alert.setHeaderText("Error Modifying Product");
            alert.setContentText("We ran into an issue while performing your Product modification request. Please verify that all fields are filled in correctly and try again.");
            alert.showAndWait();
        }
    }

    /**
     * Adds the chosen Part from the top (All Parts) TableView to the bottom (Associated Parts) TableView
     * @param actionEvent an ActionEvent object
     */
    public void addAssociatedPart(ActionEvent actionEvent) {
        Part partSelected = (Part) partTableView1.getSelectionModel().getSelectedItem();

        if (partSelected == null) {
            return;
        }

        // Remove selectedPart from top TableView (All Parts)
        allParts.remove(partSelected);

        // Move selectedPart to bottom TableView (Associated Parts)
        associatedParts.add(partSelected);

        partTableView1.refresh();
    }

    /**
     * Searches for the Part by its name or for the supplied Part ID
     * @param actionEvent an ActionEvent object
     */
    public void searchForPart(ActionEvent actionEvent) {
        String input = searchPartTextField.getText();

        ObservableList<Part> parts = this.inventory.lookupPart(input);

        // Check if no Part was added to list through a partial/full name. If empty, then maybe user
        // typed in a ID no., so search based on that argument
        if (parts.size() == 0) {
            try {
                int id = Integer.parseInt(input);
                Part part = inventory.lookupPart(id);

                if (part != null) {
                    parts.add(part);
                }
            }
            catch (NumberFormatException e) {
                // ignore for now...
            }
        }

        // Show the returned results in the TableView
        partTableView1.setItems(parts);
        partTableView1.refresh();
    }

    /**
     * Removes the chosen Part from the bottom (Associated Parts) TableView and adds it back to the
     * top (All Parts) TableView
     * @param actionEvent an ActionEvent object
     */
    public void removeAssociatedPart(ActionEvent actionEvent) {
        Part partSelected = (Part) partTableView2.getSelectionModel().getSelectedItem();

        if (partSelected == null) {
            return;
        }

        // Remove the selected, associated part from the bottom TableView (Associated Parts)
        associatedParts.remove(partSelected);

        // Add the selectedPart back to the top TableView (All Parts)
        allParts.add(partSelected);
    }

    /**
     * Cancels the Add Product request and transitions back to the Main Form
     * @param actionEvent an ActionEvent object
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
}
