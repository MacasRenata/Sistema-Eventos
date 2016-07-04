package backingbeans;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import modelo.AreaConhecimento;
import modelo.Categoria;
import modelo.Evento;
import modelo.Inscricao;
import modelo.Usuario;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;
import org.primefaces.component.fileupload.FileUpload;
import org.primefaces.context.RequestContext;
import persistencia.EventoDAO;
import persistencia.UsuarioDAO;
import org.primefaces.event.ToggleEvent;
import persistencia.InscricaoDAO;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import persistencia.AreaConhecimentoDAO;
import persistencia.CategoriaDAO;

@ManagedBean
@SessionScoped


public class SisEventosBean {

    private Evento evento = new Evento();
    private Usuario usuario = new Usuario();
    private Usuario usuarioLogado = new Usuario();
    private Inscricao inscricao = new Inscricao();
    private Categoria categoria = new Categoria();
    private AreaConhecimento areaC = new AreaConhecimento();

   

    private List<Evento> listaEventos;
    private List<Usuario> listaUsuarios;
    private List<Inscricao> listaInscricao;
    private List<Categoria> categorias;
    private List<AreaConhecimento> listaAreasC;

    private int idCategoria;
  
    private int[] idAreasC;

    private final EventoDAO evtDao = new EventoDAO();
    private final UsuarioDAO usuarioDao = new UsuarioDAO();
    private final InscricaoDAO inscricaoDao = new InscricaoDAO();
    private final CategoriaDAO categoriaDao = new CategoriaDAO();
    private final AreaConhecimentoDAO areaConhecimentoDao = new AreaConhecimentoDAO();

    public SisEventosBean() {
        listaEventos = evtDao.listar();
        listaUsuarios = usuarioDao.listar();
        listaInscricao = inscricaoDao.listar();
        listaAreasC = areaConhecimentoDao.listar();
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }
   
    public List<AreaConhecimento> getListaAreaConhecimentos() {
        this.listaAreasC = areaConhecimentoDao.listar();
        return listaAreasC;
    }

    public List<Evento> getListaEventos() {
        this.listaEventos = evtDao.listar();
        return listaEventos;
    }

    public List<Evento> getListaEventosComInscricao() {
        listaEventos = evtDao.listarComInscricao();
        return listaEventos;
    }

    public List<Evento> getListaEventosComInscricaoComVagas() {
        List<Evento> listaEventos1;
        Date date = new Date();
        //listaInscricao.clear();
        if (this.verificarSessao()) {
            listaInscricao = inscricaoDao.listarPorUsuario(usuarioLogado.getId_user());
        }

        listaEventos.clear();
        listaEventos1 = evtDao.listarComInscricao();

        ArrayList<Integer> qtd = new ArrayList<Integer>();

        for (int index = 0; index < listaEventos1.size(); index++) {
            qtd.add(index, inscricaoDao.qtdInscritosPorEvento(listaEventos1.get(index).getId_evento()));
        }

        for (int index = 0; index < listaEventos1.size(); index++) {
            if (date.before(listaEventos1.get(index).getData_final())) {
                if (listaEventos1.get(index).isLimite_inscricoes() && listaEventos1.get(index).getQuantidade_inscritos() > qtd.get(index) && date.after(listaEventos1.get(index).getData_inicial_inscricao()) && date.before(listaEventos1.get(index).getData_final_inscricao())) {
                    this.listaEventos.add(listaEventos1.get(index));
                }
            }
        }
        if (this.verificarSessao()) {
            for (int index = 0; index < listaInscricao.size(); index++) {
                for (int x = 0; x < listaEventos.size(); x++) {
                    if (listaEventos.get(x).getId_evento() == listaInscricao.get(x).getEvento().getId_evento()) {
                        this.listaEventos.remove(listaEventos.get(x));
                    }
                }
            }
        }

        return listaEventos;
    }

