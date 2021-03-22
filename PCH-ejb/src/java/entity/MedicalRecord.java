/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author USER
 */
@Entity
public class MedicalRecord implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    
    @Column(nullable = false, length = 32)
    @Size(min = 1, max = 32)
    @NotNull
    private String name;
    
    @Column(nullable = false, length = 32, unique = true)
    @Size(min = 1, max = 32)
    @NotNull
    private String nric;
    
    @Column(nullable = false, length = 128)
    @Size(min = 1, max = 128)
    private String address;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @NotNull
    private Date dob;
    
    @Column(nullable = false, length = 32)
    @Size(min = 8, max = 8)
    @NotNull
    private String contact_number;
    
    @Column(length = 32)
    @Size(min = 1, max = 32)
    private String blood_type;
    
    @Column
    private List<String> drug_allergys;
    
    @Column
    private List<String> family_historys;
    
    @Column
    private List<String> past_medical_historys;
    
    @Column
    private List<String> vaccinations;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @NotNull
    private Date date_created;
    
    @OneToMany(mappedBy = "medical_record", fetch = FetchType.EAGER)
    private List<Appointment> appointments;
    
    @OneToOne
    private Patient patient;

    public MedicalRecord() {
    }

    public MedicalRecord(String name, String nric, String address, Date dob, String contact_number, String blood_type, List<String> drug_allergys, List<String> family_historys, List<String> past_medical_historys, List<String> vaccinations) {
        this.name = name;
        this.nric = nric;
        this.address = address;
        this.dob = dob;
        this.contact_number = contact_number;
        this.blood_type = blood_type;
        this.drug_allergys = drug_allergys;
        this.family_historys = family_historys;
        this.past_medical_historys = past_medical_historys;
        this.vaccinations = vaccinations;
        this.date_created = new Date();
        this.appointments = new ArrayList<>();
    }
    
    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNric() {
        return nric;
    }

    public void setNric(String nric) {
        this.nric = nric;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public String getBlood_type() {
        return blood_type;
    }

    public void setBlood_type(String blood_type) {
        this.blood_type = blood_type;
    }

    public List<String> getDrug_allergys() {
        return drug_allergys;
    }

    public void setDrug_allergys(List<String> drug_allergys) {
        this.drug_allergys = drug_allergys;
    }

    public List<String> getFamily_historys() {
        return family_historys;
    }

    public void setFamily_historys(List<String> family_historys) {
        this.family_historys = family_historys;
    }

    public List<String> getPast_medical_historys() {
        return past_medical_historys;
    }

    public void setPast_medical_historys(List<String> past_medical_historys) {
        this.past_medical_historys = past_medical_historys;
    }

    public List<String> getVaccinations() {
        return vaccinations;
    }

    public void setVaccinations(List<String> vaccinations) {
        this.vaccinations = vaccinations;
    }

    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (Id != null ? Id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the Id fields are not set
        if (!(object instanceof MedicalRecord)) {
            return false;
        }
        MedicalRecord other = (MedicalRecord) object;
        if ((this.Id == null && other.Id != null) || (this.Id != null && !this.Id.equals(other.Id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.MedicalRecord[ id=" + Id + " ]";
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    
    
}
