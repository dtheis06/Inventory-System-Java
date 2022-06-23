package Controllers;

import Part.Part;
import Part.InHouse;
import Part.Outsourced;
import Inventory.Inventory;
import Product.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private TableView<Part> PartTable;

    @FXML
    private TableColumn<Part, Integer> PartIDColumn;

    @FXML
    private TableColumn<Part, String> PartNameColumn;

    @FXML
    private TableColumn<Part, Integer> PriceInventoryColumn;

    @FXML
    private TableColumn<Part, Double> PartPriceColumn;

    @FXML
    private TableView<Product> ProductTable;

    @FXML
    private TableColumn<Product, Integer> ProductIDColumn;

    @FXML
    private TableColumn<Product, String> ProductNameColumn;

    @FXML
    private TableColumn<Product, Integer> ProductInventoryColumn;

    @FXML
    private TableColumn<Product, Integer> ProductPriceColumn;

    @FXML
    private Label PartsLabel;

    @FXML
    private Button PartAddButton;

    @FXML
    private Button PartModifyButton;

    @FXML
    private Button PartDeleteButton;

    @FXML
    private Button ProductAddButton;

    @FXML
    private Button ProductModifyButton;

    @FXML
    private Button ProductDeleteButton;

    @FXML
    private Button ProgramExitButton;

    @FXML
    private Label ProductsLabel;

    @FXML
    private TextField ProductSearch;

    @FXML
    private TextField PartSearch;

    @FXML
    private Label partErrorLabel;

    @FXML
    private Label productErrorLabel;

    /**
     * Initializes the Main.fxml with the main controller. The tables are set up and match the needed part/product fields.
     * Samples are added to the inventory as well.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Sets up the Part Table Columns with the Part fields
        PartTable.setItems(Inventory.getAllParts());
        PartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        PriceInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Adds Sample parts to the Inventory
        InHouse samplePart1 = new InHouse(0,"Alpha",2,5,1,6,2);
        Outsourced samplePart2 = new Outsourced(1,"Beta",4,3,2,4,"Sample Company");
        InHouse samplePart3 = new InHouse(2,"Part3", 3, 4, 3, 5, 6);
        Inventory.addPart(samplePart1);
        Inventory.addPart(samplePart2);
        Inventory.addPart(samplePart3);

        //Sets up the Product Table Columns with the Product fields
        ProductTable.setItems(Inventory.getAllProducts());
        ProductIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        ProductNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ProductInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ProductPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        Product sampleProduct1 = new Product(0,"Product",3,4,4,5);
        Product sampleProduct2 = new Product(1,"Sample",4,3,2,4);
        Product sampleProduct3 = new Product(2,"Stuff", 3, 5, 5, 5);
        Inventory.addProduct(sampleProduct1);
        Inventory.addProduct(sampleProduct2);
        Inventory.addProduct(sampleProduct3);
        sampleProduct1.addAssociatedPart(samplePart1);
    }


    /**
     * Adds functionality to the Add Part button
     */
    public void setPartAddButton(ActionEvent event) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("/Fxml/PartAdd.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Adds functionality to the Delete Part button
     */
    public void setPartDeleteButton(ActionEvent event) throws Exception {
        try{
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Delete \"" + PartTable.getSelectionModel().getSelectedItem().getName() + "\"?");
        a.setHeaderText("Delete \"" + PartTable.getSelectionModel().getSelectedItem().getName() + "\"?");
        a.setContentText("Are you sure you want to delete this part?");
        a.showAndWait();
            if (a.getResult() == ButtonType.OK) {
                Inventory.deletePart(PartTable.getSelectionModel().getSelectedItem());
                partErrorLabel.setText("");
            } else {
                a.close();
                partErrorLabel.setText("");
            }
        } catch(NullPointerException e) {
            partErrorLabel.setText("Nothing selected");
        }
    }


    /**
     * Adds functionality to the Modify Part button
     */
    public void setPartModifyButton(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/PartModify.fxml"));
            Parent parent = loader.load();

            Part part = PartTable.getSelectionModel().getSelectedItem();
            int index = PartTable.getSelectionModel().getSelectedIndex();
            Scene scene = new Scene(parent);
            PartModifyController controller2 = loader.getController();
            controller2.initData(part, index);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            partErrorLabel.setText("");
        }
        catch(NullPointerException e) {
            partErrorLabel.setText("Nothing selected");
        }
    }


    /**
     * Adds functionality to the Part Table's search box
     */
    public void setPartSearch(ActionEvent event) throws Exception {
        String partSearch = PartSearch.getText();
        PartTable.setItems(Inventory.searchParts(partSearch));
    }


    /**
     * Adds functionality to the Add Product button
     */
    public void setProductAddButton(ActionEvent event) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("/Fxml/ProductAdd.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Adds functionality to the Delete Product button
     */
    public void setProductDeleteButton(ActionEvent event) throws IOException {
        try{
            Product deletedProduct = ProductTable.getSelectionModel().getSelectedItem();
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Delete \"" + ProductTable.getSelectionModel().getSelectedItem().getName() + "\"?");
        a.setHeaderText("Delete \"" + ProductTable.getSelectionModel().getSelectedItem().getName() + "\"?");
        a.setContentText("Are you sure you want to delete this Product?");
        a.showAndWait();
            if (a.getResult() == ButtonType.OK) {
                if(deletedProduct.getAllAssociatedParts().isEmpty()) {
                    Inventory.deleteProduct(deletedProduct);
                }
                else {
                    productErrorLabel.setText(deletedProduct.getName() + " contains parts so cannot be deleted" );
                }
            } else {
                a.close();
            }
        } catch(NullPointerException e) {
            productErrorLabel.setText("Nothing selected");
        }
    }


    /**
     * Adds functionality to the Product Modify button
     */
    public void setProductModifyButton(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/ProductModify.fxml"));
            Parent parent = loader.load();

            Product product = ProductTable.getSelectionModel().getSelectedItem();
            int index = ProductTable.getSelectionModel().getSelectedIndex();
            Scene scene = new Scene(parent);
            ProductModifyController controller2 = loader.getController();
            controller2.initData(product, index);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            productErrorLabel.setText("");
        }
        catch(NullPointerException e) {
            productErrorLabel.setText("Nothing selected");
        }
    }


    /**
     * Adds functionality to the Product Table's search box
     */
    public void setProductSearch(ActionEvent event) throws Exception {
        String productSearch = ProductSearch.getText();
        ProductTable.setItems(Inventory.searchProducts(productSearch));
    }


    /**
     * Adds functionality to the program's exit button
     */
    public void setProgramExitButton() {
        Stage stage = (Stage) ProgramExitButton.getScene().getWindow(); //gets the stage
        stage.close(); // closes it
    }
}

