/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.MedicationEntitySessionBeanLocal;
import entity.Medication;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

/**
 *
 * @author USER
 */
@Named(value = "viewAllMedicationsManagedBean")
@ViewScoped
public class ViewAllMedicationsManagedBean implements Serializable{

    @EJB
    private MedicationEntitySessionBeanLocal medicationEntitySessionBeanLocal;
    
    private String searchString;
    private List<Medication> medications;

    /**
     * Creates a new instance of ViewAllMedicationsManagedBean
     */
    public ViewAllMedicationsManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct() {
        setSearchString((String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("medicationSearchString"));
       
        if(getSearchString() == null || getSearchString().trim().length() == 0)
        {
        
        medications = medicationEntitySessionBeanLocal.retrieveAll();
        }
        else
        {
            medications = medicationEntitySessionBeanLocal.searchMedicinesByName(getSearchString());
        }
    }
    
    
    public void searchMedication(){
         if(getSearchString() == null || getSearchString().trim().length() == 0)
        {
        
        medications = medicationEntitySessionBeanLocal.retrieveAll();
        }
        else
        {
            medications = medicationEntitySessionBeanLocal.searchMedicinesByName(getSearchString());
        }
        
    }
    

    public List<Medication> getMedications() {
        return medications;
    }

    public void setMedications(List<Medication> medications) {
        this.medications = medications;
    }

    /**
     * @return the searchString
     */
    public String getSearchString() {
        return searchString;
    }

    /**
     * @param searchString the searchString to set
     */
    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
    
}
