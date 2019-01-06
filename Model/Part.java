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


/**
 *
 * @author Raymond Stewart
 * Base-class for inventory parts.
 */
public abstract class Part 
{
    
    /*
        Property declarations.
    */
    private final IntegerProperty id;
    private final StringProperty name;
    private final DoubleProperty price;
    private final IntegerProperty stock;
    private final IntegerProperty min;
    private final IntegerProperty max;    
    
    public Part()
    {
        id = new SimpleIntegerProperty(0);
        name = new SimpleStringProperty("Unassigned");
        price = new SimpleDoubleProperty(1.00);
        stock = new SimpleIntegerProperty(0);
        min = new SimpleIntegerProperty(0);
        max = new SimpleIntegerProperty(10);
    }
    
    public Part(int theId, String theName, double thePrice, int theStock, int theMin, int theMax)
    {
        id = new SimpleIntegerProperty(theId);
        name = new SimpleStringProperty(theName);
        price = new SimpleDoubleProperty(thePrice);
        stock = new SimpleIntegerProperty(theStock);
        min = new SimpleIntegerProperty(theMin);
        max = new SimpleIntegerProperty(theMax);
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
     
    /*
        Validate class property fields.
    */
//    public boolean isPartValid(StringBuilder errorMessage)
//    {
//        boolean isValidPart = true;
//        
//        if (name.get() == null) {
//            isValidPart = false;
//            errorMessage.append("Name field is blank.");
//        }
//        if (stock.get() < 1) {
//            isValidPart = false;
//            errorMessage.append("The inventory must be greater than 0.");
//        }
//        if (price.get() < 0.01d) {
//            isValidPart = false;
//            errorMessage.append("The price must be greater than $0.0");
//        }
//        if (min.get() > max.get()) {
//            isValidPart = false;
//            errorMessage.append("The inventory MIN must be less than the MAX.");
//        }
//        if (stock.get() < min.get() || stock.get() > max.get()) {
//            isValidPart = false;
//            errorMessage.append("Part inventory must be between MIN and MAX values.");
//        }        
//        
//        return isValidPart;
//    }
}
