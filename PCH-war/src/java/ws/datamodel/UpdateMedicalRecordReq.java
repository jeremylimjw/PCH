/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import java.util.Date;
import java.util.List;

/**
 *
 * @author ngjunkai
 */
public class UpdateMedicalRecordReq {
    
     private Long id;
     private String name;
     private String nric;
     private String address;
     private Date dob;
     private String contact_number;
     private String blood_type;
     private List<String> drug_allergys;
     private List<String> family_historys;
     private List<String> past_medical_historys;
     private List<String> vaccinations;

    public UpdateMedicalRecordReq() {
    }

    public UpdateMedicalRecordReq(Long id, String name, String nric, String address, Date dob, String contact_number, String blood_type, List<String> drug_allergys, List<String> family_historys, List<String> past_medical_historys, List<String> vaccinations) {
        this.id = id;
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
    }



    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the nric
     */
    public String getNric() {
        return nric;
    }

    /**
     * @param nric the nric to set
     */
    public void setNric(String nric) {
        this.nric = nric;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the dob
     */
    public Date getDob() {
        return dob;
    }

    /**
     * @param dob the dob to set
     */
    public void setDob(Date dob) {
        this.dob = dob;
    }

    /**
     * @return the contact_number
     */
    public String getContact_number() {
        return contact_number;
    }

    /**
     * @param contact_number the contact_number to set
     */
    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the blood_type
     */
    public String getBlood_type() {
        return blood_type;
    }

    /**
     * @param blood_type the blood_type to set
     */
    public void setBlood_type(String blood_type) {
        this.blood_type = blood_type;
    }

    /**
     * @return the drug_allergys
     */
    public List<String> getDrug_allergys() {
        return drug_allergys;
    }

    /**
     * @param drug_allergys the drug_allergys to set
     */
    public void setDrug_allergys(List<String> drug_allergys) {
        this.drug_allergys = drug_allergys;
    }

    /**
     * @return the family_historys
     */
    public List<String> getFamily_historys() {
        return family_historys;
    }

    /**
     * @param family_historys the family_historys to set
     */
    public void setFamily_historys(List<String> family_historys) {
        this.family_historys = family_historys;
    }

    /**
     * @return the past_medical_historys
     */
    public List<String> getPast_medical_historys() {
        return past_medical_historys;
    }

    /**
     * @param past_medical_historys the past_medical_historys to set
     */
    public void setPast_medical_historys(List<String> past_medical_historys) {
        this.past_medical_historys = past_medical_historys;
    }

    /**
     * @return the vaccinations
     */
    public List<String> getVaccinations() {
        return vaccinations;
    }

    /**
     * @param vaccinations the vaccinations to set
     */
    public void setVaccinations(List<String> vaccinations) {
        this.vaccinations = vaccinations;
    }
     
     
     
     
}
