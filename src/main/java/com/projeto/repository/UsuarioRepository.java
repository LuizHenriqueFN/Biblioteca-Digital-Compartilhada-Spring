package com.projeto.repository;

import com.projeto.model.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class UsuarioRepository extends BaseRepository<Usuario, Long> {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("BibliotecaDigital");

    public UsuarioRepository() {
        super(Usuario.class);
    }

    public List<Usuario> buscarPorNome(String nome) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM Usuario u WHERE u.nome LIKE :nome", Usuario.class)
                    .setParameter("nome", "%" + nome + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Usuario buscarPorLogin(String login) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM Usuario u WHERE u.login = :login", Usuario.class)
                    .setParameter("login", login)
                    .getSingleResult();
        } catch (Exception e) {
            return null; // Retorna null caso o login não seja encontrado
        } finally {
            em.close();
        }
    }
}
