/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import util.enumeration.AppointmentTypeEnum;
import util.enumeration.ScheduleTypeEnum;
import util.enumeration.StatusEnum;

/**
 *
 * @author USER
 */
@Entity
public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 32)
    @Size(max = 32)
    private String queue_no;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @NotNull
    private Date date_time;
    
    @Column(length = 128)
    @Size(max = 128)
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private StatusEnum status;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private ScheduleTypeEnum schedule_type;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private AppointmentTypeEnum appointment_type;
    
    @Column(nullable = false, precision = 11, scale = 2)
    @NotNull
    @DecimalMin("0.00")
    @Digits(integer = 9, fraction = 2)
    private BigDecimal total_price;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @NotNull
    private Date date_created;
    
    @OneToMany(fetch = FetchType.EAGER)
    private List<Medication> medications;
    
    @ManyToOne
    private Employee employee;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private MedicalRecord medical_record;
    
    @OneToOne
    private MedicalCertificate medical_certificate;

    public Appointment() {
    }

    public Appointment(Employee employee, MedicalRecord medical_record, Date date_time, ScheduleTypeEnum schedule_type, AppointmentTypeEnum appointment_type, StatusEnum status) {
        this.queue_no = "None";
        this.date_time = date_time;
        this.description = "";
        this.status = status;
        this.schedule_type = schedule_type;
        this.appointment_type = appointment_type;
        this.total_price = new BigDecimal(0);
        this.medications = new ArrayList<>();
        this.employee = employee;
        this.medical_record = medical_record;
        this.date_created = new Date();
        this.medical_certificate = null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQueue_no() {
        return queue_no;
    }

    public void setQueue_no(String queue_no) {
        this.queue_no = queue_no;
    }

    public Date getDate_time() {
        return date_time;
    }

    public void setDate_time(Date date_time) {
        this.date_time = date_time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public ScheduleTypeEnum getSchedule_type() {
        return schedule_type;
    }

    public void setSchedule_type(ScheduleTypeEnum schedule_type) {
        this.schedule_type = schedule_type;
    }

    public AppointmentTypeEnum getAppointment_type() {
        return appointment_type;
    }

    public void setAppointment_type(AppointmentTypeEnum appointment_type) {
        this.appointment_type = appointment_type;
    }

    public BigDecimal getTotal_price() {
        return total_price;
    }

    public void setTotal_price(BigDecimal total_price) {
        this.total_price = total_price;
    }

    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }

    public List<Medication> getMedications() {
        return medications;
    }

    public void setMedications(List<Medication> medications) {
        this.medications = medications;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public MedicalRecord getMedical_record() {
        return medical_record;
    }

    public void setMedical_record(MedicalRecord medical_record) {
        this.medical_record = medical_record;
    }

    public MedicalCertificate getMedical_certificate() {
        return medical_certificate;
    }

    public void setMedical_certificate(MedicalCertificate medical_certificate) {
        this.medical_certificate = medical_certificate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Appointment)) {
            return false;
        }
        Appointment other = (Appointment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Appointment[ id=" + id + " ]";
    }
    
}
