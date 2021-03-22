/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Patient;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author GraceLi
 */
@Stateless
public class PatientEntitySessionBean implements PatientEntitySessionBeanLocal {

    @PersistenceContext(unitName = "PCH-ejbPU")
    private EntityManager em;

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
}
