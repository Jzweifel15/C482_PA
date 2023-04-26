package demo;

import demo.controller.MainController;
import demo.model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Inventory inventory = new Inventory();
        dummyData(inventory);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view/main-view.fxml"));
        MainController controller = new MainController(inventory);
        fxmlLoader.setController(controller);
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 900, 400);
        stage.setTitle("Inventory Management System!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void dummyData(Inventory inventory) {
        //Add InHouse Parts
        Part part1 = new InHouse(1, "Part A", 2.99, 10, 5, 100, 101);
        Part part2 = new InHouse(3, "Part B", 4.99, 11, 5, 100, 102);
        inventory.addPart(new InHouse(2, "Part C", 3.99, 9, 5, 100, 103));
        inventory.addPart(new InHouse(4, "Part D", 5.99, 15, 5, 100, 104));
        inventory.addPart(new InHouse(5, "Part E", 6.99, 5, 5, 100, 105));

        //Add OutSourced Parts
        Part part3 = new Outsourced(6, "Part F", 2.99, 10, 5, 100, "Bob's Burgers");
        Part part4 = new Outsourced(7, "Part G", 3.99, 9, 5, 100, "Amazon LLC");
        inventory.addPart(new Outsourced(8, "Part H", 2.99, 10, 5, 100, "The Broken Stool"));
        inventory.addPart(new Outsourced(9, "Part I", 2.99, 10, 5, 100, "Amazon LLC"));
        inventory.addPart(new Outsourced(10, "Part J", 2.99, 10, 5, 100, "The Drunken Clam"));

        //Add allProducts
        Product product1 = new Product(100, "Product 1", 9.99, 20, 5, 100);
        product1.addAssociatedPart(part1);
        product1.addAssociatedPart(part3);
        inventory.addProduct(product1);

        Product product2 = new Product(200, "Product 2", 9.99, 29, 5, 100);
        product2.addAssociatedPart(part2);
        product2.addAssociatedPart(part4);
        inventory.addProduct(product2);
    }

    public static void main(String[] args) {
        launch();
    }
}