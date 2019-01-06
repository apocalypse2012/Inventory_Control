/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Inventory_Control.View_Controller;

import Inventory_Control.Model.Inventory;
import Inventory_Control.Model.Part;
import Inventory_Control.Model.Product;
import Inventory_Control.Model.ViewablePart;
import java.io.IOException;
import java.util.Optional;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author Raymond Stewart
 */
public class Product_ScreenController 
{
    @FXML
    private AnchorPane Products_pane;
    
    @FXML
    private Label Product_Label;
    
    @FXML
    private TextField ID_Text;
    
    @FXML
    private TextField Name_Text;
    
    @FXML
    private TextField Inv_Text;
    
    @FXML
    private TextField Price_Text;
    
    @FXML
    private TextField Max_Text;
    
    @FXML
    private TextField Min_Text;
    
    @FXML
    private ToggleButton Search_btn;
    
    @FXML
    private TextField Search_Text;
    
    @FXML
    private TableView<ViewablePart> QueryParts_tbl;
    
    @FXML
    private TableColumn<Part, Integer> QueryParts_ID_Column;
    
    @FXML
    private TableColumn<Part, String> QueryParts_Name_Column;
    
    @FXML
    private TableColumn<Part, Integer> QueryParts_Level_Column;
    
    @FXML
    private TableColumn<Part, Double> QueryParts_Price_Column;
    
    @FXML
    private Button Add_btn;
    
    @FXML
    private TableView<ViewablePart> ProductParts_tbl;
    
    @FXML
    private TableColumn<Part, Integer> ProdParts_ID_Column;
    
    @FXML
    private TableColumn<Part, String> ProdParts_Name_Column;
    
    @FXML
    private TableColumn<Part, Integer> ProdParts_Level_Column;
    
    @FXML
    private TableColumn<Part, Double> ProdParts_Price_Column;
    
    @FXML
    private Button Delete_btn;
    
    @FXML
    private Button Save_btn;
    
    @FXML
    private Button Cancel_btn;
    
    // Screen mode to identify this as an add or modify UI...
    private Screen_Mode Current_Mode;
    private Product ProductToModify;
    private Inventory modelInventory;
    
    /*
     * The Constructor.
     */
    public Product_ScreenController()
    {
    }    
    
    
    @FXML
    private void handleButton_Cancel_btn(ActionEvent event) throws IOException
    {
        Alert confirmCancel = new Alert(Alert.AlertType.CONFIRMATION);
        confirmCancel.initModality(Modality.NONE);
        if (Current_Mode == Screen_Mode.ADD)
        {
            confirmCancel.setTitle("Cancel Add Part");
            confirmCancel.setHeaderText("Are you sure you want to cancel?\nPart will not be saved...");
        }
        else
        {
            confirmCancel.setTitle("Cancel Modify Part");
            confirmCancel.setHeaderText("Are you sure you want to cancel?\nChange will not be saved...");
        }
        Optional<ButtonType> isConfirmed = confirmCancel.showAndWait();
        
        if (isConfirmed.get() == ButtonType.OK)
        {
            System.out.println("Cancel has been confirmed.");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main_Screen.fxml"));
            Parent returnScreen = loader.load();

            Scene returnScene = new Scene(returnScreen);
            Stage returnStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            returnStage.hide();
            returnStage.setScene(returnScene);
            returnStage.show();
        }
        else
        {
            System.out.println("Cancel has been refused.");
        }
    }
    
    @FXML
    private void handleButton_Save_btn(ActionEvent event) throws IOException 
    {
        int productPartCount = ProductToModify.getPartCount();
        
        // Early out if product has no parts.
        if (productPartCount == 0)
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Save Product Error");
            errorAlert.setHeaderText("Cannot Save the product");
            errorAlert.setContentText(ProductToModify.getName() + " contains " + Integer.toString(productPartCount) + " parts and cannot be saved.");
            errorAlert.showAndWait();
            return;
        }
        
