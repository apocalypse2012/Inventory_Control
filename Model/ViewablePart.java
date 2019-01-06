/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Inventory_Control.Model;

/**
 *
 * @author Raymond Stewart
 * This interface fulfills the contract requirements of the Parts screen view-controller.
 * This interface is to be applied to all part model objects whose data is manipulated 
 * in the Parts add or modify display. Method implementations are responsible for
 * marshaling view data produced or consumed by the methods.
 */
public interface ViewablePart//<T>
{
    
    //void fillForm(T t);
    
    void setViewID(String theID);
    String getViewID();
    
    void setViewName(String theName);
    String getViewName();
    
    void setViewStock(String theStock);
    String getViewStock();
    
    void setViewPrice(String thePrice);
    String getViewPrice();
    
    void setViewMin(String theMin);
    String getViewMin();
    
    void setViewMax(String theMax);
    String getViewMax();
    
    void setViewSource(String theSource);
    String getViewSource();
    
    String getViewType();
}
