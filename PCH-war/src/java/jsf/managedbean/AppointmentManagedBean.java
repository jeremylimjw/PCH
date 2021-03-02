/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateful.QueueBoardSessionBeanLocal;
import ejb.session.stateless.AppointmentSessionBeanLocal;
import entity.Appointment;
import entity.Employee;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import util.enumeration.AppointmentTypeEnum;
import util.enumeration.RoleEnum;
import util.enumeration.ScheduleTypeEnum;
import util.enumeration.StatusEnum;
import util.exception.AppointmentEntityException;
import util.exception.EmployeeEntityException;

/**
 *
 * @author USER
 */
@Named(value = "appointmentManagedBean")
@SessionScoped
public class AppointmentManagedBean implements Serializable {

    @EJB
    private QueueBoardSessionBeanLocal queueBoardSessionBeanLocal;

    @EJB
    private AppointmentSessionBeanLocal appointmentSessionBeanLocal;
    
    private Employee user;
    private List<Appointment> appointments;
    private List<Appointment> queue;
    private String calling;
    private String previous;
    
    public AppointmentManagedBean() {
        appointments = new ArrayList<>();
        queue = new LinkedList<>();
        calling = "-";
        previous = "-";
    }
    
    @PostConstruct
    public void postConstruct() {
        user = (Employee) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        getAllAppointmentsForToday();
        getOngoingQueue();
    }
    
    public void getAllAppointmentsForToday() {
        if (user.getRole().equals(RoleEnum.DOCTOR)) {
            appointments = appointmentSessionBeanLocal.retrieveAppointmentsByDoctorIdByDay(user.getId(), new Date());
        } else {
            appointments = appointmentSessionBeanLocal.retrieveAppointmentsByDay(new Date());
        }
    }
    
    public void getOngoingQueue() {
        queue = appointmentSessionBeanLocal.retrieveOngoingQueue();
    }
    
    public void updateStatus(Appointment appointment, StatusEnum e) {
        if (isCallingSomeone() && e.equals(StatusEnum.IN_PROGRESS)) {
//            FacesContext.getCurrentInstance().addMessage("message", new FacesMessage(FacesMessage.SEVERITY_ERROR, "You are already seeing someone.", ":("));
            System.out.println("(TO BE DISPLAYED IN THE UI) You are already seeing someone");
        } else {
            try {
                if (e.equals(StatusEnum.IN_PROGRESS)) {

                    // If its a walk-in, associate it with the doctor
                    if (appointment.getSchedule_type().equals(ScheduleTypeEnum.WALK_IN)) appointmentSessionBeanLocal.assignAppointment(appointment.getId(), user.getId());

                    queueBoardSessionBeanLocal.add(user.getId(), appointment.getId());

                    previous = calling;
                    calling = appointment.getQueue_no();
                }
                appointmentSessionBeanLocal.updateStatus(appointment.getId(), e);
                getAllAppointmentsForToday();
                getOngoingQueue();
            } catch(EmployeeEntityException | AppointmentEntityException ex) {
                System.out.println(ex.getMessage());
    //            FacesContext.getCurrentInstance().addMessage("message", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to update appointment status.", ":("));
            }
        }
    }
    
    public boolean isCallingSomeone() {
        for (Appointment a : appointments) {
            if (a.getStatus().equals(StatusEnum.IN_PROGRESS)) return true;
        }
        for (Appointment a : queue) {
            if (a.getStatus().equals(StatusEnum.IN_PROGRESS)) return true;
        }
        return false;
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

    public String getCalling() {
        return calling;
    }

    public void setCalling(String calling) {
        this.calling = calling;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }
}
