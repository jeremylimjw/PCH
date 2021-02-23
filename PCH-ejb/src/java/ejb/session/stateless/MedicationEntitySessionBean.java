/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Medication;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    


    public MedicationEntitySessionBean() {
     
    }
    
    
    
    
    @Override
    public List<Medication> retrieveAll() {
        Query query = em.createQuery("SELECT m FROM Medication m");
        return query.getResultList();
    }
    
    @Override
    public Long create(Medication m) {
        em.persist(m);
        em.flush();
        return m.getId();
    }
    
    
    @Override
    public List<Medication> searchProductsByName(String searchString)
    {
        Query query = em.createQuery("SELECT m FROM Medication m WHERE m.name LIKE :inSearchString ORDER BY m.quantity_on_hand ASC");
        query.setParameter("inSearchString", "%" + searchString + "%");
        List<Medication> medications = query.getResultList();
        
       
        
        return medications;
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
    
    
    
}
