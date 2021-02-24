/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.AppointmentSessionBeanLocal;
import entity.Appointment;
import entity.Employee;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import util.enumeration.AppointmentTypeEnum;
import util.enumeration.RoleEnum;
import util.exception.AppointmentEntityException;

/**
 *
 * @author USER
 */
@Named(value = "appointmentManagedBean")
@RequestScoped
public class AppointmentManagedBean implements Serializable {

    @EJB
    private AppointmentSessionBeanLocal appointmentSessionBeanLocal;
    
    private Employee user;
    private List<Appointment> appointments;
    private List<Appointment> queue;
    
    public AppointmentManagedBean() {
        appointments = new ArrayList<>();
        queue = new LinkedList<>();
    }
    
    @PostConstruct
    public void postConstruct() {
        user = (Employee) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        getAllAppointmentsForToday();
        getOngoingQueue();
    }
    
    public void getAllAppointmentsForToday() {
        if (user.getRole().equals(RoleEnum.DOCTOR)) {
            appointments = appointmentSessionBeanLocal.retrieveAppointmentsByDoctorId(user.getId(), new Date());
        } else {
            appointments = appointmentSessionBeanLocal.retrieveAppointmentsByDay(new Date());
        }
    }
    
    public void getOngoingQueue() {
        queue = appointmentSessionBeanLocal.retrieveOngoingQueue();
    }
    
    // ---- FOR TESTING ONLY ----
    // ADD SAMPLE APPOINTMENT
    public void addRandomAppointment() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, (int)(Math.random()*((23-0)+1))+0);
        c.set(Calendar.MINUTE, (int)(Math.random()*((60-0)+1))+0);
        c.set(Calendar.SECOND, 0);
        
        try {
            appointmentSessionBeanLocal.createAppointment(user.getId(), 1l, c.getTime(), AppointmentTypeEnum.CONSULTATION);
        } catch (AppointmentEntityException ex) {
            System.out.println(ex.getMessage());
        }
        
        getAllAppointmentsForToday();
    }
    
    // ---- FOR TESTING ONLY ----
    // ADD SAMPLE WALK IN
    public void addRandomWalkIn() {
        try {
            appointmentSessionBeanLocal.createWalkIn(1l, AppointmentTypeEnum.CONSULTATION);
        } catch (AppointmentEntityException ex) {
            System.out.println(ex.getMessage());
        }
        
        getOngoingQueue();
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public List<Appointment> getQueue() {
        return queue;
    }

    public void setQueue(List<Appointment> queue) {
        this.queue = queue;
    }
}
