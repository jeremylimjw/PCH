/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Date;
import util.enumeration.AppointmentTypeEnum;

/**
 *
 * @author USER
 */
public class RequestBodyCreateAppointment {
    private Long doctor_id;
    private Long medical_record_id;
    private Date date;
    private AppointmentTypeEnum type;
    
    public RequestBodyCreateAppointment() {
        
    }

    public RequestBodyCreateAppointment(Long doctor_id, Long medical_record_id, Date date, AppointmentTypeEnum type) {
        this.doctor_id = doctor_id;
        this.medical_record_id = medical_record_id;
        this.date = date;
        this.type = type;
    }

    public Long getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(Long doctor_id) {
        this.doctor_id = doctor_id;
    }

    public Long getMedical_record_id() {
        return medical_record_id;
    }

    public void setMedical_record_id(Long medical_record_id) {
        this.medical_record_id = medical_record_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public AppointmentTypeEnum getType() {
        return type;
    }

    public void setType(AppointmentTypeEnum type) {
        this.type = type;
    }
    
}
