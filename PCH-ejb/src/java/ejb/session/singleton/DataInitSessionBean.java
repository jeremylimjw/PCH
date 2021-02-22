/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.MedicationEntitySessionBeanLocal;
import entity.Employee;
import entity.Medication;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.enumeration.RoleEnum;

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
            medicationEntitySessionBeanLocal.create(new Medication("Medication A","testA", 10));
            medicationEntitySessionBeanLocal.create(new Medication("Medication B","testB", 20));
            medicationEntitySessionBeanLocal.create(new Medication("Medication C","testC", 30));
            medicationEntitySessionBeanLocal.create(new Medication("Medication D","testD", 40));
            
            em.persist(new Employee("Alice", "alice", "password", RoleEnum.DOCTOR));
            em.persist(new Employee("Bob", "bob", "password", RoleEnum.NURSE));
        }
    }
    
}
