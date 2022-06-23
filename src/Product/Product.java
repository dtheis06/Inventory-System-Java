package Product;

import Part.Part;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Product Class
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;


    /**
     * Product Constructor
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }


    /**
     * @return the id
     */
    public int getId() {
        return id;
    }


    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * @return the name
     */
    public String getName() {
        return name;
    }


    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }


    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }


    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }


    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }


    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }


    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }


    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }


    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }


    /**
     * @param part to add to associated parts list
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }


    /**
     * @param selectedAssociatedPart to delete from associated parts list
     * @return True if a part was deleted or not
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        boolean deleted = false;
        for (Part currPart : this.associatedParts) {
            if (selectedAssociatedPart.equals(currPart)) {
                this.associatedParts.remove(currPart);
                deleted = true;
            }
        }
        return deleted;


    }/**
     * @return allParts associated with the Product.
     */
    public ObservableList<Part> getAllAssociatedParts() {
        ObservableList<Part> allParts = FXCollections.observableArrayList();
        for(Part currPart: this.associatedParts) {
            allParts.add(currPart);
        }
        return allParts;
    }
}
