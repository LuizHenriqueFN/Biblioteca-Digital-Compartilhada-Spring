package com.projeto.repository;

import com.projeto.model.Emprestimo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.time.LocalDate;
import java.util.List;

public class EmprestimoRepository extends BaseRepository<Emprestimo, Long> {
    private EntityManagerFactory emf;

    public EmprestimoRepository() {
        super(Emprestimo.class);
        this.emf = Persistence.createEntityManagerFactory("BibliotecaDigital");
    }

    public List<Emprestimo> listarPorUsuario(Long usuarioId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                    "SELECT e FROM Emprestimo e WHERE e.usuarioSolicitante.id = :usuarioId OR e.usuarioProprietario.id = :usuarioId",
                    Emprestimo.class)
                    .setParameter("usuarioId", usuarioId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Emprestimo> listarPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        EntityManager em = emf.createEntityManager();
        try {
            return em
                    .createQuery("SELECT e FROM Emprestimo e WHERE e.dataEmprestimo BETWEEN :dataInicio AND :dataFim",
                            Emprestimo.class)
                    .setParameter("dataInicio", dataInicio)
                    .setParameter("dataFim", dataFim)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
