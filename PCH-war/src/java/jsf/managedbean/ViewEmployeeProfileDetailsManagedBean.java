/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.EmployeeEntitySessionBeanLocal;
import entity.Employee;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.exception.EmployeeEntityException;

/**
 *
 * @author GraceLi
 */
@Named(value = "viewEmployeeProfileDetailsManagedBean")
@ViewScoped
public class ViewEmployeeProfileDetailsManagedBean implements Serializable {

    @EJB
    private EmployeeEntitySessionBeanLocal employeeEntitySessionBean;
    
    private Employee user;

    /**
     * Creates a new instance of ViewEmployeeProfileDetailsManagedBean
     */
    public ViewEmployeeProfileDetailsManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct() {
        user = (Employee) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
    }
    
    public void doUpdateUserDetails(ActionEvent event) {
        user = (Employee)event.getComponent().getAttributes().get("user");
    }
    
    public void updateUserDetails(ActionEvent event) {        
        try {
            employeeEntitySessionBean.updateEmployeeDetails(user);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Employee details updated successfully", null));
            
        } catch(EmployeeEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating employee details: " + ex.getMessage(), null));
        } catch(Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    
    public void updateUserPassword(ActionEvent event) {
        try {
            employeeEntitySessionBean.updateEmployeePassword(user);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Employee details updated successfully", null));
        } catch (EmployeeEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating employee details: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
        
    }


    public Employee getUser() {
        return user;
    }

    public void setUser(Employee user) {
        this.user = user;
    }
    
}
