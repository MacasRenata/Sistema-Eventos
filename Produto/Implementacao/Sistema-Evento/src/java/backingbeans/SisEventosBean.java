package backingbeans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.annotation.WebServlet;
import modelo.Categoria;
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
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import persistencia.CategoriaDAO;

@ManagedBean
@SessionScoped
@WebServlet("/index")

public class SisEventosBean {

    private Evento evento = new Evento();
    private Usuario usuario = new Usuario();
    private Usuario usuarioLogado = new Usuario();
    private Inscricao inscricao = new Inscricao();
    private List<Evento> listaEventos;
    private List<Usuario> listaUsuarios;
    private List<Inscricao> listaInscricao;
    private List<Writer> listaPagina;
    private UploadedFile file;
    
    private Long idCategoria;
    private List<Categoria> categorias;
    private Categoria categoria = new Categoria();
    
    private final EventoDAO evtDao = new EventoDAO();
    private final UsuarioDAO usuarioDao = new UsuarioDAO();
    private final InscricaoDAO inscricaoDao = new InscricaoDAO();

    public SisEventosBean() {
        listaEventos = evtDao.listar();
        listaUsuarios = usuarioDao.listar();
        listaInscricao = inscricaoDao.listar();
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

     public Long getIdCategoria() {
        return idCategoria;
    }
    
    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String incluirEvento() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg;
        CategoriaDAO categoriaDao = new CategoriaDAO();
        evento.setCategoria(categoriaDao.carregar(idCategoria));
        evtDao.incluir(evento);
        listaEventos = evtDao.listar();
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Evento criado com sucesso!", "");
        // evento = new Evento();
        context.addMessage(null, msg);

        PrintWriter writer;
        //FileOutputStream fos = new FileOutputStream("t.tmp");
        //ObjectOutputStream oos = new ObjectOutputStream(fos);

        writer = new PrintWriter("C:\\Users\\Maçãs2\\Documents\\GitHub\\Sistema-Eventos\\Produto\\Implementacao\\Sistema-Evento\\web\\eventos\\evento.html", "UTF-8");
        writer.println("<html>");
        writer.println("<head>");
        writer.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8>");
        writer.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"../resources/css/style.css\">");
        writer.println("<title>Sistema Evento</title>");
        writer.println("</head>");
        writer.println("<body>");
        writer.println("<div id=\"header\">");
        writer.println("<h1>Sistema de Eventos");
        writer.println("</h1>");
        writer.println("</div>");
        writer.println("<div id=\"nav\">");
        writer.println("Evento " + evento.getTitulo() + "/n");
        writer.println("<br>Informações " + evento.getDescricao_evento()+"");  // detalhes sobre o evento
        writer.println("</div>");
        writer.println("<div id=\"section\">");
        writer.println("<h1> Evento");
        writer.println("</h1>");
        writer.println("Local " + evento.getLocal_evento() + "");
        writer.println("Data Inicío " + evento.getData_inicial() + "");
        writer.println("Periodo de Inscrições " + evento.getData_inicial_inscricao() + "");
        writer.println("Horário " + evento.getHorario_inicio() + "");
        writer.println("Area " + evento.getArea_evento() + "");
        writer.println("Categoria " + evento.getCategoria_evento() + "");
        writer.println("Local " + evento.getLocal_evento() + "");
        writer.println("<br>Local: " + evento.getLocal_evento() + "");
        writer.println("</p></div>");
        writer.println("<div id=\"footer\">");
        writer.println("Copyright © sistemaeventos.com");
        writer.println("</div>");
        writer.println("</body>");
        writer.println("</html>");
        
        //oos.writeObject(evento);
        //oos.close();  
        writer.close();
        /*
        FileInputStream fis = new FileInputStream("t.tmp");
        ObjectInputStream ois = new ObjectInputStream(fis);
        try {
            List<Evento> evento = (List<Evento>) ois.readObject();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SisEventosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        ois.close();*/

