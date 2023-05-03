package demo.controller;

import demo.model.Inventory;
import demo.model.Part;
import demo.model.Product;
import javafx.application.Platform;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    public Inventory inventory;

    public AnchorPane mainFormPane;
    public Pane partsPane;
    public Pane productsPane;

    // Parts Table, its repsective columns, and its associated controls
    public TableView partsTableView;
    public TableColumn partIdColumn;
    public TableColumn partNameColumn;
    public TableColumn partInventoryLevelColumn;
    public TableColumn partPricePerUnitColumn;
    public Button partAddButton;
    public Button partModifyButton;
    public Button partDeleteButton;
    public TextField partSearchField;

    // Products Table, its respective columns, and its associated controls
    public TableView productsTableView;
    public TableColumn productIdColumn;
    public TableColumn productNameColumn;
    public TableColumn productInventoryLevelColumn;
    public TableColumn productPricePerUnitColumn;
    public Button productAddButton;
    public Button productModifyButton;
    public Button productDeleteButton;
    public TextField productSearchField;

    // Exit Main Form (quits the program)
    public Button mainFormExitButton;

    public MainController() {
        Inventory inventory = new Inventory();
    }

    public MainController(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Add all Parts to the PartsTableView
        partsTableView.setItems(this.inventory.getAllParts());
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPricePerUnitColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Add all Products to the ProductsTableView
        productsTableView.setItems(this.inventory.getAllProducts());
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPricePerUnitColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Transitions to the Add Part Form for the user to add a new part to the Parts TableView
     * @param actionEvent an action event object
     * @throws IOException when the getResource method cannot find the fxml file to transition back to
     */
    public void onAddPartClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/demo/view/add-part-view.fxml"));
        AddPartController controller = new AddPartController(this.inventory);
        fxmlLoader.setController(controller);

        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 600);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Transitions to the Modify Part Form for the user to modify an existing part from the Parts TableView
     * @param actionEvent an action event object
     * @throws IOException when the getResource method cannot find the fxml file to transition back to
     */
    public void onModifyPartClicked(ActionEvent actionEvent) throws IOException {
        Part partSelected = (Part) partsTableView.getSelectionModel().getSelectedItem();

        if (partSelected == null) {
            return;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/demo/view/modify-part-view.fxml"));
        ModifyPartController controller = new ModifyPartController(this.inventory, partSelected);
        fxmlLoader.setController(controller);

        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 600);
        stage.setTitle("Modify Part");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Deletes the selected Part from inventory
     * @param actionEvent an ActionEvent object
     */
    public void onDeletePartClicked(ActionEvent actionEvent) {
        Part partSelected = (Part) partsTableView.getSelectionModel().getSelectedItem();

        if (partSelected == null) {
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Part");
        alert.setHeaderText("Are you sure you want to delete: " + partSelected.getName());
        alert.setContentText("Click ok to confirm");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            boolean deleted = Inventory.deletePart(partSelected);
        }
    }

    /**
     * Transitions to the Add Product Form for the user to add a new product to the Products TableView
     * @param actionEvent an action event object
     * @throws IOException when the getResource method cannot find the fxml file to transition back to
     */
    public void onAddProductClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/demo/view/add-product-view.fxml"));
        AddProductController controller = new AddProductController(this.inventory);
        fxmlLoader.setController(controller);

        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 600);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Transitions to the Modify Product Form for the user to modify a an existing product from the Products TableView
     * @param actionEvent an action event object
     * @throws IOException when the getResource method cannot find the fxml file to transition back to
     */
    public void onModifyProductClicked(ActionEvent actionEvent) throws IOException {
        Product productSelected = (Product) productsTableView.getSelectionModel().getSelectedItem();

        if (productSelected == null) {
            return;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/demo/view/modify-product-view.fxml"));
        ModifyProductController controller = new ModifyProductController(this.inventory, productSelected);
        fxmlLoader.setController(controller);

        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 600);
        stage.setTitle("Modify Product");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Deletes the selected Product from Inventory, only if the Product instance does not
     * have any remaining associated parts
     * @param actionEvent an ActionEvent object
     */
    public void onDeleteProductClicked(ActionEvent actionEvent) {
        Product productSelected = (Product) productsTableView.getSelectionModel().getSelectedItem();

        if (productSelected == null) {
            return;
        }
        else if (!(productSelected.getAssociatedParts().isEmpty())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error Trying to Delete Product");
            alert.setContentText("Cannot delete the selected product because it currently still has parts " +
                                    "associated with it.");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Product");
            alert.setHeaderText("Are you sure you want to delete: " + productSelected.getName());
            alert.setContentText("Click OK to confirm");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                boolean deleted = Inventory.deleteProduct(productSelected);
            }
        }
    }

    /**
     * Searches for the Part by its name or for the supplied Part ID
     * @param actionEvent an ActionEvent object
     */
    public void searchForPart(ActionEvent actionEvent) {
        String input = partSearchField.getText();

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
        partsTableView.setItems(parts);
        partsTableView.refresh();
    }

    /**
     *
     * @param actionEvent
     */
    public void searchForProduct(ActionEvent actionEvent) {
        String input = productSearchField.getText();

        ObservableList<Product> products = this.inventory.lookupProduct(input);

        // Check if no Product was added to list through a partial/full name. If empty, then maybe user
        // typed in a ID no., so search based on that argument
        if (products.size() == 0) {
            try {
                int id = Integer.parseInt(input);
                Product product = inventory.lookupProduct(id);

                if (product != null) {
                    products.add(product);
                }
            }
            catch (NumberFormatException e) {
                // ignore for now...
            }
        }

        // Show the returned results in the TableView
        productsTableView.setItems(products);
        productsTableView.refresh();
    }

    /**
     * Action Event for the exit button that will quit the entire program when pressed
     * @param actionEvent an action event object
     */
    public void exitButtonClicked(ActionEvent actionEvent) {
        Platform.exit();
    }

}
