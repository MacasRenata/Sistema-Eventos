package modelo;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author luis
 *
 */
//@FacesConverter(forClass = Categoria.class)
@FacesConverter("conversorCategoria")
public class ConversorCategoria implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && !value.isEmpty()) {
            return (Categoria) uiComponent.getAttributes().get(value);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value instanceof Categoria) {
            Categoria categoria = (Categoria) value;
            if (categoria instanceof Categoria && categoria.getId() != null) {
                uiComponent.getAttributes().put(categoria.getId().toString(), categoria);
                return categoria.getId().toString();

            }
        }
        return "";
    }
    
}

