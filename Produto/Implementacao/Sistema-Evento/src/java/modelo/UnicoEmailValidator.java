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
        if (value == null) {
            return; // Let required="true" handle, if any.
        }

        String email = (String) value;
        Usuario usr = usuarioDao.verificarEmail(email);
        String emailBD = usr.getEmail();
        if (emailBD != null){
            if (emailBD.equals(email)) {
                throw new ValidatorException(new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Email ja cadastrado.", null));
            }
        }
            
    }

}
    

