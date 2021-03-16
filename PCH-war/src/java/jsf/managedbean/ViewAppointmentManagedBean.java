/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.AppointmentSessionBeanLocal;
import entity.Appointment;
import entity.Prescription;
import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import util.enumeration.AppointmentTypeEnum;
import util.exception.AppointmentEntityException;

/**
 *
 * @author USER
 */
@Named(value = "viewAppointmentManagedBean")
@RequestScoped
public class ViewAppointmentManagedBean {

    @EJB(name = "AppointmentSessionBeanLocal")
    private AppointmentSessionBeanLocal appointmentSessionBeanLocal;
    
    private BigDecimal basicRate = new BigDecimal(40);
    private Appointment appointment;
    
    public ViewAppointmentManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct() {
        try {
            Long id = Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
            appointment = appointmentSessionBeanLocal.retrieveById(id);
            
            if (appointment.getSchedule_type().equals(AppointmentTypeEnum.CONSULTATION)) basicRate = new BigDecimal(40);
            else if (appointment.getSchedule_type().equals(AppointmentTypeEnum.HEALTH_CHECKUP)) basicRate = new BigDecimal(20);
            else if (appointment.getSchedule_type().equals(AppointmentTypeEnum.VACCINATION)) basicRate = new BigDecimal(10);
            else  basicRate = new BigDecimal(40);
            
            calculateTotal();
            
        } catch (NumberFormatException | AppointmentEntityException ex) { 
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }
    
    public void calculateTotal() {
        BigDecimal total = new BigDecimal(0);
        for (Prescription p : appointment.getPrescriptions()) {
            total = total.add(p.getMedication().getPrice_per_quantity().multiply(new BigDecimal(p.getQuantity())));
        }
        appointment.setTotal_price(total.add(basicRate));
    }
    
    public void viewMc() {
//        --- View MC Logic here ---
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public BigDecimal getBasicRate() {
        return basicRate;
    }

    public void setBasicRate(BigDecimal basicRate) {
        this.basicRate = basicRate;
    }
    
}
