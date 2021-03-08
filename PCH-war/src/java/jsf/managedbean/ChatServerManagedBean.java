/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.EmployeeEntitySessionBeanLocal;
import entity.Employee;
import entity.Message;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.faces.event.ActionEvent;
import javax.faces.event.WebsocketEvent;
import javax.faces.event.WebsocketEvent.Closed;
import javax.faces.event.WebsocketEvent.Opened;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;

/**
 *
 * @author USER
 */
@Named(value = "chatServerManagedBean")
@ApplicationScoped
public class ChatServerManagedBean {
    @Inject @Push(channel = "chatChannel")
    private PushContext pushContext;

    @EJB(name = "EmployeeEntitySessionBeanLocal")
    private EmployeeEntitySessionBeanLocal employeeEntitySessionBeanLocal;
    
    private Set<Employee> users;

    public ChatServerManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct() {        
        users = new HashSet<>();
    }
    
    public void sendMessage(Employee user, String message) {
        try{
            String pattern = "h:mm a";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String date = simpleDateFormat.format(new Date());
            
            for (Employee u : users) {
                pushContext.send(new Message(user.getName(), message, date, "MESSAGE"), u.getId());
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void onOpen(@Observes @Opened WebsocketEvent event) {
        try{
            Long userId = event.getUser();
            Employee user = employeeEntitySessionBeanLocal.retrieveById(userId);
            users.add(user);
            for (Employee u : users) {
                pushContext.send(new Message(user.getName(), user.getName() + " has joined the chat.", null, "USER_JOIN"), u.getId());
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
     }

     public void onClose(@Observes @Closed WebsocketEvent event) {
        try{
            Long userId = event.getUser();
            Employee user = employeeEntitySessionBeanLocal.retrieveById(userId);
            users.remove(user);
            for (Employee u : users) {
                pushContext.send(new Message(user.getName(), user.getName() + " has left the chat.", null, "USER_LEFT"), u.getId());
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
     }

    public Set<Employee> getUsers() {
        return users;
    }

    public void setUsers(Set<Employee> users) {
        this.users = users;
    }
    
}
