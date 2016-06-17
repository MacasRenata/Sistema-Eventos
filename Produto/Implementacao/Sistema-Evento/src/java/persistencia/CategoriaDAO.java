
package persistencia;

import java.util.ArrayList;
import org.hibernate.Session;
import modelo.Categoria;
import org.hibernate.Transaction;
/**
 *
 * @author luis
 */
public class CategoriaDAO {
        private Session sessao;
    
    public CategoriaDAO() {
        sessao = HibernateUtil.getSessionFactory().openSession();
    }
    
    public Categoria carregar(Long id) {
        return (Categoria) sessao.get(Categoria.class, id);
    }
    
    public ArrayList<Categoria> listar() {
        return (ArrayList<Categoria>) sessao.createCriteria(Categoria.class).list();
    }

    public void incluir(Categoria cat) {
       Transaction t = sessao.beginTransaction();
            sessao.save(cat);
            t.commit();
    }
}
