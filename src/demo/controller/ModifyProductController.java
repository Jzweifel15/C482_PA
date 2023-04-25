package demo.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ModifyProductController implements Initializable {
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

    @Override
    public void initialize(URL location, ResourceBundle resources) { }

}
