
package persistencia;

import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import modelo.Evento;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class EventoDAO {
    private Session sessao;
    
    public EventoDAO(){
        sessao = HibernateUtil.getSessionFactory().openSession();
    
    }
    
    public void incluir(Evento evt) {
        if(evt.getData_final().after(evt.getData_inicial()) || evt.getData_final().equals(evt.getData_inicial()) ){
            Transaction t = sessao.beginTransaction();
            sessao.save(evt);
            t.commit();
        } else {
            FacesContext.getCurrentInstance().addMessage("incluirEvento",new FacesMessage("Data final do evento inferir a inicial"));
        }
        
    }
    
      public void alterar(Evento evt) {
        if(evt.getData_final().after(evt.getData_inicial()) || evt.getData_final().equals(evt.getData_inicial()) ){
            Transaction t = sessao.beginTransaction();
            sessao.update(evt);
            t.commit();
            sessao.flush();
        } else {
            FacesContext.getCurrentInstance().addMessage("alterarEvento",new FacesMessage("Data final do evento inferir a inicial"));
        }
        
            
    }
      
      
      public ArrayList<Evento> listar() {
          return (ArrayList<Evento>) sessao.createCriteria(Evento.class).list();
 }

      public Evento carregar(int id) {
          return (Evento) sessao.get(Evento.class, id);
      }
      
}