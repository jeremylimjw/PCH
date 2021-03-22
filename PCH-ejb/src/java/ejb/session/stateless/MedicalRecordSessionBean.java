/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.MedicalRecord;
import java.util.Set;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.MedicalRecordEntityException;

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
    
    @TransactionAttribute
    @Override
    public void update(MedicalRecord medicalRecord) throws MedicalRecordEntityException {
        Set<ConstraintViolation<MedicalRecord>> constraints = validator.validate(medicalRecord);
        if (!constraints.isEmpty()) throw new MedicalRecordEntityException(getValidatorErrors(constraints));
        
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
    
    private String getValidatorErrors(Set<ConstraintViolation<MedicalRecord>> constraints) {
        String str = "Error: The following input value(s) are invalid!";
            
        for(ConstraintViolation constraintViolation : constraints) {
            str += "\nThe field '" + constraintViolation.getPropertyPath() + "' with input value '" + constraintViolation.getInvalidValue() + "' " + constraintViolation.getMessage();
        }
        
        return str;
    }
}
