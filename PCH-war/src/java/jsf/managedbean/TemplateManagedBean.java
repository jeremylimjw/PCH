/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.AppointmentSessionBeanLocal;
import entity.Employee;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import util.enumeration.RoleEnum;

/**
 *
 * @author USER
 */
@Named(value = "templateManagedBean")
@RequestScoped
public class TemplateManagedBean {

    @EJB(name = "AppointmentSessionBeanLocal")
    private AppointmentSessionBeanLocal appointmentSessionBeanLocal;
    
    private Employee user;
    
    private static final String CONTEXT_ROOT = "/PCH-war";
    private String url;
    
    private int appointmentCount;
    private int queueCount;

    /**
     * Creates a new instance of TemplateManagedBean
     */
    public TemplateManagedBean() {
        appointmentCount = 0;
        queueCount = 0;
    }
    
    @PostConstruct
    public void postConstruct() {
        user = (Employee) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        
        if (user.getRole().equals(RoleEnum.DOCTOR)) {
            appointmentCount = appointmentSessionBeanLocal.retrieveAppointmentsByDoctorIdByDay(user.getId(), new Date()).size();
        } else {
            appointmentCount = appointmentSessionBeanLocal.retrieveAppointmentsByDay(new Date()).size();
        }
        queueCount = appointmentSessionBeanLocal.retrieveOngoingQueue().size();
        
        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpServletRequest servletRequest = (HttpServletRequest) ctx.getExternalContext().getRequest();
        url = servletRequest.getRequestURI();
    }
    
    // Pass in one or more url where if matches any of them, will highlight the navigation element
    public String isUrl(String... pageNames) {
        for (String pageName : pageNames) {
            if (url.equals(CONTEXT_ROOT + pageName)) {
                return "selected";
            }
        }
        return "";
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getAppointmentCount() {
        return appointmentCount;
    }

    public void setAppointmentCount(int appointmentCount) {
        this.appointmentCount = appointmentCount;
    }

    public int getQueueCount() {
        return queueCount;
    }

    public void setQueueCount(int queueCount) {
        this.queueCount = queueCount;
    }
    
}
