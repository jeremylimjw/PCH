/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

/**
 *
 * @author ngjunkai
 */
@Named(value = "printReceiptManagedBean")
@ViewScoped
public class PrintReceiptManagedBean implements Serializable {

    @Resource(name = "pchDataSource")
    private DataSource pchDataSource;

    /**
     * Creates a new instance of PrintReceiptManagedBean
     */
    @Inject
    private ServePatientManagedBean servePatientManagedBean;

    public PrintReceiptManagedBean() {
    }

    public void printReceipt() {
        if (!servePatientManagedBean.getAppointment().getPrescriptions().isEmpty()) {

            try {

                HashMap parameters = new HashMap();
                parameters.put("Invoice", "Invoice");
                parameters.put("INPUT_ID", servePatientManagedBean.getAppointment().getId());
                parameters.put("TotalPrice", "Total Price: ");
                parameters.put("Consultation_type", "Consultation Type");
                parameters.put("BasicRate", "Basic Rate : ");
                parameters.put("basic_Rate", servePatientManagedBean.getBasicRate());
                parameters.put("SubTotal", "Subtotal");
                InputStream reportStream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/jasperreport/invoice2.jasper");
                OutputStream outputStream = FacesContext.getCurrentInstance().getExternalContext().getResponseOutputStream();

                JasperRunManager.runReportToPdfStream(reportStream, outputStream, parameters, pchDataSource.getConnection());

            } catch (JRException ex) {
                ex.printStackTrace();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
            }

        } else {

            try {

                HashMap parameters = new HashMap();
                parameters.put("Invoice", "Invoice");
                parameters.put("INPUT_ID", servePatientManagedBean.getAppointment().getId());
                parameters.put("TotalPrice", "Total Price: ");
                parameters.put("Consultation_type", "Consultation Type");
                parameters.put("BasicRate", "Basic Rate : ");
                parameters.put("basic_Rate", servePatientManagedBean.getBasicRate());
              
                InputStream reportStream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/jasperreport/basic_invoice.jasper");
                OutputStream outputStream = FacesContext.getCurrentInstance().getExternalContext().getResponseOutputStream();

                JasperRunManager.runReportToPdfStream(reportStream, outputStream, parameters, pchDataSource.getConnection());

            } catch (JRException ex) {
                ex.printStackTrace();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
            }
        }

    }

   
    public ServePatientManagedBean getServePatientManagedBean() {
        return servePatientManagedBean;
    }

  
    public void setServePatientManagedBean(ServePatientManagedBean servePatientManagedBean) {
        this.servePatientManagedBean = servePatientManagedBean;
    }

}
