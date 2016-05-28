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
    
    public Usuario verificarLogin(Usuario usuario) throws Exception {
		Usuario us = null;
		try {
			sessao = HibernateUtil.getSessionFactory().openSession();
			String hql = "FROM Usuario WHERE email = '" + usuario.getEmail()
					+ "' and senha = '" + usuario.getSenha() + "'";
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
    
        public Usuario verificarSenha(Usuario usuario) throws Exception {
		Usuario us = null;
		try {
			sessao = HibernateUtil.getSessionFactory().openSession();
			String hql = "FROM Usuario WHERE senha = '" + usuario.getSenha() + "'";
			Query query = sessao.createQuery(hql);

			if (!query.list().isEmpty()) {
				us = (Usuario) query.list().get(0);
                                    int rowCount = 0;
                                    Transaction tx = sessao.beginTransaction();
                                    String sql = "update Usuario set senha = :senha where email = :email";
                                    Query query1 = sessao.createQuery(sql);

                                    query1.setString("senha", usuario.getSenhaNova());
                                    query1.setString("email", usuario.getEmail());

                                    rowCount = query1.executeUpdate();
                                    tx.commit();

                                    sessao.close();

			}
		} catch (Exception e) {
			throw e;
		}
		return us;
    }
        
        public Usuario verificarEmail(Usuario usuario) throws Exception {
		Usuario us = null;
		try {
			sessao = HibernateUtil.getSessionFactory().openSession();
                        String hql = "FROM Usuario WHERE email = '" + usuario.getEmail()
					+ "' and data_nascimento = '" + usuario.getData_nascimento() + "'";
			Query query = sessao.createQuery(hql);

			if (!query.list().isEmpty()) {
				us = (Usuario) query.list().get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return us;
	}
}
    
    