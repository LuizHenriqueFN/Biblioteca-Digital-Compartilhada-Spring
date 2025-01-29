package com.projeto.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public abstract class BaseRepository<T, ID> {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("BibliotecaDigital");
    private final Class<T> entityClass;

    public BaseRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    private EntityManager createEntityManager() {
        return ENTITY_MANAGER_FACTORY.createEntityManager();
    }

    public T salvar(T entity) {
        EntityManager entityManager = createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Erro ao salvar a entidade", e);
        } finally {
            entityManager.close();
        }
    }

    public T atualizar(T entity) {
        EntityManager entityManager = createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            T mergedEntity = entityManager.merge(entity);
            transaction.commit();
            return mergedEntity;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Erro ao atualizar a entidade", e);
        } finally {
            entityManager.close();
        }
    }

    public void deletar(ID id) {
        EntityManager entityManager = createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            T entity = entityManager.find(entityClass, id);
            if (entity != null) {
                entityManager.remove(entity);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Erro ao deletar a entidade", e);
        } finally {
            entityManager.close();
        }
    }

    public T buscarPorId(ID id) {
        EntityManager entityManager = createEntityManager();
        try {
            return entityManager.find(entityClass, id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar a entidade por ID", e);
        } finally {
            entityManager.close();
        }
    }

    public List<T> buscarTodos() {
        EntityManager entityManager = createEntityManager();
        try {
            return entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar todas as entidades", e);
        } finally {
            entityManager.close();
        }
    }
}
