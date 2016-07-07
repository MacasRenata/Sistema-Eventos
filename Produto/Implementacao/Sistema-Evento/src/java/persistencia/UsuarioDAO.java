/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.util.ArrayList;
import modelo.Usuario;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UsuarioDAO {

    private Session sessao;

    public UsuarioDAO() {
        sessao = HibernateUtil.getSessionFactory().openSession();

    }

    public void incluir(Usuario usr) {
        Transaction t = sessao.beginTransaction();
        sessao.save(usr);
        t.commit();
    }

    public void alterar(Usuario usr) {
        Transaction t = sessao.beginTransaction();
        sessao.clear();
        sessao.update(usr);
        t.commit();
        sessao.flush();

    }

    public void excluir(int id_usuario) {
        Transaction t = sessao.beginTransaction();
        sessao.delete(carregar(id_usuario));
        t.commit();

    }
    
    public Usuario verificarLogin(Usuario usuarioLogado) throws Exception {
		Usuario us = null;
		try {
			//sessao = HibernateUtil.getSessionFactory().openSession();
			String hql = "FROM Usuario WHERE email = '" + usuarioLogado.getEmail()
					+ "' and senha = '" + usuarioLogado.getSenha() + "'";
			Query query = sessao.createQuery(hql);

			if (!query.list().isEmpty()) {
				us = (Usuario) query.list().get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return us;
	}
    public ArrayList<Usuario> listar() {
        return (ArrayList<Usuario>) sessao.createCriteria(Usuario.class).list();
    }

    public Usuario carregar(int id) {
        return (Usuario) sessao.get(Usuario.class, id);
    }
    
    public Usuario carregarMeusDados(int id) {
        return (Usuario) sessao.get(Usuario.class, id);
    }
    
    public Usuario buscarUsuario(String email) {
        //sessao = HibernateUtil.getSessionFactory().openSession();
        Query query = sessao.createQuery("from Usuario u where u.email = :email");
        Usuario u = (Usuario) query.setString("email", email).uniqueResult();

        return u;
    }
    
    
        public Usuario verificarSenha(Usuario usuarioLogado) throws Exception {
		Usuario us = null;
		try {
			//sessao = HibernateUtil.getSessionFactory().openSession();
			String hql = "FROM Usuario WHERE senha = '" + usuarioLogado.getSenha() + "'";
			Query query = sessao.createQuery(hql);

			if (!query.list().isEmpty()) {
				us = (Usuario) query.list().get(0);
                                    int rowCount = 0;
                                    Transaction tx = sessao.beginTransaction();
                                    String sql = "update Usuario set senha = :senha where email = :email";
                                    Query query1 = sessao.createQuery(sql);

                                    query1.setString("senha", usuarioLogado.getSenhaNova());
                                    query1.setString("email", usuarioLogado.getEmail());
                                    //query1.setBoolean("trocasenha", usuarioLogado.getTrocasenha());

                                    rowCount = query1.executeUpdate();
                                    tx.commit();

                                    sessao.close();

			}
		} catch (Exception e) {
			throw e;
		}
		return us;
    }
        
        public Usuario verificarEmail(String email) {
		Usuario us = null;
		
			//sessao = HibernateUtil.getSessionFactory().openSession();
                        String hql = "FROM Usuario WHERE email = '" + email + "'";
			Query query = sessao.createQuery(hql);

			if (!query.list().isEmpty()) {
				us = (Usuario) query.list().get(0);
			
                        }
		return us;
	}
        
        	    public Usuario verificarAdmin(Usuario usuario) throws Exception {
		Usuario us = null;
		try {
			//sessao = HibernateUtil.getSessionFactory().openSession();
                        String hql = "FROM Usuario WHERE admin = '" + usuario.getAdmin() + "'";
			Query query = sessao.createQuery(hql);

			if (!query.list().isEmpty()) {
				us = (Usuario) query.list().get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return us;
	}
        
        public Usuario recuperarSenha(Usuario usuarioLogado) throws Exception {
            Usuario us = null;
            try {
			sessao = HibernateUtil.getSessionFactory().openSession();
                                    int rowCount = 0;
                                    Transaction tx = sessao.beginTransaction();
                                    String sql = "update Usuario set trocasenha = :trocasenha where email = :email";
                                    Query query1 = sessao.createQuery(sql);

                                    query1.setBoolean("trocasenha", usuarioLogado.getTrocasenha());
                                    query1.setString("email", usuarioLogado.getEmail());
                                    
                                    

                                    rowCount = query1.executeUpdate();
                                    tx.commit();

                                    sessao.close();
                                    
            } catch (Exception e) {
			throw e;
            }
            return us; 
       }
}
    
    