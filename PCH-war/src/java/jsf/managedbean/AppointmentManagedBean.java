/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.singleton.QueueBoardSessionBeanLocal;
import ejb.session.stateless.AppointmentSessionBeanLocal;
import ejb.session.stateless.EmployeeEntitySessionBeanLocal;
import ejb.session.stateless.MedicalRecordSessionBeanLocal;
import entity.Appointment;
import entity.Employee;
import entity.MedicalRecord;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
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
@RequestScoped
public class AppointmentManagedBean implements Serializable {

    @EJB
    private EmployeeEntitySessionBeanLocal employeeEntitySessionBean;

    @EJB
    private MedicalRecordSessionBeanLocal medicalRecordSessionBean;

    @EJB
    private QueueBoardSessionBeanLocal queueBoardSessionBeanLocal;

    @EJB
    private AppointmentSessionBeanLocal appointmentSessionBeanLocal;
    
    @Inject
    private SessionManagedBean sessionManagedBean;
    
    private Employee user;
    private List<Appointment> appointments;
    private List<Appointment> queue;
    private List<Employee> doctors;
    private Date dateOfAppointment;
    private AppointmentTypeEnum appointmentType;
    private String patientNric;
    
    public AppointmentManagedBean() {
        appointments = new ArrayList<>();
        queue = new LinkedList<>();
        doctors = new ArrayList<>();
        dateOfAppointment = new Date();
        patientNric = "";
    }
    
    @PostConstruct
    public void postConstruct() {
        user = (Employee) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        getAllAppointmentsForToday();
        doctors = employeeEntitySessionBean.retrieveAllDoctors();
    }
    
    public void getAllAppointmentsForToday() {
        Date today = new Date();
        if (user.getRole().equals(RoleEnum.DOCTOR)) {
            appointments = appointmentSessionBeanLocal.retrieveAppointmentsByDoctorIdByDay(user.getId(), today);
            queue = appointmentSessionBeanLocal.retrieveWalkInByDoctorIdByDay(user.getId(), today);
        } else {
            appointments = appointmentSessionBeanLocal.retrieveAppointmentsByDay(ScheduleTypeEnum.APPOINTMENT, today);
            queue = appointmentSessionBeanLocal.retrieveAppointmentsByDay(ScheduleTypeEnum.WALK_IN, today);
        }
    }
    
    public void updateStatus(Appointment appointment, StatusEnum e) {
        try {
            if (e.equals(StatusEnum.IN_PROGRESS)) {     // When user clicks the 'CALL' button
                callAppointment(appointment);
                return;
            } else if (e.equals(StatusEnum.MISSED)) {   // When user clicks the 'SKIP' button
                if (!appointment.getEmployee().equals(user)) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "You are not the original caller.", null));
                    return;
                }
            }
            
            appointmentSessionBeanLocal.updateStatus(appointment.getId(), e);
            getAllAppointmentsForToday();
            
            
        } catch(EmployeeEntityException | AppointmentEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }
    
    public void callAppointment(Appointment appointment) throws EmployeeEntityException, AppointmentEntityException {
        if (isCallingSomeone()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "You are already calling someone.", null));
            return;
        }
        
        // Associate it with the doctor that called the appointment, if its from the walk-in queue.
        if (appointment.getSchedule_type().equals(ScheduleTypeEnum.WALK_IN)) 
            appointmentSessionBeanLocal.assignAppointment(appointment.getId(), user.getId());

        // Broadcast to QueueBoard
        queueBoardSessionBeanLocal.add(user.getId(), appointment.getId());

        sessionManagedBean.updateCalling(appointment.getQueue_no());
        
        appointmentSessionBeanLocal.updateStatus(appointment.getId(), StatusEnum.IN_PROGRESS);
        getAllAppointmentsForToday();
    }
    
    public boolean isCallingSomeone() {
        for (Appointment a : appointments) {
            if (a.getStatus().equals(StatusEnum.IN_PROGRESS)) return true;
        }
        for (Appointment a : queue) {
            if (a.getStatus().equals(StatusEnum.IN_PROGRESS) && a.getEmployee().equals(user)) return true;
        }
        return false;
    }
    
    public void callNextPatient() {
        try {
            
            Appointment earliestAppointment = appointments.stream().filter(x -> x.getStatus().equals(StatusEnum.ARRIVED)).findFirst().orElse(null);
            if (earliestAppointment == null && queue.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No patients to call", null));
            } else {
                if (earliestAppointment == null) {
                    callAppointment(queue.get(0));
                } else if (queue.isEmpty()) {
                    callAppointment(earliestAppointment);
                } else {
                    Long threshold = (earliestAppointment.getDate_time().getTime()) - (15 * 60 * 1000);
                    if (new Date().getTime() > threshold) {
                        callAppointment(earliestAppointment);
                        return;
                    } else { // not yet time
                        callAppointment(queue.get(0));
                    }
                }
            }
        
        } catch (EmployeeEntityException | AppointmentEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }
    
//    public void addAppointment(ActionEvent event) {
//        Employee doctorSelected = (Employee)event.getComponent().getAttributes().get("doctorSelected");
//        MedicalRecord selectedMedicalRecord = (MedicalRecord)event.getComponent().getAttributes().get("selectedMedicalRecord");
//        
//        
//        try {
//            appointmentSessionBeanLocal.createAppointment(doctorSelected.getId(), selectedMedicalRecord.getId(), dateOfAppointment, appointmentType);
//        } catch (AppointmentEntityException ex) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
//        }
//        
//        getAllAppointmentsForToday();
//    }
    
    public void addWalkInAppointment(ActionEvent event) {
        
        MedicalRecord selectedMedicalRecord = medicalRecordSessionBean.searchMedicalRecordsByNRIC(patientNric).get(0);
        
        try {
            appointmentSessionBeanLocal.createWalkIn(selectedMedicalRecord.getId(), appointmentType);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Walk-in Appointment has been created for patient record " + selectedMedicalRecord.getNric(), null));
        } catch (AppointmentEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
        
        getAllAppointmentsForToday();
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
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
        
        getAllAppointmentsForToday();
    }
    
    // ---- FOR TESTING ONLY ----
    // ADD SAMPLE WALK IN
    public void addRandomWalkIn() {
        try {
            appointmentSessionBeanLocal.createWalkIn(1l, AppointmentTypeEnum.CONSULTATION);
        } catch (AppointmentEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
        
        getAllAppointmentsForToday();
    }
    
    public AppointmentTypeEnum[] getAppointmentTypeEnum() {
        return AppointmentTypeEnum.values();
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

    public List<Employee> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Employee> doctors) {
        this.doctors = doctors;
    }

    public Date getDateOfAppointment() {
        return dateOfAppointment;
    }

    public void setDateOfAppointment(Date dateOfAppointment) {
        this.dateOfAppointment = dateOfAppointment;
    }

    public AppointmentTypeEnum getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(AppointmentTypeEnum appointmentType) {
        this.appointmentType = appointmentType;
    }

    public String getPatientNric() {
        return patientNric;
    }

    public void setPatientNric(String patientNric) {
        this.patientNric = patientNric;
    }
}
