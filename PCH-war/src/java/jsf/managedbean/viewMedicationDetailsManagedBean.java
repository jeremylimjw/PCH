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
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import util.exception.MedicationEntityException;

/**
 *
 * @author ngjunkai
 */
@Named(value = "viewMedicationDetailsManagedBean")
@ViewScoped
public class viewMedicationDetailsManagedBean implements Serializable {


    @EJB
    private MedicationEntitySessionBeanLocal medicationEntitySessionBean;
    private Long medicationIdToView;
    private String backMode;
    private Medication medicationToView;
    private List<String> conflictMedications;
    
    /**
     * Creates a new instance of viewMedicationDetailsManagedBean
     */
    public viewMedicationDetailsManagedBean() {
    }
    
    
    @PostConstruct
    public void postConstruct()
    {
        
        medicationIdToView = (Long)FacesContext.getCurrentInstance().getExternalContext().getFlash().get("medicationIdToView");
         backMode = (String)FacesContext.getCurrentInstance().getExternalContext().getFlash().get("backMode");
    
        try
        {
            
            if(getMedicationIdToView() != null)
            {
                setMedicationToView(getMedicationEntitySessionBean().retrieveByMedicineId(medicationIdToView));
                
            }
            else
            {
                 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No medicine has been selected", null));
            }
        }
            
        catch (MedicationEntityException ex)
        {
          FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving the medicine details: " + ex.getMessage(), null));
        }
        
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
            
    }
    
      public void back(ActionEvent event) throws IOException
    {
        if(getBackMode() == null || getBackMode().trim().length() == 0)
        {
            FacesContext.getCurrentInstance().getExternalContext().redirect("viewAllMedications.xhtml");
        }
        else
        {
            FacesContext.getCurrentInstance().getExternalContext().redirect(getBackMode() + ".xhtml");
        }
    }

      public void updateMedication(ActionEvent event) throws IOException
    {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("medicationToUpdateId", medicationIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect("updateMedication.xhtml");
    }
    
    
    /**
     * @return the medicationIdToView
     */
    public Long getMedicationIdToView() {
        return medicationIdToView;
    }

    /**
     * @param medicationIdToView the medicationIdToView to set
     */
    public void setMedicationIdToView(Long medicationIdToView) {
        this.medicationIdToView = medicationIdToView;
    }

    /**
     * @return the backMode
     */
    public String getBackMode() {
        return backMode;
    }

    /**
     * @param backMode the backMode to set
     */
    public void setBackMode(String backMode) {
        this.backMode = backMode;
    }

    /**
     * @return the medicationToView
     */
    public Medication getMedicationToView() {
        return medicationToView;
    }

    /**
     * @param medicationToView the medicationToView to set
     */
    public void setMedicationToView(Medication medicationToView) {
        this.medicationToView = medicationToView;
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
     * @return the conflictMedications
     */
    public List<String> getConflictMedications() {
          try
        {
            
            if(getMedicationIdToView() != null)
            {
                setMedicationToView(getMedicationEntitySessionBean().retrieveByMedicineId(medicationIdToView));
                this.conflictMedications = medicationEntitySessionBean.conflictMedicationByName(medicationIdToView);
                
            }
            else
            {
                 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No medicine has been selected", null));
            }
        }
            
        catch (MedicationEntityException ex)
        {
          FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving the medicine details: " + ex.getMessage(), null));
        }
        
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
            
    
        
        
        return conflictMedications;
    }

    /**
     * @param conflictMedications the conflictMedications to set
     */
    public void setConflictMedications(List<String> conflictMedications) {
        this.conflictMedications = conflictMedications;
    }

    
    
}
