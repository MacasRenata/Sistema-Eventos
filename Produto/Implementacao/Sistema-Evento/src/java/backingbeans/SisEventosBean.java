package backingbeans;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import modelo.Evento;
import modelo.Usuario;
import persistencia.EventoDAO;
import persistencia.UsuarioDAO;

@ManagedBean
@RequestScoped

public class SisEventosBean {

    private Evento evento = new Evento();
    private Usuario usuario = new Usuario();
    private List<Evento> listaEventos;
    private List<Usuario> listaUsuarios;

    private final EventoDAO evtDao = new EventoDAO();
    private final UsuarioDAO usuarioDao = new UsuarioDAO();

    public SisEventosBean() {
        listaEventos = evtDao.listar();
        listaUsuarios = usuarioDao.listar();
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public String incluirEvento() {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg;
        evtDao.incluir(evento);
        listaEventos = evtDao.listar();
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Evento criado com sucesso!", "");
       // evento = new Evento();
        context.addMessage(null, msg);
        return "listaEventos";
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

        context.addMessage(null, msg);
        return "listaEventos";

    }

    //usuario
    public String incluirUsuario() {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg;
        usuarioDao.incluir(usuario);
        listaUsuarios = usuarioDao.listar();
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Usuario cadastrado com Sucesso!", "");
        // usuario = new Usuario();
        context.addMessage(null, msg);
        return "usuario";
    }

    public String iniciaAlteracaoUsuario(int id) {
        usuario = usuarioDao.carregar(id);
        return "alterarUsuario";
    }

    public String consultarUsuario(int id) {
        usuario = usuarioDao.carregar(id);//idUsuario
        return "consultaUsuario";
    }

    public String alterarUsuario() {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg;
        usuarioDao.alterar(usuario);
        listaUsuarios = usuarioDao.listar();
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Usuario alterado com Sucesso!", "");
        //usuario = new Usuario();
        context.addMessage(null, msg);
        return "listaUsuarios";
    }

    public String excluirUsuario(int id_usuario) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg;
        usuarioDao.excluir(id_usuario);
        listaUsuarios = usuarioDao.listar();
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Usuario removido com Sucesso!", "");
        //usuario = new Usuario();
        context.addMessage(null, msg);
        return null;
    }

}
