/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Medication;
import entity.Prescription;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.MedicationEntityException;

/**
 *
 * @author USER
 */
@Stateless
public class MedicationEntitySessionBean implements MedicationEntitySessionBeanLocal {

    @PersistenceContext(unitName = "PCH-ejbPU")
    private EntityManager em;
    
    @Resource
    private EJBContext eJBContext;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    


    public MedicationEntitySessionBean() {
     validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
    
    
    
    @Override
    public List<Medication> retrieveAll() {
        Query query = em.createQuery("SELECT m FROM Medication m WHERE m.deleted =?1 ");
        query.setParameter(1, false);
        return query.getResultList();
    }
    
    @Override
    public Long create(Medication m) {
        
        em.persist(m);
        em.flush();
        return m.getId();
    }
    
    
    
    
   
    @Override
    public Medication createNewMedication(Medication newMedication, List<Long> medId , List<String> cFood) throws MedicationEntityException
    {
        
      Medication newmed = new Medication();
      newmed = newMedication;
      em.persist(newMedication);

           if(medId != null && (!medId.isEmpty()))
           {
               for(Long medicationid :medId )
               {
                   Medication med = retrieveByMedicineId(medicationid);
                  // System.out.println(med.getName());
                  // System.out.println("THE NEW MED IS "+newMedication.getName());
                   newmed.getConflicting_medications().add(med);
                    
                    med.getParent_medications().add(newmed);
               }
           }
           newMedication.setConflicting_foods(cFood);
         
          
           em.flush();
           return newMedication;
    }
    
    
    
    @Override
    public List<Medication> searchMedicinesByName(String searchString)
    {
        Query query = em.createQuery("SELECT m FROM Medication m WHERE m.name LIKE :inSearchString ORDER BY m.quantity_on_hand ASC");
        query.setParameter("inSearchString", "%" + searchString + "%");
        List<Medication> medications = query.getResultList();
        
       
        
        return medications;
    }
    
 
    
    @Override
    public List<String> conflictMedicationByName(Long mId) throws MedicationEntityException
    {
          Medication medication = em.find(Medication.class, mId);
          List<String>medicationName = new ArrayList();
         for(Medication medicine : medication.getConflicting_medications())
         {
             medicationName.add(medicine.getName());
         }
         return medicationName;
        
    }
    
    @Override
    public void updateMedication(Medication medication , List<Long> medId ,List<String> cfood) throws MedicationEntityException
    {
         if(medication != null && medication.getId() != null)
        {
            Set<ConstraintViolation<Medication>>constraintViolations = validator.validate(medication);
        
            if(constraintViolations.isEmpty())
            {
                Medication medicationToUpdate = retrieveByMedicineId(medication.getId());

                if(medicationToUpdate.getId().equals(medication.getId()))
                {
                     
                    for(Medication dissMed : medication.getConflicting_medications()) 
                    {
                        dissMed.getParent_medications().remove(medication);
                    }
                    medication.getConflicting_medications().clear();
                     for(Medication dissMed2 : medicationToUpdate.getConflicting_medications()) 
                    {
                        dissMed2.getParent_medications().remove(medicationToUpdate);
                    }
                    medicationToUpdate.getConflicting_medications().clear();
                    
                    medicationToUpdate.setBrand(medication.getBrand());
                    medicationToUpdate.setName(medication.getName());
                    medicationToUpdate.setPrescription_quantity(medication.getPrescription_quantity());
                    medicationToUpdate.setQuantity_on_hand(medication.getQuantity_on_hand());
                    medicationToUpdate.setDescription(medication.getDescription());
                    medicationToUpdate.setConflicting_foods(medication.getConflicting_foods());
                   
                for(Long medicationid :medId )
                {
                    
                   Medication med = retrieveByMedicineId(medicationid);
                 
                  // System.out.println(med.getName());
                  // System.out.println("THE NEW MED IS "+newMedication.getName());
                   medication.getConflicting_medications().add(med);
                    
                    med.getParent_medications().add(medication);
                   
               }
             medicationToUpdate.setConflicting_medications(medication.getConflicting_medications());
                       
                    
                }
                else
                {
                    throw new MedicationEntityException("Medication record to be updated does not match the existing record");
                }
            }
        }
    }
    
    @Override
    public Medication retrieveByMedicineId(Long mId) throws MedicationEntityException
    {
        Medication medication = em.find(Medication.class, mId);
        
        if(medication != null)
        {
            return medication;
        }
        else
        {
            throw new MedicationEntityException("Medication Id " + mId + " does not exist!");
        }
    }
    
    
    @Override
    public Long deleteMedication(Long medication_id, List<Medication> medication) throws MedicationEntityException{
        
        
          Medication medication_to_delete = retrieveByMedicineId(medication_id);
         
          for(int i =0; i<medication.size(); i++)
          {
              if(medication.get(i).getConflicting_medications().contains(medication_to_delete))
              {
                  medication.get(i).getConflicting_medications().remove(medication_to_delete);
                  medication.get(i).getParent_medications().remove(medication_to_delete);
              }
              
          }
          for(Medication dissMed: medication_to_delete.getConflicting_medications())
          {
              dissMed.getParent_medications().remove(medication_to_delete);
          }
          medication_to_delete.getConflicting_medications().clear();
          Long id = delete(medication_to_delete.getId());
        
        return id;
    }

     
    @Override
     public Long delete(Long medication_id) throws MedicationEntityException{
         
         
         Medication medication_to_delete = retrieveByMedicineId(medication_id);
         if(medication_to_delete.isDeleted()){
             throw new MedicationEntityException("Error: Medication with ID "+ medication_id + " has already been deleted!");
         }
               
                    
                    
                     if(medication_to_delete.hasConflictingMedicationAssociated()){
             throw new MedicationEntityException("Error: Medication is associated with a conflicting medication and cannot be deleted!");
                     }
             
                    medication_to_delete.setDeleted(true);
                    
         return medication_to_delete.getId();
     }
    
    @TransactionAttribute
    @Override
    public void processPrescriptions(List<Prescription> prescriptions) throws MedicationEntityException {
        try {
            for (Prescription p : prescriptions) {
                Medication medication = retrieveByMedicineId(p.getMedication().getId());
                if (medication.getQuantity_on_hand() < p.getQuantity()) throw new MedicationEntityException(medication.getName() + " does not have sufficient stock in hand!");
                medication.setQuantity_on_hand(medication.getQuantity_on_hand() - p.getQuantity());
            }
        } catch (MedicationEntityException ex) {
            eJBContext.setRollbackOnly();
            throw ex;
        }
    }
     
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Medication>>constraintViolations)
    {
        String msg = "Input data validation error!:";
            
        for(ConstraintViolation constraintViolation:constraintViolations)
        {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        
        return msg;
    }
     
}
