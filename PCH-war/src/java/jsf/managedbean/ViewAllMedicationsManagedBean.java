/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.MedicationEntitySessionBeanLocal;
import entity.Medication;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author USER
 */
@Named(value = "viewAllMedicationsManagedBean")
@RequestScoped
public class ViewAllMedicationsManagedBean {

    @EJB
    private MedicationEntitySessionBeanLocal medicationEntitySessionBeanLocal;
    
    private List<Medication> medications;

    /**
     * Creates a new instance of ViewAllMedicationsManagedBean
     */
    public ViewAllMedicationsManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct() {
        medications = medicationEntitySessionBeanLocal.retrieveAll();
    }

    public List<Medication> getMedications() {
        return medications;
    }

    public void setMedications(List<Medication> medications) {
        this.medications = medications;
    }
    
}
