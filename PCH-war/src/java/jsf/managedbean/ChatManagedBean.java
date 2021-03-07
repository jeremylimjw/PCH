/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import entity.Employee;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

/**
 *
 * @author USER
 */
@Named(value = "chatManagedBean")
@RequestScoped
public class ChatManagedBean {
    @Inject
    private ChatServerManagedBean chatServerManagedBean;
    
    private Employee user;
    private String message;
    
    public ChatManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct() {        
        user = (Employee) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
    }
    
    public void sendMessage(ActionEvent event) {
        chatServerManagedBean.sendMessage(user.getName(), message);
        message = "";
    }

    public Employee getUser() {
        return user;
    }

    public void setUser(Employee user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
