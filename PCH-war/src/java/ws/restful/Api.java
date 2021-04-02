/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.singleton.QueueBoardSessionBeanLocal;
import ejb.session.stateless.AppointmentSessionBeanLocal;
import ejb.session.stateless.EmployeeEntitySessionBeanLocal;
import ejb.session.stateless.PatientSessionBeanLocal;
import entity.Appointment;
import entity.Employee;
import entity.Patient;
import entity.Prescription;
import entity.QueueBoardItem;
import entity.RequestBodyCreateAppointment;
import entity.RequestBodyPatientLogin;
import entity.RequestBodyPatientUpdate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import util.enumeration.AppointmentTypeEnum;
import util.exception.AppointmentEntityException;
import util.exception.PatientEntityException;

/**
 *
 * @author USER
 */
@Path("/")
public class Api {
    
    private AppointmentSessionBeanLocal appointmentSessionBeanLocal;
    private EmployeeEntitySessionBeanLocal employeeEntitySessionBeanLocal;
    private QueueBoardSessionBeanLocal queueBoardSessionBeanLocal;
    private PatientSessionBeanLocal patientSessionBeanLocal;
    
    public Api() {
        hookUpJNDI();
    }
    
    
    @Path("queueBoard")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQueueBoard() {
        try {
            List<QueueBoardItem> queueBoard = queueBoardSessionBeanLocal.retrieveQueueBoard();
            List<QueueBoardItem> qb = new ArrayList<>();
            
            for(QueueBoardItem q : queueBoard) {
                Employee e = new Employee();
                e.setName(q.getEmployee().getName());
                Appointment a = new Appointment();
                a.setQueue_no(q.getAppointment().getQueue_no());
                
                qb.add(0, new QueueBoardItem(e, a));
            }
            
            GenericEntity<List<QueueBoardItem>> genericEntity = new GenericEntity<List<QueueBoardItem>>(qb) { };    
            
            return Response.status(Response.Status.OK).entity(genericEntity).build();
        } catch(Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }
    
    @Path("appointments/medicalRecord/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveByMedicalRecordId(@PathParam("id") Long id) {
        try {
            List<Appointment> appointments = appointmentSessionBeanLocal.retrieveByMedicalRecordId(id);
            
            for(Appointment a : appointments) {
                for(Prescription p : a.getPrescriptions()) p.getMedication().getParent_medications().clear();
                if (a.getEmployee() != null) a.getEmployee().getAppointments().clear();
                a.getMedical_record().getAppointments().clear();
            }
            
            GenericEntity<List<Appointment>> genericEntity = new GenericEntity<List<Appointment>>(appointments) { };      
            
            return Response.status(Response.Status.OK).entity(genericEntity).build();
        } catch(Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }
    
    @Path("appointments/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAppointments(@PathParam("id") Long id) {
        try {
            Appointment a = appointmentSessionBeanLocal.retrieveById(id);
            
            for(Prescription p : a.getPrescriptions()) p.getMedication().getParent_medications().clear();
            if (a.getEmployee() != null) a.getEmployee().getAppointments().clear();
            a.getMedical_record().getAppointments().clear();
            
            return Response.status(Response.Status.OK).entity(a).build();
        } catch(AppointmentEntityException ex) {            
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } catch(Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }
    
    @Path("appointments/cancel")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cancelAppointments(Long id) {
        try {
            appointmentSessionBeanLocal.cancelAppointment(id);
            
            return Response.status(Response.Status.OK).entity(id).build();
        } catch(AppointmentEntityException ex) {            
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } catch(Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }
    
    
    @Path("appointments")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchAppointment() {
        try {
            List<Appointment> appointments = appointmentSessionBeanLocal.retrieveAllAppointments();
            
            for(Appointment a : appointments) {
                for(Prescription p : a.getPrescriptions()) p.getMedication().getParent_medications().clear();
                if (a.getEmployee() != null) a.getEmployee().getAppointments().clear();
                a.getMedical_record().getAppointments().clear();
            }
            
            GenericEntity<List<Appointment>> genericEntity = new GenericEntity<List<Appointment>>(appointments) { };    
            
            return Response.status(Response.Status.OK).entity(genericEntity).build();
        } catch(Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }
    
    @Path("appointments")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addAppointment(RequestBodyCreateAppointment a) {
        try {
            Long id = appointmentSessionBeanLocal.createAppointment(a.getDoctor_id(), a.getMedical_record_id(), a.getDate(), a.getType());
            return Response.status(Response.Status.OK).entity(id).build();
        } catch(AppointmentEntityException ex) {            
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } catch(Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }
    
    @Path("doctors")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveDoctors() {
        try {
            List<Employee> employees = employeeEntitySessionBeanLocal.retrieveAllDoctors();
            
            for(Employee e : employees) {
                e.setAppointments(null);
                e.setPassword(null);
            }
            
            return Response.status(Response.Status.OK).entity(employees).build();
        } catch(Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }
    
    @Path("patient/login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response patientLogin(RequestBodyPatientLogin p) {
        try {
            Patient patient = patientSessionBeanLocal.login(p.getUsername(), p.getPassword());
            
            if (patient.getMedical_record() != null && patient.getMedical_record().getAppointments() != null)
                patient.getMedical_record().getAppointments().clear();
            
            return Response.status(Response.Status.OK).entity(patient).build();
        } catch(PatientEntityException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } catch(Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }
    
    @Path("patient/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response patientUpdateEmail(RequestBodyPatientUpdate p) {
        try {
            Patient patient = patientSessionBeanLocal.retrieveById(p.getId());
            
            patient.setEmail(p.getEmail());
            patientSessionBeanLocal.updatePatientDetails(patient);
            
            return Response.status(Response.Status.OK).build();
        } catch (PatientEntityException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } catch(Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }
    
    @Path("patient/changePassword")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response patientUpdatePassword(RequestBodyPatientUpdate p) {
        try {
            Patient patient = patientSessionBeanLocal.retrieveById(p.getId());
            
            patient.setEmail(p.getPassword());
            patientSessionBeanLocal.updatePatientDetails(patient);
            
            return Response.status(Response.Status.OK).build();
        } catch (PatientEntityException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } catch(Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }
    
    private void hookUpJNDI() {
        try {
            javax.naming.Context c = new InitialContext();
            appointmentSessionBeanLocal = (AppointmentSessionBeanLocal) c.lookup("java:global/PCH/PCH-ejb/AppointmentSessionBean!ejb.session.stateless.AppointmentSessionBeanLocal");
            employeeEntitySessionBeanLocal = (EmployeeEntitySessionBeanLocal) c.lookup("java:global/PCH/PCH-ejb/EmployeeEntitySessionBean!ejb.session.stateless.EmployeeEntitySessionBeanLocal");
            queueBoardSessionBeanLocal = (QueueBoardSessionBeanLocal) c.lookup("java:global/PCH/PCH-ejb/QueueBoardSessionBean!ejb.session.singleton.QueueBoardSessionBeanLocal");
            patientSessionBeanLocal = (PatientSessionBeanLocal) c.lookup("java:global/PCH/PCH-ejb/PatientSessionBean!ejb.session.stateless.PatientSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
