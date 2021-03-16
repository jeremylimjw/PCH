/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.AppointmentSessionBeanLocal;
import entity.Appointment;
import java.io.IOException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author USER
 */
@Named(value = "manageAppointmentsManagedBean")
@RequestScoped
public class ManageAppointmentsManagedBean {

    @EJB(name = "AppointmentSessionBeanLocal")
    private AppointmentSessionBeanLocal appointmentSessionBeanLocal;
    
    private List<Appointment> appointments;
    
    public ManageAppointmentsManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct() {
        appointments = appointmentSessionBeanLocal.retrieveAll();
    }
    
    public void redirect(Long id) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/viewAppointment.xhtml?id=" + id);
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
    
}
