/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import entity.Message;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
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
    
    private List<String> users;

    public ChatServerManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct() {        
        users = new ArrayList<>();
    }
    
    public void sendMessage(String user, String message) {
        try{
            String pattern = "h:mm a";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String date = simpleDateFormat.format(new Date());
            
            for (String u : users) {
                pushContext.send(new Message(user, message, date, "MESSAGE"), u);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void onOpen(@Observes @Opened WebsocketEvent event) {
        String user = event.getUser();
        users.add(user);
        try{
            for (String u : users) {
                pushContext.send(new Message(user, user + " has joined the chat.", null, "USER_JOIN"), u);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
     }

     public void onClose(@Observes @Closed WebsocketEvent event) {
        String user = event.getUser();
        users.remove(user);
        try{
            for (String u : users) {
                pushContext.send(new Message(user, user + " has left the chat.", null, "USER_LEFT"), u);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
     }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }
    
}
