package persistencia;

import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import modelo.Evento;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class EventoDAO {

    private Session sessao;

    public EventoDAO() {
        sessao = HibernateUtil.getSessionFactory().openSession();

    }

    public void incluir(Evento evt) {
        if (evt.getData_final().after(evt.getData_inicial()) || evt.getData_final().equals(evt.getData_inicial())) {
            Transaction t = sessao.beginTransaction();
            sessao.save(evt);
            t.commit();
        } else {
            FacesContext.getCurrentInstance().addMessage("incluirEvento", new FacesMessage("Data final do evento não pode ser antes do inicío do evento"));
        }

    }

    public void alterar(Evento evt) {
        if (evt.getData_final().after(evt.getData_inicial()) || evt.getData_final().equals(evt.getData_inicial())) {
            Transaction t = sessao.beginTransaction();
            sessao.clear();
            sessao.update(evt);
            t.commit();
            sessao.flush();
        } else {
            FacesContext.getCurrentInstance().addMessage("alterarEvento", new FacesMessage("Data final do evento inferior a inicial"));
        }

    }

    public ArrayList<Evento> listar() {
        return (ArrayList<Evento>) sessao.createCriteria(Evento.class).list();
    }

    public Evento carregar(int id) {
        return (Evento) sessao.get(Evento.class, id);
    }
    
    public List<Evento> listarComInscricao() {
        sessao = HibernateUtil.getSessionFactory().openSession();
        Query query = sessao.createQuery("FROM Evento WHERE inscricoes = '1'");
        List results = query.list();
        return results;
    }
    
    public List<Evento> listarSemInscricao() {
        sessao = HibernateUtil.getSessionFactory().openSession();
        Query query = sessao.createQuery("FROM Evento WHERE inscricoes = '0'");
        List results = query.list();
        return results;
    }
    
    public List<Evento> listarComInscricaoEncerrada() {
        sessao = HibernateUtil.getSessionFactory().openSession();
        Query query = sessao.createQuery("FROM Evento WHERE inscricoes = '1'");
        List results = query.list();
        return results;
    }

}
