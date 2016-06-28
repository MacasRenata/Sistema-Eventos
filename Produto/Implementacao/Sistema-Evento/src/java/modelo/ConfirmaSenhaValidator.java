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
        String confirma = (String) component.getAttributes().get("confirma");
        String confirma1 = (String) component.getAttributes().get("confirma1");
        
        //if (senha == null || confirma == null || confirma1 == null) {
        //    return; // Just ignore and let required="true" do its job.
        //}

        if (!senha.equals(confirma)) {
            throw new ValidatorException(new FacesMessage("Senhas não estão iguais"));
        } else{
            return;
        }
    }

    
}
