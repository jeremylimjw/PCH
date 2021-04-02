/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.MedicalRecordSessionBeanLocal;
import entity.Appointment;
import entity.MedicalRecord;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.exception.MedicalRecordEntityException;
import ws.datamodel.UpdateMedicalRecordReq;

/**
 * REST Web Service
 *
 * @author ngjunkai
 */
@Path("MedicalRecord")
public class MedicalRecordResource {

    MedicalRecordSessionBeanLocal medicalRecordSessionBean = lookupMedicalRecordSessionBeanLocal();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of MedicalRecordResource
     */
    public MedicalRecordResource() {
    }

    @Path("retrieveMedicalRecord/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveMedicalRecordById(@PathParam("id") Long id) {
        try {

            MedicalRecord mr = medicalRecordSessionBean.retrieveById(id);

            mr.getAppointments().clear();

            //    
            return Response.status(Response.Status.OK).entity(mr).build();
        } catch (MedicalRecordEntityException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateMedicalRecordById(UpdateMedicalRecordReq updateMedicalRecordReq) {
        try {

            medicalRecordSessionBean.updateMedReq(updateMedicalRecordReq.getId(), updateMedicalRecordReq.getName(), updateMedicalRecordReq.getNric(), updateMedicalRecordReq.getAddress(), updateMedicalRecordReq.getDob(), updateMedicalRecordReq.getContact_number(), updateMedicalRecordReq.getBlood_type(), updateMedicalRecordReq.getDrug_allergys(), updateMedicalRecordReq.getFamily_historys(), updateMedicalRecordReq.getPast_medical_historys(), updateMedicalRecordReq.getVaccinations());

            return Response.status(Response.Status.OK).build();
        } catch (MedicalRecordEntityException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    private MedicalRecordSessionBeanLocal lookupMedicalRecordSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (MedicalRecordSessionBeanLocal) c.lookup("java:global/PCH/PCH-ejb/MedicalRecordSessionBean!ejb.session.stateless.MedicalRecordSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
