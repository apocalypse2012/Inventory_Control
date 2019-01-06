/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Inventory_Control;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Inventory_Control.Model.Inventory;
import Inventory_Control.Model.InhousePart;
import Inventory_Control.Model.OutsourcedPart;
import Inventory_Control.Model.Product;
import Inventory_Control.Model.ViewablePart;

/**
 *
 * @author Raymond Stewart
 */
public class InventoryControl extends Application {
    
    private Inventory modelInventory;
    
    public void init() throws Exception 
    {
        modelInventory = new Inventory();
        
        // Test input.
        for (int i = 0; i<5; i++)
        {
            ViewablePart testPart = new<InhousePart> InhousePart(0, modelInventory.getPartCount(), "Test InhousePart", 1.00, 10, 1, 100);
            Product testProduct = new<Product> Product(modelInventory.getProductCount(), "Test Product", 1.00, 10, 1, 100);
            modelInventory.addPart(testPart);
            modelInventory.addProduct(testProduct);
            ViewablePart outPart = new<OutsourcedPart> OutsourcedPart("Outsource Company", modelInventory.getPartCount(), "Test OutsourcedPart", 1.00, 10, 1, 100);
            modelInventory.addPart(outPart);
        }
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("View_Controller/Main_Screen.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Inventory Control");
        stage.show();
    }
    
    public Inventory getInventory()
    {
        return modelInventory;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
