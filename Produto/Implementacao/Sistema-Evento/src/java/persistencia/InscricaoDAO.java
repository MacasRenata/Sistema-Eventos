
package persistencia;

import java.util.List;
import modelo.Inscricao;
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
}


