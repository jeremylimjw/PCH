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
public enum StatusEnum {
    BOOKED {
        @Override
        public String toString() {
            return "Booked";
        }
    },
    IN_PROGRESS {
        @Override
        public String toString() {
            return "In Progress";
        }
    },
    ARRIVED {
        @Override
        public String toString() {
            return "Arrived";
        }
    },
    COMPLETED {
        @Override
        public String toString() {
            return "Completed";
        }
    },
    CANCELLED {
        @Override
        public String toString() {
            return "Cancelled";
        }
    },
    MISSED {
        @Override
        public String toString() {
            return "Missed";
        }
    };
    
}
