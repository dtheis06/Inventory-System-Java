package Controllers;

import Inventory.Inventory;
import Part.Part;
import Product.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductAddController implements Initializable {
    Product newProduct;
    private ObservableList<Part> aparts = FXCollections.observableArrayList(); //Place holder just in case you don't hit save it doesn't change the rest

    @FXML
    private TableView<Part> table1;

    @FXML
    private TableColumn<Part, Integer> table1PartIdColumn;

    @FXML
    private TableColumn<Part, String> table1PartNameColumn;

    @FXML
    private TableColumn<Part, Integer> table1InventoryColumn;

    @FXML
    private TableColumn<Part, Double> table1PriceColumn;

    @FXML
    private Button addButton;

    @FXML
    private TableView<Part> table2;

    @FXML
    private TableColumn<Part, Integer> table2PartIdColumn;

    @FXML
    private TableColumn<Part, String> table2PartNameColumn;

    @FXML
    private TableColumn<Part, Integer> table2InventoryColumn;

    @FXML
    private TableColumn<Part, Double> table2PriceColumn;

    @FXML
    private Button removeButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField partSearch;

    @FXML
    private Label addProductLabel;

    @FXML
    private Label idLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label invLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label maxLabel;

    @FXML
    private Label errorLabel1;

    @FXML
    private Label errorLabel2;

    @FXML
    private Label errorMessage3;

    @FXML
    private TextField idText;

    @FXML
    private TextField nameText;

    @FXML
    private TextField priceText;

    @FXML
    private TextField invText;

    @FXML
    private TextField maxText;

    @FXML
    private TextField minText;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        newProduct = new Product(-1,"empty",0,0,0,0);
        setUpTableOne(); //Sets up table 1 and align the columns with the fields
        setUpTableTwo(); //Sets up table 2 and align the columns with the fields
        setTextBoxes(); // Sets text boxes to show the selected Product's information

    }

    /**
     * Button to add a part to the associated part list
     */
    public void setAddButton() {
        Part newPart = table1.getSelectionModel().getSelectedItem(); //Assigns the user selected part to newPart
        if(newPart != null) {
            aparts.add(newPart); //Adds the newPart to the placeholder
        }
        table2.setItems(aparts); //Shows the placeholder list
    }


    /**
     * Sets Save Button
     */
    public void setSaveButton() {
        resetErrorLabels();
        boolean error = false; //flag
        try {
            //Parses text fields
            int inv = Integer.parseInt(invText.getText());
            double price = Double.parseDouble(priceText.getText());
            int max = Integer.parseInt(maxText.getText());
            int min = Integer.parseInt(minText.getText());
            int id = generateProductId();
            if(max < min) { //check
                errorLabel2.setText("Min has to be lower than Max");
                error = true;
            }
            if(inv > max || inv < min) {
                errorMessage3.setText("Inventory must be between Min and Max");
                error = true; //flag
            }

            newProduct = new Product(id, nameText.getText(), price, inv, min, max); //Create new product with updated info

            for (int i = 0; i < aparts.size(); i++) {
                newProduct.addAssociatedPart(aparts.get(i));  //Adds part placeholder data to the newProduct
            }
            if(!error) { // If no error
                Inventory.addProduct(newProduct); //Add the newProduct to the inventory
                Stage stage = (Stage) saveButton.getScene().getWindow(); //gets the stage
                stage.close(); // closes it
            }
        }
        catch(NumberFormatException e) { //handles NumberFormatException
            errorLabel1.setText("Number Format Error " + e.getMessage());
            error = true;
        }
    }
    public void setRemoveButton() {
        try{
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setTitle("Delete \"" + table2.getSelectionModel().getSelectedItem().getName() + "\"?");
            a.setHeaderText("Delete \"" + table2.getSelectionModel().getSelectedItem().getName() + "\"?");
            a.setContentText("Are you sure you want to delete this Associated Part?");
            a.showAndWait();
            if (a.getResult() == ButtonType.OK) {
                aparts.remove(table2.getSelectionModel().getSelectedItem());
                table2.setItems(aparts);
            } else {
                a.close();
            }
        } catch(NullPointerException e) {
            errorLabel2.setText("Nothing selected");
        }
    }
    public void setPartSearch() {
        String partSearchBox = partSearch.getText();
        table1.setItems(Inventory.searchParts(partSearchBox));
    }


    /**
     * Sets cancel button
     */
    public void setCancelButton() {
        Stage stage = (Stage) cancelButton.getScene().getWindow(); //gets the stage
        stage.close(); // closes it
    }

    /**
     * Resets all error labels
     */
    private void resetErrorLabels() {
        errorLabel1.setText("");
        errorLabel2.setText("");
    }

    /**
     * Sets textboxes to match the product's field data
     */
    private void setTextBoxes() {
        idText.setText("");
        nameText.setText("");
        invText.setText("");
        priceText.setText("");
        minText.setText("");
        maxText.setText("");
    }


    /**
     * Aligns columns of all parts table to the needed fields and sets the items in the table.
     */
    private void setUpTableTwo() {
        table2.setItems(newProduct.getAllAssociatedParts());
        table2PartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        table2PartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        table2InventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        table2PriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Aligns columns of associated parts table to the needed fields and sets the items in the table.
     */
    private void setUpTableOne() {
        table1.setItems(Inventory.getAllParts()); //Set table one to all possible parts
        table1PartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        table1PartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        table1InventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        table1PriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Generate the part ID for the part
     */
    public int generateProductId() {
        ObservableList<Product> mProducts = Inventory.getAllProducts();
        int id = 0;
        for(Product currProduct:mProducts) {
            if(currProduct.getId() >= id) {
                id = currProduct.getId() + 1;
            }
        }
        return id;
    }
}

