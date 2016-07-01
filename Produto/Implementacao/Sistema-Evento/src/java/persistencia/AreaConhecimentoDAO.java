
package persistencia;

import java.util.ArrayList;
import modelo.AreaConhecimento;
import org.hibernate.Session;
import org.hibernate.Transaction;
/**
 *
 * @author luis
 */
public class AreaConhecimentoDAO {
        private Session sessao;
    
    public AreaConhecimentoDAO() {
        sessao = HibernateUtil.getSessionFactory().openSession();
    }
    
    public AreaConhecimento carregar(int id) {
        return (AreaConhecimento) sessao.get(AreaConhecimento.class, id);
    }
    
    public ArrayList<AreaConhecimento> listar() {
        return (ArrayList<AreaConhecimento>) sessao.createCriteria(AreaConhecimento.class).list();
    }

    public void incluir(AreaConhecimento areaC) {
       Transaction t = sessao.beginTransaction();
            sessao.save(areaC);
            t.commit();
        
    }
}
  
