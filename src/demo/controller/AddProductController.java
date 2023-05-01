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
import java.util.Collections;
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

    public Button addAssociatedPartButton;
    public Button removeAssociatedPartButton;
    public Button saveProductButton;
    public Button cancelProductButton;

    public AddProductController(Inventory inventory) {
        this.inventory = inventory;
        this.allParts = FXCollections.observableArrayList(inventory.getAllParts());
        this.associatedParts = FXCollections.observableArrayList();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
     *
     * @param actionEvent
     * @throws IOException
     */
    public void saveProduct (ActionEvent actionEvent) throws IOException {
        int id = generateRandomId();
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

            // Set variables to their respective TextField values
            name = nameTextField.getText();
            inv = Integer.parseInt(inventoryTextField.getText());
            priceCost = Double.parseDouble(priceCostTextField.getText());
            max = Integer.parseInt(maxTextField.getText());
            min = Integer.parseInt(minTextField.getText());

            // Create a new Product instance
            newProduct = new Product(id, name, priceCost, inv, min, max);

            // Add all the selected associated parts to the new Product's list of associated parts
            for (Part associatedPart : this.associatedParts) {
                newProduct.addAssociatedPart(associatedPart);
            }

            inventory.addProduct(newProduct);

            // Clear all TextFields before transitioning back to Main View
            nameTextField.clear();
            inventoryTextField.clear();
            priceCostTextField.clear();
            maxTextField.clear();
            minTextField.clear();

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
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error Adding New Product");
            alert.setContentText("We ran into an issue trying to add your new product request. Please verify that all fields are filled in and try again.");
            alert.showAndWait();
        }
    }

    /**
     * Add the selected associated part from the top (All Parts) TableView
     * @param actionEvent an action even object
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
    }

    /**
     * Removes the selected associated part from the bottom (Associated Parts) TableView
     * @param actionEvent an action event objects
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

    public int generateRandomId() {
        Random rand = new Random();
        Integer randNum = rand.nextInt(100);

        return randNum;
    }
}
