
package persistencia;

import java.util.ArrayList;
import modelo.Evento;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class EventoDAO {
    private Session sessao;
    
    public EventoDAO(){
        sessao = HibernateUtil.getSessionFactory().openSession();
    
    }
    
    public void incluir(Evento evt) {
        Transaction t = sessao.beginTransaction();
        sessao.save(evt);
        t.commit();
    }
    
      public void alterar(Evento evt) {
        Transaction t = sessao.beginTransaction();
        sessao.update(evt);
        t.commit();
    }
      
      
      public ArrayList<Evento> listar() {
          return (ArrayList<Evento>) sessao.createCriteria(Evento.class).list();
 }

      public Evento carregar(int id) {
          return (Evento) sessao.get(Evento.class, id);
      }
      
}