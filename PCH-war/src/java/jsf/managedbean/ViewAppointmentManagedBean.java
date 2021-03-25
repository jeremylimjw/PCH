/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.AppointmentSessionBeanLocal;
import entity.Appointment;
import entity.Prescription;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import util.enumeration.AppointmentTypeEnum;
import util.exception.AppointmentEntityException;

/**
 *
 * @author USER
 */
@Named(value = "viewAppointmentManagedBean")
@ViewScoped
public class ViewAppointmentManagedBean implements Serializable {

    @Resource(name = "pchDataSource")
    private DataSource pchDataSource;

    @EJB(name = "AppointmentSessionBeanLocal")
    private AppointmentSessionBeanLocal appointmentSessionBeanLocal;

    private BigDecimal basicRate = new BigDecimal(40);
    private Appointment appointment;
    private Long duration;

    public ViewAppointmentManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        try {

            Long id = Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
            appointment = appointmentSessionBeanLocal.retrieveById(id);

            if (appointment.getSchedule_type().equals(AppointmentTypeEnum.CONSULTATION)) {
                basicRate = new BigDecimal(40);
            } else if (appointment.getSchedule_type().equals(AppointmentTypeEnum.HEALTH_CHECKUP)) {
                basicRate = new BigDecimal(20);
            } else if (appointment.getSchedule_type().equals(AppointmentTypeEnum.VACCINATION)) {
                basicRate = new BigDecimal(10);
            } else {
                basicRate = new BigDecimal(40);
            }

            calculateTotal();

        } catch (NumberFormatException | AppointmentEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
        }
    }

    public void calculateTotal() {
        BigDecimal total = new BigDecimal(0);
        for (Prescription p : appointment.getPrescriptions()) {
            total = total.add(p.getMedication().getPrice_per_quantity().multiply(new BigDecimal(p.getQuantity())));
        }
        appointment.setTotal_price(total.add(basicRate));
    }

    public void viewMc(ActionEvent event) {
//        --- View MC Logic here ---

        try {
            duration = appointment.getMedical_certificate().getEnd_date().getTime() - appointment.getMedical_certificate().getStart_date().getTime();
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

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public BigDecimal getBasicRate() {
        return basicRate;
    }

    public void setBasicRate(BigDecimal basicRate) {
        this.basicRate = basicRate;
    }

    /**
     * @return the duration
     */
    public Long getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(Long duration) {
        this.duration = duration;
    }

}
