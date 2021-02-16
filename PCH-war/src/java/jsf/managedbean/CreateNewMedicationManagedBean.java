/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.MedicationEntitySessionBeanLocal;
import entity.Medication;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

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

    /**
     * Creates a new instance of CreateNewMedicationManagedBean
     */
    public CreateNewMedicationManagedBean() {
        newMedication = new Medication();
    }
    
    public void createNewMedication(ActionEvent event) {
        Long id = medicationEntitySessionBeanLocal.create(newMedication);
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Created successfully", "New Medication ID: " + id));
    }

    public Medication getNewMedication() {
        return newMedication;
    }

    public void setNewMedication(Medication newMedication) {
        this.newMedication = newMedication;
    }
    
}
