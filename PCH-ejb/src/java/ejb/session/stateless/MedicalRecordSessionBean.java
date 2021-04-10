/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.MedicalRecord;

import java.util.Date;
import entity.Appointment;
import java.io.IOException;
import java.util.List;
import java.util.Date;
import java.util.Set;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
    public Long create(MedicalRecord medicalRecord) throws MedicalRecordEntityException{
        Set<ConstraintViolation<MedicalRecord>>constraintViolations = validator.validate(medicalRecord);
        
        Query query = em.createQuery("SELECT m FROM MedicalRecord m WHERE m.nric = ?1");
        query.setParameter(1, medicalRecord.getNric());
        if (query.getResultList().size() > 0) throw new MedicalRecordEntityException("Error: NRIC already exists!");

        if(constraintViolations.isEmpty()){
            em.persist(medicalRecord);
            em.flush();

            return medicalRecord.getId();
        } else {
            throw new MedicalRecordEntityException(getValidatorErrors(constraintViolations));
        }
    }
    

    @Override
    public MedicalRecord retrieveById(Long id) throws MedicalRecordEntityException {
        if (id == null) {
            throw new MedicalRecordEntityException("Error: Medical Record ID is not provided!");
        }
        MedicalRecord medicalRecord = em.find(MedicalRecord.class, id);
        if (medicalRecord == null) {
            throw new MedicalRecordEntityException("Error: Medical Record with ID " + id + " is not found!");
        }
        return medicalRecord;
    }

    @TransactionAttribute
    @Override
    public void update(MedicalRecord medicalRecord) throws MedicalRecordEntityException {
        Set<ConstraintViolation<MedicalRecord>> constraints = validator.validate(medicalRecord);
        if (!constraints.isEmpty()) {
            throw new MedicalRecordEntityException(getValidatorErrors(constraints));
        }

        MedicalRecord oldMedicalRecord = retrieveById(medicalRecord.getId());

        oldMedicalRecord.setName(medicalRecord.getName());
        oldMedicalRecord.setAddress(medicalRecord.getAddress());
        oldMedicalRecord.setContact_number(medicalRecord.getContact_number());
        oldMedicalRecord.setBlood_type(medicalRecord.getBlood_type());
        oldMedicalRecord.setDob(medicalRecord.getDob());
        oldMedicalRecord.setDrug_allergys(medicalRecord.getDrug_allergys());
        oldMedicalRecord.setFamily_historys(medicalRecord.getFamily_historys());
        oldMedicalRecord.setPast_medical_historys(medicalRecord.getPast_medical_historys());
        oldMedicalRecord.setVaccinations(medicalRecord.getVaccinations());
    }

    @TransactionAttribute
    @Override
    public void updateMedReq(Long id, String name, String nric, String address, Date dob, String contact_number, String blood_type, List<String> drug_allergys, List<String> family_historys, List<String> past_medical_historys, List<String> vaccinations) throws MedicalRecordEntityException 
    {

        MedicalRecord oldMedicalRecord = retrieveById(id);

        oldMedicalRecord.setName(name);
        oldMedicalRecord.setNric(nric);
        oldMedicalRecord.setAddress(address);
        oldMedicalRecord.setContact_number(contact_number);
        oldMedicalRecord.setBlood_type(blood_type);
        oldMedicalRecord.setDob(dob);
        oldMedicalRecord.setDrug_allergys(drug_allergys);
        oldMedicalRecord.setFamily_historys(family_historys);
        oldMedicalRecord.setPast_medical_historys(past_medical_historys);
        oldMedicalRecord.setVaccinations(vaccinations);
    }

    @Override
    public Long createNewMedicalRecord(MedicalRecord newMedicalRecord) throws InputDataValidationException {
        Set<ConstraintViolation<MedicalRecord>> constraintViolations = validator.validate(newMedicalRecord);

        if (constraintViolations.isEmpty()) {
            em.persist(newMedicalRecord);
            em.flush();

            return newMedicalRecord.getId();
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<MedicalRecord>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }

    @Override
    public List<MedicalRecord> retrieveAllMedicalRecords() {
        Query query = em.createQuery("SELECT m FROM MedicalRecord m");
        List<MedicalRecord> medicalRecords = query.getResultList();

        for (MedicalRecord medicalRecord : medicalRecords) {
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

        for (MedicalRecord medicalRecord : medicalRecords) {
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

        for (MedicalRecord medicalRecord : medicalRecords) {
            medicalRecord.getAppointments();
            medicalRecord.getPatient();
        }

        return medicalRecords;
    }

    @Override
    public MedicalRecord retrieveMedicalRecordByNRIC(String patientNric) throws MedicalRecordNotFoundException {
        try {
            Query query = em.createQuery("SELECT m FROM MedicalRecord m WHERE m.nric = :inNric");
            query.setParameter("inNric", patientNric);
            MedicalRecord medicalRecord = (MedicalRecord) query.getSingleResult();
            return medicalRecord;
        } catch (NoResultException ex) {
            throw new MedicalRecordNotFoundException("No medical record found for NRIC: " + patientNric);
        }
    }

    @Override
    public void updateMedicalRecord(MedicalRecord medicalRecord) throws MedicalRecordNotFoundException, MedicalRecordEntityException {
        if (medicalRecord != null && medicalRecord.getId() != null) {
            Set<ConstraintViolation<MedicalRecord>> constraintViolations = validator.validate(medicalRecord);
            if (constraintViolations.isEmpty()) {
                MedicalRecord medicalRecordToUpdate = retrieveById(medicalRecord.getId());

                if (medicalRecordToUpdate.getNric().equals(medicalRecord.getNric())) {
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

        for (ConstraintViolation constraintViolation : constraints) {
            str += "\nThe field '" + constraintViolation.getPropertyPath() + "' with input value '" + constraintViolation.getInvalidValue() + "' " + constraintViolation.getMessage();
        }

        return str;
    }

}
