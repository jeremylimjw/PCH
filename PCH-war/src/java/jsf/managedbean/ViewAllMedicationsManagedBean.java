/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.MedicationEntitySessionBeanLocal;
import entity.Medication;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import util.exception.MedicationEntityException;

/**
 *
 * @author USER
 */
@Named(value = "viewAllMedicationsManagedBean")
@ViewScoped
public class ViewAllMedicationsManagedBean implements Serializable{



    @EJB
    private MedicationEntitySessionBeanLocal medicationEntitySessionBeanLocal;
    
    @Inject
    private ViewMedicationManagedBean viewMedicationManagedBean;
    
    private String searchString;
    private List<Medication> medications;
    private List<Medication> newMedicationList;
    //Create
    private Medication newMedication;
    private String foodName;
    private List<String> cfoodArray;
    private List<Medication> conflict_medicine;
    private List<Long> conflict_med_ids;
    
    //Update and Delete
    private Medication selectedMedicationEntityToUpdate;
    private List<Long> conflict_med_ids_to_update;

    /**
     * Creates a new instance of ViewAllMedicationsManagedBean
     */
    public ViewAllMedicationsManagedBean() {
        newMedication = new Medication();
        cfoodArray = new ArrayList<>();
    }
    
    @PostConstruct
    public void postConstruct() {
        
       
        
        setSearchString((String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("medicationSearchString"));
       
     
        
        medications = getMedicationEntitySessionBeanLocal().retrieveAll();
       
    
    }
    public List<Medication> viewAllMedications()
    {
        List<Medication> newMedList = getMedicationEntitySessionBeanLocal().retrieveAll();
        
        return newMedList;
    }
    
    
    public void searchMedication(){
         if(getSearchString() == null || getSearchString().trim().length() == 0)
        {
        
        medications = getMedicationEntitySessionBeanLocal().retrieveAll();
        }
        else
        {
            medications = getMedicationEntitySessionBeanLocal().searchMedicinesByName(getSearchString());
        }
        
    }
    
    
     public void viewMedicationDetails(ActionEvent event) throws IOException
    {
        Long medicationIdToView = (Long)event.getComponent().getAttributes().get("medicineId");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("medicationIdToView", medicationIdToView);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("backMode", "viewAllMedications");
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewMedicationDetails.xhtml");
    }
     public void addconflictFood(ActionEvent event)
    {
       
        
       
      //  FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Created successfully", "New Food Name: " + foodName));
    }

      public void createNewMedication(ActionEvent event) {
       // Long id = medicationEntitySessionBeanLocal.create(newMedication);
        try{
         
             Medication med = getMedicationEntitySessionBeanLocal().createNewMedication(getNewMedication(), getConflict_med_ids(), cfoodArray);
              
             conflict_med_ids = new ArrayList<>();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Created successfully", "New Medication Name: " + med.getName()));
        }
       catch(MedicationEntityException ex)
       {
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new Medications", null));
       }
    }
      
      
      
  
      
      public void doUpdate(ActionEvent event){
          
          selectedMedicationEntityToUpdate = (Medication)event.getComponent().getAttributes().get("MedicationToUpdate");
          
           conflict_med_ids_to_update = new ArrayList<>();
           cfoodArray= new ArrayList<>();

        for(Medication medication:selectedMedicationEntityToUpdate.getConflicting_medications())
        {
            conflict_med_ids_to_update.add(medication.getId());
        }
          cfoodArray=selectedMedicationEntityToUpdate.getConflicting_foods();
      }
     
    public void updateMedication(ActionEvent event) {
        try {
            medicationEntitySessionBeanLocal.updateMedication(selectedMedicationEntityToUpdate, conflict_med_ids_to_update, cfoodArray);
            conflict_med_ids = new ArrayList<>();
            
           

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Medication updated successfully", null));
        } catch (MedicationEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating medication: " + ex.getMessage(), null));
        }
        
      

    }
    public void deleteMedication (ActionEvent event)
    {
            try{
        Medication medicationToDelete = (Medication)event.getComponent().getAttributes().get("medicationToDelete");
        medicationEntitySessionBeanLocal.deleteMedication(medicationToDelete.getId(), medications);
        medications.remove(medicationToDelete);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Medication deleted successfully", null));
            }
            catch(MedicationEntityException ex)
            {
                 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting product: " + ex.getMessage(), null));
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

    /**
     * @return the medicationEntitySessionBeanLocal
     */
    public MedicationEntitySessionBeanLocal getMedicationEntitySessionBeanLocal() {
        return medicationEntitySessionBeanLocal;
    }

    /**
     * @param medicationEntitySessionBeanLocal the medicationEntitySessionBeanLocal to set
     */
    public void setMedicationEntitySessionBeanLocal(MedicationEntitySessionBeanLocal medicationEntitySessionBeanLocal) {
        this.medicationEntitySessionBeanLocal = medicationEntitySessionBeanLocal;
    }

    /**
     * @return the viewMedicationManagedBean
     */
    public ViewMedicationManagedBean getViewMedicationManagedBean() {
        return viewMedicationManagedBean;
    }

    /**
     * @param viewMedicationManagedBean the viewMedicationManagedBean to set
     */
    public void setViewMedicationManagedBean(ViewMedicationManagedBean viewMedicationManagedBean) {
        this.viewMedicationManagedBean = viewMedicationManagedBean;
    }

    /**
     * @return the newMedication
     */
    public Medication getNewMedication() {
        return newMedication;
    }

    /**
     * @param newMedication the newMedication to set
     */
    public void setNewMedication(Medication newMedication) {
        this.newMedication = newMedication;
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
     * @return the selectedMedicationEntityToUpdate
     */
    public Medication getSelectedMedicationEntityToUpdate() {
        return selectedMedicationEntityToUpdate;
    }

    /**
     * @param selectedMedicationEntityToUpdate the selectedMedicationEntityToUpdate to set
     */
    public void setSelectedMedicationEntityToUpdate(Medication selectedMedicationEntityToUpdate) {
        this.selectedMedicationEntityToUpdate = selectedMedicationEntityToUpdate;
    }

    /**
     * @return the conflict_med_ids_to_update
     */
    public List<Long> getConflict_med_ids_to_update() {
        return conflict_med_ids_to_update;
    }

    /**
     * @param conflict_med_ids_to_update the conflict_med_ids_to_update to set
     */
    public void setConflict_med_ids_to_update(List<Long> conflict_med_ids_to_update) {
        this.conflict_med_ids_to_update = conflict_med_ids_to_update;
    }
        /**
     * @return the newMedicationList
     */
    public List<Medication> getNewMedicationList() {
        return newMedicationList = getMedicationEntitySessionBeanLocal().retrieveAll();
    }

    /**
     * @param newMedicationList the newMedicationList to set
     */
    public void setNewMedicationList(List<Medication> newMedicationList) {
        this.newMedicationList = newMedicationList;
    }
}
