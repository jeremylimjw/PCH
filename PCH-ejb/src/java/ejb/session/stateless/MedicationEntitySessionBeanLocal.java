/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Medication;
import entity.Prescription;
import java.util.List;
import javax.ejb.Local;
import util.exception.MedicationEntityException;

/**
 *
 * @author USER
 */
@Local
public interface MedicationEntitySessionBeanLocal {
    public List<Medication> retrieveAll();
    public Long create(Medication m);
    public Medication retrieveByMedicineId(Long mId) throws MedicationEntityException;
    public List<Medication> searchMedicinesByName(String searchString);
    public List<String> conflictMedicationByName(Long mId) throws MedicationEntityException;
    public void updateMedication(Medication medication , List<Long> medId ,List<String> cfood, List<String>cDrug) throws MedicationEntityException;
    public Medication createNewMedication(Medication newMedication, List<Long> medId , List<String> cFood, List<String> cdrugs) throws MedicationEntityException;
    public Long delete(Long medication_id) throws MedicationEntityException;
    public Long deleteMedication(Long medication_id, List<Medication> medication) throws MedicationEntityException;
    public void processPrescriptions(List<Prescription> prescriptions) throws MedicationEntityException;
}
