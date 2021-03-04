/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.MedicationEntitySessionBeanLocal;
import entity.Medication;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import util.exception.MedicationEntityException;

/**
 *
 * @author USER
 */
@Named(value = "createNewMedicationManagedBean")
@RequestScoped
public class CreateNewMedicationManagedBean {


    @EJB
    private MedicationEntitySessionBeanLocal medicationEntitySessionBeanLocal;

    private Medication newMedication;
    private String foodName;
    private List<String> cfoodArray;
    private List<Medication> conflict_medicine;
    private List<Long> conflict_med_ids;
    private List<String> conflict_medication_Name;

    /**
     * Creates a new instance of CreateNewMedicationManagedBean
     */
    public CreateNewMedicationManagedBean() {
        newMedication = new Medication();
        cfoodArray = new ArrayList();
    }
    
    
    @PostConstruct
    public void postConstruct()
    {
        conflict_medicine = medicationEntitySessionBeanLocal.retrieveAll();
     
    }
    
    public void addconflictFood(ActionEvent event)
    {
       
        
         
      //  FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Created successfully", "New Food Name: " + foodName));
    }
    
    

    public void createNewMedication(ActionEvent event) {
       // Long id = medicationEntitySessionBeanLocal.create(newMedication);
        try{
         
             Medication med = medicationEntitySessionBeanLocal.createNewMedication(newMedication, conflict_med_ids,cfoodArray);
             cfoodArray=new ArrayList<>();
             conflict_med_ids = new ArrayList<>();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Created successfully", "New Medication Name: " + med.getName()));
        }
       catch(MedicationEntityException ex)
       {
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new Medications", null));
       }
    }
    
    
    
    //public void addConflictMedication()

    public Medication getNewMedication() {
        return newMedication;
    }

    public void setNewMedication(Medication newMedication) {
        this.newMedication = newMedication;
    }

    /**
     * @return the conflict_medicine
     */
    public List<Medication> getConflict_medicine() {
        return conflict_medicine;
    }

    /**
     * @param conflict_medicine the conflict_medicine to set
     */
    public void setConflict_medicine(List<Medication> conflict_medicine) {
        this.conflict_medicine = conflict_medicine;
    }

    /**
     * @return the conflict_med_ids
     */
    public List<Long> getConflict_med_ids() {
        return conflict_med_ids;
    }

    /**
     * @param conflict_med_ids the conflict_med_ids to set
     */
    public void setConflict_med_ids(List<Long> conflict_med_ids) {
        this.conflict_med_ids = conflict_med_ids;
    }
    /**
     * @return the foodName
     */
    public String getFoodName() {
        return foodName;
    }

    /**
     * @param foodName the foodName to set
     */
    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    /**
     * @return the cfoodArray
     */
    public List<String> getCfoodArray() {
        return cfoodArray;
    }

    /**
     * @param cfoodArray the cfoodArray to set
     */
    public void setCfoodArray(List<String> cfoodArray) {
        this.cfoodArray = cfoodArray;
    }

}
