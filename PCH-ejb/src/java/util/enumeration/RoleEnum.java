/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.enumeration;

/**
 *
 * @author USER
 */
public enum RoleEnum {
    DOCTOR {
        @Override
        public String toString() {
            return "Doctor";
        }
    },
    NURSE {
        @Override
        public String toString() {
            return "Nurse";
        }
    };
}
