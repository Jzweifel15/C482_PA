package demo.controller;

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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

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

    public MainController() { }

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    /**
     * Transitions to the Add Part Form for the user to add a new part to the Parts TableView
     * @param actionEvent an action event object
     */
    public void onAddPartClicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/demo/view/add-part-view.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 600);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void onModifyPartClicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/demo/view/modify-part-view.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 600);
        stage.setTitle("Modify Part");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
