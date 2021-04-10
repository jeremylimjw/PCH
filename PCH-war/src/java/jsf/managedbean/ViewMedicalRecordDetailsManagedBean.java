/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.AppointmentSessionBeanLocal;
import ejb.session.stateless.MedicalRecordSessionBeanLocal;
import entity.Appointment;
import entity.MedicalRecord;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import util.exception.MedicalRecordEntityException;
import util.exception.MedicalRecordNotFoundException;

/**
 *
 * @author 13002
 */
@Named(value = "viewMedicalRecordDetailsManagedBean")
@ViewScoped
public class ViewMedicalRecordDetailsManagedBean implements Serializable {

    @EJB
    private AppointmentSessionBeanLocal appointmentSessionBeanLocal;


    @EJB(name = "MedicalRecordSessionBeanLocal")
    private MedicalRecordSessionBeanLocal medicalRecordSessionBeanLocal;
    
    
    
    private MedicalRecord record;
    private Long medicalRecordToBeUpdated;
    
    private List<Appointment> appointments;

    /**
     * Creates a new instance of ViewMedicalRecordDetailsManagedBean
     */
    public ViewMedicalRecordDetailsManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct() {
                
        try {
            //medicalRecordToBeUpdated = (Long)FacesContext.getCurrentInstance().getExternalContext().getFlash().get("recordIdToView");
            Long id = Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
            record = medicalRecordSessionBeanLocal.retrieveById(id);
            appointments = appointmentSessionBeanLocal.retrieveByMedicalRecordId(id);
        } catch (MedicalRecordEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
               
    }
    
    public void doUpdate() {
        try {        
            medicalRecordSessionBeanLocal.updateMedicalRecord(record);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Record updated succesfully.", null));
        } catch (MedicalRecordNotFoundException | MedicalRecordEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
        
    }
    
//    public void doUpdate(ActionEvent event) throws MedicalRecordNotFoundException, MedicalRecordEntityException {
//        medicalRecordSessionBeanLocal.updateMedicalRecord();
//        
//    }

    /**
     * @return the record
     */
    public MedicalRecord getRecord() {
        return record;
    }

    /**
     * @param record the record to set
     */
    public void setRecord(MedicalRecord record) {
        this.record = record;
    }

    /**
     * @return the medicalRecordToBeUpdated
     */
    public Long getMedicalRecordToBeUpdated() {
        return medicalRecordToBeUpdated;
    }

    /**
     * @param medicalRecordToBeUpdated the medicalRecordToBeUpdated to set
     */
    public void setMedicalRecordToBeUpdated(Long medicalRecordToBeUpdated) {
        this.medicalRecordToBeUpdated = medicalRecordToBeUpdated;
    }
    
    /**
     * @return the appointments
     */
    public List<Appointment> getAppointments() {
        return appointments;
    }

    /**
     * @param appointments the appointments to set
     */
    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    
    public void redirectAppointment(Long id) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/viewAppointment.xhtml?id=" + id);
    }
    
}
