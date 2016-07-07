/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;

import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author sergio
 */


@FacesValidator("confirmaSenhaValidator")

public class ConfirmaSenhaValidator implements Validator {
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String senha = (String) value;
        String confirma1 = (String) component.getAttributes().get("confirma1");
        String confirma2 = (String) component.getAttributes().get("confirma2");
        String confirma;
        
        if (confirma1 != null) {
            confirma = confirma1;
        } else {
            confirma = confirma2;
        }

        if (!senha.equals(confirma)) {
            throw new ValidatorException(new FacesMessage
                (FacesMessage.SEVERITY_ERROR, "Senhas não conferem.", null));
            
        }
    }

    
}
  
        