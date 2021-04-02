/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.MedicalRecord;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import util.exception.MedicalRecordEntityException;

/**
 *
 * @author USER
 */
@Local
public interface MedicalRecordSessionBeanLocal {
    public MedicalRecord retrieveById(Long id) throws MedicalRecordEntityException;
    public void update(MedicalRecord medicalRecord) throws MedicalRecordEntityException;

    public void updateMedReq(Long id, String name, String nric, String address, Date dob, String contact_number, String blood_type, List<String> drug_allergys, List<String> family_historys, List<String> past_medical_historys, List<String> vaccinations) throws MedicalRecordEntityException;
}
