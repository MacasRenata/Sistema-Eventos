
package persistencia;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author luis
 */
public class HibernateUtil {
    private static SessionFactory sf;
    
    public static SessionFactory getSessionFactory() {
        if (sf == null)
            
            sf = new Configuration().configure().buildSessionFactory();
            return sf;
    }
    
}