    public List<Evento> getListaEventosComInscricaoEsgotada() {
        List<Evento> listaEventos1;
        Date date = new Date();
        listaEventos.clear();
        listaEventos1 = evtDao.listarComInscricao();

        ArrayList<Integer> qtd = new ArrayList<Integer>();

        for (int index = 0; index < listaEventos1.size(); index++) {
            qtd.add(index, inscricaoDao.qtdInscritosPorEvento(listaEventos1.get(index).getId_evento()));
        }

        for (int index = 0; index < listaEventos1.size(); index++) {
            if (date.before(listaEventos1.get(index).getData_final())) {
                if (listaEventos1.get(index).isLimite_inscricoes() && listaEventos1.get(index).getQuantidade_inscritos() <= qtd.get(index)) {
                    this.listaEventos.add(listaEventos1.get(index));
                }
            }
        }
        if (this.verificarSessao()) {
            for (int index = 0; index < listaInscricao.size(); index++) {
                for (int x = 0; x < listaEventos.size(); x++) {
                    if (listaEventos.get(x).getId_evento() == listaInscricao.get(index).getEvento().getId_evento()) {
                        this.listaEventos.remove(listaEventos.get(x));
                    }
                }
            }
        }

        return listaEventos;
    }

    public List<Evento> getListaEventosForaDasInscricoes() {
        List<Evento> listaEventos1;
        Date date = new Date();
        listaEventos.clear();
        listaEventos1 = evtDao.listarComInscricao();

        ArrayList<Integer> qtd = new ArrayList<Integer>();

        for (int index = 0; index < listaEventos1.size(); index++) {
            qtd.add(index, inscricaoDao.qtdInscritosPorEvento(listaEventos1.get(index).getId_evento()));
        }

        for (int index = 0; index < listaEventos1.size(); index++) {
            if (date.before(listaEventos1.get(index).getData_final())) {
                if (listaEventos1.get(index).isLimite_inscricoes() && listaEventos1.get(index).getQuantidade_inscritos() > qtd.get(index)) {
                    if (date.before(listaEventos1.get(index).getData_inicial_inscricao()) || date.after(listaEventos1.get(index).getData_final_inscricao())) {
                        this.listaEventos.add(listaEventos1.get(index));
                    }
                }
            }
        }
        return listaEventos;
    }

    public List<Evento> getListaEventosJaRealizados() {
        List<Evento> listaEventos1;
        Date date = new Date();
        listaEventos.clear();
        listaEventos1 = evtDao.listar();

        for (int index = 0; index < listaEventos1.size(); index++) {
            if (date.after(listaEventos1.get(index).getData_final())) {
                this.listaEventos.add(listaEventos1.get(index));
            }
        }
        return listaEventos;
    }

