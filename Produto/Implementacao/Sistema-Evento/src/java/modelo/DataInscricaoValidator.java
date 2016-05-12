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
@FacesValidator("dataInscricaoValidator")
public class DataInscricaoValidator extends DataEventoValidator implements Validator{
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value == null) {
            return; // Let required="true" handle.
        }

        UIInput startDateComponent = (UIInput) component.getAttributes().get("startDateComponent2");
        UIInput endDateComponent = (UIInput) component.getAttributes().get("endDateComponent1");
        if (!startDateComponent.isValid()) {
            return; // Already invalidated. Don't care about it then.
        }

        Date startDate = (Date) startDateComponent.getValue();

        if (startDate == null) {
            return; // Let required="true" handle.
        }

        Date endDate2 = (Date) value;
        Date endDate3 = (Date) endDateComponent.getValue();
        
        

        if (startDate.after(endDate2)) {
            startDateComponent.setValid(false);
            throw new ValidatorException(new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "Data de incricao errada.", null));
        }
        
        if (endDate3 != null){
            if (endDate2.after(endDate3)) {
                startDateComponent.setValid(false);
                throw new ValidatorException(new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "Data de incricao errada.", null));
            }
        }
        

    }
    
}
