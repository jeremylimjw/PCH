/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.converter;

import entity.Medication;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author USER
 */
@FacesConverter(value = "medicationConverter", forClass = Medication.class)
public class MedicationConverter implements Converter {
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.length() == 0 || value.equals("null")) {
            return null;
        }

        try {            
            Long objLong = Long.parseLong(value);
            
            List<Medication> medications = (List<Medication>)context.getExternalContext().getSessionMap().get("medications");
            
            for(Medication m : medications) {
                if(m.getId().equals(objLong)) {
                    return m;
                }
            }
        } catch(Exception ex) {
            throw new IllegalArgumentException("Please select a valid value");
        }
        
        return null;
    }

    
    
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        }
        
        if (value instanceof String) {
            return "";
        }
        
        if (value instanceof Medication) {            
            Medication medication = (Medication) value;                        
            
            try {
                return medication.getId().toString();
            } catch(Exception ex) {
                throw new IllegalArgumentException("Invalid value");
            }
        } else {
            throw new IllegalArgumentException("Invalid value");
        }
    }
}
