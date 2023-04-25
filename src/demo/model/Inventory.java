package demo.model;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

/**
 * @author Justin R. Zweifel
 */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private ObservableList<Product> allProducts = FXCollections.observableArrayList();

    // Constructor
    public Inventory() { }

    /**
     * @return an ObservableList of all Parts in Inventory
     */
    public ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * @return an ObservableList of all Products in Inventory
     */
    public ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * @param newPart the new part to add to the allParts list
     */
    public void addPart(Part newPart) {

        if (newPart != null) {
            this.allParts.add(newPart);
        }

    }

    /**
     * @param newProduct the new product to add to the allProducts list
     */
    public void addProduct(Product newProduct) {

        if (newProduct != null) {
            this.allProducts.add(newProduct);
        }

    }

//    /**
//     * @param partId the ID of the part to look up
//     * @return the found Part with partId
//     */
//    public Part lookupPart(int partId) {
//
//    }

//    /**
//     * @param partName the name of the part to look up
//     * @return an ObservableList of multiple parts with the same name
//     */
//    public ObservableList<Part> lookupPart(String partName) {
//
//    }

//    /**
//     * @param productId the ID of the product to look up
//     * @return the found Product with productId
//     */
//    public Product lookupProduct(int productId) {
//
//    }

//    /**
//     * @param productName the name of the product to look up
//     * @return an ObservableList of multiple products with the same name
//     */
//    public ObservableList<Product> lookupProduct(String productName) {
//
//    }

    /**
     * @param index
     * @param selectedPart
     */
    public void updatePart(int index, Part selectedPart) {

    }

    /**
     * @param index
     * @param newProduct
     */
    public void updateProduct(int index, Product newProduct) {

    }

//    /**
//     * @param selectedPart the part trying to be deleted from inventory
//     * @return true if the supplied Part was deleted; false if not found
//     */
//    public boolean deletePart(Part selectedPart) {
//
//    }

//    /**
//     *
//     * @param selectedProduct the product trying to be deleted from inventory
//     * @return true if the supplied Product was deleted; false if not found
//     */
//    public boolean deleteProduct(Product selectedProduct) {
//
//    }

}