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
}
