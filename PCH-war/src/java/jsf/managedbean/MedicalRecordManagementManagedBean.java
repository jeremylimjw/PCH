/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.MedicalRecordSessionBeanLocal;
import entity.MedicalRecord;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.exception.InputDataValidationException;

/**
 *
 * @author 13002
 */
@Named(value = "MedicalRecordManagementManagedBean")
@ViewScoped
public class MedicalRecordManagementManagedBean implements Serializable {

    @EJB
    private MedicalRecordSessionBeanLocal medicalRecordSessionBeanLocal;
    
    private MedicalRecord newRecord;
    private List<MedicalRecord> records;
    private List<MedicalRecord> filteredRecords;
    
    private String searchString;
    
    /**
     * Creates a new instance of patientRecordManagementManagedBean
     */
    public MedicalRecordManagementManagedBean() {
        newRecord = new MedicalRecord();
        records = new ArrayList<>();
    }
    
    @PostConstruct
    public void PostConstruct() {
        records = medicalRecordSessionBeanLocal.retrieveAllMedicalRecords();
    }
    
    public void createNewMedicalRecord(ActionEvent event) throws InputDataValidationException {
        newRecord.setDate_created(new Date());
        Long newRecordId = medicalRecordSessionBeanLocal.createNewMedicalRecord(newRecord);
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New Medical Record created successfully!", "New Record ID: " + newRecordId));
        
        System.out.println("********** CreateNewRecordManagedBean.createNewRecord: " + event.getComponent().getAttributes().get("test"));
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
    
    public void searchMedicalRecord() {
        if(searchString == null || searchString.trim().length() == 0) {
            filteredRecords = records;
        } else {
            filteredRecords = medicalRecordSessionBeanLocal.searchMedicalRecordsByName(searchString);
        }
    }
    
}
