/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Appointment;
import entity.Employee;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import util.enumeration.AppointmentTypeEnum;
import util.enumeration.ScheduleTypeEnum;
import util.enumeration.StatusEnum;
import util.exception.AppointmentEntityException;

/**
 *
 * @author USER
 */
@Local
public interface AppointmentSessionBeanLocal {
    public Long createAppointment(Long doctorId, Long patientRecordId, Date dateTime, AppointmentTypeEnum appointment_type) throws AppointmentEntityException;
    public Long createWalkIn(Long patientRecordId, AppointmentTypeEnum appointment_type) throws AppointmentEntityException;
    public List<Appointment> retrieveAppointmentsByDoctorIdByDay(Long doctorId, Date date);
    public List<Appointment> retrieveAppointmentsByDay(ScheduleTypeEnum scheduleType, Date date);
    public List<Appointment> retrieveWalkInByDoctorIdByDay(Long doctorId, Date date);
    public void update(Appointment appointment) throws AppointmentEntityException;
    public void updateStatus(Long appointmentId, StatusEnum status) throws AppointmentEntityException;
    public void assignAppointment(Long appointmentId, Long doctorId) throws AppointmentEntityException;
    public Appointment retrieveById(Long id) throws AppointmentEntityException;
    public List<Appointment> retrieveAll();
    public List<Appointment> retrieveByMedicalRecordId(Long medicalRecordId);

}
