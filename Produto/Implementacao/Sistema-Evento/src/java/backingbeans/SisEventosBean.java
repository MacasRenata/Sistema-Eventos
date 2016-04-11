package backingbeans;

import excecoes.SisEventoException;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import modelo.Evento;
import org.primefaces.model.UploadedFile;
import persistencia.EventoDAO;


@ManagedBean
@SessionScoped
public class SisEventosBean {

    private Evento evento = new Evento();
    private List<Evento> listaEventos;
    private UploadedFile file;
 

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
    
     public String iniciaAlteracaoEvento() {    
         EventoDAO dao = new EventoDAO();
     evento = dao.carregar(evento.getId());
     return "alterarEvento2";
    }
    
    public String alterarEvento() {               //alterando o evento com os dados armazenados no banco 
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg;
        EventoDAO dao = new EventoDAO();
        dao.alterar(evento);
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Evento alterado com sucesso!", "");
        evento = new Evento();
        context.addMessage(null, msg);
        return "alterarEvento";
    }
    
    public UploadedFile getFile() {   //método para submeter arquivos
        return file;
    }
 
    public void setFile(UploadedFile file) {
        this.file = file;
    }
     
    public void upload() {
        if(file != null) {
            FacesMessage message = new FacesMessage("Sucesso", file.getFileName() + " arquivo submetido.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
}
