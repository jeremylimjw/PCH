/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.MedicalRecord;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.MedicalRecordEntityException;

/**
 *
 * @author USER
 */
@Stateless
public class MedicalRecordSessionBean implements MedicalRecordSessionBeanLocal {

    @PersistenceContext(unitName = "PCH-ejbPU")
    private EntityManager em;
    
    @Override
    public MedicalRecord retrieveById(Long id) throws MedicalRecordEntityException {
        if (id == null) throw new MedicalRecordEntityException("Error: Medical Record ID is not provided!");
        MedicalRecord medicalRecord = em.find(MedicalRecord.class, id);
        if (medicalRecord == null) throw new MedicalRecordEntityException("Error: Medical Record with ID " + id + " is not found!");
        return medicalRecord;
    }
}
