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

    public TextField nameTextField;
    public TextField idTextField;
    public TextField inventoryTextField;
    public TextField PriceCostTextField;
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

        // Add the remaining Parts that are not associated with the passed Product
        // instance to the allParts List for the top TableView (All Parts)
        this.allParts = FXCollections.observableArrayList(inventory.getAllParts());
        for (int i = 0; i < allParts.size(); i++) {
            for (int j = 0; j < product.getAssociatedParts().size(); j++) {
                if (product.getAssociatedParts().get(j).getId() == allParts.get(i).getId()) {
                    allParts.remove(product.getAssociatedParts().get(j));
                }
            }
        }

     }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idTextField.setText(Integer.toString(this.product.getId()));
        nameTextField.setText(this.product.getName());
        inventoryTextField.setText(Integer.toString(this.product.getStock()));
        PriceCostTextField.setText(Double.toString(this.product.getPrice()));
        maxTextField.setText(Integer.toString(this.product.getMax()));
        minTextField.setText(Integer.toString(this.product.getMin()));

        partTableView1.setItems(this.allParts);
        partIdTable1Column.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameTable1Column.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryLevelTable1Column.setCellValueFactory(new PropertyValueFactory<>("stock"));
        pricePerUnitTable1Column.setCellValueFactory(new PropertyValueFactory<>("price"));

        partTableView2.setItems(this.product.getAssociatedParts());
        partIdTable2Column.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameTable2Column.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryLevelTable2Column.setCellValueFactory(new PropertyValueFactory<>("stock"));
        pricePerUnitTable2Column.setCellValueFactory(new PropertyValueFactory<>("price"));
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
