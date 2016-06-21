
package persistencia;

import java.util.List;
import modelo.Inscricao;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

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
        Query query = sessao.createQuery("FROM inscricoes WHERE id_usuario = '"+ id_usuario + "'");
        //String hql = "FROM Inscricoes WHERE id_usuario = '"+ id_usuario + "'";
        //Query query = sessao.createQuery(hql);
        List results = query.list();
        //Criteria criteria = sessao.createCriteria(Inscricao.class);
        //criteria.add(Restrictions.eq("usuario", id_usuario));
        return results;
        
        
    }
}


