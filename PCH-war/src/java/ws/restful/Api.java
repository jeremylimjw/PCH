/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.AppointmentSessionBeanLocal;
import entity.Appointment;
import entity.Prescription;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import util.exception.AppointmentEntityException;

/**
 *
 * @author USER
 */
@Path("/")
public class Api {
    
    private AppointmentSessionBeanLocal appointmentSessionBeanLocal;
    
    public Api() {
        hookUpJNDI();
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
    public Response retrieveBook(@PathParam("id") Long id) {
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
    
    private void hookUpJNDI() {
        try {
            javax.naming.Context c = new InitialContext();
            appointmentSessionBeanLocal = (AppointmentSessionBeanLocal) c.lookup("java:global/PCH/PCH-ejb/AppointmentSessionBean!ejb.session.stateless.AppointmentSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
