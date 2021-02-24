/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Appointment;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import util.enumeration.AppointmentTypeEnum;
import util.exception.AppointmentEntityException;

/**
 *
 * @author USER
 */
@Local
public interface AppointmentSessionBeanLocal {
    public Long createAppointment(Long doctorId, Long patientRecordId, Date dateTime, AppointmentTypeEnum appointment_type) throws AppointmentEntityException;
    public Long createWalkIn(Long patientRecordId, AppointmentTypeEnum appointment_type) throws AppointmentEntityException;
    public List<Appointment> retrieveAppointmentsByDoctorId(Long doctor_id, Date date);
    public List<Appointment> retrieveAppointmentsByDay(Date date);
    public List<Appointment> retrieveOngoingQueue();
}
