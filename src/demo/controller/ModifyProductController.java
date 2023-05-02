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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

    public ModifyProductController(Inventory inventory, Product product) {
        this.inventory = inventory;
        this.product = product;
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
     * @param actionEvent
     * @throws IOException
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

            Product updatedProduct = new Product(id, name, priceCost, inv, min, max);

            for (Part associatedPart : this.associatedParts) {
                updatedProduct.addAssociatedPart(associatedPart);
            }

            inventory.updateProduct(index, updatedProduct);

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
    }

    /**
     *
     * @param actionEvent
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
}
