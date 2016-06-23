
package persistencia;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import modelo.Inscricao;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author luis
 */
public class InscricaoDAO {
      private Session sessao;
    
    public InscricaoDAO() {
        sessao = HibernateUtil.getSessionFactory().openSession();
    }
    
      public void incluir(Inscricao inscricao) {
            Transaction t = sessao.beginTransaction();
            sessao.save(inscricao);
            t.commit();
       
      }
      
    
    public void excluir(Inscricao inscricao) {
        Transaction t = sessao.beginTransaction();
        sessao.delete(inscricao);
        t.commit();
    }
    
    public Inscricao carregar(int id) {
        return (Inscricao) sessao.get(Inscricao.class, id);
    }
    
    public List<Inscricao> listar() {
        return sessao.createCriteria(Inscricao.class).list();
    }
    
    public List<Inscricao> listarPorUsuario(int id_usuario) {
        sessao = HibernateUtil.getSessionFactory().openSession();
        Query query = sessao.createQuery("FROM Inscricao WHERE id_usuario = '"+ id_usuario + "'");
        //String hql = "FROM Inscricoes WHERE id_usuario = '"+ id_usuario + "'";
        //Query query = sessao.createQuery(hql);
        List results = query.list();
        //Criteria criteria = sessao.createCriteria(Inscricao.class);
        //criteria.add(Restrictions.eq("usuario", id_usuario));
        return results;
    }
    
    public int qtdInscritosPorEvento(int id) {
        int qtd = 0;	
        sessao = HibernateUtil.getSessionFactory().openSession();
        String hql = "FROM Inscricao WHERE id_evento = '" + id + "'";
        Query query = sessao.createQuery(hql);
        qtd = query.list().size();
        return qtd;
    }
}


