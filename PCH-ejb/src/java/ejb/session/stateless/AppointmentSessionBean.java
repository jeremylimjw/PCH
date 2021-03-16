/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Appointment;
import entity.Employee;
import entity.MedicalRecord;
import entity.Prescription;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
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
    
    @Resource
    private EJBContext eJBContext;
    
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
    public List<Appointment> retrieveAppointmentsByDoctorIdByDay(Long doctorId, Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0); c.set(Calendar.MINUTE, 0); c.set(Calendar.SECOND, 0);
        Date start = c.getTime();
        c.add(Calendar.DATE, 1);
        Date end = c.getTime();
        Query query = em.createQuery("SELECT a FROM Appointment a WHERE a.schedule_type = ?1 AND a.employee.id = ?2 AND a.date_time >= ?3 AND a.date_time < ?4 ORDER BY CASE a.status WHEN ?5 THEN 0 WHEN ?6 THEN 1 WHEN ?7 THEN 2 ELSE 3 END, a.date_time");
        query.setParameter(1, ScheduleTypeEnum.APPOINTMENT);
        query.setParameter(2, doctorId);
        query.setParameter(3, start);
        query.setParameter(4, end);
        query.setParameter(5, StatusEnum.IN_PROGRESS);
        query.setParameter(6, StatusEnum.ARRIVED);
        query.setParameter(7, StatusEnum.COMPLETED);
        return query.getResultList();
    }
    
    @Override
    public List<Appointment> retrieveAppointmentsByDay(ScheduleTypeEnum scheduleType, Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0); c.set(Calendar.MINUTE, 0); c.set(Calendar.SECOND, 0);
        Date start = c.getTime();
        c.add(Calendar.DATE, 1);
        Date end = c.getTime();
        Query query = em.createQuery("SELECT a FROM Appointment a WHERE a.schedule_type = ?1 AND a.date_time >= ?2 AND a.date_time < ?3 ORDER BY CASE a.status WHEN ?4 THEN 0 WHEN ?5 THEN 1 WHEN ?6 THEN 2 ELSE 3 END, a.date_time");
        query.setParameter(1, scheduleType);
        query.setParameter(2, start);
        query.setParameter(3, end);
        query.setParameter(4, StatusEnum.BOOKED);
        query.setParameter(5, StatusEnum.IN_PROGRESS);
        query.setParameter(6, StatusEnum.ARRIVED);
        return query.getResultList();
    }
    
    @Override
    public List<Appointment> retrieveWalkInByDoctorIdByDay(Long doctorId, Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0); c.set(Calendar.MINUTE, 0); c.set(Calendar.SECOND, 0);
        Date start = c.getTime();
        c.add(Calendar.DATE, 1);
        Date end = c.getTime();
        Query query = em.createQuery("SELECT a FROM Appointment a WHERE (a.schedule_type = ?1 AND a.status  = ?2) OR (a.schedule_type = ?1 AND a.status = ?3 AND a.employee.id = ?4) AND a.date_time >= ?5 AND a.date_time < ?6  ORDER BY a.date_time ASC");
        query.setParameter(1, ScheduleTypeEnum.WALK_IN);
        query.setParameter(2, StatusEnum.ARRIVED);
        query.setParameter(3, StatusEnum.IN_PROGRESS);
        query.setParameter(4, doctorId);
        query.setParameter(5, start);
        query.setParameter(6, end);
        return query.getResultList();
    }
    
    @Override
    public void updateStatus(Long appointmentId, StatusEnum status) throws AppointmentEntityException {
        Appointment appointment = retrieveById(appointmentId);
        appointment.setStatus(status);
    }
    
    @TransactionAttribute
    @Override
    public void update(Appointment appointment) throws AppointmentEntityException {
        try {
            Set<ConstraintViolation<Appointment>> constraints = validator.validate(appointment);
            if (!constraints.isEmpty()) throw new AppointmentEntityException(getValidatorErrors(constraints));

            Appointment oldAppointment = retrieveById(appointment.getId());

            oldAppointment.setMedical_certificate(appointment.getMedical_certificate());
            oldAppointment.setPrescriptions(appointment.getPrescriptions());
            oldAppointment.setTotal_price(appointment.getTotal_price());
            oldAppointment.setStatus(appointment.getStatus());
            oldAppointment.setPatient_notes(appointment.getPatient_notes());

            medicalRecordSessionBeanLocal.update(appointment.getMedical_record());
        } catch (MedicalRecordEntityException ex) {
            eJBContext.setRollbackOnly();
            throw new AppointmentEntityException(ex.getMessage());
        }
    }
    
    @Override
    public void assignAppointment(Long appointmentId, Long doctorId) throws AppointmentEntityException {
        try {
            Appointment appointment = retrieveById(appointmentId);
            if (appointment.getEmployee() != null) throw new AppointmentEntityException("Error: Appointment ID " + appointmentId + " has already been assigned to a doctor!");
            Employee doctor = employeeEntitySessionBeanLocal.retrieveById(doctorId);

            appointment.setEmployee(doctor);
        
        } catch (EmployeeEntityException ex) {
            throw new AppointmentEntityException(ex.getMessage());
        }
    }
    
    @Override
    public List<Appointment> retrieveAll() {
        Query query = em.createQuery("SELECT a FROM Appointment a ORDER BY a.date_created DESC");
        return query.getResultList();
    }
    
    @Override
    public List<Appointment> retrieveByMedicalRecordId(Long medicalRecordId) {
        Query query = em.createQuery("SELECT a FROM Appointment a WHERE a.medical_record.id = ?1 ORDER BY a.date_created DESC");
        query.setParameter(1, medicalRecordId);
        return query.getResultList();
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
