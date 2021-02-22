/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author ngjunkai
 */
public class MedicationEntityException extends Exception {

    /**
     * Creates a new instance of <code>MedicationEntityException</code> without
     * detail message.
     */
    public MedicationEntityException() {
    }

    /**
     * Constructs an instance of <code>MedicationEntityException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public MedicationEntityException(String msg) {
        super(msg);
    }
}
