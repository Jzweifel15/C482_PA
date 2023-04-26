package demo.controller;

import demo.model.Inventory;
import demo.model.Part;
import javafx.application.Platform;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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
        productsTableView.setItems(this.inventory.getAllParts());
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
        //Parent root = FXMLLoader.load(getClass().getResource("/demo/view/add-part-view.fxml"));
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
        Parent root = FXMLLoader.load(getClass().getResource("/demo/view/modify-part-view.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 600);
        stage.setTitle("Modify Part");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Action Event for the exit button that will quit the entire program when pressed
     * @param actionEvent an action event object
     */
    public void exitButtonClicked(ActionEvent actionEvent) {
        Platform.exit();
    }

    /**
     * Transitions to the Add Product Form for the user to add a new product to the Products TableView
     * @param actionEvent an action event object
     * @throws IOException when the getResource method cannot find the fxml file to transition back to
     */
    public void onAddProductClicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/demo/view/add-product-view.fxml"));
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
        Parent root = FXMLLoader.load(getClass().getResource("/demo/view/modify-product-view.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 600);
        stage.setTitle("Modify Product");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
