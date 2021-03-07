/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.AppointmentSessionBeanLocal;
import ejb.session.stateless.MedicationEntitySessionBeanLocal;
import entity.Appointment;
import entity.Employee;
import entity.MedicalRecord;
import entity.Medication;
import entity.Patient;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.enumeration.AppointmentTypeEnum;
import util.enumeration.RoleEnum;
import util.enumeration.ScheduleTypeEnum;
import util.exception.AppointmentEntityException;

/**
 *
 * @author USER
 */
@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

    @EJB
    private AppointmentSessionBeanLocal appointmentSessionBeanLocal;
    
    @PersistenceContext(unitName = "PCH-ejbPU")
    private EntityManager em;
    
    @PostConstruct
    public void postConstruct() {
        if (em.find(Medication.class, 1l) == null) {

            try {
                em.persist(new Medication("Medication A","Brand A", "10mg", new BigDecimal(10), 10, new ArrayList<>(Arrays.asList("Food 1", "Food 2")), null, null));
                em.persist(new Medication("Medication B","Brand A", "100ml", new BigDecimal(20), 12, null, null, null));
                em.persist(new Medication("Medication C","Brand B", "23mg", new BigDecimal(30), 14, null, null, null));
                em.persist(new Medication("Medication D","Brand B", "55mg", new BigDecimal(40), 12, null, null, null));

                Employee alice = new Employee("Alice", "alice", "password", "alice@gmail.com", RoleEnum.DOCTOR); em.persist(alice);
                Employee bob = new Employee("Bob", "bob", "password", "bob@gmail.com", RoleEnum.DOCTOR); em.persist(bob);
                Employee charlie = new Employee("Charlie", "charlie", "password", "charlie@gmail.com", RoleEnum.NURSE); em.persist(charlie);


                MedicalRecord desmondRecord = new MedicalRecord("Desmond", "S94626123A", "address1", new SimpleDateFormat("dd/MM/yyyy").parse("20/6/1996"), "82746726", "B+", new ArrayList<>(Arrays.asList("Drug 1")), new ArrayList<>(), new ArrayList<>(), new ArrayList<>()); em.persist(desmondRecord);
                Patient desmond = new Patient("desmond", "password", "desmond@gmail.com", desmondRecord); em.persist(desmond);

            } catch (ParseException ex) {
                System.out.println(ex.getMessage());
            }

        }
    }
    
}
