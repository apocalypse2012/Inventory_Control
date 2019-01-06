/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Inventory_Control.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 *
 * @author Raymond Stewart
 */
public class Product 
{
    private ObservableList<ViewablePart> associatedParts = FXCollections.observableArrayList();
    private final IntegerProperty id;
    private final StringProperty name;
    private final DoubleProperty price;
    private final IntegerProperty stock;
    private final IntegerProperty min;
    private final IntegerProperty max;
    
    public Product()
    {
        id = new SimpleIntegerProperty();
        name = new SimpleStringProperty();
        price =  new SimpleDoubleProperty();
        stock = new SimpleIntegerProperty();
        min = new SimpleIntegerProperty();
        max = new SimpleIntegerProperty();
    }
    
    public Product(int theId, String theName, double thePrice, int theStock, int theMin, int theMax)
    {
        id = new SimpleIntegerProperty(theId);
        name = new SimpleStringProperty(theName);
        price = new SimpleDoubleProperty(thePrice);
        stock = new SimpleIntegerProperty(theStock);
        min = new SimpleIntegerProperty(theMin);
        max = new SimpleIntegerProperty(theMax);
    }
    
    public Product(Product theProduct)
    {
        id = new SimpleIntegerProperty(theProduct.getId());
        name = new SimpleStringProperty(theProduct.getName());
        price = new SimpleDoubleProperty(theProduct.getPrice());
        stock = new SimpleIntegerProperty(theProduct.getStock());
        min = new SimpleIntegerProperty(theProduct.getMin());
        max = new SimpleIntegerProperty(theProduct.getMax());
        associatedParts = FXCollections.observableArrayList(theProduct.getProductParts());
    }
    
    /*
        Property getters
    */
    public IntegerProperty idProperty()
    {
        return id;
    }

    public StringProperty nameProperty()
    {
        return name;
    }

     public DoubleProperty priceProperty()
     {
         return price;
     }

     public IntegerProperty stockProperty()
     {
         return stock;
     }

     public IntegerProperty minProperty()
     {
         return min;
     }
     
    public IntegerProperty maxProperty()
    {
        return max;
    }
    
    /*
       Getters.
    */
    public final int getId()
    {
         return id.get();
    }

    public final String getName()
    {
        return name.get();
    }

    public final double getPrice()
    {
        return price.get();
    }

    public final int getStock()
    {
        return stock.get();
    }

    public final int getMin()
    {
        return min.get();
    }

    public final int getMax()
    {
        return max.get();
    }
    
    public ObservableList getProductParts() 
    {
        return associatedParts;
    }

     
    /*
       Setters
    */
    public final void setId(int theId)
    {
        id.set(theId);
    }

    public final void setName(String theName)
    {
        name.set(theName);
    }

    public final void setPrice(double thePrice)
    { 
        price.set(thePrice);
    }

    public final void setStock(int theStock)
    {
        stock.set(theStock);
    }

    public final void setMin(int theMin)
    {
        min.set(theMin);
    }

    public final void setMax(int theMax)
    {
        max.set(theMax);
    }

    public void setProductParts(ObservableList<ViewablePart> parts) {
        this.associatedParts = parts;
    }
    
    //public void addPart(ViewablePart<Part_ScreenController>  thePart)
    public void addPart(ViewablePart thePart)
    {
        associatedParts.add(thePart);
    }

    //public void addPart(ViewablePart<Part_ScreenController>  thePart)
    public void replacePart(ViewablePart thePart, int theIndex)
    {
        associatedParts.remove(theIndex);
        associatedParts.add(theIndex, thePart);
    }
    
    public ViewablePart lookupPart(int partId)
    {
        return associatedParts.get(partId);
    }
    
    public ObservableList<ViewablePart> getAllParts()
    {
        return associatedParts;
    }
    
    public int getPartCount()
    {
        return associatedParts.size();
    }
       
    public void removePart(ViewablePart thePart) 
    {
        associatedParts.remove(thePart);
    }

    /////////////////////////////////////////////////////////////////////////
    // These are methods mirror the interface methods for the Parts classes.
    // The Idea is similar. These methods detirmine what the view controller can
    // do with the object. If more kinds of products existed, this would be refactored
    // to derive from an interface.
    public void setViewID(String theID)
    {
        int intID = Integer.parseInt(theID);
        setId(intID);
    }
    
    public String getViewID()
    {
        return Integer.toString(getId());
    }
    
    public void setViewName(String theName)
    {
        setName(theName);
    }
    
    public String getViewName()
    {
        return getName();
    }
    
    public void setViewStock(String theStock)
    {
        int intID = Integer.parseInt(theStock);
        setStock(intID);
    }
    
    public String getViewStock()
    {
        return Integer.toString(getStock());
    }
    
    public void setViewPrice(String thePrice)
    {
        double dblPrice = Double.valueOf(thePrice);
        setPrice(dblPrice);
    }
    
    public String getViewPrice()
    {
//        return Double.toString(getPrice());
        return String.format("%.2f", getPrice());
    }
    
    public void setViewMin(String theMin)
    {
        int intID = Integer.parseInt(theMin);
        setMin(intID);
    }
    
    public String getViewMin()
    {
        return Integer.toString(getMin());
    }
    
    public void setViewMax(String theMax)
    {
        int intID = Integer.parseInt(theMax);
        setMax(intID);
    }
    
    public String getViewMax()
    {
        return Integer.toString(getMax());
    }
    
    
    //Product Validation Method
//    public boolean isProductValid(StringBuilder errorMessage)
//    {
//        boolean isValidPart = true;
//        
//        double sumParts = 0.00;
//        for (int i = 0; i < associatedParts.size(); i++) 
//        {
//            sumParts = sumParts + Integer.parseInt(associatedParts.get(i).getViewPrice());
//        }
//        if (name.get() == null) 
//        {
//            isValidPart = false;
//            errorMessage.append("Name field is blank.");
//        }
//        if (min.get() < 0) 
//        {
//            isValidPart = false;
//            errorMessage.append("The inventory must be greater than 0.");
//        }
//        if (price.get() < 0.01d) 
//        {
//            isValidPart = false;
//            errorMessage.append("The price must be greater than $0.0");
//        }
//        if (min.get() > max.get()) 
//        {
//            isValidPart = false;
//            errorMessage.append("The inventory MIN must be less than the MAX.");
//        }
//        if (stock.get() < min.get() || stock.get() > max.get()) 
//        {
//            isValidPart = false;
//            errorMessage.append("Part inventory must be between MIN and MAX values.");
//        }
//        if (associatedParts.size() < 1) 
//        {
//            isValidPart = false;
//            errorMessage.append("Product must contain at least 1 part.");
//        }
//        if (sumParts > price.get()) 
//        {
//            isValidPart = false;
//            errorMessage.append("Product price must be greater than cost of parts.");
//        }
//        return isValidPart;
//    }
//    

}
