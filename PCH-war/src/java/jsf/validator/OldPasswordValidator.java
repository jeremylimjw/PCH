/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.validator;

import entity.Employee;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author GraceLi
 */
@FacesValidator(value = "oldPasswordValidator")
public class OldPasswordValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value != null) {
            if (value instanceof String) {
                String oldPassword = (String)value;
                //System.out.println(oldPassword);
                Employee user = (Employee) context.getCurrentInstance().getExternalContext().getSessionMap().get("user");
                //System.out.println(user.getPassword());
                if (!oldPassword.equals(user.getPassword())) {
                    FacesMessage msg = new FacesMessage("Password does not match!", null);
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);

                    throw new ValidatorException(msg);
                }
            }
        }
    }
    
}
