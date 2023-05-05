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
import java.util.Random;
import java.util.ResourceBundle;

public class AddProductController implements Initializable {

    private Inventory inventory;

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

    /**
     * Constructor for the Add Product Controller
     * @param inventory the current state of the inventory
     */
    public AddProductController(Inventory inventory) {
        this.inventory = inventory;
        this.allParts = FXCollections.observableArrayList(inventory.getAllParts());
        this.associatedParts = FXCollections.observableArrayList();
    }

    /**
     * Add initial data to the TableViews when the Add Product View is accessed
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        generateRandomId();

        // Add all Parts in inventory to the top TableView
        partTableView1.setItems(this.allParts);
        partIdTable1Column.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameTable1Column.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryLevelTable1Column.setCellValueFactory(new PropertyValueFactory<>("stock"));
        pricePerUnitTable1Column.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Prepare the bottom TableView to hold the Associated Parts for if/when an associated part is added
        partTableView2.setItems(this.associatedParts);
        partIdTable2Column.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameTable2Column.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryLevelTable2Column.setCellValueFactory(new PropertyValueFactory<>("stock"));
        pricePerUnitTable2Column.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Save the new Product and transition back to the Main View
     * @param actionEvent an ActionEvent object
     * @throws IOException when the getResource method cannot find the fxml file to transition back to
     */
    public void saveProduct (ActionEvent actionEvent) throws IOException {
        int id;
        String name;
        int inv;
        double priceCost;
        int max;
        int min;
        Product newProduct;

        // Check that TextFields are filled in
        if (!(nameTextField.getText().isEmpty()) && !(inventoryTextField.getText().isEmpty()) &&
                !(priceCostTextField.getText().isEmpty()) && !(maxTextField.getText().isEmpty()) &&
                !(minTextField.getText().isEmpty())) {

            try {
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
                    alert.setHeaderText("Error Adding New Product");
                    alert.setContentText("The total number in inventory cannot exceed the upper bound (max) allowed in inventory");
                    alert.showAndWait();
                } else if (inv < min) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error Adding New Product");
                    alert.setContentText("The total number in inventory cannot be less than the lower bound (min) allowed in inventory");
                    alert.showAndWait();
                } else if (min == max) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error Adding New Product");
                    alert.setContentText("The upper bound and lower bound allowed in inventory cannot be the same");
                    alert.showAndWait();
                } else if (min > max) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error Adding New Product");
                    alert.setContentText("The lower bound allowed in inventory cannot exceed the upper bound");
                    alert.showAndWait();
                } else if (max < min) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error Adding New Product");
                    alert.setContentText("The upper bound allowed in inventory cannot be less than the lower bound");
                    alert.showAndWait();
                } else {
                    // Create a new Product instance
                    newProduct = new Product(id, name, priceCost, inv, min, max);

                    // Add all the selected associated parts to the new Product's list of associated parts
                    for (Part associatedPart : this.associatedParts) {
                        newProduct.addAssociatedPart(associatedPart);
                    }

                    // Add the new Product to inventory
                    inventory.addProduct(newProduct);

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
                alert.setHeaderText("Error Adding New Product");
                alert.setContentText("We ran into an issue trying to add your new product request. Please verify that all fields are filled in correctly and try again.");
                alert.showAndWait();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error Adding New Product");
            alert.setContentText("We ran into an issue trying to add your new product request. Please verify that all fields are filled in and try again.");
            alert.showAndWait();
        }
    }

    /**
     * Add the selected part from the top (All Parts) TableView to the bottom (Associated Parts) TableView
     * @param actionEvent an ActionEvent object
     */
    public void addAssociatedPart(ActionEvent actionEvent) {
        Part partSelected = (Part) partTableView1.getSelectionModel().getSelectedItem();

        if (partSelected == null) {
            return;
        }

        if (!(searchPartTextField.getText().equals(""))) {
            searchPartTextField.clear();
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

        // Looks up Part by name (String) first
        ObservableList<Part> parts = this.inventory.lookupPart(input);

        // Check if no Part was added to list through a partial/full name. If empty, then maybe user
        // typed in a ID no., so search based on that argument
        if (parts.isEmpty() == true) {
            try {
                int id = Integer.parseInt(input);
                Part part = inventory.lookupPart(id);

                if (part != null) {
                    parts.add(part);
                }
            }
            catch (NumberFormatException e) {
                // Do nothing
            }
        }

        // Do second check to see if Parts is still empty. If so, then show Error alert window
        if (parts.isEmpty() == true) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error Searching for Part");
            alert.setContentText("Seems we could not find that Part. Please, try again.");
            alert.showAndWait();
        }

        // Show the returned results in the TableView
        partTableView1.setItems(parts);
    }

    /**
     * Removes the selected part from the bottom (Associated Parts) TableView back to the top (All Parts) TableView
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

    /**
     * Generates a random ID for the new Product
     * @return the random ID
     */
    private void generateRandomId() {
        Random randomNum = new Random();
        Integer num = randomNum.nextInt(1000);

        if (this.inventory.getAllProducts().size() == 0 || this.inventory.lookupProduct(num) == null) {
            idTextField.setText(num.toString());
        }
        else {
            generateRandomId();
        }
    }
}
