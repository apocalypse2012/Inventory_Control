/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Inventory_Control.View_Controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import Inventory_Control.Model.Inventory;
import Inventory_Control.Model.Part;
import Inventory_Control.Model.Product;
import Inventory_Control.Model.ViewablePart;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.ToggleButton;

enum Screen_Mode 
{
    ADD, MODIFY;
}

/**
 * FXML Controller class
 *
 * @author Raymond Stewart
 */
public class Main_ScreenController //implements Initializable 
{

    // Gui elements
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane Main_Screen_pane;

    @FXML
    private Label label;

    @FXML
    private ToggleButton Parts_Search_btn;
    
    @FXML
    private TextField Parts_Text;
    
    @FXML
    private TableView< ViewablePart > Parts_Table;
    
    @FXML
    private TableColumn<Part, Integer> Parts_ID_Column;
    
    @FXML
    private TableColumn<Part, String> Parts_Name_Column;
    
    @FXML
    private TableColumn<Part, Integer> Parts_Level_Column;
    
    @FXML
    private TableColumn<Part, Double> Parts_Price_Column;
    
    @FXML
    private Button Add_Parts_btn;
    
    @FXML
    private Button Modify_Parts_btn;
    
    @FXML
    private Button Delete_Parts_btn;
    
    @FXML
    private ToggleButton Products_Search_btn;
    
    @FXML
    private TextField Products_Text;
    
    @FXML
    private TableView<Product> Products_Table;

    @FXML
    private TableColumn<Product, Integer> Products_ID_Column;
    
    @FXML
    private TableColumn<Product, String> Products_Name_Column;
    
    @FXML
    private TableColumn<Product, Integer> Products_Level_Column;
    
    @FXML
    private TableColumn<Product, Double> Products_Price_Column;
    
    @FXML
    private Button Add_Products_btn;
    
    @FXML
    private Button Modify_Products_btn;
    
    @FXML
    private Button Delete_Products_btn;
    
    @FXML
    private Button exit_btn;
    
    // Reference to main application.
    private Inventory modelInventory;
    
    /*
     * The Constructor.
     */
    public Main_ScreenController()
    {
    }
        
    @FXML
    private void handleButton_AddPart(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Part_Screen.fxml"));
        Parent addParts = loader.load();
        
        Scene partScene = new Scene(addParts);
        Stage partStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        partStage.hide();
        partStage.setScene(partScene);
        
        partStage.show();
    }
    
    @FXML
    private void handleButton_AddProduct(ActionEvent event) throws IOException
    {        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Product_Screen.fxml"));
        Parent addPoducts = loader.load();
        
        Scene productScene = new Scene(addPoducts);
        Stage productStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        productStage.hide();
        productStage.setScene(productScene);
        
        productStage.show();
    }
    
