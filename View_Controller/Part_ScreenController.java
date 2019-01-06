/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Inventory_Control.View_Controller;

import Inventory_Control.InventoryControl;
import Inventory_Control.Model.InhousePart;
import Inventory_Control.Model.Inventory;
import Inventory_Control.Model.OutsourcedPart;
import Inventory_Control.Model.Part;
import java.io.IOException;
import java.util.Optional;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import Inventory_Control.Model.ViewablePart;

/**
 * FXML Controller class
 *
 * @author Raymond Stewart
 */
public class Part_ScreenController 
{
    @FXML
    private AnchorPane Parts_pane;

    @FXML
    private Label Part_Label;
    
    @FXML
    private ToggleGroup Category;
    
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
    private TextField Source_Text;
    
    @FXML
    private Label Source_Label;
    
    @FXML
    private RadioButton inhouse_Radio;
    
    @FXML
    private RadioButton outsourced_Radio;
    
    @FXML
    private Button Save_btn;
    
    @FXML
    private Button Cancel_btn;
    
    // Screen mode to identify this as an add or modify UI...
    private Screen_Mode Current_Mode;
    private ViewablePart PartToModify;
    private Inventory modelInventory;
    
    /*
     * The Constructor.
     */
    public Part_ScreenController()
    {
    }

    @FXML
    private void handleButton_Cancel_btn(ActionEvent event) throws IOException
    {
        Alert confirmCancel = new Alert(AlertType.CONFIRMATION);
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
            // Return to the Main editor screen.
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
        
        // Early out if Stock is not within range.
        int stockVal = Integer.parseInt(PartToModify.getViewStock());
        int maxVal = Integer.parseInt(PartToModify.getViewMax());
        int minVal = Integer.parseInt(PartToModify.getViewMin());
        if (stockVal > maxVal || stockVal < minVal)
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Save Part Error");
            errorAlert.setHeaderText("Cannot Save the Part");
            errorAlert.setContentText("Part inventory (" + PartToModify.getViewStock() + 
                    ") is less than the minimum or more than the maximum allowed. \n\nMax: " + 
                    PartToModify.getViewMax() + "\nMin: " + PartToModify.getViewMin() );  
            errorAlert.showAndWait();
            return;
        }
        
