/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Patient;
import java.util.List;
import javax.ejb.Local;
import util.exception.InputDataValidationException;
import util.exception.MedicalRecordNotFoundException;

/**
 *
 * @author GraceLi
 */
@Local
public interface PatientEntitySessionBeanLocal {

    public List<Patient> retrieveAllPatients();

    public Long createNewPatient(Patient newPatient, String NRIC) throws MedicalRecordNotFoundException, InputDataValidationException;
    
}
