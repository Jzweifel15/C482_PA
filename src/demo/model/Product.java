package demo.model;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

/**
 * @author Justin R. Zweifel
 */
public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Add a Part that is associated with a specific Product
     * @param part the specific Part to be added as the associated Part for the Product
     */
    public void addAssociatedPart(Part part) {
        this.associatedParts.add(part);
    }

    /**
     * Delete a Part that is associated with a specific Product
     * @param selectedAssociatedPart the associated Part to delete
     * @return true if the associated Part successfully deleted; false, if part is not found to be associated with the Product
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        for (Part associatedPart : associatedParts) {
            if (selectedAssociatedPart.getId() == associatedPart.getId()) {
                associatedParts.remove(selectedAssociatedPart);
                return true;
            }
        }

        return false;
    }

    /**
     * Retrieve all associated Parts for a specific Product
     * @return an ObservableList of all Parts associated with the specific Product
     */
    public ObservableList<Part> getAssociatedParts() {
        return this.associatedParts;
    }

    // Setter Methods

    /**
     * Change/set the ID of a Product
     * @param id the new ID to set the Product to
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Change/set the name of a Product
     * @param name the new name to set the Product to
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Change/set the price of a Product
     * @param Price the new price to set the Product to
     */
    public void setPrice(double Price) {
        this.price = price;
    }

    /**
     * Change/set the stock of a Product
     * @param stock the new stock to set the Product to
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Change/set the min of a Product
     * @param min the new min to set the Product to
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Change/set the max of a Product
     * @param max the new max to set the Product to
     */
    public void setMax(int max) {
        this.max = max;
    }

    // Getter Methods

    /**
     * Get the Product's ID
     * @return the specific Product instance's ID
     */
    public int getId() {
        return this.id;
    }

    /**
     * Get the Product's name
     * @return the specific Product instance's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the Product's price
     * @return the specific Product instance's price
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * Get the Product's stock
     * @return the specific Product instance's stock
     */
    public int getStock() {
        return this.stock;
    }

    /**
     * Get the Product's min
     * @return the specific Product instance's min
     */
    public int getMin() {
        return this.min;
    }

    /**
     * Get the Product's max
     * @return the specific Product instance's max
     */
    public int getMax() {
        return this.max;
    }

    @Override
    public String toString() {
        return "ID=" + this.getId() + ", Name=" + this.getName() + ", Price=" + this.getPrice() +
                ", Stock=" + this.getStock() + ", Min=" + this.getMin() + ", Max=" + this.getMax() +
                ", AssociatedParts=" + this.getAssociatedParts();
    }

}