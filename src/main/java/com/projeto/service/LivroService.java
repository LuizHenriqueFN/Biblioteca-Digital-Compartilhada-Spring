package com.projeto.service;

import com.projeto.model.Livro;
import com.projeto.repository.LivroRepository;

import java.util.List;

public class LivroService extends BaseService<Livro, Long> {

    private final LivroRepository livroRepository;

    public LivroService() {
        super(new LivroRepository());
        this.livroRepository = new LivroRepository();
    }

    public List<Livro> listarDisponiveis() {
        return livroRepository.listarDisponiveis();
    }

    public Livro buscarPorTitulo(String titulo) {
        return livroRepository.buscarPorTitulo(titulo);
    }
}
