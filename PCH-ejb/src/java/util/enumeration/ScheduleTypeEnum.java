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
public enum ScheduleTypeEnum {
    APPOINTMENT {
        @Override
        public String toString() {
            return "Appointment";
        }
    },
    WALK_IN {
        @Override
        public String toString() {
            return "Walk In";
        }
    };
}
