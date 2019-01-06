/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Inventory_Control.Model;

import java.util.HashSet;
import java.util.Set;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author Raymond Stewart
 */
public class InhousePart extends Part implements ViewablePart
{
    private final IntegerProperty machineId;
    
    public InhousePart()
    {
        super();
        machineId = new SimpleIntegerProperty(0);
    }
    
    public InhousePart(int theMachineId, int theId, String theName, double thePrice, int theStock, int theMin, int theMax)
    {
        super(theId, theName, thePrice, theStock, theMin, theMax);
        machineId = new SimpleIntegerProperty(0);
    }
    
    public InhousePart(Part thePart)
    {
        super(thePart.getId(), thePart.getName(), thePart.getPrice(), thePart.getStock(), thePart.getMin(), thePart.getMax());
        machineId = new SimpleIntegerProperty(0);
    }
    
    // Property getters
    public IntegerProperty machineIdProperty ()
    {
        return machineId;
    }
    
    // Getter
    public final int getMachineId()
    {
        return machineId.get();
    }
    
    // Setter
    public final void setMachineId(int theMachineId)
    {
        machineId.set(theMachineId);
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
        int intID = Integer.parseInt(theSource);
        setMachineId(intID);
    }
    
    @Override
    public String getViewSource()
    {
        return Integer.toString(getMachineId());
    }
    
    @Override
    public String getViewType()
    {
        return "Inhouse";
    }

}
