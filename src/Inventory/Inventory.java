package Inventory;

import Product.Product;
import Part.Part;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Locale;

/** RUNTIME ERROR: I was stuck for seven hours debugging why nothing was showing up in the tables when I first launch the program.
 * I checked my main controller code and compared it with countless examples online along with checking out more
 * java FX examples involving tables thinking I possibly messed something up. That was my first .fxml file that I've
 * ever made so that definitely was a possibility. Turns out, I had everything right for the longest time. I just
 * didn't initialise my ObservableLists correctly. 7 hours and I was missing just half a line of code. Thinking back now,
 * I really should have figured that out sooner since it was showing a NullPointerException.
 *
 * Inventory Class
 */

public class Inventory {
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();


    /**
     * Adds a part to the Inventory
     * @param newPart to add to the allParts list
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }


    /**
     * Adds a product to the inventory
     * @param newProductto add to the allProducts list
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }


    /**
     * Returns an ObservableList<Part> of parts with ids that match the searchedID
     * @param searchedID of part to lookup
     * @return Parts to return
     */
    public static ObservableList<Part> lookupPart(int searchedID) {
        ObservableList<Part> parts = FXCollections.observableArrayList();
        for (Part currPart : allParts) { //Cycles through all parts in the inventory
            if (currPart.getId() == searchedID) { //If the currently checked part == searchedID
                parts.add(currPart); //Add the part to the list to be returned
            }
        }
        return parts; //Return list of all parts with the id
    }


    /**
     * Returns an ObservableList<Product> of products with ids that match the searchedID
     * @param productID of part to lookup
     * @return product to return as ID
     */
    public static ObservableList<Product> lookupProduct(int productID) {
        ObservableList<Product> products = FXCollections.observableArrayList();
        for(Product currProduct: allProducts) { //Cycles through all products in the inventory
            if(currProduct.getId() == productID) { //If the currently checked product == searchedID
                products.add(currProduct); //Add the product to the list to be returned
            }
        }
        return products; //Return list of all products with the id
    }


    /**
     * Returns an ObservableList<Parts> of parts with names that contain the searched input
     * @param input that filters the search by names that match
     * @return Parts to return
     */
    public static ObservableList<Part> lookupPart(String input) {
        ObservableList<Part> parts = FXCollections.observableArrayList();
        for(Part currPart: allParts) { //Cycles through all parts in the inventory
            if(currPart.getName().contains(input)) { //If the currently checked part contains input
                parts.add(currPart); //Add part to the list to be returned
            }
        }
        return parts; //Return the list of all parts that contain the input
    }


    /**
     * Returns an ObservableList<Product> of products with names that contain the searched input
     * @param input that filters the search by names that match
     * @return Products to return
     */
    public static ObservableList<Product> lookupProduct(String input) {
        ObservableList<Product> products = FXCollections.observableArrayList();
        for(Product currProduct: allProducts) { //Cycles through all products in the inventory
            if(currProduct.getName().contains(input)) { //If the currently checked product contains input
                products.add(currProduct); //Add product to the list to be returned
            }
        }
        return products; //Return the list of all products that contain the input
    }


    /**
     * Returns an ObservableList<Part> of products in the inventory
     * @return allPart in the inventory
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }


    /**
     * Returns an ObservableList<Product> of products in the inventory
     * @return allProducts in the inventory
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }


    /**
     * Returns an ObservableList<Part> of parts in the inventory
     * @param selectedPart that you want to delete
     * @return true if the part was successfully removed
     */
    public static boolean deletePart(Part selectedPart) {
        boolean removed = false;
        if(allParts.remove(selectedPart)) {
            removed = true;
        }
        return removed;
    }


    /**
     * Returns an ObservableList<Product> of products in the inventory
     * @param selectedProduct that you want to delete
     * @return true if the product was successfully removed
     */
    public static boolean deleteProduct(Product selectedProduct) {
        boolean removed = false;
        if(allProducts.remove(selectedProduct)) {
            removed = true;
        }
        return removed;
    }


    /**
     * Updates the specified Part at the specified index
     * @param index of the selectedPart that you want to update
     * @param selectedPart that you want to update
     */
    public static void updatePart(int index,Part selectedPart) {
        Inventory.getAllParts().set(index, selectedPart);
    }


    /**
     * Updates the specified Product at the specified index
     * @param index of the selectedProduct that you want to update
     * @param selectedProduct that you want to update
     */
    public static void updateProduct(int index,Product selectedProduct) {
        Inventory.getAllProducts().set(index, selectedProduct);
    }


    /**
     * Searches the list of parts for names that contain the input in the parameter
     * @param input a string that you want to filter the list by
     * @return ObservableList<Part> of names that contain the input in the parameter
     */
    public static ObservableList<Part> searchParts(String input) {
        ObservableList<Part> parts = FXCollections.observableArrayList();
        for(Part currPart: allParts) { //Cycles through allParts
            String id = String.valueOf(currPart.getId());
            if(currPart.getName().toLowerCase(Locale.ROOT).contains(input.toLowerCase(Locale.ROOT)) || id.contains(input)) {
                parts.add(currPart); //after converted to lower case, id and name matches input, add the currently checked part
            }
        }
        return parts;
    }


    /**
     * Searches the list of products for names that contain the input in the parameter
     * @param input a string that you want to filter the list by
     * @return ObservableList<Part> of names that contain the input in the parameter
     */
    public static ObservableList<Product> searchProducts(String input) {
        ObservableList<Product> products = FXCollections.observableArrayList();
        for(Product currProduct: allProducts) { //Cycles through allProducts
            String id = String.valueOf(currProduct.getId());
            if(currProduct.getName().toLowerCase(Locale.ROOT).contains(input.toLowerCase(Locale.ROOT)) || id.contains(input)) {
                products.add(currProduct); //after converted to lower case, id and name matches input, add the currently checked product
            }
        }
        return products;
    }
}

