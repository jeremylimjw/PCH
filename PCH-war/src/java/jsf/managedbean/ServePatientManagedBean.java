/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.AppointmentSessionBeanLocal;
import ejb.session.stateless.MedicationEntitySessionBeanLocal;
import entity.Appointment;
import entity.MedicalCertificate;
import entity.Medication;
import entity.Prescription;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;


import java.util.Calendar;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;

import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import util.enumeration.AppointmentTypeEnum;

import util.enumeration.StatusEnum;
import util.exception.AppointmentEntityException;
import util.exception.MedicationEntityException;

/**
 *
 * @author USER
 */
@Named(value = "servePatientManagedBean")
@ViewScoped
public class ServePatientManagedBean implements Serializable {

    @Resource(name = "pchDataSource")
    private DataSource pchDataSource;

    @EJB(name = "MedicationEntitySessionBeanLocal")
    private MedicationEntitySessionBeanLocal medicationEntitySessionBeanLocal;

    @EJB(name = "AppointmentSessionBeanLocal")
    private AppointmentSessionBeanLocal appointmentSessionBeanLocal;

    private List<Medication> medications;

    private Appointment appointment;
    private Date mc_start_date;
    private Date mc_end_date;

    private BigDecimal basicRate = new BigDecimal(40);

    public ServePatientManagedBean() {
        mc_start_date = null;
        mc_end_date = null;
    }

    @PostConstruct
    public void postConstruct() {
        try {
            Long id = Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
            appointment = appointmentSessionBeanLocal.retrieveById(id);

            medications = medicationEntitySessionBeanLocal.retrieveAll();



            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("medications", medications);

            
            if (appointment.getSchedule_type().equals(AppointmentTypeEnum.CONSULTATION)) basicRate = new BigDecimal(40);
            else if (appointment.getSchedule_type().equals(AppointmentTypeEnum.HEALTH_CHECKUP)) basicRate = new BigDecimal(20);
            else if (appointment.getSchedule_type().equals(AppointmentTypeEnum.VACCINATION)) basicRate = new BigDecimal(10);
            else basicRate = new BigDecimal(40);
            

            appointment.setTotal_price(basicRate);




        } catch (NumberFormatException | AppointmentEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error retrieving selected appointment.", null));
        }
    }

    public void addPrescription() {
        appointment.getPrescriptions().add(new Prescription(1, medications.get(0)));
        calculateTotal();
    }

    public void removePrescription(int i) {
        appointment.getPrescriptions().remove(i);
        calculateTotal();
    }

    public void calculateTotal() {
        BigDecimal total = new BigDecimal(0);
        for (Prescription p : appointment.getPrescriptions()) {
            total = total.add(p.getMedication().getPrice_per_quantity().multiply(new BigDecimal(p.getQuantity())));
        }

        appointment.setTotal_price(total.add(basicRate));
    }

    public void validateAndUpdate() throws AppointmentEntityException {
        if (appointment.getStatus().equals(StatusEnum.IN_PROGRESS)) { // Validate these fields only if appointment is ongoing
            // Create new list to combine duplicated prescriptions

            List<Prescription> set = new ArrayList<>();

            for (Prescription p : appointment.getPrescriptions()) {
                for (Prescription s : set) {
                    if (s.getMedication().equals(p.getMedication())) {
                        s.setQuantity(s.getQuantity() + p.getQuantity());
                        break;
                    }
                }
                set.add(new Prescription(p.getQuantity(), p.getMedication()));
            }


            // Check if medication stock satisfies
            for (Prescription p : set) {
                if (p.getMedication().getQuantity_on_hand() < p.getQuantity()) {
                    throw new AppointmentEntityException(p.getMedication().getName() + " does not have enough stock (Stock: " + p.getMedication().getQuantity_on_hand() + ").");
                }
            }

            // Check if drug allergy conflicts
            for (Prescription p : set) {
                for (String containing_drug : p.getMedication().getContaining_drugs()) {
                    for (String drug_allergy : appointment.getMedical_record().getDrug_allergys()) {
                        if (containing_drug.toLowerCase().equals(drug_allergy.toLowerCase())) {
                            throw new AppointmentEntityException("Patient has drug allergy " + drug_allergy + " that is conflicted with " + p.getMedication().getName() + ".");
                        }
                    }
                }


                
                // Check if conflicting medications exists
                for (Medication cm : p.getMedication().getConflicting_medications()) {
                    for (Prescription m : set) {
                        if (m.getMedication().equals(cm)) throw new AppointmentEntityException("Medication " + p.getMedication().getName() + " is conflicted with " + m.getMedication().getName() + ".");
                    }
                }


            }

            if (appointment.getMedical_certificate() == null && (mc_start_date != null || mc_end_date != null)) { // Check if MC date fields are filled up by the user
                if (mc_start_date == null || mc_end_date == null) {
                    throw new AppointmentEntityException("MC start/end date cannot be empty.");
                }
                if (mc_end_date.getTime() < mc_start_date.getTime()) {
                    throw new AppointmentEntityException("MC end date cannot be before start date.");
                }


                Calendar today = Calendar.getInstance();
                today.set(Calendar.HOUR_OF_DAY, 0);
                today.set(Calendar.MINUTE, 0);
                today.set(Calendar.SECOND, 0);
                today.set(Calendar.MILLISECOND, 0);
                if (mc_start_date.getTime() < today.getTime().getTime()) throw new AppointmentEntityException("MC cannot start before today.");


                MedicalCertificate mc = new MedicalCertificate(mc_start_date, mc_end_date, null);
                appointment.setMedical_certificate(mc);
            }


        }

        appointmentSessionBeanLocal.update(appointment);
    }

