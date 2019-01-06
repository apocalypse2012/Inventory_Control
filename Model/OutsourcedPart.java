/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Inventory_Control.Model;

import java.util.Set;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;


/**
 *
 * @author Raymond Stewart
 */
public class OutsourcedPart extends Part implements ViewablePart
{
    private final StringProperty companyName;
    
    public OutsourcedPart()
    {
        super();
        companyName = new SimpleStringProperty("Unassigned");
    }

    public OutsourcedPart(String theCompanyName, int theId, String theName, double thePrice, int theStock, int theMin, int theMax)
    {
        super(theId, theName, thePrice, theStock, theMin, theMax);
        companyName = new SimpleStringProperty(theCompanyName);
    }

    public OutsourcedPart(Part thePart)
    {
        //Part partVal = (Part) thePart;
        super(thePart.getId(), thePart.getName(), thePart.getPrice(), thePart.getStock(), thePart.getMin(), thePart.getMax());
        companyName = new SimpleStringProperty("Unassigned");
    }    
    
    // Property getters
    public StringProperty companyNameProperty()
    {
        return companyName;
    }
    
    // Getter
    public final String getCompanyName()
    {
        return companyName.get();
    }
    
    // Setter
    public final void setCompanyName(String theCompanyName)
    {
        companyName.set(theCompanyName);
    }
      
   
    /*//////////////////////////////////////////////////////////////////////////
    // Implementation for ViewablePart Interface. This interface implementation 
    // guarantees the contract of the with the View Controller.
    */
    @Override
    public void setViewID(String theID)
    {
        int intID = Integer.parseInt(theID);
        setId(intID);
    }
    
    @Override
    public String getViewID()
    {
        return Integer.toString(getId());
    }
    
    @Override
    public void setViewName(String theName)
    {
        setName(theName);
    }
    
    @Override
    public String getViewName()
    {
        return getName();
    }
    
    @Override
    public void setViewStock(String theStock)
    {
        int intID = Integer.parseInt(theStock);
        setStock(intID);
    }
    
    @Override
    public String getViewStock()
    {
        return Integer.toString(getStock());
    }
    
    @Override
    public void setViewPrice(String thePrice)
    {
        double dblPrice = Double.valueOf(thePrice);
        setPrice(dblPrice);
    }
    
    @Override
    public String getViewPrice()
    {
//        return Double.toString(getPrice());
        return String.format("%.2f", getPrice());
    }
    
    @Override
    public void setViewMin(String theMin)
    {
        int intID = Integer.parseInt(theMin);
        setMin(intID);
    }
    
    @Override
    public String getViewMin()
    {
        return Integer.toString(getMin());
    }
    
    @Override
    public void setViewMax(String theMax)
    {
        int intID = Integer.parseInt(theMax);
        setMax(intID);
    }
    
    @Override
    public String getViewMax()
    {
        return Integer.toString(getMax());
    }
    
    @Override
    public void setViewSource(String theSource)
    {
        setCompanyName(theSource);
    }
    
    @Override
    public String getViewSource()
    {
        return getCompanyName();
    }
    
    @Override
    public String getViewType()
    {
        return "Outsource";
    }

}

