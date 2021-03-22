/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author USER
 */
public class QueueBoardItem {
    private Employee employee;
    private Appointment appointment;
    
    public QueueBoardItem() { }

    public QueueBoardItem(Employee employee, Appointment appointment) {
        this.employee = employee;
        this.appointment = appointment;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
    
    @Override
    public String toString() {
        return this.employee.getName() + " IS CALLING " + this.appointment.getQueue_no();
    }
}
