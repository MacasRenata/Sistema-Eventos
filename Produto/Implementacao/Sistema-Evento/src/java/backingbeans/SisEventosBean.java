package backingbeans;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
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
    
 

    private EventoDAO evtDao = new EventoDAO();

    public SisEventosBean() {
        listaEventos = evtDao.listar();
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public List<Evento> getListaEventos() {
        return listaEventos;
    }

    public String incluirEvento() {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg;
        evtDao.incluir(evento);
        listaEventos = evtDao.listar();
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Evento criado, incluído..., com sucesso!", "");
        evento = new Evento();
        context.addMessage(null, msg);
        return null;
    }

    public String consultarEvento(int id) {
        evento = evtDao.carregar(id);//idEvento
        return "consultaEvento";

    }
}
