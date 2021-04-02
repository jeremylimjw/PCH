/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Patient;
import javax.ejb.Local;
import util.exception.PatientEntityException;

/**
 *
 * @author USER
 */
@Local
public interface PatientSessionBeanLocal {
    public Patient login(String username, String password) throws PatientEntityException;
    public Patient retrieveById(Long id) throws PatientEntityException;
    public Patient retrieveByUsername(String username) throws PatientEntityException;
    public void updatePatientDetails(Patient patient) throws PatientEntityException;
    public void updatePatientPassword(Patient patient) throws PatientEntityException;
    
}
