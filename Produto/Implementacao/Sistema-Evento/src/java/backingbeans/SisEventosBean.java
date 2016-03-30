
package backingbeans;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import modelo.Evento;
import persistencia.EventoDAO;



/**
 *
 * @author luis
 */
@ManagedBean
@SessionScoped
public class SisEventosBean {
    
    private Evento evento = new Evento();
    private List<Evento> listaEventos;
    
    
    private final EventoDAO evtDao = new EventoDAO();
  
    /*
    public SisEventosBean() {
        listaEventos = evtDao.listar();
    }
*/
}