        return "listaEventos";
    }

    public String consultarEvento(int id) {
        evento = evtDao.carregar(id);//idEvento
        return "consultaEvento";
    }

    //Para ir para a página de alteração do Evento selecionado à partir de Editar, em detalhes do Evento// 
    public String iniciaAlteracaoEvento(int id) {
        evento = evtDao.carregar(id);
     //   idCategoria = evento.getCategoria().getId();
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

    public String iniciaMeusEventos(int id) {
        usuarioLogado = usuarioDao.carregar(id);
        return "meusEventos";
    }
    
    public List<Usuario> getListaUsuariaos() {
        return listaUsuarios;
    }
    
    public List<Inscricao> getListaInscricoesUsuario() {
        //List<Inscricao> lista = new List<InscricaoDao>();
        listaInscricao = inscricaoDao.listar();
        Usuario u = usuarioDao.carregar(usuarioLogado.getId_user());
        return u.getInscricoesEvt();
    }
    
    public String desfazerInscricao(int id_inscricao) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg = null;
        InscricaoDAO dao = new InscricaoDAO();
        dao.excluir(dao.carregar(id_inscricao));
        listaUsuarios = usuarioDao.listar();
        listaInscricao = inscricaoDao.listar();
        
        //private List<Inscricao> listaInscricao;
        
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                                "Inscrição desfeita com sucesso!", "");
        context.addMessage(null, msg);
        return null;
}
   
      public String editarInscricao() {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg;

        Inscricao inscr = new Inscricao();
        inscr.setArquivo(this.getInscricao().getArquivo());
        inscr.setCaminho(this.getInscricao().getCaminho());
        inscricaoDao.incluir(inscr);

        msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Inscrição atualizada com sucesso!", "");
        context.addMessage(null, msg);
        return null;
}
      
    public String iniciaInscricaoEvento(int id_user, int id_evt) {
        //System.out.println(id_user);
        //System.out.println(id_evt);
        String retorno = "inscricaoEvento";
        usuarioLogado = usuarioDao.carregar(id_user);
        evento = evtDao.carregar(id_evt);
            if (evento.isSubmissao()){
                retorno = "inscricaoEventoAnexo";
            }
        return retorno;

    }
    
    public String iniciaEditarInscricaoEvento(int id) {
        inscricao = inscricaoDao.carregar(id);
        return "editarInscricao";

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

                email.setHostName("smtp.gmail.com");
                //email.setSmtpPort(465);
                email.setSslSmtpPort("587");
                email.setStartTLSRequired(true);
                email.setSSLOnConnect(true);
                email.setSSLCheckServerIdentity(true);

                email.setAuthenticator(new DefaultAuthenticator("eventos@gambarra.com.br", "xxxxxxx"));

                try {
                    email.setFrom("eventos@gambarra.com.br");
                    email.setDebug(true);
                    email.setSubject("Senha do sistema de eventos");
                    //email.setMsg("Sua senha é: " + usr.getSenha());

                    email.setHtmlMsg("<html>"
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

            if (this.usuarioLogado.getTrocasenha() == false) {
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
        Date date = new Date();
        if (date.before(evento.getData_inicial_inscricao()) || date.after(evento.getData_final_inscricao()) ){
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
                "Fora do período de inscrição!", "");
            context.addMessage(null, msg);
        } else{
            Inscricao inscr = new Inscricao();
            inscr.setArquivo(this.getInscricao().getArquivo());
            inscr.setCaminho(this.getInscricao().getCaminho());
            inscr.setUsuario(usuarioLogado);
            inscr.setEvento(evento);
            inscricaoDao.incluir(inscr);

            msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Inscrição realizada com sucesso!", "");
            context.addMessage(null, msg);
        }
        return null;
    }
   
    public void subirArquivo(FileUploadEvent event) {

        try {
            String realPath = FacesContext.getCurrentInstance()
                    .getExternalContext().getRealPath("/");
            File file = new File(realPath + "/arquivos/");
            file.mkdirs();
            byte[] arquivo = event.getFile().getContents();
            String caminho = realPath + "/arquivos/"
                    + event.getFile().getFileName();
            //Inscricao inscr = new Inscricao();
            this.getInscricao().setCaminho(caminho);
            this.getInscricao().setArquivo(event.getFile().getFileName());
            FileOutputStream fileOS = new FileOutputStream(caminho);
            fileOS.write(arquivo);
            fileOS.close();
            // Salva neste local: /home/luis/Documentos/Dev/Sistema-Eventos/Produto/Implementacao/Sistema-Evento/build/web//arquivos/
            System.out.println("caminho do arquivo: " + caminho);
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
      
    }
   
     public ArrayList<Categoria> getListaCategorias() {
        CategoriaDAO dao = new CategoriaDAO();
        return dao.listar();
    }

     public void setCategorias(List<Categoria> categorias){
        this.categorias = categorias;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    /**
     * @return the inscricao
     */
    public Inscricao getInscricao() {
        return inscricao;
    }

    /**
     * @param inscricao the inscricao to set
     */
    public void setInscricao(Inscricao inscricao) {
        this.inscricao = inscricao;
    }

   public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    public void upload(FileUploadEvent event) {
        file = event.getFile();

        if (file != null) {

            File imagem = new File("Caminho que voce deseja salvar a imagem", file.getFileName()); 
            try {
                FileOutputStream fos = new FileOutputStream(imagem);
                fos.write(event.getFile().getContents());
                fos.close();

                FacesContext instance = FacesContext.getCurrentInstance();
                instance.addMessage("mensagens", new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        file.getFileName() + " anexado com sucesso", null));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } 
      }
  }
    
}
