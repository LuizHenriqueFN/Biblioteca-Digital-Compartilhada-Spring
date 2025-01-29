package com.projeto.repository;

import com.projeto.model.Livro;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class LivroRepository extends BaseRepository<Livro, Long> {
    private EntityManagerFactory emf;

    public LivroRepository() {
        super(Livro.class);
        this.emf = Persistence.createEntityManagerFactory("BibliotecaDigital");
    }

    public Livro buscarPorTitulo(String titulo) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT l FROM Livro l WHERE l.titulo = :titulo", Livro.class)
                    .setParameter("titulo", titulo)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    public List<Livro> listarDisponiveis() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT l FROM Livro l WHERE l.disponivel = true", Livro.class).getResultList();
        } finally {
            em.close();
        }
    }
}
