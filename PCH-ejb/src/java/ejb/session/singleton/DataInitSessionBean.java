/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.MedicationEntitySessionBeanLocal;
import entity.Medication;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author USER
 */
@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

    @EJB
    private MedicationEntitySessionBeanLocal medicationEntitySessionBeanLocal;
    
    @PersistenceContext(unitName = "PCH-ejbPU")
    private EntityManager em;
    
    @PostConstruct
    public void postConstruct() {
        if (em.find(Medication.class, 1l) == null) {
            medicationEntitySessionBeanLocal.create(new Medication("Medication A"));
            medicationEntitySessionBeanLocal.create(new Medication("Medication B"));
            medicationEntitySessionBeanLocal.create(new Medication("Medication C"));
            medicationEntitySessionBeanLocal.create(new Medication("Medication D"));
        }
    }
    
}
