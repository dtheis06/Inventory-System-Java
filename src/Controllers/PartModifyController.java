package Controllers;

import Part.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import Part.InHouse;
import Part.Outsourced;
import Inventory.Inventory;


public class PartModifyController {

    private Part selectedPart;
    private InHouse inHousePart;
    private Outsourced outsourcedPart;
    private int originalIndex;

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
     * Imports data from MainViewController and sets the textfields
     */
    public void initData(Part part, int index) {
        originalIndex = index;
        selectedPart = part;

        //If the part modified is an InHouse Part
        if (part instanceof InHouse) {
            inHouseRadio.setSelected(true);
            inHousePart = (InHouse) part;
            machineIdTextBox.setText(String.valueOf(inHousePart.getMachineId()));
        }

        //If the part modified is an Outsourced Part
        if (part instanceof Outsourced) {
            outSourcedRadio.setSelected(true);
            outsourcedPart = (Outsourced) part;
            machineIdTextBox.setText(outsourcedPart.getCompanyName());
            idOrCompanyLabel.setText("Company");
        }
        //Set the text fields with the imported data
        setTextBoxes();
    }

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
     * Adds functionality to the cancel button
     */
    @FXML
    public void setCancelButton(ActionEvent event) throws Exception {
        Stage stage = (Stage) cancelButton.getScene().getWindow(); //gets the stage
        stage.close(); // closes it
    }


    /**
     * Adds functionality to the save button
     */
    @FXML
    void setSaveButton(ActionEvent event) {
        boolean error = false;
        String stringId = idTextBox.getText();
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
            int id = Integer.parseInt(stringId);
            if(min > max) {
                errorMessage1.setText("Max higher than Min"); // Ensures max cannot be higher than min
                successMessage.setText("");
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
                    Inventory.updatePart(originalIndex,newPart); // Adds part to the inventory
                    Stage stage = (Stage) saveButton.getScene().getWindow();
                    stage.close();
                }
            }

            // If OutSourced Part
            if (outSourcedRadio.isSelected()) {
                Outsourced newPart = new Outsourced(id, name, price, inv, min, max, companyName); //newPart made of user input
                if(!error) { // If no error
                    Inventory.updatePart(originalIndex,newPart); // Adds part to the inventory
                    Stage stage = (Stage) saveButton.getScene().getWindow();
                    stage.close();
                }
            }
        }
        catch(NumberFormatException e) {
            errorMessage2.setText("Number Format Error " + e.getMessage()); // For number format exception
            error = true; // flag
        }
    }

    /**
     * Clears all errors
     */
    private void clearErrors() {
        errorMessage1.setText("");
        errorMessage2.setText("");
    }

    /**
     * Sets textboxes to match the part's data
     */
    private void setTextBoxes() {
        idTextBox.setText(String.valueOf(selectedPart.getId()));
        nameTextBox.setText(selectedPart.getName());
        invTextBox.setText(String.valueOf(selectedPart.getStock()));
        priceTextBox.setText(String.valueOf(selectedPart.getPrice()));
        minTextBox.setText(String.valueOf(selectedPart.getMin()));
        maxTextBox.setText(String.valueOf(selectedPart.getMax()));
    }
}
