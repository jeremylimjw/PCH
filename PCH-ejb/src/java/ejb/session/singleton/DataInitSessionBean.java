/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.AppointmentSessionBeanLocal;
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
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.enumeration.AppointmentTypeEnum;
import util.enumeration.RoleEnum;
import util.enumeration.ScheduleTypeEnum;
import util.enumeration.StatusEnum;
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
                em.persist(new Medication("Medication A","Brand A", "10mg", new BigDecimal(10), 10, 
                        new ArrayList<>(Arrays.asList("Food 1", "Food 2")), 
                        new ArrayList<>(Arrays.asList("Ibuprofen", "Penicillin", "Aspirin")), "This is med A", null));
                em.persist(new Medication("Medication B","Brand A", "100ml", new BigDecimal(20), 12, new ArrayList<>(), new ArrayList<>(), null, null));
                em.persist(new Medication("Medication C","Brand B", "23mg", new BigDecimal(30), 14, new ArrayList<>(), new ArrayList<>(), null, null));
                em.persist(new Medication("Medication D","Brand B", "55mg", new BigDecimal(40), 12, new ArrayList<>(), new ArrayList<>(), null, null));

                Employee alice = new Employee("Alice", "alice", "password", "alice@gmail.com", RoleEnum.DOCTOR); em.persist(alice);
                Employee bob = new Employee("Bob", "bob", "password", "bob@gmail.com", RoleEnum.DOCTOR); em.persist(bob);
                Employee charlie = new Employee("Charlie", "charlie", "password", "charlie@gmail.com", RoleEnum.NURSE); em.persist(charlie);


                MedicalRecord desmondRecord = new MedicalRecord("Desmond", "S94626123A", "address1", 
                        new SimpleDateFormat("dd/MM/yyyy").parse("20/6/1996"), "82746726", "B+", 
                        new ArrayList<>(Arrays.asList("Ibuprofen", "Drug 1", "Drug 2", "Drug 3")), 
                        new ArrayList<>(Arrays.asList("Family History 1", "Family History 2", "Family History 3")), 
                        new ArrayList<>(Arrays.asList("Past History 1", "Past History 2", "Past History 3")), 
                        new ArrayList<>(Arrays.asList("Vaccination 1", "Vaccination 2", "Vaccination 3")));
                Patient desmond = new Patient("desmond", "password", "desmond@gmail.com", desmondRecord); em.persist(desmond);
                
                MedicalRecord ericRecord = new MedicalRecord("Eric", "S92726521F", "address1", 
                        new SimpleDateFormat("dd/MM/yyyy").parse("20/6/1996"), "83873682", "O", 
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>()); 
                Patient eric = new Patient("eric", "password", "eric@gmail.com", ericRecord); em.persist(eric);
                

            } catch (ParseException ex) {
                System.out.println(ex.getMessage());
            }

        }
        
        generateAppointments();
    }
    
    private void generateAppointments() {
        
        double p = 0.8; // probability for a time slot to be booked
        int monthsBefore = -1; // how far before to add appointments since
        int monthsAfter = 1; // how far after to add apopintments til
        
        Query query1 = em.createQuery("SELECT e FROM Employee e WHERE e.role = ?1");
        query1.setParameter(1, RoleEnum.DOCTOR);
        List<Employee> doctors = query1.getResultList();
        int doctorsSize = doctors.size();
        
        Query query2 = em.createQuery("SELECT m FROM MedicalRecord m");
        List<MedicalRecord> medical_records = query2.getResultList();
        int mrsSize = medical_records.size();
        
        int[][] times = {{8,0},{8,15},{8,30},{8,45},{9,0},{9,15},{9,30},{9,45},{10,0},{10,15},{10,30},{10,45},{11,0},{11,15},{11,30},{11,45},
            {14,0},{14,15},{14,30},{14,45},{15,0},{15,15},{15,30},{15,45},{16,0},{16,15},{16,30},{16,45},{17,0},{17,15},{17,30},{17,45}};
        Random random = new Random();

        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, monthsBefore);
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1, 0, 0, 0);

        Calendar today = Calendar.getInstance();
        today.set(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DATE), 0, 0, 0);

        while (c.getTime().getTime() < today.getTime().getTime() && c.get(Calendar.DAY_OF_WEEK) != 1 && c.get(Calendar.DAY_OF_WEEK) != 7) {

            for (int i = 0; i < times.length; i++) {
                c.set(Calendar.HOUR_OF_DAY, times[i][0]);
                c.set(Calendar.MINUTE, times[i][1]);

                if(random.nextDouble() < p) {
                    Appointment a = new Appointment(doctors.get(i%doctorsSize), medical_records.get((i%mrsSize)), c.getTime(), ScheduleTypeEnum.APPOINTMENT, AppointmentTypeEnum.CONSULTATION, StatusEnum.BOOKED);
                    em.persist(a);
                    a.setQueue_no(String.format("A%03d", a.getId()));
                }
            }

            c.add(Calendar.DATE, 1);
        }
    }
    
}
