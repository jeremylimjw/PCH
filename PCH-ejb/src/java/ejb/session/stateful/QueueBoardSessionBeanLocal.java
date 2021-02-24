/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateful;

import javax.ejb.Local;
import util.exception.AppointmentEntityException;
import util.exception.EmployeeEntityException;

/**
 *
 * @author USER
 */
@Local
public interface QueueBoardSessionBeanLocal {
    public void add(Long employeeId, Long appointmentId) throws EmployeeEntityException, AppointmentEntityException;
}
