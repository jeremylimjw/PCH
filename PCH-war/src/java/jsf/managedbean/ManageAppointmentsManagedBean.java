/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.AppointmentSessionBeanLocal;
import entity.Appointment;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import org.primefaces.event.SelectEvent;

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
    private Appointment selected;
    
    public ManageAppointmentsManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct() {
        appointments = appointmentSessionBeanLocal.retrieveAll();
    }
    
    public void onRowSelect(SelectEvent<Appointment> event) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/viewAppointment.xhtml?id=" + String.valueOf(event.getObject().getId()));
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public Appointment getSelected() {
        return selected;
    }

    public void setSelected(Appointment selected) {
        this.selected = selected;
    }
    
}
