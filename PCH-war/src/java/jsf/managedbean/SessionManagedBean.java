/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author USER
 */
@Named(value = "sessionManagedBean")
@SessionScoped
public class SessionManagedBean implements Serializable {

    private String calling;
    private String previous;
    
    public SessionManagedBean() {
        calling = "-";
        previous = "-";
    }
    
    public void updateCalling(String queueNo) {
        previous = calling;
        calling = queueNo;
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
