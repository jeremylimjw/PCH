/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateful;

import ejb.session.stateless.AppointmentSessionBeanLocal;
import ejb.session.stateless.EmployeeEntitySessionBeanLocal;
import entity.Appointment;
import entity.Employee;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import util.exception.AppointmentEntityException;
import util.exception.EmployeeEntityException;

/**
 *
 * @author USER
 */
@Stateful
public class QueueBoardSessionBean implements QueueBoardSessionBeanLocal {

    @EJB
    private AppointmentSessionBeanLocal appointmentSessionBeanLocal;
    @EJB
    private EmployeeEntitySessionBeanLocal employeeEntitySessionBeanLocal;

    HashMap<Employee,Appointment> queue;
    
    public QueueBoardSessionBean() {
        queue = new HashMap<>();
    }
    
    @Override
    public void add(Long employeeId, Long appointmentId) throws EmployeeEntityException, AppointmentEntityException {
        Employee employee = employeeEntitySessionBeanLocal.retrieveById(employeeId);
        Appointment appointment = appointmentSessionBeanLocal.retrieveById(appointmentId);
        
        queue.put(employee, appointment);
        
        for (Map.Entry<Employee, Appointment> e : queue.entrySet()) 
            System.out.println("ADDED TO QUEUEBOARD - Key: " + e.getKey() + " Value: " + e.getValue()); 
    }
}
