package backingbeans;

import excecoes.SisEventoException;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import modelo.Evento;
import persistencia.EventoDAO;


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

    // Conferir se está Duplicando a inclusão
    public String incluirEvento() {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg;
        evtDao.incluir(evento);
        listaEventos = evtDao.listar();
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Evento criado, incluído..., com sucesso!", "");
    //    evento = new Evento();
        context.addMessage(null, msg);
        return null;
    }

    public String consultarEvento(int id) {
        evento = evtDao.carregar(id);//idEvento
        return "consultaEvento";

    }
    
     public String iniciaAlteracaoEvento() {    
     //    EventoDAO dao = new EventoDAO();
     evento = evtDao.carregar(evento.getId());
     return "alterarEvento2";
    }
    
     
     //Método alterado, deve funcionar, conferir no xhtml
     //msg na alteraçao:
     //nested transactions not suported
    public String alterarEvento() {             
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg;
    //    EventoDAO dao = new EventoDAO();
        evtDao.alterar(evento);
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Evento alterado com sucesso!", "");
        evento = new Evento();
        context.addMessage(null, msg);
        return "alterarEvento";
    }
    
}
