/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Medication;
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

    public List<Medication> searchProductsByName(String searchString);

    public Medication retrieveByMedicineId(Long mId) throws MedicationEntityException;
    
}
