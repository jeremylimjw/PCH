/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.AppointmentSessionBeanLocal;
import ejb.session.stateless.EmployeeEntitySessionBeanLocal;
import entity.Appointment;
import entity.Employee;
import entity.MedicalRecord;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;
import util.enumeration.AppointmentTypeEnum;
import util.exception.AppointmentEntityException;

/**
 *
 * @author USER
 */
@Named(value = "appointmentManagedBean")
@ApplicationScoped
public class AppointmentManagedBean {

    @EJB
    private EmployeeEntitySessionBeanLocal employeeEntitySessionBeanLocal;
    @EJB
    private AppointmentSessionBeanLocal appointmentSessionBeanLocal;
    
    
    private Map<Employee, List<Appointment>> appointments;
    private Queue<Appointment> queue;
    
    public AppointmentManagedBean() {
        appointments = new HashMap<>();
        queue = new LinkedList<>();
    }
    
    @PostConstruct
    public void postConstruct() {
        getAllAppointmentsForToday();
    }
    
    public void getAllAppointmentsForToday() {
        appointments = new HashMap<>();
        Date today = new Date();
        List<Employee> doctors = employeeEntitySessionBeanLocal.retrieveAllDoctors();
        for (Employee d : doctors) {
            appointments.put(d, appointmentSessionBeanLocal.retrieveAppointmentsByDoctorId(d.getId(), today));
        }
    }
    
    // ---- FOR TESTING ONLY ----
    // ADD SAMPLE APPOINTMENT
    public void addRandomAppointment(Employee user) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, (int)(Math.random()*((23-0)+1))+0);
        c.set(Calendar.MINUTE, (int)(Math.random()*((60-0)+1))+0);
        c.set(Calendar.SECOND, 0);
        
        try {
            appointmentSessionBeanLocal.create(user.getId(), 1l, c.getTime(), AppointmentTypeEnum.CONSULTATION);
        } catch (AppointmentEntityException ex) {
            System.out.println(ex.getMessage());
        }
        
        getAllAppointmentsForToday();
    }
    
    // ---- FOR TESTING ONLY ----
    // ADD SAMPLE APPOINTMENT
    public void addRandomWalkIn() {
        MedicalRecord mr = new MedicalRecord();
        mr.setName("Non-persistent");
        mr.setDate_created(new Date());
        Appointment appointment = new Appointment(null, mr, null, AppointmentTypeEnum.VACCINATION);
        queue.add(appointment);
    }

    public Map<Employee, List<Appointment>> getAppointments() {
        return appointments;
    }

    public void setAppointments(Map<Employee, List<Appointment>> appointments) {
        this.appointments = appointments;
    }

    public Queue<Appointment> getQueue() {
        return queue;
    }

    public void setQueue(Queue<Appointment> queue) {
        this.queue = queue;
    }
    
    public int getTotalAppointmentCount() {
        int count = 0;
        for (Map.Entry entry : appointments.entrySet()) {
            for (Appointment a : (List<Appointment>) entry.getValue()) {
                count++;
            }
        }
        return count;
    }
    
    public List<Appointment> getMyAppointments(Employee user) {
        List<Appointment> myAppointments = new ArrayList<>();
        for (Map.Entry entry : appointments.entrySet()) {
            if (entry.getKey().equals(user)) {
                for (Appointment a : (List<Appointment>) entry.getValue()) {
                    myAppointments.add(a);
                }
            }
        }
        return myAppointments;
    }
    
    public int getMyAppointmentCount(Employee user) {
        int count = 0;
        for (Map.Entry entry : appointments.entrySet()) {
            if (entry.getKey().equals(user)) {
                for (Appointment a : (List<Appointment>) entry.getValue()) {
                    count++;
                }
            }
        }
        return count;
    }
}
