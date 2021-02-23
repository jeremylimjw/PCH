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
import javax.persistence.TemporalType;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.enumeration.AppointmentTypeEnum;
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
    public Long create(Long doctorId, Long patientRecordId, Date dateTime, AppointmentTypeEnum type) throws AppointmentEntityException {
        try {
            Employee doctor = employeeEntitySessionBeanLocal.retrieveById(doctorId);
            MedicalRecord medicalRecord = medicalRecordSessionBeanLocal.retrieveById(patientRecordId);
            Appointment appointment = new Appointment(doctor, medicalRecord, dateTime, AppointmentTypeEnum.CONSULTATION);

            Set<ConstraintViolation<Appointment>> constraints = validator.validate(appointment);
            if (!constraints.isEmpty()) throw new AppointmentEntityException(getValidatorErrors(constraints));

            em.persist(appointment);
            em.flush();

            return appointment.getId();
        } catch (EmployeeEntityException | MedicalRecordEntityException ex) {
            throw new AppointmentEntityException(ex.getMessage());
        }
    }
    
    @Override
    public List<Appointment> retrieveAppointmentsByDoctorId(Long doctor_id, Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0); c.set(Calendar.MINUTE, 0); c.set(Calendar.SECOND, 0);
        Date start = c.getTime();
        c.add(Calendar.DATE, 1);
        Date end = c.getTime();
        Query query = em.createQuery("SELECT a FROM Appointment a WHERE a.employee.id = ?1 AND a.date_time >= ?2 AND a.date_time < ?3");
        query.setParameter(1, doctor_id);
        query.setParameter(2, start);
        query.setParameter(3, end);
        return query.getResultList();
    }
    
    private String getValidatorErrors(Set<ConstraintViolation<Appointment>> constraints) {
        String str = "Error: The following input value(s) are invalid!";
            
        for(ConstraintViolation constraintViolation : constraints) {
            str += "\nThe field '" + constraintViolation.getPropertyPath() + "' with input value '" + constraintViolation.getInvalidValue() + "' " + constraintViolation.getMessage();
        }
        
        return str;
    }
    
}
