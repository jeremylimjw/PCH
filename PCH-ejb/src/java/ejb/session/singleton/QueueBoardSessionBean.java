/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.AppointmentSessionBeanLocal;
import ejb.session.stateless.EmployeeEntitySessionBeanLocal;
import entity.Appointment;
import entity.Employee;
import entity.QueueBoardItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import util.exception.AppointmentEntityException;
import util.exception.EmployeeEntityException;

/**
 *
 * @author USER
 */
@Singleton
public class QueueBoardSessionBean implements QueueBoardSessionBeanLocal {

    @EJB
    private AppointmentSessionBeanLocal appointmentSessionBeanLocal;
    @EJB
    private EmployeeEntitySessionBeanLocal employeeEntitySessionBeanLocal;

    List<QueueBoardItem> queueBoard;
    
    public QueueBoardSessionBean() { }
    
    @PostConstruct
    public void postConstruct() {
        queueBoard = new ArrayList<>();
    }
    
    @Override
    public void add(Long employeeId, Long appointmentId) throws EmployeeEntityException, AppointmentEntityException {
        Employee employee = employeeEntitySessionBeanLocal.retrieveById(employeeId);
        Appointment appointment = appointmentSessionBeanLocal.retrieveById(appointmentId);
        
        queueBoard.add(new QueueBoardItem(employee, appointment));
        
        System.out.println("---- QUEUEBOARD SINGLETON BEAN ----");
        for (QueueBoardItem q : queueBoard) System.out.println(q);
    }
    
    @Override
    public List<QueueBoardItem> retrieveQueueBoard() {
        return queueBoard;
    }

    @Override
    public List<QueueBoardItem> getQueueBoard() {
        return queueBoard;
    }
    
    @Override
    public void setQueueBoard(List<QueueBoardItem> queueBoard) {
        this.queueBoard = queueBoard;
    }
    
}
