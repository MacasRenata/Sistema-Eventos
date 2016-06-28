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
import persistencia.UsuarioDAO;

/**
 *
 * @author sergio
 */


@FacesValidator("unicoEmailValidator")
public class UnicoEmailValidator implements Validator {
    private final UsuarioDAO usuarioDao = new UsuarioDAO();
    private Usuario usuario = new Usuario();
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        int id_user = (int) component.getAttributes().get("txtId");
        String email = (String) value;
        Usuario usr = usuarioDao.verificarEmail(email);
        if (value == null) {
            return; // Let required="true" handle, if any.
        }
        if (usr == null || usr.getId_user() == id_user){
            return;
        }
        
        //String emailBD = usr.getEmail();
        if (usr.getEmail() != null){
            if (usr.getEmail().equals(email)) {
                throw new ValidatorException(new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Email ja cadastrado.", null));
            }
        }
        
    }

}
    

