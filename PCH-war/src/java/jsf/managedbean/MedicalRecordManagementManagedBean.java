/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.MedicalRecordSessionBeanLocal;
import entity.MedicalRecord;
import java.io.Console;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.exception.InputDataValidationException;
import util.exception.MedicalRecordEntityException;

/**
 *
 * @author 13002
 */
@Named(value = "MedicalRecordManagementManagedBean")
@RequestScoped
public class MedicalRecordManagementManagedBean {

    @EJB
    private MedicalRecordSessionBeanLocal medicalRecordSessionBeanLocal;
    
    private MedicalRecord newRecord;
    private List<MedicalRecord> records;
    private List<MedicalRecord> filteredRecords;
    
    /**
     * Creates a new instance of patientRecordManagementManagedBean
     */
    public MedicalRecordManagementManagedBean() {
        newRecord = new MedicalRecord();
        records = new ArrayList<>();
        filteredRecords = records;
    }
    
    @PostConstruct
    public void PostConstruct() {
        records = medicalRecordSessionBeanLocal.retrieveAllMedicalRecords();
    }
    
    public void createNewMedicalRecord(ActionEvent event) throws InputDataValidationException, MedicalRecordEntityException {
        try {
           newRecord.setDate_created(new Date());
            List<String> help = new ArrayList<>();
            help.add("");
            newRecord.setDrug_allergys(new ArrayList<>());
            newRecord.setFamily_historys(help);
            newRecord.setPast_medical_historys(help);
            newRecord.setVaccinations(help);
            Long newRecordId = medicalRecordSessionBeanLocal.createNewMedicalRecord(newRecord);
            //newRecord = medicalRecordSessionBeanLocal.retrieveById(newRecordId);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New Medical Record created successfully!", "New Record ID: " + newRecordId));

            System.out.println("********** MedicalRecordManagementManagedBean.createNewMedicalRecord: " + event.getComponent().getAttributes().get("test")); 
            newRecord = new MedicalRecord();
        } catch (InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating medical record: " + ex.getMessage(), null));
        }
        
    }
    
    public void clearNewRecord() {
        newRecord = new MedicalRecord();
    }
    
        /**
     * @return the record
     */
    public MedicalRecord getNewRecord() {
        return newRecord;
    }

    /**
     * @param record the record to set
     */
    public void setRecord(MedicalRecord record) {
        this.newRecord = record;
    }

    /**
     * @return the records
     */
    public List<MedicalRecord> getRecords() {
        return records;
    }

    /**
     * @param records the records to set
     */
    public void setRecords(List<MedicalRecord> records) {
        this.records = records;
    }

    /**
     * @return the filteredRecords
     */
    public List<MedicalRecord> getFilteredRecords() {
        return filteredRecords;
    }

    /**
     * @param filteredRecords the filteredRecords to set
     */
    public void setFilteredRecords(List<MedicalRecord> filteredRecords) {
        this.filteredRecords = filteredRecords;
    }
    

    
    public void viewMedicalRecordDetails(ActionEvent event) throws IOException {
        System.out.println("reached2");
        Long recordIdToView = (Long)event.getComponent().getAttributes().get("recordId");
//        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("recordIdToView", recordIdToView);
//        FacesContext.getCurrentInstance().getExternalContext().redirect("viewMedicalRecordDetails.xhtml");
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/viewMedicalRecordDetails.xhtml?id=" + recordIdToView);

        System.out.println("reached");
    }
    
}
