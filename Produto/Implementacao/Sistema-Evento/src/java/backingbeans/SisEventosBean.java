package backingbeans;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.annotation.WebServlet;
import modelo.Evento;
import modelo.Inscricao;
import modelo.Usuario;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;
import persistencia.EventoDAO;
import persistencia.UsuarioDAO;
import org.primefaces.event.ToggleEvent;
import persistencia.InscricaoDAO;

@ManagedBean
@SessionScoped
@WebServlet("/index")


public class SisEventosBean {

    private Evento evento = new Evento();
    private Usuario usuario = new Usuario();
    private Usuario usuarioLogado = new Usuario();
    private List<Evento> listaEventos;
    private List<Usuario> listaUsuarios;

    private final EventoDAO evtDao = new EventoDAO();
    private final UsuarioDAO usuarioDao = new UsuarioDAO();
    private InscricaoDAO inscricaoDao = new InscricaoDAO();
    private int usuarioSelecionado;
    
    private int eventoSelecionado;

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
    
     public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
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
         
     PrintWriter writer;
   
            writer = new PrintWriter("C:\\a\\evento.html", "UTF-8");
            writer.println("<html>");
            writer.println("<body>");
            writer.println("Evento " + evento.getTitulo() + "/n" );
            writer.println("Local " + evento.getLocal_evento()+ "" );
            writer.println("Data Inicío " + evento.getData_inicial()+ "" );
            writer.println("Periodo de Inscrições " + evento.getData_inicial_inscricao()+ "" );
            writer.println("Horário " + evento.getHorario_inicio()+ "" );
            writer.println("Area " + evento.getArea_evento()+ "" );
            writer.println("Categoria " + evento.getCategoria_evento()+ "" );
            writer.println("Local " + evento.getLocal_evento()+ "" );
            writer.println("</body>");
            writer.println("</html>");
            writer.close();
    
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
        usuarioLogado = usuarioDao.carregar(id);
        return "meusDados";
    }
    
    public String iniciaInscricaoEvento(int id_user, int id_evt) {
        //System.out.println(id_user);
        //System.out.println(id_evt);
        usuarioLogado = usuarioDao.carregar(id_user);
        evento = evtDao.carregar(id_evt);
        return "inscricaoEvento";
        
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

        usuarioDao.alterar(usuarioLogado);

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
			// Enviando a encriptacao
                        
                        String encript = DigestUtils.shaHex(this.getUsuarioLogado().getSenha());
			
			this.getUsuarioLogado().setSenha(encript);
                        
			us = usuDAO.verificarLogin(this.getUsuarioLogado());
			if (us != null) {

				FacesContext.getCurrentInstance().getExternalContext()
						.getSessionMap().put("usuarioLogado", us);
                                //usuario = new Usuario();
				resultado = "indexAdmin";
                                
			} else {
                            msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Erro de login!", "");
                            context.addMessage(null, msg);
                            usuarioLogado = new Usuario();
                            return null;
			}
		} catch (Exception e) {
			throw e;
		}

		return resultado;
	}
    
    public boolean verificarSessaoAdmin() {
		boolean estado;

			if (FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap().get("usuarioLogado") == null) {
				estado = false;
			} else {
				estado = true;
			}

		return estado;
    }
    
    
    	public boolean verificarSessao() {
		boolean estado;

		if (FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get("usuarioLogado") == null) {
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
        
        
        
            public String EnviarSenha() throws Exception {
                FacesContext context = FacesContext.getCurrentInstance();
                FacesMessage msg;
		UsuarioDAO usuDAO = new UsuarioDAO();
                usuDAO.carregar(0);
                this.usuarioLogado.setTrocasenha(true);
                usuDAO.recuperarSenha(usuarioLogado);
		Usuario us;
		String resultado;
                InetAddress ia = null;
                    try {
                        ia = InetAddress.getLocalHost();
                        us = usuDAO.verificarEmail(this.usuarioLogado);
                        if (us != null) {
  
                        Usuario usr = us;

                        HtmlEmail email = new HtmlEmail();
                        
                        email.setHostName( "smtp.gmail.com" );
                        //email.setSmtpPort(465);
                        email.setSslSmtpPort( "587" );
                        email.setStartTLSRequired(true);
                        email.setSSLOnConnect(true);
                        email.setSSLCheckServerIdentity(true);

                        email.setAuthenticator(new DefaultAuthenticator("eventos@gambarra.com.br", "xxxxxxx"));

                        
                        try {
                            email.setFrom("eventos@gambarra.com.br");
                            email.setDebug(true); 
                            email.setSubject("Senha do sistema de eventos");
                            //email.setMsg("Sua senha é: " + usr.getSenha());

                            email.setHtmlMsg( "<html>"
                            + "<head>"
                            + "<title>Recuperação de Senha</title>"
                            + "</head>"
                            + "<body>"
                            + "<div style='font-size: 14px'>"
                            + "<p>Olá " + usr.getNome()
                            + "</p>"
                            + "<p>Sua senha provisoria é: " + usr.getSenha()
                            + "</p>"
                            + "<p>Para alterar sua senha clique no link "
                            + "<a href=\"http://" + ia.getHostAddress() + ":8080/Sistema-Evento/faces/alterarSenha.xhtml?parametro=" + usr.getSenha() + "\">Nova Senha</a>"
                            + "</p>"
                            + "</div>"
                            + "<p> Antenciosamente <br/> Sistema de Eventos </p> "
                            + "</body>"
                            + "</html>");
                            email.addTo(usr.getEmail()); 
                            email.send();

                        } catch (EmailException e) {
                            e.printStackTrace();
                        }

                            msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    "Senha enviada com Sucesso!", "");
                            context.addMessage(null, msg);
                            resultado = "index";

                        } else {
                            msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Email não cadastrado", "");
                            context.addMessage(null, msg);
                            return null;
                        }
                    } catch (Exception e) {
                        throw e;
                    }

		return resultado;
    }
            
            public String verificarSenha() throws Exception {
                FacesContext context = FacesContext.getCurrentInstance();
                FacesMessage msg;
		UsuarioDAO usuDAO = new UsuarioDAO();
		
                Usuario u = usuDAO.buscarUsuario(usuarioLogado.getEmail());
                usuarioLogado.setTrocasenha(u.getTrocasenha());
		String resultado;
                            
                            try {
                                // Enviando a encriptacao
                                
                                if (this.usuarioLogado.getTrocasenha() == false){
                                    String encript = DigestUtils.shaHex(this.usuarioLogado.getSenha());
                                    this.usuarioLogado.setSenha(encript);
                                }
                                
                                String encript1 = DigestUtils.shaHex(this.usuarioLogado.getSenhaNova());
                                this.usuarioLogado.setSenhaNova(encript1);
                                u = usuDAO.verificarSenha(this.usuarioLogado);
                            	if (u != null) {
                                    this.usuarioLogado.setTrocasenha(false);
                                    u = usuDAO.recuperarSenha(usuarioLogado);
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
        
        public String realizarInscricao() {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg;
        
            //int id = eventoSelecionado;
            //Evento e = evtDao.carregar(id);
            Inscricao inscr = new Inscricao();//Não consigo carregar os Ids do usuário e do evento
            inscr.setUsuario(usuarioLogado);
            inscr.setEvento(evento); 
            //evento.setInscricoesEvt((List<Inscricao>)inscr); //  Erro java.lang.ClassCastException: modelo.Inscricao cannot be cast to java.util.List
            inscricaoDao.incluir(inscr);
            
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                                "Inscrição realizada com sucesso!", "");
            
        
      return null;
    }

    
}