        Alert confirmSave = new Alert(AlertType.CONFIRMATION);
        confirmSave.initModality(Modality.NONE);
        confirmSave.setTitle("Save the Part");
        confirmSave.setHeaderText("Are you are ready to save?");
        Optional<ButtonType> isConfirmed = confirmSave.showAndWait();
        if (isConfirmed.get() == ButtonType.OK)
        {
            System.out.println("Save has been confirmed.");

            // Save the Part currently being edited.
            int thePartIndex = Integer.parseInt(PartToModify.getViewID());
            System.out.println("thePartIndex: " + Integer.toString(thePartIndex));
            System.out.println("modelInventory.getPartCount(): " + Integer.toString(modelInventory.getPartCount()));
            if (Current_Mode == Screen_Mode.MODIFY)
            {
                modelInventory.replacePart(PartToModify, thePartIndex);
            }
            else
            {
                modelInventory.insertPart(PartToModify, thePartIndex);
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
    
        
    /**
     * Initializes the controller class.
     */
    //    @Override
    //    public void initialize(URL url, ResourceBundle rb) 
    @FXML
    public void initialize()
    {
        // Initialize to Add Part Panel for an in-house part. Can be overridden by setPartToModify()...
        modelInventory = new Inventory();
        PartToModify = new<InhousePart> InhousePart();
        
        // RTS: replace with a 'next available' ID.
        
        PartToModify.setViewID(Integer.toString(modelInventory.getNextAvailablePartID()));
        Current_Mode = Screen_Mode.ADD;
        Part_Label.setText("Add Part");
        initPartView();
    }    
    
    public ViewablePart getPartToModify()
    {
        return PartToModify;
    }

    public void setPartToModify(int theIndex)
    {
        // Reinitialize to Modify Part Panel for the supplied part index. We use
        // an index here to gaurantee that the datamodel operated on is in scope.
        ViewablePart thePart;
        if (theIndex < modelInventory.getPartCount())
        {
            thePart = modelInventory.lookupPart(theIndex);       
        }
        else
        {
            throw new ArrayIndexOutOfBoundsException("There is no valid part for the given index.");
        }
        
        // Modify radio button and Source text to reflect the class of the supplied part.
        switch (thePart.getViewType()) 
        {
            case "Inhouse":
                InhousePart inhousePart = (InhousePart) thePart;
                PartToModify = new InhousePart(inhousePart);
                PartToModify.setViewSource(inhousePart.getViewSource());
                break;
            case "Outsource":
                OutsourcedPart outsourcedPart = (OutsourcedPart) thePart;
                PartToModify = new OutsourcedPart(outsourcedPart);
                PartToModify.setViewSource(outsourcedPart.getViewSource());
                break;
             default:
                // If this happens the Viewable Part interface is not implemented, implemented incorrectly, or an unsupported case.
                throw new IncompatibleClassChangeError("Part is of unkown type.");
        }        
        Current_Mode = Screen_Mode.MODIFY;
        Part_Label.setText("Modify Part");
        initPartView();
    }
    
    private void initPartView()
    {
        // Modify radio button and Source text to reflect the class of the supplied part.
        switch (PartToModify.getViewType()) 
        {
            case "Inhouse":
                inhouse_Radio.setSelected(true);
                Source_Label.setText("Machine ID");
                break;
            case "Outsource":
                outsourced_Radio.setSelected(true);
                Source_Label.setText("Company Name");
                break;
             default:
                // If this happens the Viewable Part interface is not implemented, implemented incorrectly, or an unsupported case.
                throw new IncompatibleClassChangeError("Part is of unkown type.");
        }
        
        // Collect data from the Part model.
        String partID = PartToModify.getViewID();
        String partName = PartToModify.getViewName();
        String partInv = PartToModify.getViewStock();
        String partPrice = PartToModify.getViewPrice();
        String partMin = PartToModify.getViewMin();
        String partMax = PartToModify.getViewMax();
        String partSource = PartToModify.getViewSource();
        
        // Modify data view.
        ID_Text.setText(partID);
        Name_Text.setText(partName);
        Inv_Text.setText(partInv);
        Price_Text.setText(partPrice);
        Max_Text.setText(partMax);
        Min_Text.setText(partMin);
        Source_Text.setText(partSource);
        
        // setup callbacks for screen events.
        ID_Text.setOnKeyReleased((event) -> {
            PartToModify.setViewID(ID_Text.getText());
            System.out.println("ID_Text.setOnAction()");
        });
        Name_Text.setOnKeyReleased((event) -> {
            PartToModify.setViewName(Name_Text.getText());
            System.out.println("ID_Text.setOnAction()");
        });        
        Inv_Text.setOnKeyReleased((event) -> {
            PartToModify.setViewStock(Inv_Text.getText());
            System.out.println("Inv_Text.setOnAction()");
        });
        Price_Text.setOnKeyReleased((event) -> {
            PartToModify.setViewPrice(Price_Text.getText());
            System.out.println("Price_Text.setOnAction()");
        });
//        Max_Text.setOnKeyReleased((event) -> {
//            PartToModify.setViewMax(Max_Text.getText());
//            System.out.println("Max_Text.setOnAction()");
//        });
//        Min_Text.setOnKeyReleased((event) -> {
//            PartToModify.setViewMin(Min_Text.getText());
//            System.out.println("Min_Text.setOnAction()");
//        });
        
        // Guarantee that Max is greater than min.
        Max_Text.setOnKeyReleased((event) -> {
            int theMin = Integer.parseInt(Min_Text.getText());
            int theMax = Integer.parseInt(Max_Text.getText());
            
            PartToModify.setViewMax(Max_Text.getText());
            if (theMin > theMax)
            {
                String newMin = Integer.toString(theMax);
                PartToModify.setViewMin(newMin);
                Min_Text.setText(newMin);
            }            
            System.out.println("Max_Text.setOnAction()");
        });
        Min_Text.setOnKeyReleased((event) -> {
            int theMin = Integer.parseInt(Min_Text.getText());
            int theMax = Integer.parseInt(Max_Text.getText());
            
            PartToModify.setViewMin(Min_Text.getText());
            if (theMin > theMax)
            {
                String newMax = Integer.toString(theMin);
                PartToModify.setViewMax(newMax);
                Max_Text.setText(newMax);
            }
            
            System.out.println("Min_Text.setOnAction()");
        });        
        
        Source_Text.setOnKeyReleased((event) -> {
            PartToModify.setViewSource(Source_Text.getText());
            System.out.println("Source_Text.setOnAction()");
        });
        inhouse_Radio.setOnAction((event) -> {
            updateViewData();
            System.out.println("inhouse_Radio.setOnAction()");
        });
        outsourced_Radio.setOnAction((event) -> {
            updateViewData();
            System.out.println("outsourced_Radio.setOnAction()");
        });
    }

    /**
     * fill out the view with current model data.
     */
    private void updateViewData()
    {
        RadioButton selectedRadioButton = (RadioButton) Category.getSelectedToggle();
        // If the part type does not match the type selection, cast to the correct type.
        if (selectedRadioButton == inhouse_Radio && PartToModify.getViewType().equals("Outsource"))
        {
            Part convertPart = (Part) PartToModify;
            PartToModify = new<ViewablePart> InhousePart(convertPart);
            initPartView();
        }
        else if (selectedRadioButton == outsourced_Radio && PartToModify.getViewType().equals("Inhouse"))
        {
            Part convertPart = (Part) PartToModify;
            PartToModify = new<ViewablePart> OutsourcedPart(convertPart);
            initPartView();
        }       
    }
}
