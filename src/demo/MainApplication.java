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

    /**
     * RUNTIME ERROR ENCOUNTERED: The biggest issue I encountered for this project was trying to figure out how to pass
     * data between the different views/windows, which originally started in this first, main application file. I had
     * originally added the 'fx:controller' options in my fxml files, but while doing this, I could not find a sufficient
     * way to pass the data back and forth. The one option I could logically think to do, was to pass the data to each
     * Controller class through their constructor, then transition to the respective view that was trying to be accessed
     * via one of the form buttons. While doing this, I did not delete the 'fx:controller' option in the fxml file, which
     * then generated a runtime error stating that the "Controller was already set" (or, something along those lines).
     * Therefore, to fix this error, I deleted the 'fx:controller' option from each fxml file and I was able to transition
     * between each view normally and pass the necessary data via the constructors.
     * @param stage a Stage object
     * @throws IOException when the getResource method cannot find the supplied fxml file to transition to
     */
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
        inventory.addPart(part1);
        inventory.addPart(part2);
        inventory.addPart(new InHouse(2, "Part C", 3.99, 9, 5, 100, 103));
        inventory.addPart(new InHouse(4, "Part D", 5.99, 15, 5, 100, 104));
        inventory.addPart(new InHouse(5, "Part E", 6.99, 5, 5, 100, 105));

        //Add OutSourced Parts
        Part part3 = new Outsourced(6, "Part F", 2.99, 10, 5, 100, "Bob's Burgers");
        Part part4 = new Outsourced(7, "Part G", 3.99, 9, 5, 100, "Amazon LLC");
        inventory.addPart(part3);
        inventory.addPart(part4);
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

    /**
     * RUNTIME ERROR ENCOUNTERED: This comment can be found in the MainApplication class (this file), for the start method
     *
     * FUTURE ENHANCEMENT: A future enhancement that I would make to this application would be to add the functionality to
     * automatically update a TableView when the user is typing in either of the search fields, instead of performing the
     * search on the click of a button. For example, if I type a single character, say 'P', then the TableView would update with
     * all instances that start with 'P.' Then, if I tacked on another character to that string, say 'Pa', then the TableView would
     * automatically update to show all instances that start with 'Pa', so on and so forth until a user finds what they're
     * trying to search.
     * @param args a string array of arguments supplied from the command line
     */
    public static void main(String[] args) {
        launch();
    }
}