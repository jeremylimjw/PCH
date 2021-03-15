/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.MedicalRecordSessionBeanLocal;
import entity.MedicalRecord;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author 13002
 */
@Named(value = "viewAllMedicalRecordsManagedBean")
@RequestScoped
public class ViewAllMedicalRecordsManagedBean {
    
    @EJB
    private MedicalRecordSessionBeanLocal medicalRecordSessionBeanLocal;
    /**
     * Creates a new instance of ViewAllMedicalRecordsManagedBean
     */
    public ViewAllMedicalRecordsManagedBean() {
    }
    @PostConstruct
    public void PostConstruct() {
        records = medicalRecordSessionBeanLocal.retrieveAllMedicalRecords();
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
    
    private List<MedicalRecord> records;


    
}
