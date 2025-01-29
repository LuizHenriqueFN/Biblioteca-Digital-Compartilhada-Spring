package com.projeto.service;

import com.projeto.repository.BaseRepository;

import java.util.List;

public abstract class BaseService<T, ID> {

    private final BaseRepository<T, ID> repository;

    public BaseService(BaseRepository<T, ID> repository) {
        this.repository = repository;
    }

    public T salvar(T entity) {
        return repository.salvar(entity);
    }

    public T atualizar(T entity) {
        return repository.atualizar(entity);
    }

    public void deletar(ID id) {
        repository.deletar(id);
    }

    public T buscarPorId(ID id) {
        return repository.buscarPorId(id);
    }

    public List<T> buscarTodos() {
        return repository.buscarTodos();
    }
}
