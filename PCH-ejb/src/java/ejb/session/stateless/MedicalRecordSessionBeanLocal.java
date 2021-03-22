/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Appointment;
import entity.MedicalRecord;
import java.util.List;
import javax.ejb.Local;
import util.exception.InputDataValidationException;
import util.exception.MedicalRecordEntityException;
import util.exception.MedicalRecordNotFoundException;

/**
 *
 * @author USER
 */
@Local
public interface MedicalRecordSessionBeanLocal {

    public Long createNewMedicalRecord(MedicalRecord newMedicalRecord) throws InputDataValidationException;

    public List<MedicalRecord> retrieveAllMedicalRecords();

    public List<MedicalRecord> searchMedicalRecordsByName(String searchString);

    public List<MedicalRecord> searchMedicalRecordsByNRIC(String searchString);

    public void updateMedicalRecord(MedicalRecord medicalRecord, Long patientId, List<Appointment> appointments) throws InputDataValidationException, MedicalRecordNotFoundException, MedicalRecordEntityException;

    public MedicalRecord retrieveById(Long id) throws MedicalRecordEntityException;
    public void update(MedicalRecord medicalRecord) throws MedicalRecordEntityException;
}
