/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Patient;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.PatientEntityException;

/**
 *
 * @author USER
 */
@Stateless
public class PatientSessionBean implements PatientSessionBeanLocal {

    @PersistenceContext(unitName = "PCH-ejbPU")
    private EntityManager em;
    
    private final ValidatorFactory validatorFactory;
    private final Validator validator;
    
    public PatientSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
    
    public Long create(Patient patient) throws PatientEntityException{
        Set<ConstraintViolation<Patient>>constraintViolations = validator.validate(patient);

        if(constraintViolations.isEmpty()){
            em.persist(patient);
            em.flush();

            return patient.getId();
        } else {
            throw new PatientEntityException(getValidatorErrors(constraintViolations));
        }
    }
    
    @Override
    public Patient login(String username, String password) throws PatientEntityException {
        Patient patient = retrieveByUsername(username);
        
        if (patient.getPassword().equals(password)) {
            return patient;
        } else {
            throw new PatientEntityException("Invalid password!");
        }
    }

    @Override
    public Patient retrieveById(Long id) throws PatientEntityException {
        if (id == null) throw new PatientEntityException("Error: Patient ID is not provided!");
        Patient patient = em.find(Patient.class, id);
        if (patient == null) throw new PatientEntityException("Error: Patient with ID " + id + " is not found!");
        return patient;
    }

    @Override
    public Patient retrieveByUsername(String username) throws PatientEntityException {
        Query query = em.createQuery("SELECT p FROM Patient p WHERE p.username = ?1");
        query.setParameter(1, username);

        try {
            return (Patient)query.getSingleResult();
        } catch(NoResultException | NonUniqueResultException e) {
            throw new PatientEntityException("Username " + username + " does not exist!");
        }
    }
    
    @Override
    public void updatePatientDetails(Patient patient) throws PatientEntityException {
        if (patient != null && patient.getId() != null) {
            
            Patient patientToUpdate = retrieveById(patient.getId());
            
            if (patientToUpdate.getUsername().equals(patient.getUsername())) {
                patientToUpdate.setEmail(patient.getEmail());
            }
        } else {
            throw new PatientEntityException("Patient ID not provided");
        }
    }
    
    @Override
    public void updatePatientPassword(Patient patient) throws PatientEntityException {
        if (patient != null && patient.getId() != null) {
            
            Patient patientToUpdate = retrieveById(patient.getId());
            
            if (patientToUpdate.getUsername().equals(patient.getUsername())) {
                patientToUpdate.setPassword(patient.getPassword());
            }
        } else {
            throw new PatientEntityException("Patient ID not provided");
        }
    }
    
    private String getValidatorErrors(Set<ConstraintViolation<Patient>> constraints) {
        String str = "Error: The following input value(s) are invalid!";

        for(ConstraintViolation constraintViolation : constraints) {
            str += "\nThe field '" + constraintViolation.getPropertyPath() + "' with input value '" + constraintViolation.getInvalidValue() + "' " + constraintViolation.getMessage();
        }

        return str;
    }
}
