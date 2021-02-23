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
public enum AppointmentTypeEnum {
    CONSULTATION {
        @Override
        public String toString() {
            return "Consultation";
        }
    },
    HEALTH_CHECKUP {
        @Override
        public String toString() {
            return "Health Checkup";
        }
    },
    VACCINATION {
        @Override
        public String toString() {
            return "Vaccination";
        }
    };
}