    public void doUpdate() {
        try {
            validateAndUpdate();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Record updated succesfully.", null));
        } catch (AppointmentEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }

    public void doUpdateRedirect() throws IOException {
        try {

            if (!appointment.getStatus().equals(StatusEnum.IN_PROGRESS)) {
                throw new AppointmentEntityException("Patient is not called in or the appointment has past.");
            }



            validateAndUpdate();
            appointmentSessionBeanLocal.updateStatus(appointment.getId(), StatusEnum.COMPLETED);


            medicationEntitySessionBeanLocal.processPrescriptions(appointment.getPrescriptions()); //  This will throw error if somehow any quantity exceeds stock in hand

            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/index.xhtml");
        } catch (AppointmentEntityException | MedicationEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }

    

    public void viewMc(ActionEvent event) throws IOException {
//        --- View MC Logic here ---

        try {
            long duration = appointment.getMedical_certificate().getEnd_date().getTime() - appointment.getMedical_certificate().getStart_date().getTime();

            long diff = TimeUnit.MILLISECONDS.toDays(duration) + 1;
            HashMap parameters = new HashMap();
            parameters.put("Title", "Medical Certificate");
            parameters.put("INPUT_ID", appointment.getId());
            parameters.put("INPUT_MedID", appointment.getMedical_certificate().getId());
            parameters.put("Duration", "For the duration of");
            parameters.put("INPUT_NAME", diff);
            parameters.put("Message", "This is to certify that Mr/Ms :  ");
            parameters.put("dr ", "Doctor");
            parameters.put("line", "__________________");
            parameters.put("header", "____________________________________________________________________________________________________");
            parameters.put("patient", appointment.getMedical_record().getName());
            parameters.put("from", "Was examined and treated at PCH and would need futher medical attention");
            parameters.put("FROM", "From ");
            parameters.put("Date", "Date: ");
            parameters.put("Day", "Day(s) ");
            parameters.put("to", "to");
            parameters.put("line1", "______________________________________");
            parameters.put("line2", "________________________");
            parameters.put("line3", "_________________________");
            parameters.put("line4", "_________________________");
           

            parameters.put("Dr_name", appointment.getEmployee().getName());
            InputStream reportStream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/jasperreport/medicalcert.jasper");
            OutputStream outputStream = FacesContext.getCurrentInstance().getExternalContext().getResponseOutputStream();

            JasperRunManager.runReportToPdfStream(reportStream, outputStream, parameters, pchDataSource.getConnection());
       
        } catch (JRException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
        }
    }

    public BigDecimal getBasicRate() {
        return basicRate;
    }

    public void setBasicRate(BigDecimal basicRate) {
        this.basicRate = basicRate;

    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public Date getMc_start_date() {
        return mc_start_date;
    }

    public void setMc_start_date(Date mc_start_date) {
        this.mc_start_date = mc_start_date;
    }

    public Date getMc_end_date() {
        return mc_end_date;
    }

    public void setMc_end_date(Date mc_end_date) {
        this.mc_end_date = mc_end_date;
    }

    public List<Medication> getMedications() {
        return medications;
    }

    public void setMedications(List<Medication> medications) {
        this.medications = medications;
    }


}
