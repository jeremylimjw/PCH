/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.converter;

import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author USER
 */
@FacesConverter(value = "isListEmptyConverter")
public class IsListEmptyConverter implements Converter {
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return "";
    }
    
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        }
        
        if (value instanceof String){
            return "";
        }
        
        if (value instanceof List) {
            List list = (List) value;
            if (list.isEmpty()) {
                return "No";
            } else {
                return "Yes";
            }
        } else {
            throw new IllegalArgumentException("Please enter a valid list.");
        }
    }
}
