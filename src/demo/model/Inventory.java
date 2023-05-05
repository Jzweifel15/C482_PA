package demo.model;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

/**
 * @author Justin R. Zweifel
 */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

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

    /**
     * @param partId the ID of the part to look up
     * @return the found Part with partId
     */
    public Part lookupPart(int partId) {
        // The list of all Parts to iterate through to check for the ID number
        ObservableList<Part> allParts = this.getAllParts();

        // Find Part with ID no. and return that Part
        for (Part part : allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }

        // Will return NULL if no match is found
        return null;
    }

    /**
     * @param partName the name of the part to look up
     * @return an ObservableList of multiple parts with the same name
     */
    public ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> namedParts = FXCollections.observableArrayList();

        // The list of all Parts to iterate through to check for the partial name
        ObservableList<Part> allParts = this.getAllParts();

        for (Part part : allParts) {
            // Returns a boolean to check if the partial name that has been entered in search field
            // does or does not exist in allParts
            if (part.getName().toLowerCase().contains(partName.toLowerCase())) {
                namedParts.add(part);
            }
        }

        return namedParts;
    }

    /**
     * @param productId the ID of the product to look up
     * @return the found Product with productId
     */
    public Product lookupProduct(int productId) {
        // The list of all Parts to iterate through to check for the ID number
        ObservableList<Product> allProducts = this.getAllProducts();

        // Find Part with ID no. and return that Part
        for (Product product : allProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }

        // Will return NULL if no match is found
        return null;
    }

    /**
     * @param productName the name of the product to look up
     * @return an ObservableList of multiple products with the same name
     */
    public ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> namedProducts = FXCollections.observableArrayList();

        // The list of all Products to iterate through to check for the partial name
        ObservableList<Product> allProducts = this.getAllProducts();

        for (Product product : allProducts) {
            // Returns a boolean to check if the partial name that has been entered in search field
            // does or does not exist in allProducts
            if (product.getName().contains(productName)) {
                namedProducts.add(product);
            }
        }

        return namedProducts;
    }

    /**
     * @param index
     * @param selectedPart
     */
    public void updatePart(int index, Part selectedPart) {
        for (Part p : Inventory.allParts) {
            if (allParts.get(index).getId() == selectedPart.getId()) {
                allParts.set(index, selectedPart);
                break;
            }
        }
    }

    /**
     * @param index
     * @param selectedProduct
     */
    public void updateProduct(int index, Product selectedProduct) {
        for (Product p : Inventory.allProducts) {
            if (allProducts.get(index).getId() == selectedProduct.getId()) {
                allProducts.set(index, selectedProduct);
                break;
            }
        }
    }

    /**
     * @param selectedPart the part trying to be deleted from inventory
     * @return true if the supplied Part was deleted; false if not found
     */
    public static boolean deletePart(Part selectedPart) {
        for (Part part : Inventory.allParts) {
            if (part.getId() == selectedPart.getId()) {
                Inventory.allParts.remove(part);
                return true;
            }
        }

        return false;
    }

    /**
     *
     * @param selectedProduct the product trying to be deleted from inventory
     * @return true if the supplied Product was deleted; false if not found
     */
    public static boolean deleteProduct(Product selectedProduct) {
        for (Product product : Inventory.allProducts) {
            if (product.getId() == selectedProduct.getId()) {
                Inventory.allProducts.remove(product);
                return true;
            }
        }

        return false;
    }

}