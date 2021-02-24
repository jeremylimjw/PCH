/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Appointment;
import entity.Employee;
import entity.MedicalRecord;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.enumeration.AppointmentTypeEnum;
import util.enumeration.ScheduleTypeEnum;
import util.enumeration.StatusEnum;
import util.exception.AppointmentEntityException;
import util.exception.EmployeeEntityException;
import util.exception.MedicalRecordEntityException;

/**
 *
 * @author USER
 */
@Stateless
public class AppointmentSessionBean implements AppointmentSessionBeanLocal {

    @EJB
    private MedicalRecordSessionBeanLocal medicalRecordSessionBeanLocal;

    @EJB
    private EmployeeEntitySessionBeanLocal employeeEntitySessionBeanLocal;

    @PersistenceContext(unitName = "PCH-ejbPU")
    private EntityManager em;
    

    private final ValidatorFactory validatorFactory;
    private final Validator validator;
    
    public AppointmentSessionBean() {
	validatorFactory = Validation.buildDefaultValidatorFactory();
	validator = validatorFactory.getValidator();
    }
    
    @Override
    public Long createAppointment(Long doctorId, Long patientRecordId, Date dateTime, AppointmentTypeEnum appointment_type) throws AppointmentEntityException {
        try {
            Employee doctor = employeeEntitySessionBeanLocal.retrieveById(doctorId);
            MedicalRecord medicalRecord = medicalRecordSessionBeanLocal.retrieveById(patientRecordId);
            Appointment appointment = new Appointment(doctor, medicalRecord, dateTime, ScheduleTypeEnum.APPOINTMENT, appointment_type, StatusEnum.BOOKED);

            Set<ConstraintViolation<Appointment>> constraints = validator.validate(appointment);
            if (!constraints.isEmpty()) throw new AppointmentEntityException(getValidatorErrors(constraints));

            em.persist(appointment);
            em.flush();
            
            appointment.setQueue_no(String.format("A%03d", appointment.getId()));
            
            return appointment.getId();
        } catch (EmployeeEntityException | MedicalRecordEntityException ex) {
            throw new AppointmentEntityException(ex.getMessage());
        }
    }
    
    @Override
    public Long createWalkIn(Long patientRecordId, AppointmentTypeEnum appointment_type) throws AppointmentEntityException {
        try {
            MedicalRecord medicalRecord = medicalRecordSessionBeanLocal.retrieveById(patientRecordId);
            Appointment appointment = new Appointment(null, medicalRecord, new Date(), ScheduleTypeEnum.WALK_IN, appointment_type, StatusEnum.ARRIVED);

            Set<ConstraintViolation<Appointment>> constraints = validator.validate(appointment);
            if (!constraints.isEmpty()) throw new AppointmentEntityException(getValidatorErrors(constraints));

            em.persist(appointment);
            em.flush();
            
            appointment.setQueue_no(String.format("W%03d", appointment.getId()));

            return appointment.getId();
        } catch (MedicalRecordEntityException ex) {
            throw new AppointmentEntityException(ex.getMessage());
        }
    }
    
    @Override
    public List<Appointment> retrieveAppointmentsByDoctorIdByDay(Long doctor_id, Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0); c.set(Calendar.MINUTE, 0); c.set(Calendar.SECOND, 0);
        Date start = c.getTime();
        c.add(Calendar.DATE, 1);
        Date end = c.getTime();
        Query query = em.createQuery("SELECT a FROM Appointment a WHERE a.schedule_type = ?1 AND a.employee.id = ?2 AND a.date_time >= ?3 AND a.date_time < ?4 ORDER BY a.date_time");
        query.setParameter(1, ScheduleTypeEnum.APPOINTMENT);
        query.setParameter(2, doctor_id);
        query.setParameter(3, start);
        query.setParameter(4, end);
        return query.getResultList();
    }
    
    @Override
    public List<Appointment> retrieveAppointmentsByDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0); c.set(Calendar.MINUTE, 0); c.set(Calendar.SECOND, 0);
        Date start = c.getTime();
        c.add(Calendar.DATE, 1);
        Date end = c.getTime();
        Query query = em.createQuery("SELECT a FROM Appointment a WHERE a.schedule_type = ?1 AND a.date_time >= ?2 AND a.date_time < ?3 ORDER BY a.date_time");
        query.setParameter(1, ScheduleTypeEnum.APPOINTMENT);
        query.setParameter(2, start);
        query.setParameter(3, end);
        return query.getResultList();
    }
    
    @Override
    public List<Appointment> retrieveOngoingQueue() {
        Query query = em.createQuery("SELECT a FROM Appointment a WHERE a.schedule_type = ?1 AND a.status IN(?2,?3) ORDER BY a.date_time ASC");
        query.setParameter(1, ScheduleTypeEnum.WALK_IN);
        query.setParameter(2, StatusEnum.ARRIVED);
        query.setParameter(3, StatusEnum.IN_PROGRESS);
        return query.getResultList();
    }
    
    @Override
    public void updateStatus(Long appointmentId, StatusEnum status) throws AppointmentEntityException {
        Appointment appointment = em.find(Appointment.class, appointmentId);
        if (appointment == null) throw new AppointmentEntityException("Error: Appointment ID " + appointmentId + " does not exist!");
        appointment.setStatus(status);
    }

    @Override
    public Appointment retrieveById(Long id) throws AppointmentEntityException {
        if (id == null) throw new AppointmentEntityException("Error: Appointment ID is not provided!");
        Appointment appointment = em.find(Appointment.class, id);
        if (appointment == null) throw new AppointmentEntityException("Error: Appointment with ID " + id + " is not found!");
        return appointment;
    }
    
    private String getValidatorErrors(Set<ConstraintViolation<Appointment>> constraints) {
        String str = "Error: The following input value(s) are invalid!";
            
        for(ConstraintViolation constraintViolation : constraints) {
            str += "\nThe field '" + constraintViolation.getPropertyPath() + "' with input value '" + constraintViolation.getInvalidValue() + "' " + constraintViolation.getMessage();
        }
        
        return str;
    }
    
}
