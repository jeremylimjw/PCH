/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.MedicalRecord;
import javax.ejb.Local;
import util.exception.MedicalRecordEntityException;

/**
 *
 * @author USER
 */
@Local
public interface MedicalRecordSessionBeanLocal {
    public MedicalRecord retrieveById(Long id) throws MedicalRecordEntityException;
}