    @FXML
    private void handleButton_ModPart(ActionEvent event) throws IOException
    {
        ViewablePart thePart = Parts_Table.getSelectionModel().getSelectedItem();            
        // Open the panel if there is a valid part to modify.
        if (thePart != null)
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Part_Screen.fxml"));
            Parent modParts = loader.load();

            Scene partScene = new Scene(modParts);
            Stage partStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            partStage.hide();
            partStage.setScene(partScene);

            Part_ScreenController partController = loader.<Part_ScreenController>getController();
            int thePartIndex = Integer.parseInt(thePart.getViewID());
            partController.setPartToModify(thePartIndex);
            partStage.show();
        }
        else
        {
            // Warn that there is no part to modify
            Alert errorAlert = new Alert(Alert.AlertType.WARNING);
            errorAlert.setTitle("Part modify warning");
            errorAlert.setHeaderText("No Part Selected.");
            errorAlert.setContentText("A part must be selected to be modified.");
            errorAlert.showAndWait();
        }
    }
    
    @FXML
    private void handleButton_ModProduct(ActionEvent event) throws IOException
    {
        Product theProduct = Products_Table.getSelectionModel().getSelectedItem();
        // Open the panel if there is a valid product to modify.
        if (theProduct != null)
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Product_Screen.fxml"));
            Parent modPoducts = loader.load();
            
            Scene productScene = new Scene(modPoducts);
            Stage productStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            productStage.hide();
            productStage.setScene(productScene);
                    
            Product_ScreenController productController = loader.<Product_ScreenController>getController();
            int theProductIndex = Integer.parseInt(theProduct.getViewID());
            productController.setProductToModify(theProductIndex);
            productStage.show();
        }
        else
        {
            // Warn that there is no product to modify
            Alert errorAlert = new Alert(Alert.AlertType.WARNING);
            errorAlert.setTitle("product modify warning");
            errorAlert.setHeaderText("No product Selected.");
            errorAlert.setContentText("A product must be selected to be modified.");
            errorAlert.showAndWait();
        }
    }
    
    @FXML
    private void handleButton_DelPart() throws IOException
    {
        ViewablePart thePart = Parts_Table.getSelectionModel().getSelectedItem();
        
        // Prevent null pointer exceptions on delete.
        if (thePart != null)
        {

            // Confirm the Delete
            Alert doDeleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
            doDeleteAlert.initModality(Modality.NONE);
            doDeleteAlert.setTitle("Delete Part");
            doDeleteAlert.setHeaderText("Confirm Delete!");
            doDeleteAlert.setContentText("Are you sure you want to delete this part? " + thePart.getViewName());
            Optional<ButtonType> doExit = doDeleteAlert.showAndWait();

            if (doExit.get() == ButtonType.OK)
            {
                modelInventory.removePart(thePart);
            }
            else
            {
                System.out.println("Delete Canceled.");
            }
            
        }
        else
        {
            // Warn that there is no part to delete
            Alert errorAlert = new Alert(Alert.AlertType.WARNING);
            errorAlert.setTitle("Part delete warning");
            errorAlert.setHeaderText("No Part Selected.");
            errorAlert.setContentText("A part must be selected to be deleted.");
            errorAlert.showAndWait();
        }
    }
    
    @FXML
    private void handleButton_DelProduct() throws IOException
    {
        Product theProduct = Products_Table.getSelectionModel().getSelectedItem();
        
        // Prevent null pointer exceptions on delete.
        if (theProduct != null)
        {
            
            int partCount = theProduct.getPartCount();

            // Early out if product cant be deleted.
            if (partCount > 0)
            {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Delete Product Error");
                errorAlert.setHeaderText("Cannot delete the selected product");
                errorAlert.setContentText(theProduct.getName() + " contains " + Integer.toString(partCount) + " parts and cannot be deleted.");
                errorAlert.showAndWait();
                return;
            }

            // Confirm the Delete
            Alert doDeleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
            doDeleteAlert.initModality(Modality.NONE);
            doDeleteAlert.setTitle("Delete Product");
            doDeleteAlert.setHeaderText("Confirm Delete!");
            doDeleteAlert.setContentText("Are you sure you want to delete this product? " + theProduct.getViewName());
            Optional<ButtonType> doExit = doDeleteAlert.showAndWait();

            if (doExit.get() == ButtonType.OK)
            {
                modelInventory.removeProduct(theProduct);
            }
            else
            {
               System.out.println("Delete Canceled.");
            }
            
        }
        else
        {
            // Warn that there is no product to delete
            Alert errorAlert = new Alert(Alert.AlertType.WARNING);
            errorAlert.setTitle("Product delete warning");
            errorAlert.setHeaderText("No Product Selected.");
            errorAlert.setContentText("A product must be selected to be deleted.");
            errorAlert.showAndWait();
        }
    }    
    @FXML
    private void handleButton_Exit(ActionEvent event)
    {
        Alert doExitAlert = new Alert(Alert.AlertType.CONFIRMATION);
        doExitAlert.initModality(Modality.NONE);
        doExitAlert.setTitle("Exit Inventory Control");
        doExitAlert.setHeaderText("Confirm exit!");
        doExitAlert.setContentText("Do you want to exit?");
        Optional<ButtonType> doExit = doExitAlert.showAndWait();
        
        if (doExit.get() == ButtonType.OK)
        {
            System.exit(0);
        }
        else
        {
            System.out.println("Exit refused.");
        }
    }
    
    /**
     * Initializes the controller class.
     */
    //    @Override
    //    public void initialize(URL url, ResourceBundle rb) 
    @FXML
    public void initialize()
    {
        modelInventory = new Inventory();
        
        // Set parts table column data accessors.
        Parts_ID_Column.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        Parts_Name_Column.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        Parts_Level_Column.setCellValueFactory(cellData -> cellData.getValue().stockProperty().asObject());
        Parts_Price_Column.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        
        // Set products table column data accessors.
        Products_ID_Column.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        Products_Name_Column.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        Products_Level_Column.setCellValueFactory(cellData -> cellData.getValue().stockProperty().asObject());
        Products_Price_Column.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());

        /////////////////////////////////////////////////////////////////////////////////
        // Set up Automatic filtering. Filter by string and enable or disable by search button.
        // Wrap the ObservableLists in a FilteredList (initially display all data).
        FilteredList<ViewablePart> filteredDataParts = new FilteredList<> (modelInventory.getAllParts(), p -> true);
        FilteredList<Product> filteredDataProducts = new FilteredList<> (modelInventory.getAllProducts(), p -> true);
        
        // PARTS: Set the filter Predicate whenever the filter changes.
        Parts_Text.textProperty().addListener(( observable, oldValue, newValue) -> 
        {
            filteredDataParts.setPredicate( ViewablePart -> 
            {
                // If filter text is empty, display all Parts.
                if (newValue == null || newValue.isEmpty()) 
                {
                    return true;
                }
                
                // Compare name and ID of every Part with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                
                if (ViewablePart.getViewName().toLowerCase().contains(lowerCaseFilter)) 
                {
                    return true; // Filter matches name.
                } 
                else if (ViewablePart.getViewID().toLowerCase().contains(lowerCaseFilter)) 
                {
                    return true; // Filter matches ID.
                }
                return false; // Does not match.
            });
        });
  
        // PRODUCTS: Set the filter Predicate whenever the filter changes.
        Products_Text.textProperty().addListener(( observable, oldValue, newValue) -> 
        {
            filteredDataProducts.setPredicate( Product -> 
            {
                // If filter text is empty, display all Products.
                if (newValue == null || newValue.isEmpty()) 
                {
                    return true;
                }
                
                // Compare name and ID of every Product with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                
                //RTS: Turn this back on when Products are viewable.
                if (Product.getViewName().toLowerCase().contains(lowerCaseFilter)) 
                {
                    return true; // Filter matches name.
                } 
                else if (Product.getViewID().toLowerCase().contains(lowerCaseFilter)) 
                {
                    return true; // Filter matches ID.
                }
                return false; // Does not match.
            });
        });        
        
        // Wrap the FilteredList in a SortedList. 
        SortedList<ViewablePart> sortedDataParts = new SortedList<>(filteredDataParts);
        SortedList<Product> sortedDataProducts = new SortedList<>(filteredDataProducts);
        
        // Bind the SortedList comparator to the TableView comparator.
        sortedDataParts.comparatorProperty().bind(Parts_Table.comparatorProperty());
        sortedDataProducts.comparatorProperty().bind(Products_Table.comparatorProperty());
        
        // Add data to the Parts table on toggle.
        Parts_Search_btn.setOnAction((event) -> 
        {
            if (Parts_Search_btn.isSelected())
            {
                // Add sorted (and filtered) data to the tables.
               Parts_Table.setItems(sortedDataParts);
            }
            else
            {
                // Add Raw data to the tables.
                Parts_Table.setItems(modelInventory.getAllParts());
            }
        });
        Parts_Search_btn.fire();
        
        // Add data to the Products table on toggle.
        Products_Search_btn.setOnAction((event) -> 
        {
            if (Products_Search_btn.isSelected())
            {
                // Add sorted (and filtered) data to the tables.
                Products_Table.setItems(sortedDataProducts);
            }
            else
            {
                // Add Raw data to the tables.
                Products_Table.setItems(modelInventory.getAllProducts());
            }
        });
        Products_Search_btn.fire();

    }    

}
