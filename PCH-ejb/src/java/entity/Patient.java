/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author USER
 */
@Entity
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientId;
    
    @Column(nullable = false, length = 32, unique = true)
    @Size(min = 1, max = 32)
    @NotNull
    private String username;
    
    @Column(nullable = false, length = 32)
    @Size(min = 1, max = 32)
    @NotNull
    private String password;
    
    @Column(nullable = false, length = 32)
    @Email
    @Size(min = 1, max = 32)
    @NotNull
    private String email;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @NotNull
    private Date date_created;
    
    @OneToOne(mappedBy = "patient")
    @JoinColumn(nullable = false)
    @NotNull
    private MedicalRecord medical_record;

    public Patient() {
    }

    public Patient(String username, String password, String email, MedicalRecord medical_record) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.date_created = new Date();
        this.medical_record = medical_record;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }

    public MedicalRecord getMedical_record() {
        return medical_record;
    }

    public void setMedical_record(MedicalRecord medical_record) {
        this.medical_record = medical_record;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (patientId != null ? patientId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the patientId fields are not set
        if (!(object instanceof Patient)) {
            return false;
        }
        Patient other = (Patient) object;
        if ((this.patientId == null && other.patientId != null) || (this.patientId != null && !this.patientId.equals(other.patientId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Patient[ id=" + patientId + " ]";
    }
    
}