    public List<Evento> getListaEventosSemInscricao() {
        listaEventos = evtDao.listarSemInscricao();
        List<Evento> listaEventos1;
        listaEventos1 = evtDao.listarSemInscricao();
        Date date = new Date();

        for (int index = 0; index < listaEventos.size(); index++) {
            if (date.after(listaEventos.get(index).getData_final())) {
                this.listaEventos.remove(listaEventos.get(index));
            }
        }
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

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int[] getIdAreasC() {
        return idAreasC;
    }

    public void setIdAreasC(int[] idAreasC) {
        this.idAreasC = idAreasC;
    }

    public String incluirEvento() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg;
        //  CategoriaDAO categoriaDao = new CategoriaDAO();
        evento.setCategoria(categoriaDao.carregar(idCategoria));
        
        for (int id : idAreasC) {
            evento.adicionaAreasC(areaConhecimentoDao.carregar(id));
        }
        evtDao.incluir(evento);
        listaEventos = evtDao.listar();
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Evento criado com sucesso!", "");
        // evento = new Evento();
        context.addMessage(null, msg);
        
        // Gravar arquivo html de evento criado dir\eventos
        //trocar o diretorio
        String caminho1 = "C:\\Users\\Maçãs2\\Documents\\GitHub\\Sistema-Eventos\\Produto\\Implementacao\\Sistema-Evento\\web\\eventos/";

        //writer2 = new PrintWriter(new FileWriter(fileStem + ".html"));
        File file = new File(caminho1 + getEvento().getTitulo() + ".html");
        file.getParentFile().mkdirs();

        PrintWriter printWriter = new PrintWriter(file);

        //for (File files : File.listRoots()){
        printWriter.print("<html>");
        printWriter.print("<head>");
        printWriter.print("<div background-color:black;\n"
                + "    color:white;\n"
                + "    text-align:center;\n"
                + "    padding:5px>");
        printWriter.print("<title>Sistema Evento</title>");
        printWriter.print(evento.getImagem());
        printWriter.print("</div>");
        printWriter.print("</head>");
        printWriter.print("<body>");
        printWriter.print("<div line-height:30px;\n"
                + "    background-color:#eeeeee;\n"
                + "    height:300px;\n"
                + "    width:100px;\n"
                + "    float:left;\n"
                + "    padding:5px>");
        printWriter.print(evento.getId_evento());
        printWriter.print(evento.getTitulo());
        printWriter.print("</div>");
        printWriter.print("<div width:350px;\n"
                + "    float:left;\n"
                + "    padding:10px;>");
        printWriter.print("<h1> Informações sobre o Evento </h1>");
        printWriter.print("<p> Data Inicial:");
        printWriter.print(evento.getData_inicial());
        printWriter.print("</p>");
        printWriter.print("</br>");
        printWriter.print("<p> Inicio das inscrições:");
        printWriter.print(evento.getData_inicial_inscricao());
        printWriter.print("</p>");
        printWriter.print("</br>");
        printWriter.print("<p> Encerramento das inscrições:");
        printWriter.print(evento.getData_final_inscricao());
        printWriter.print("</p>");
        printWriter.print("</br>");
        printWriter.print("<p> Horario de inicio do evento:");
        printWriter.print(evento.getHorario_inicio());
        printWriter.print("</p>");
        printWriter.print("</br>");
        printWriter.print("<p> Horario do fim do evento");
        printWriter.print(evento.getHorario_fim());
        printWriter.print("</p>");
        printWriter.print("</br>");
        printWriter.print("<p> Descrição do Evento:");
        printWriter.print(evento.getDescricao_evento());
        printWriter.print("</p>");
        printWriter.print("</br>");
        printWriter.print("<p> Local do Evento:");
        printWriter.print(evento.getLocal_evento());
        printWriter.print("</p>");
        printWriter.print("</br>");
        printWriter.print("<p> Categoria:");
        printWriter.print(evento.getCategoria());
        printWriter.print("</p>");
        printWriter.print("</br>");
        printWriter.print("<p> Categoria:");
        //    printWriter.print(evento.getArea_evento());
        printWriter.print("</p>");
        printWriter.print("</br>");
        printWriter.print("</div>");

        printWriter.print("<div background-color:black;\n"
                + "    color:white;\n"
                + "    clear:both;\n"
                + "    text-align:center;\n"
                + "    padding:5px; >");
        printWriter.print("Copyright © sistemaeventos.com");
        printWriter.print("</div>");

        printWriter.print("</body>");
        printWriter.print("</html>");

        //o método flush libera a escrita no arquivo
        printWriter.flush();

        //No final precisamos fechar o arquivo
        printWriter.close();

        
      
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
    public String incluirUsuario() throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg;
        UsuarioDAO usuDAO = new UsuarioDAO();

        String encript = DigestUtils.shaHex(this.usuario.getSenha());

        this.usuario.setSenha(encript);
        this.usuario.setSenhaNova(encript);
        usuDAO.incluir(this.usuario);
        InetAddress ia = null;
        try {
            ia = InetAddress.getLocalHost();
            HtmlEmail email = new HtmlEmail();

            email.setHostName("smtp.gmail.com");
            //email.setSmtpPort(465);
            email.setSslSmtpPort("587");
            email.setStartTLSRequired(true);
            email.setSSLOnConnect(true);
            email.setSSLCheckServerIdentity(true);

            email.setAuthenticator(new DefaultAuthenticator("eventos@gambarra.com.br", "eventos2016"));

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
                        + "<p>Olá " + this.usuario.getNome()
                        + "</p>"
                        + "<p>Bem Vindo ao Sistema de Eventos - DEV2"
                        + "</p>"
                        + "<p>Para acessar o sistema clique no link "
                        + "<a href=\"http://" + ia.getHostAddress() + ":8080/Sistema-Evento/"
                        + "</p>"
                        + "</div>"
                        + "<p> Antenciosamente <br/> Sistema de Eventos </p> "
                        + "</body>"
                        + "</html>");
                email.addTo(this.usuario.getEmail());
                email.send();

            } catch (EmailException e) {
                e.printStackTrace();
            }

            msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Usuario cadastrado com Sucesso!", "");
            // usuario = new Usuario();
            context.addMessage(null, msg);
            usuario = new Usuario();
            return "usuario";
        } catch (Exception e) {
            throw e;
        }
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
        //Usuario u = usuarioDao.carregar(usuarioLogado.getId_user());
        listaInscricao = inscricaoDao.listarPorUsuario(usuarioLogado.getId_user());
        return listaInscricao;
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

        Inscricao inscr = inscricao;

        inscr.setArquivo(this.getInscricao().getArquivo());
        inscr.setCaminho(this.getInscricao().getCaminho());
        if (evento.isSubmissao() && this.getInscricao().getArquivo() == null) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Para garantir sua inscrição, você tem até " + evento.getData_final_inscricao() + " para enviar seu arquivo", "");
            inscr.setOuvinte(true);
            context.addMessage(null, msg);
        } else {
            inscr.setOuvinte(false);

        }
        inscricaoDao.alterar(inscr);
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Inscrição atualizada com sucesso!", "");
        context.addMessage(null, msg);
        return "meusEventos";
    }

    public String iniciaInscricaoEvento(int id_user, int id_evt) {
        System.out.println(id_user);
        System.out.println(id_evt);
        String retorno = "incluirUsuario";
        if (id_user != 0) {
            retorno = "inscricaoEvento";
            usuarioLogado = usuarioDao.carregar(id_user);
            evento = evtDao.carregar(id_evt);
            if (evento.isSubmissao()) {
                retorno = "inscricaoEventoAnexo";
            }
        }
        return retorno;
    }

    public String iniciaEditarInscricaoEvento(int id) {
        inscricao = inscricaoDao.carregar(id);
        String retorno = "editarInscricao";
        if (id != 0) {
            retorno = "editarInscricao";
            if (inscricao.getEvento().isSubmissao()) {
                retorno = "editarInscricaoAnexo";
            }
        }
        return retorno;

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

        context.addMessage(null, msg);

        return "listaUsuarios";
    }

    public String alterarSenhaUsuario() throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg;
        try {

            if (!this.usuario.getSenhaNova().equals(this.usuario.getSenha())) {
                String encript = DigestUtils.shaHex(this.usuario.getSenhaNova());
                this.usuario.setSenha(encript);
                usuarioDao.alterar(usuario);
                listaUsuarios = usuarioDao.listar();
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Senha do usuario alterada com Sucesso!", "");
                context.addMessage(null, msg);
                return "listaUsuarios";
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Senha do usuario nao alterada!", "");
                context.addMessage(null, msg);
                return null;
            }

        } catch (Exception e) {
            throw e;
        }
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
                this.usuarioLogado = us;
                resultado = "index";
                usuario = this.usuarioLogado;

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

        if (this.usuarioLogado.getAdmin()) {
            estado = true;
        } else {
            estado = false;
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
        usuarioLogado = new Usuario();
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
            us = usuDAO.verificarEmail(this.usuarioLogado.getEmail());
            if (us != null) {

                Usuario usr = us;

                HtmlEmail email = new HtmlEmail();

                email.setHostName("smtp.gmail.com");
                //email.setSmtpPort(465);
                email.setSslSmtpPort("587");
                email.setStartTLSRequired(true);
                email.setSSLOnConnect(true);
                email.setSSLCheckServerIdentity(true);

                email.setAuthenticator(new DefaultAuthenticator("eventos@gambarra.com.br", "eventos2016"));

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
        boolean jaInscrito = false;

        for (int index = 0; index < listaInscricao.size(); index++) {
            if (listaInscricao.get(index).getUsuario().getId_user() == usuarioLogado.getId_user() && listaInscricao.get(index).getEvento().getId_evento() == evento.getId_evento()) {
                jaInscrito = true;
            }
            //qtd.add(index, inscricaoDao.qtdInscritosPorEvento(listaEventos1.get(index).getId_evento()));
        }

        int qtd = inscricaoDao.qtdInscritosPorEvento(evento.getId_evento());

        if (evento.isLimite_inscricoes() && evento.getQuantidade_inscritos() <= qtd) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Capacidade máxima de inscrições atingida!", "");
            context.addMessage(null, msg);
        } else if (date.before(evento.getData_inicial_inscricao()) || date.after(evento.getData_final_inscricao())) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Fora do período de inscrição!", "");
            context.addMessage(null, msg);
        } else if (jaInscrito) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Voce ja esta inscrito neste evento!", "");
            context.addMessage(null, msg);
        } else {
            Inscricao inscr = new Inscricao();
            inscr.setArquivo(this.getInscricao().getArquivo());
            inscr.setCaminho(this.getInscricao().getCaminho());
            inscr.setUsuario(usuarioLogado);
            inscr.setEvento(evento);
            if (evento.isSubmissao() && this.getInscricao().getArquivo() == null) {
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "Para garantir sua inscrição, você tem até " + evento.getData_final_inscricao() + " para enviar seu arquivo", "");
                inscr.setOuvinte(true);
                context.addMessage(null, msg);
            }

            inscricaoDao.incluir(inscr);

            msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Inscrição realizada com sucesso!", "");
            context.addMessage(null, msg);

        }
        return "meusEventos";
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

    //Metodo alternativo
    public void sobeArquivo(FileUploadEvent event) {

        String caminho;

        try {

            if (System.getProperties().get("os.name").toString().trim().equalsIgnoreCase("Linux")) {
                caminho = "/home/luis/Documentos/Dev/Sistema-Eventos/Arquivos/";
            } else {
                caminho = "c://files//Documentos/Dev/Sistema-Eventos/Arquivos//";
            }

            File file = new File(caminho);
            file.mkdirs();

            byte[] arquivo = event.getFile().getContents();
            String arch = caminho + event.getFile().getFileName();

            this.getInscricao().setCaminho(caminho);
            this.getInscricao().setArquivo(event.getFile().getFileName());

            FileOutputStream fos = new FileOutputStream(arch);
            fos.write(arquivo);
            fos.close();

            System.out.println("O caminho do arquivo: " + caminho);

            FacesMessage message = new FacesMessage("O arquivo", file.getName() + " foi enviado.");
            FacesContext.getCurrentInstance().addMessage(null, message);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Categoria> getListaCategorias() {
        CategoriaDAO dao = new CategoriaDAO();
        return dao.listar();
    }

    public void setCategorias(List<Categoria> categorias) {
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
/*
    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void upload(FileUploadEvent event) {   // incluir imagem ao evento
        
         String caminhoImagem;
 

        try {

            if (System.getProperties().get("os.name").toString().trim().equalsIgnoreCase("Linux")) {
                caminhoImagem = "/home/usr/Documentos/Dev/Sistema-Eventos/IMG/";
            } else {
                caminhoImagem = "c://files//Documentos/Dev/Sistema-Eventos/IMG//";
            }

            File imagem = new File(caminhoImagem + getEvento().getTitulo());
            imagem.mkdirs();

            byte[] arquivo = event.getFile().getContents();
            String arch = caminhoImagem + event.getFile().getFileName();

            this.getEvento().setImagem(caminhoImagem);
            this.getEvento().setImagem(event.getFile().getFileName());

            FileOutputStream img = new FileOutputStream(arch);
            img.write(arquivo);
            img.close();

            System.out.println("O caminho do arquivo: " + caminhoImagem + imagem);

                FacesContext instance = FacesContext.getCurrentInstance();
                instance.addMessage("mensagens", new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        imagem.getName() + " anexado com sucesso", null));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    */
       // metodo para upload de imagem
  	     public void uploadImagem(FileUploadEvent event) {
    
        try {
            String realPath = FacesContext.getCurrentInstance()
                    .getExternalContext().getRealPath("/");
            File file = new File(realPath + "/imagens/");
            file.mkdirs();
            byte[] imagem = event.getFile().getContents();
            String caminho3 = realPath + "/imagens/"
                    + event.getFile().getFileName();
            
            this.getEvento().setImagem(caminho3);
            this.getEvento().setImagem(event.getFile().getFileName());
            FileOutputStream fileOS = new FileOutputStream(caminho3);
            fileOS.write(imagem);
            fileOS.close();
            // Salva neste local: /home/user/Documentos/Dev/Sistema-Eventos/Produto/Implementacao/Sistema-Evento/build/web//arquivos/
            System.out.println("caminho da Imagem: " + caminho3);

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
  
    }

    //Metodo alternativo upload de imagem Linux
    public void uploadImagem2(FileUploadEvent event) {

        String caminho3;

        try {

            if (System.getProperties().get("os.name").toString().trim().equalsIgnoreCase("Linux")) {
                caminho3 = "/home/usr/Documentos/Dev/Sistema-Eventos/Imagens/";
            } else {
                caminho3 = "c://files//Documentos/Dev/Sistema-Eventos/Imagens//";
            }

            File file = new File(caminho3);
            file.mkdirs();

            byte[] imagem = event.getFile().getContents();
            String arch = caminho3 + event.getFile().getFileName();

            this.getEvento().setImagem(caminho3);
            this.getEvento().setImagem(event.getFile().getFileName());

            FileOutputStream fos = new FileOutputStream(arch);
            fos.write(imagem);
            fos.close();

            System.out.println("O caminho da Imagem: " + caminho3);

            FacesMessage message = new FacesMessage("A imagem", file.getName() + " foi enviada.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            
         
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    
    
}
    