        // Early out if Product price is less than the sum of part prices...
        double partTotal = 0.00d;
        for (ViewablePart p: ProductToModify.getAllParts())
        {
            partTotal += Double.parseDouble( p.getViewPrice());
        }
        if (partTotal > ProductToModify.getPrice())
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Save Product Error");
            errorAlert.setHeaderText("Cannot Save the product");
            errorAlert.setContentText("Product price ($" + ProductToModify.getViewPrice() + 
                    ") is less than the total price of the parts in the product ($" + 
                    String.format("%.2f", partTotal) + ").");  
            errorAlert.showAndWait();
            return;
        }
        
        // Early out if Stock is not within range.
        int stockVal = Integer.parseInt(ProductToModify.getViewStock());
        int maxVal = Integer.parseInt(ProductToModify.getViewMax());
        int minVal = Integer.parseInt(ProductToModify.getViewMin());
        if (stockVal > maxVal || stockVal < minVal)
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Save Product Error");
            errorAlert.setHeaderText("Cannot Save the product");
            errorAlert.setContentText("Product inventory (" + ProductToModify.getViewStock() + 
                    ") is less than the minimum or more than the maximum allowed. \n\nMax: " + 
                    ProductToModify.getViewMax() + "\nMin: " + ProductToModify.getViewMin() );  
            errorAlert.showAndWait();
            return;
        }
        
        Alert confirmSave = new Alert(AlertType.CONFIRMATION);
        confirmSave.initModality(Modality.NONE);
        confirmSave.setTitle("Save the Product");
        confirmSave.setHeaderText("Are you are ready to save?");
        Optional<ButtonType> isConfirmed = confirmSave.showAndWait();
        if (isConfirmed.get() == ButtonType.OK)
        {
            System.out.println("Save has been confirmed.");

            // Save the Part currently being edited.
            int theProductIndex = Integer.parseInt(ProductToModify.getViewID());
            System.out.println("theProductIndex: " + Integer.toString(theProductIndex));
            System.out.println("modelInventory.getProductCount(): " + Integer.toString(modelInventory.getProductCount()));
            if (Current_Mode == Screen_Mode.MODIFY)
            {
                modelInventory.replaceProduct(ProductToModify, theProductIndex);
            }
            else
            {
                modelInventory.insertProduct(ProductToModify, theProductIndex);
            }

            // Return to the Main editor screen.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main_Screen.fxml"));
            Parent returnScreen = loader.load();
            Scene returnScene = new Scene(returnScreen);
            Stage returnStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            returnStage.hide();
            returnStage.setScene(returnScene);           
            returnStage.show();
        }
        else
        {
            System.out.println("Save has been refused.");
        }
    }
    
    @FXML
    private void handleButton_Add_btn(ActionEvent event) throws IOException
    {
        // Add the selected Part.
        ViewablePart thePart = QueryParts_tbl.getSelectionModel().getSelectedItem();
        
        // Prevent null pointer exceptions on delete.
        if (thePart != null)
        {
            ProductToModify.addPart(thePart);
        }
        else
        {
            // Warn that there is no selected part to add the product
            Alert errorAlert = new Alert(Alert.AlertType.WARNING);
            errorAlert.setTitle("Part add warning");
            errorAlert.setHeaderText("No Part Selected.");
            errorAlert.setContentText("A part must be selected to be added to the Product's part list.");
            errorAlert.showAndWait();
        }
    }
    
    @FXML
    private void handdleButton_Delete_btn(ActionEvent event) throws IOException
    {
        // RTS: Product_ScreenController::handdleButton_Delete_btn() 
        // Confirm-Alert is only here to satisfy a literal interpretation of the rubric.
        // This should not be here. It is a great example of bad UX. A confirmation
        // dialog is appropriate when an action is destructive to a significant unit of
        // work. This is not the case here. A singular part assignment to a list 
        // presented side by side is less work than the confirmation dialog. Confimation
        // litterally costs every delete. Bad deletes more easily fixed by repeating the
        // assignment.
        // *however, there is a null pointer check requires on thePart.
        
        ViewablePart thePart = ProductParts_tbl.getSelectionModel().getSelectedItem();

        // Prevent null pointer exceptions on delete.
        if (thePart != null)
        {

            // Confirm the Delete
            Alert doDeleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
            doDeleteAlert.initModality(Modality.NONE);
            doDeleteAlert.setTitle("Delete Part");
            doDeleteAlert.setHeaderText("Confirm Delete!");
            doDeleteAlert.setContentText("Are you sure you want to delete this Part from the Product part list? " + thePart.getViewName());
            Optional<ButtonType> doDelete = doDeleteAlert.showAndWait();

            if (doDelete.get() == ButtonType.OK)
            {
                ProductToModify.removePart(thePart);
            }
            else
            {
                System.out.println("Delete has been refused.");
            }
            
        }
        else
        {
            // Warn that there is no selected part to delete
            Alert errorAlert = new Alert(Alert.AlertType.WARNING);
            errorAlert.setTitle("Part delete warning");
            errorAlert.setHeaderText("No Part Selected.");
            errorAlert.setContentText("A part must be selected to be deleted.");
            errorAlert.showAndWait();
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
        // Initialize to Add Product Panel. Can be overridden by setProductToModify()...
        modelInventory = new Inventory();
        ProductToModify = new<Product> Product();
        
        // RTS: replace with a 'next available' ID.
        ProductToModify.setViewID(Integer.toString(modelInventory.getNextAvailableProductID()));
        Current_Mode = Screen_Mode.ADD;
        Product_Label.setText("Add Product");
        
        // Set parts table column data accessors.
        ProdParts_ID_Column.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        ProdParts_Name_Column.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        ProdParts_Level_Column.setCellValueFactory(cellData -> cellData.getValue().stockProperty().asObject());
        ProdParts_Price_Column.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        
        // Set parts table column data accessors.
        QueryParts_ID_Column.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        QueryParts_Name_Column.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        QueryParts_Level_Column.setCellValueFactory(cellData -> cellData.getValue().stockProperty().asObject());
        QueryParts_Price_Column.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        
        /////////////////////////////////////////////////////////////////////////////////
        // Set up Automatic filtering. Filter by string and enable or disable by search button.
        // Wrap the ObservableLists in a FilteredList (initially display all data).
        FilteredList<ViewablePart> filteredDataParts = new FilteredList<> (modelInventory.getAllParts(), p -> true);
        
        // PARTS: Set the filter Predicate whenever the filter changes.
        Search_Text.textProperty().addListener(( observable, oldValue, newValue) -> 
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

        // Wrap the FilteredList in a SortedList. 
        SortedList<ViewablePart> sortedDataParts = new SortedList<>(filteredDataParts);
        
        // Bind the SortedList comparator to the TableView comparator.
        sortedDataParts.comparatorProperty().bind(QueryParts_tbl.comparatorProperty());
        
        // Add data to the Parts table on toggle.
        Search_btn.setOnAction((event) -> 
        {
            if (Search_btn.isSelected())
            {
                // Add sorted (and filtered) data to the tables.
               QueryParts_tbl.setItems(sortedDataParts);
            }
            else
            {
                // Add Raw data to the tables.
                QueryParts_tbl.setItems(modelInventory.getAllParts());
            }
        });
        Search_btn.fire();
        
        initProductView();
    }
     
    public Product getProductToModify()
    {
        return ProductToModify;
    }
       
    public void setProductToModify(int theIndex)
    {
        // Reinitialize to Modify Product Panel for the supplied product index. We use
        // an index here to gaurantee that the datamodel operated on is in scope.
        Product theProduct;
        if (theIndex < modelInventory.getProductCount())
        {
            theProduct = modelInventory.lookupProduct(theIndex);
        }
        else
        {
            throw new ArrayIndexOutOfBoundsException("There is no valid product for the given index.");
        }
        
        ProductToModify = new Product(theProduct);
        Current_Mode = Screen_Mode.MODIFY;
        Product_Label.setText("Modify Product");
        initProductView();
    }    
    
    private void initProductView()
    {
        // Collect data from the Product Model
        String partID = ProductToModify.getViewID();
        String partName = ProductToModify.getViewName();
        String partInv = ProductToModify.getViewStock();
        String partPrice = ProductToModify.getViewPrice();
        String partMin = ProductToModify.getViewMin();
        String partMax = ProductToModify.getViewMax();
        
        // Modify data view.
        ID_Text.setText(partID);
        Name_Text.setText(partName);
        Inv_Text.setText(partInv);
        Price_Text.setText(partPrice);
        Max_Text.setText(partMax);
        Min_Text.setText(partMin);
        
        // setup callbacks for screen events.
        ID_Text.setOnKeyReleased((event) -> {
            ProductToModify.setViewID(ID_Text.getText());
            System.out.println("ID_Text.setOnAction()");
        });
        Name_Text.setOnKeyReleased((event) -> {
            ProductToModify.setViewName(Name_Text.getText());
            System.out.println("ID_Text.setOnAction()");
        });        
        Inv_Text.setOnKeyReleased((event) -> {
            ProductToModify.setViewStock(Inv_Text.getText());
            System.out.println("Inv_Text.setOnAction()");
        });
        Price_Text.setOnKeyReleased((event) -> {
            ProductToModify.setViewPrice(Price_Text.getText());
            System.out.println("Price_Text.setOnAction()");
        });
        
        // Guarantee that Max is greater than min.
        Max_Text.setOnKeyReleased((event) -> {
            int theMin = Integer.parseInt(Min_Text.getText());
            int theMax = Integer.parseInt(Max_Text.getText());
            
            ProductToModify.setViewMax(Max_Text.getText());
            if (theMin > theMax)
            {
                String newMin = Integer.toString(theMax);
                ProductToModify.setViewMin(newMin);
                Min_Text.setText(newMin);
            }            
            System.out.println("Max_Text.setOnAction()");
        });
        Min_Text.setOnKeyReleased((event) -> {
            int theMin = Integer.parseInt(Min_Text.getText());
            int theMax = Integer.parseInt(Max_Text.getText());
            
            ProductToModify.setViewMin(Min_Text.getText());
            if (theMin > theMax)
            {
                String newMax = Integer.toString(theMin);
                ProductToModify.setViewMax(newMax);
                Max_Text.setText(newMax);
            }
            
            System.out.println("Min_Text.setOnAction()");
        });

        ProductParts_tbl.setItems(ProductToModify.getAllParts());
    }
         
}
