package Controllers;

import Inventory.Inventory;
import Part.InHouse;
import Part.Outsourced;
import Part.Part;
import Product.Product;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PartAddController {

    @FXML
    private AnchorPane addPartPane;

    @FXML
    private Label addPartLabel;

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
    private Label minLabel;

    @FXML
    private TextField idTextBox;

    @FXML
    private TextField nameTextBox;

    @FXML
    private TextField invTextBox;

    @FXML
    private TextField priceTextBox;

    @FXML
    private TextField maxTextBox;

    @FXML
    private TextField machineIdTextBox;

    @FXML
    private TextField minTextBox;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    private RadioButton inHouseRadio;

    @FXML
    private ToggleGroup PartToggleGroup;

    @FXML
    private RadioButton outSourcedRadio;

    @FXML
    private Label idOrCompanyLabel;

    @FXML
    private Label errorMessage2;

    @FXML
    private Label errorMessage1;

    @FXML
    private Label successMessage;

    @FXML
    private Label errorMessage3;


    /**
     * Adds functionality to the inHouseRadioButton
     */
    public void inHouseRadioListener() {
        if (inHouseRadio.isSelected()) {
            idOrCompanyLabel.setText("Machine ID"); // Changes last label in the form to Machine ID
        }
    }


    /**
     * Adds functionality to the outSourceRadioButton
     */
    public void outSourcedRadioListener() {
        if (outSourcedRadio.isSelected()) {
            idOrCompanyLabel.setText("Company"); // Changes last label in the form to Company
        }
    }


    /**
     * Adds functionality to the save button
     */
    public void setSaveButton(ActionEvent event) {
        clearErrors(); // Clears errors

        boolean error = false; // Flag that indicates if we have errors or not.

        //Get user input from text fields as strings.
        String name = nameTextBox.getText();
        String stringInv = invTextBox.getText();
        String stringPrice = priceTextBox.getText();
        String stringMax = maxTextBox.getText();
        String stringMin = minTextBox.getText();
        String companyName = machineIdTextBox.getText(); // Converted to int later if it's for an Parts.InHouse object

        try {
            //Parses the strings to int/double
            int inv = Integer.parseInt(stringInv);
            double price = Double.parseDouble(stringPrice);
            int max = Integer.parseInt(stringMax);
            int min = Integer.parseInt(stringMin);
            int id = generatePartId();
            if(min > max) {
                errorMessage1.setText("Max higher than Min"); // Ensures max cannot be higher than min
                error = true; //flag
            }
            if(inv > max || inv < min) {
                errorMessage3.setText("Inventory must be between Min and Max");
                        error = true; //flag
            }

            // If InHouse Parts
            if (inHouseRadio.isSelected()) {
                int machineId = Integer.parseInt(companyName); //Parse machineID from companyName string.
                InHouse newPart = new InHouse(id, name, price, inv, min, max, machineId); //newPart made of the user input
                if(!error) { // If no error
                    Inventory.addPart(newPart); // Adds part to the inventory
                    Stage stage = (Stage) cancelButton.getScene().getWindow(); //gets the stage
                    stage.close(); // closes it
                }
            }

            // If OutSourced Part
            if (outSourcedRadio.isSelected()) {
                Outsourced newPart = new Outsourced(id, name, price, inv, min, max, companyName); //newPart made of user input
                if(!error) { // If no error
                    Inventory.addPart(newPart); // Adds part to the inventory
                    Stage stage = (Stage) cancelButton.getScene().getWindow(); //gets the stage
                    stage.close(); // closes it
                }
            }
        }
        catch(NumberFormatException e) {
            errorMessage2.setText("Number Format Problem " + e.getMessage()); // For number format exception
            error = true; // flag
            successMessage.setText("");
        }
    }


    /**
     * Adds functionality to the cancel button
     */
    public void setCancelButton(ActionEvent event) throws Exception {
        Stage stage = (Stage) cancelButton.getScene().getWindow(); //gets the stage
        stage.close(); // closes it

    }


    /**
     * Clears all errors
     */
    private void clearErrors() {
        errorMessage1.setText("");
        errorMessage2.setText("");
    }


    /**
     * Generate the part ID for the part
     */
    public int generatePartId() {
        ObservableList<Part> mParts = Inventory.getAllParts();
        int id = 0;
        for(Part currPart:mParts) {
            if(currPart.getId() >= id) {
                id = currPart.getId() + 1;
            }
            }
        return id;
    }

    /**
     * Clears the text in all of the text boxes
     */
    public void clearText() {
        nameTextBox.setText("");
        invTextBox.setText("");
        priceTextBox.setText("");
        maxTextBox.setText("");
        minTextBox.setText("");
        machineIdTextBox.setText("");
    }
}

