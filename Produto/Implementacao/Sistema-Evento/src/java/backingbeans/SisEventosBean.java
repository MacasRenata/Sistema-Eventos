package backingbeans;

import static com.sun.codemodel.JExpr.component;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import modelo.Evento;
import modelo.Usuario;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import persistencia.EventoDAO;
import persistencia.UsuarioDAO;
import org.primefaces.event.ToggleEvent;

@ManagedBean
@SessionScoped

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

    public String incluirEvento() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg;
        evtDao.incluir(evento);
        listaEventos = evtDao.listar();
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Evento criado com sucesso!", "");
       // evento = new Evento();
        context.addMessage(null, msg);
        return "listaEventos";
    
    
    //File file = new File("C:\\user\\Desktop\\dir1\\dir2\\filename.html");
       // file.getParentFile().mkdirs();
       // FileWriter writer = new FileWriter(file);
    
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
        UsuarioDAO usuDAO = new UsuarioDAO();
        // Enviando a encriptacao
        //String encript = DigestUtils.md5Hex(this.usuario.getNombre());
        String encript = DigestUtils.shaHex(this.usuario.getSenha());
        //String encript = DigestUtils.sha1Hex(this.usuario.getClave());
        this.usuario.setSenha(encript);
        usuDAO.incluir(this.usuario);
        //usuarioDao.incluir(usuario);
        listaUsuarios = usuarioDao.listar();
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Usuario cadastrado com Sucesso!", "");
        // usuario = new Usuario();
        context.addMessage(null, msg);
        usuario = new Usuario(); 
        return "usuario";
    }

    public String iniciaAlteracaoUsuario(int id) {
        usuario = usuarioDao.carregar(id);
        return "alterarUsuario";
    }
    
    public String iniciaMeusDados(int id) {
        usuario = usuarioDao.carregar(id);
        return "meusDados";
    }
    
    
    public String consultarUsuario(int id) {
        usuario = usuarioDao.carregar(id);//idUsuario
        return "consultaUsuario";
    }

    public String alterarUsuario() {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg;
        
        // Enviando a encriptacao
        //String encript = DigestUtils.md5Hex(this.usuario.getNombre());
        String encript = DigestUtils.shaHex(this.usuario.getSenha());
        //String encript = DigestUtils.sha1Hex(this.usuario.getClave());
        this.usuario.setSenha(encript);
        usuarioDao.alterar(usuario);
        listaUsuarios = usuarioDao.listar();
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Usuario alterado com Sucesso!", "");
        //usuario = new Usuario();
        context.addMessage(null, msg);
        //usuario = new Usuario(); 
        return "listaUsuarios";
    }
    
    public String alterarMeusDados() {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg;

        usuarioDao.alterar(usuario);

        msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Usuario alterado com Sucesso!", "");

        context.addMessage(null, msg);

        return "index";
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
    
    public String verificarLogin() throws Exception {
                FacesContext context = FacesContext.getCurrentInstance();
                FacesMessage msg;
		UsuarioDAO usuDAO = new UsuarioDAO();
		Usuario us;
		String resultado;

		try {
			// Enviando la encriptacion
			//String encript = DigestUtils.md5Hex(this.usuario.getNombre());
                        String encript = DigestUtils.shaHex(this.usuario.getSenha());
			//String encript = DigestUtils.sha1Hex(this.usuario.getClave());
			this.usuario.setSenha(encript);
                        
			us = usuDAO.verificarLogin(this.usuario);
			if (us != null) {

				FacesContext.getCurrentInstance().getExternalContext()
						.getSessionMap().put("usuario", us);
                                //usuario = new Usuario();
				resultado = "indexAdmin";
                                
			} else {
                            msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Erro de login!", "");
                            context.addMessage(null, msg);
                            usuario = new Usuario();
                            return null;
			}
		} catch (Exception e) {
			throw e;
		}

		return resultado;
	}
    
    	public boolean verificarSessao() {
		boolean estado;

		if (FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get("usuario") == null) {
			estado = false;
		} else {
			estado = true;
		}

		return estado;
	}
    
        public String fecharSessao() {
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            return "index";
	}
        
        
        
            public void EnviarSenha() {
                UsuarioDAO udao = new UsuarioDAO();
                Usuario u = udao.carregar(0);
                //usuario = usuarioDao.verificarEmail(usuario);
                
                
                if (u != null) {
                    Usuario usr = u;
                    SimpleEmail simplemail = new SimpleEmail();

                    try {
                        simplemail.setDebug(true);
                        simplemail.setSmtpPort(465);
                        simplemail.setHostName("smtp.gmail.com");
                        simplemail.setAuthentication("eventos@gambarra.com.br", "eventos10");//email e senha do setorVeículos
                        simplemail.setSSL(true);
                        simplemail.addTo(usr.getEmail());  
                        simplemail.setFrom("eventos@gambarra.com.br"); //repita o email aqui (email irá enviar  a senha ao email cadastrado do servidor)
                        simplemail.setSubject("Senha do sistema de eventos");
                        simplemail.setMsg("Sua senha é: " + usr.getSenha());
                        simplemail.send();

                    } catch (EmailException e) {
                    }
                }
    }
            
            public String verificarSenha() throws Exception {
                FacesContext context = FacesContext.getCurrentInstance();
                FacesMessage msg;
		UsuarioDAO usuDAO = new UsuarioDAO();
		Usuario us;
		String resultado;
                
                            
                            try {
                                // Enviando a encriptacao
                                //String encript = DigestUtils.md5Hex(this.usuario.getNombre());
                                String encript = DigestUtils.shaHex(this.usuario.getSenha());
                                String encript1 = DigestUtils.shaHex(this.usuario.getSenhaNova());
                                
                                //String encript = DigestUtils.sha1Hex(this.usuario.getClave());
                                this.usuario.setSenha(encript);
                                this.usuario.setSenhaNova(encript1);

                                us = usuDAO.verificarSenha(this.usuario);
                            	if (us != null) {     
                                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                                            "Senha alterada com Sucesso!", "");
                                    context.addMessage(null, msg);
                                    resultado = "index";
                                
                                } else {
                                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    "Senha atual errada", "");
                                    context.addMessage(null, msg);
                                    return null;
                                }
                            } catch (Exception e) {
                                throw e;
                            }

		return resultado;
	}
        
        public void aoAtivar(ToggleEvent event) {
            event.getVisibility().name();

    }
   
                       
     
}
