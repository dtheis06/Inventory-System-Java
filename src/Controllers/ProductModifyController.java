package Controllers;

import Inventory.Inventory;
import Part.Part;
import Product.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ProductModifyController {
    private int originalIndex; //Imported product's index
    private Product selectedProduct; //Copy of the imported Product
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
    private Label ModifyProductLabel;

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

    @FXML
    private Label errorMessage3;


    /**
     * Initializes the data by importing the selectedProduct and originalIndex from the main controller
     * Also sets up the two tables and shows the selectedProduct's field data
     * Fill
     */
    public void initData(Product product, int index) {
        originalIndex = index; //Copies the imported object's index
        selectedProduct = product;;//Copies the imported object

        setUpTableOne(); //Sets up table 1 and align the columns with the fields
        setUpTableTwo(); //Sets up table 2 and align the columns with the fields
        setTextBoxes(); // Sets text boxes to show the selected Product's information

        for(Part currPart: selectedProduct.getAllAssociatedParts()) { //aparts is a placeholder so everything doesn't change if you don't hit save
            aparts.add(currPart);  //To get the placeholder to hold all the parts the modified product holds
        }
    }


    /**
     * Sets Add Button
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
            int id = Integer.parseInt(idText.getText());
            if(max < min) { //check
                errorLabel2.setText("Min has to be lower than Max");
                error = true;
            }
            if(inv > max || inv < min) {
                errorMessage3.setText("Inventory must be between Min and Max");
                error = true; //flag
            }

            Product updatedProduct = new Product(id, nameText.getText(), price, inv, min, max); //Create new product with updated info

            for (int i = 0; i < aparts.size(); i++) {
                updatedProduct.addAssociatedPart(aparts.get(i));  //Adds part placeholder data to the updatedProduct
            }
            if(!error) { // If no error
                Inventory.updateProduct(originalIndex, updatedProduct); //Add the updatedProduct to the inventory
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
        idText.setText(String.valueOf(selectedProduct.getId()));
        nameText.setText(selectedProduct.getName());
        invText.setText(String.valueOf(selectedProduct.getStock()));
        priceText.setText(String.valueOf(selectedProduct.getPrice()));
        minText.setText(String.valueOf(selectedProduct.getMin()));
        maxText.setText(String.valueOf(selectedProduct.getMax()));
    }


    /**
     * Aligns columns of all parts table to the needed fields and sets the items in the table.
     */
    private void setUpTableTwo() {
        table2.setItems(selectedProduct.getAllAssociatedParts());
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
}
