/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author sergio
 */
@FacesValidator("dataEventoValidator")

public class DataEventoValidator implements Validator {
    
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value == null) {
            return; // Let required="true" handle.
        }

        UIInput startDateComponent = (UIInput) component.getAttributes().get("startDateComponent1");

        if (!startDateComponent.isValid()) {
            return; // Already invalidated. Don't care about it then.
        }

        Date startDate1 = (Date) startDateComponent.getValue();

        if (startDate1 == null) {
            return; // Let required="true" handle.
        }

        Date endDate1 = (Date) value;
        
        if (startDate1.after(endDate1)) {
            startDateComponent.setValid(false);
            throw new ValidatorException(new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "Data inicial deve ser anterior a data final.", null));
        }
    }
 
}