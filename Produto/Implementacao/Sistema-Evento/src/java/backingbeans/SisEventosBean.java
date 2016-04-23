package backingbeans;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import modelo.Evento;
import persistencia.EventoDAO;


@ManagedBean
@SessionScoped
//Se mantenho escopo de sessão, após visualizar os detalhes do evento, quando vou criar outro evento a tela aparece com os
//campos preenchidos e ao clicar em criar o evento visualizado anteriormente é alterado.
//Se uso escopo de requisição não consigo alterar o evento, e recebo a seguinte mensagem: 
//org.hibernate.StaleStateException: Batch update returned unexpected row count from update [0]; actual row count: 0; expected: 1

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
                "Evento criado com sucesso!", "");
        evento = new Evento();
        context.addMessage(null, msg);
        return null;
    }

     
    public String consultarEvento(int id) {
       
        evento = evtDao.carregar(id);//idEvento
        return "consultaEvento";

    }
    
    //Para ir para a página de alteração do Evento selecionado à partir de Editar, em detalhes do Evento// 
    
      public String iniciaAlteracaoEvento(int id) {    
     evento = evtDao.carregar(id);
     return "alterarEvento";
      }
    
    public String alterarEvento() {             
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg;
        evtDao.alterar(evento);
        listaEventos = evtDao.listar();
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Evento alterado com sucesso!", "");
 
    
        evento = new Evento();
        context.addMessage(null, msg);
        return "listarEvento";
        
    }
    
}
