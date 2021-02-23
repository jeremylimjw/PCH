/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.MedicationEntitySessionBeanLocal;
import entity.Employee;
import entity.Medication;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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
            medicationEntitySessionBeanLocal.create(new Medication("Medication A","Brand A", "10mg", new BigDecimal(10), 10, new ArrayList<>(Arrays.asList("Food 1", "Food 2")), null, null));
            medicationEntitySessionBeanLocal.create(new Medication("Medication B","Brand A", "100ml", new BigDecimal(20), 12, null, null, null));
            medicationEntitySessionBeanLocal.create(new Medication("Medication C","Brand B", "23mg", new BigDecimal(30), 14, null, null, null));
            medicationEntitySessionBeanLocal.create(new Medication("Medication D","Brand B", "55mg", new BigDecimal(40), 12, null, null, null));
            
            em.persist(new Employee("Alice", "alice", "password", "alice@gmail.com", RoleEnum.DOCTOR));
            em.persist(new Employee("Bob", "bob", "password", "bob@gmail.com", RoleEnum.DOCTOR));
            em.persist(new Employee("Charlie", "charlie", "password", "charlie@gmail.com", RoleEnum.NURSE));
        }
    }
    
}
