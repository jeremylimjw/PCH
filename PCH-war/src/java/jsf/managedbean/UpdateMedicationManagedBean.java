/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.MedicationEntitySessionBeanLocal;
import entity.Medication;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import util.exception.MedicationEntityException;

/**
 *
 * @author ngjunkai
 */
@Named(value = "updateMedicationManagedBean")
@ViewScoped
public class UpdateMedicationManagedBean implements Serializable{

    @EJB
    private MedicationEntitySessionBeanLocal medicationEntitySessionBean;
    private Medication medicationToUpdate;
    private List<Medication> conflicting_med;
    private List<Medication> parent_med;
    private Long medicationToUpdateId;
    private List<Long> conflict_med_ids;
    /**
     * Creates a new instance of UpdateMedicationManagedBean
     */
    public UpdateMedicationManagedBean() {
    }

     @PostConstruct
     public void postConstruct()
    {
        medicationToUpdateId = (Long)FacesContext.getCurrentInstance().getExternalContext().getFlash().get("medicationToUpdateId");
         conflicting_med = medicationEntitySessionBean.retrieveAll();
        try
        {
            if(medicationToUpdateId != null)
            {
                medicationToUpdate =medicationEntitySessionBean.retrieveByMedicineId(medicationToUpdateId);
            }else
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No Medication has been selected", null));
            }
        } catch(MedicationEntityException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving the Medication details: " + ex.getMessage(), null));
        }
    }
    
     
     public void updateMedication()
     {
         try
         {
             medicationEntitySessionBean.updateMedication(medicationToUpdate,conflict_med_ids);
              conflict_med_ids = new ArrayList<>();
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Medication updated successfully", null));
         }
        catch(MedicationEntityException ex)
        {
              FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating medication: " + ex.getMessage(), null));
        }
    
    }
    
    /**
     * @return the medicationEntitySessionBean
     */
    public MedicationEntitySessionBeanLocal getMedicationEntitySessionBean() {
        return medicationEntitySessionBean;
    }

    /**
     * @param medicationEntitySessionBean the medicationEntitySessionBean to set
     */
    public void setMedicationEntitySessionBean(MedicationEntitySessionBeanLocal medicationEntitySessionBean) {
        this.medicationEntitySessionBean = medicationEntitySessionBean;
    }

    /**
     * @return the medicationToUpdate
     */
    public Medication getMedicationToUpdate() {
        return medicationToUpdate;
    }

    /**
     * @param medicationToUpdate the meficationToUpdate to set
     */
    public void setMedicationToUpdate(Medication medicationToUpdate) {
        this.medicationToUpdate = medicationToUpdate;
    }

    /**
     * @return the conflicting_med
     */
    public List<Medication> getConflicting_med() {
        return conflicting_med;
    }

    /**
     * @param conflicting_med the conflicting_med to set
     */
    public void setConflicting_med(List<Medication> conflicting_med) {
        this.conflicting_med = conflicting_med;
    }

    /**
     * @return the parent_med
     */
    public List<Medication> getParent_med() {
        return parent_med;
    }

    /**
     * @param parent_med the parent_med to set
     */
    public void setParent_med(List<Medication> parent_med) {
        this.parent_med = parent_med;
    }

    /**
     * @return the medicationToUpdateId
     */
    public Long getMedicationToUpdateId() {
        return medicationToUpdateId;
    }

    /**
     * @param medicationToUpdateId the medicationToUpdateId to set
     */
    public void setMedicationToUpdateId(Long medicationToUpdateId) {
        this.medicationToUpdateId = medicationToUpdateId;
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
    
}
