/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import entity.Medication;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author ngjunkai
 */
@Named(value = "viewMedicationManagedBean")
@ViewScoped
public class ViewMedicationManagedBean implements Serializable{
    private Medication meditcationToView;

    /**
     * Creates a new instance of ViewMedicationManagedBean
     */
    
   public ViewMedicationManagedBean() {
     meditcationToView = new Medication();
           
    }
        
    @PostConstruct
    public void postConstruct()
    {        
    }

    /**
     * @return the meditcationToView
     */
    public Medication getMeditcationToView() {
        return meditcationToView;
    }

    /**
     * @param meditcationToView the meditcationToView to set
     */
    public void setMeditcationToView(Medication meditcationToView) {
        this.meditcationToView = meditcationToView;
    }

 
    
}
