/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.MedicalRecord;
import entity.Patient;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.InputDataValidationException;
import util.exception.MedicalRecordNotFoundException;

/**
 *
 * @author GraceLi
 */
@Stateless
public class PatientEntitySessionBean implements PatientEntitySessionBeanLocal {

    @EJB
    private MedicalRecordSessionBeanLocal medicalRecordSessionBeanLocal;
    
    

    @PersistenceContext(unitName = "PCH-ejbPU")
    private EntityManager em;
    
   
    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public PatientEntitySessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
    

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @Override
    public List<Patient> retrieveAllPatients() {
        Query query = em.createQuery("SELECT p FROM Patient p");
        List<Patient> patients = query.getResultList();
        for (Patient p : patients) {
            p.getMedical_record();
        }
        return patients;
    }
    
    @Override
    public Long createNewPatient(Patient newPatient, String NRIC) throws MedicalRecordNotFoundException, InputDataValidationException {
        Set<ConstraintViolation<Patient>>constraintViolations = validator.validate(newPatient);
        
        if(constraintViolations.isEmpty()){
            
            MedicalRecord medicalRecord = medicalRecordSessionBeanLocal.searchMedicalRecordsByNRIC(NRIC).get(0);
            if (medicalRecord == null) {
                throw new MedicalRecordNotFoundException("No patient with NRIC : " + NRIC + " was found.");
            } else {
                medicalRecord.setPatient(newPatient);
                newPatient.setMedical_record(medicalRecord);
                em.persist(newPatient);
                em.flush();
                return newPatient.getPatientId();
            }
          
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Patient>> constraintViolations)
    {
        String msg = "Input data validation error!:";
            
        for(ConstraintViolation constraintViolation:constraintViolations)
        {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        
        return msg;
    }
}
