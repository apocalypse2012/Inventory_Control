/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Inventory_Control.Model;

import Inventory_Control.View_Controller.Part_ScreenController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 *
 * @author Raymond Stewart
 */
public class Inventory 
{
    
    private static final ObservableList<Product> AllProducts = FXCollections.observableArrayList();
    private static final ObservableList<ViewablePart> AllParts = FXCollections.observableArrayList();
    
    public Inventory()
    {
        
    }
    
    //public void addPart(ViewablePart<Part_ScreenController>  thePart)
    public void addPart(ViewablePart thePart)
    {
        AllParts.add(thePart);
    }
    
    public void insertPart(ViewablePart thePart, int theIndex)
    {
        AllParts.add(theIndex, thePart);
    }

    //public void addPart(ViewablePart<Part_ScreenController>  thePart)
    public void replacePart(ViewablePart thePart, int theIndex)
    {
        AllParts.remove(theIndex);
        AllParts.add(theIndex, thePart);
    }
       
    public void removePart(ViewablePart thePart) 
    {
        AllParts.remove(thePart);
    }
    
    public ViewablePart lookupPart(int partId)
    {
        return AllParts.get(partId);
    }
    
    public ObservableList<ViewablePart> getAllParts()
    {
        return AllParts;
    }
    
    public int getPartCount()
    {
        return AllParts.size();
    }

    public int getNextAvailablePartID()
    {
        int lastNumber = 0;
        int nextNumber;
        int availableIndex;
        for (ViewablePart p: AllParts )
        {
            nextNumber = Integer.parseInt(p.getViewID());
            if (nextNumber - lastNumber > 1)
            {
                availableIndex = lastNumber + 1;
                return availableIndex;
            }
            lastNumber = nextNumber;
        }
        
        return lastNumber + 1;
    }
    
    public void addProduct(Product theProduct)
    {
        AllProducts.add(theProduct);
    }
    
    public void insertProduct(Product theProduct, int theIndex)
    {
        AllProducts.add(theIndex, theProduct);
    }
    
    public void replaceProduct(Product theProduct, int theIndex)
    {
        AllProducts.remove(theIndex);
        AllProducts.add(theIndex, theProduct);
    }

    public void removeProduct(Product theProduct) 
    {
        AllProducts.remove(theProduct);
    }
    
    public Product lookupProduct(int productId)
    {
        return AllProducts.get(productId);
    }
    
    public ObservableList<Product> getAllProducts()
    {
        return AllProducts;
    }
    
    public int getProductCount()
    {
        return AllProducts.size();
    }
    
    public int getNextAvailableProductID()
    {
        int lastNumber = 0;
        int nextNumber;
        int availableIndex;
        for (Product p: AllProducts )
        {
            nextNumber = Integer.parseInt(p.getViewID());
            if (nextNumber - lastNumber > 1)
            {
                availableIndex = lastNumber + 1;
                return availableIndex;
            }
            lastNumber = nextNumber;
        }
        
        return lastNumber + 1;
    }
}
