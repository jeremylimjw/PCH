/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.MedicalRecord;
import entity.Appointment;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.InputDataValidationException;
import util.exception.MedicalRecordEntityException;
import util.exception.MedicalRecordNotFoundException;



/**
 *
 * @author USER
 */
@Stateless
public class MedicalRecordSessionBean implements MedicalRecordSessionBeanLocal {

    @PersistenceContext(unitName = "PCH-ejbPU")
    private EntityManager em;
    
    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public MedicalRecordSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
    @Override
    public MedicalRecord retrieveById(Long id) throws MedicalRecordEntityException {
        if (id == null) throw new MedicalRecordEntityException("Error: Medical Record ID is not provided!");
        MedicalRecord medicalRecord = em.find(MedicalRecord.class, id);
        if (medicalRecord == null) throw new MedicalRecordEntityException("Error: Medical Record with ID " + id + " is not found!");
        return medicalRecord;
    }
    
    @Override
    public Long createNewMedicalRecord(MedicalRecord newMedicalRecord) throws InputDataValidationException{
        Set<ConstraintViolation<MedicalRecord>>constraintViolations = validator.validate(newMedicalRecord);
        
        if(constraintViolations.isEmpty()){
            em.persist(newMedicalRecord);
            em.flush();
            
            return newMedicalRecord.getId();
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<MedicalRecord>>constraintViolations)
    {
        String msg = "Input data validation error!:";
            
        for(ConstraintViolation constraintViolation:constraintViolations)
        {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        
        return msg;
    }
    
    @Override
    public List<MedicalRecord> retrieveAllMedicalRecords() {
        Query query = em.createQuery("SELECT m FROM MedicalRecord m");
        List<MedicalRecord> medicalRecords = query.getResultList();
        
        for (MedicalRecord medicalRecord:medicalRecords){
            medicalRecord.getAppointments();
            medicalRecord.getPatient(); 
        }
        return medicalRecords;
    }
    
    @Override
    public List<MedicalRecord> searchMedicalRecordsByName(String searchString) {
        Query query = em.createQuery("SELECT m FROM MedicalRecord m WHERE m.name LIKE :inSearchString ORDER BY m.name ASC");
        query.setParameter("inSearchString", "%" + searchString + "%");
        List<MedicalRecord> medicalRecords = query.getResultList();
        
        for (MedicalRecord medicalRecord:medicalRecords){
            medicalRecord.getAppointments();
            medicalRecord.getPatient(); 
        }
        
        return medicalRecords;
    }
    
    @Override
    public List<MedicalRecord> searchMedicalRecordsByNRIC(String searchString) {
        Query query = em.createQuery("SELECT m FROM MedicalRecord m WHERE m.nric LIKE :inSearchString ORDER BY m.name ASC");
        query.setParameter("inSearchString", "%" + searchString + "%");
        List<MedicalRecord> medicalRecords = query.getResultList();
        
        for (MedicalRecord medicalRecord:medicalRecords){
            medicalRecord.getAppointments();
            medicalRecord.getPatient(); 
        }
        
        return medicalRecords;
    }
    
    @Override
    public void updateMedicalRecord(MedicalRecord medicalRecord) throws MedicalRecordNotFoundException, MedicalRecordEntityException{
        if (medicalRecord != null && medicalRecord.getId() != null) {
            Set<ConstraintViolation<MedicalRecord>>constraintViolations = validator.validate(medicalRecord);
            if (constraintViolations.isEmpty()) {
                MedicalRecord medicalRecordToUpdate = retrieveById(medicalRecord.getId());
                
                if(medicalRecordToUpdate.getNric().equals(medicalRecord.getNric())) {
                    // to tie patient account with medical record
//                    if(patientId != null && (!medicalRecordToUpdate.getPatient().getPatientId().equals(patientId))) {
//                        Patient patientToUpdate = patientSessionBeanLocal.retrievePatientByPatientId(patientId);
//                        medicalRecordToUpdate.setPatient(patientToUpdate);
//                        
//                    }
                }
                
                medicalRecordToUpdate.setName(medicalRecord.getName());
                medicalRecordToUpdate.setAddress(medicalRecord.getAddress());
                medicalRecordToUpdate.setNric(medicalRecord.getNric());
                medicalRecordToUpdate.setAppointments(medicalRecord.getAppointments());
                medicalRecordToUpdate.setDob(medicalRecord.getDob());
                medicalRecordToUpdate.setContact_number(medicalRecord.getContact_number());
                medicalRecordToUpdate.setBlood_type(medicalRecord.getBlood_type());
                medicalRecordToUpdate.setDrug_allergys(medicalRecord.getDrug_allergys());
                medicalRecordToUpdate.setFamily_historys(medicalRecord.getFamily_historys());
                medicalRecordToUpdate.setPast_medical_historys(medicalRecord.getPast_medical_historys());
                medicalRecordToUpdate.setVaccinations(medicalRecord.getVaccinations());
                
            } else {
                throw new MedicalRecordEntityException(getValidatorErrors(constraintViolations));
            }
        } else {
            throw new MedicalRecordNotFoundException("MedicalRecord Id not provided for medical record to be updated");
        }
    }
    
    
    private String getValidatorErrors(Set<ConstraintViolation<MedicalRecord>> constraints) {
        String str = "Error: The following input value(s) are invalid!";
            
        for(ConstraintViolation constraintViolation : constraints) {
            str += "\nThe field '" + constraintViolation.getPropertyPath() + "' with input value '" + constraintViolation.getInvalidValue() + "' " + constraintViolation.getMessage();
        }
        
        return str;
    }
    
}
