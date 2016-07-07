/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;

import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import persistencia.UsuarioDAO;

/**
 *
 * @author sergio
 */


@FacesValidator("confirmaSenhaValidator")
public class ConfirmaSenhaValidator implements Validator {
    
    private final UsuarioDAO usuarioDao = new UsuarioDAO();
    private Usuario usuario = new Usuario();
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
         
        String confirma = (String) value;
        UIInput confirma1Component = (UIInput) component.getAttributes().get("senhaNova1Component");
        UIInput confirma2Component = (UIInput) component.getAttributes().get("senhaNova2Component");
        UIInput txtEmailComponent = (UIInput) component.getAttributes().get("txtEmailComponent");
        
        String senhaNova1 = "";
        String senhaNova2 = "";
        String email = "";
        
        if (confirma1Component!= null){
            senhaNova1 = (String) confirma1Component.getValue();
        }
        if (confirma2Component!= null){
            senhaNova2 = (String) confirma2Component.getValue();
        }
        if (txtEmailComponent!= null){
            email = (String) txtEmailComponent.getValue();
        }
        
        if (component.getAttributes().get("txtEmail") != null) {
            email = (String) component.getAttributes().get("txtEmail");
        }
        
        Usuario usr = usuarioDao.verificarEmail(email);
        if (!email.equals("")){
            if (usr == null) {
                throw new ValidatorException(new FacesMessage(
                        FacesMessage.SEVERITY_ERROR, "Email não cadastrado.",null));
        }
        }
        
        String senhaNova;
        if (!senhaNova1.equals("")) {
            senhaNova = senhaNova1;
        } else {
            senhaNova = senhaNova2;
        }

        if (!confirma.equals(senhaNova)) {
            throw new ValidatorException(new FacesMessage
                (FacesMessage.SEVERITY_ERROR, "Senhas não conferem.", null));
            
        }
    }
   

}
  
        
