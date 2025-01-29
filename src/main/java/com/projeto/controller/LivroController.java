package com.projeto.controller;

import com.projeto.model.Livro;
import com.projeto.service.LivroService;

import java.util.List;

public class LivroController {

    private final LivroService livroService;

    public LivroController() {
        this.livroService = new LivroService();
    }

    public Livro salvarLivro(Livro livro) {
        return livroService.salvar(livro);
    }

    public List<Livro> obterLivros() {
        return livroService.buscarTodos();
    }

    public Livro buscarLivroPorId(Long id) {
        return livroService.buscarPorId(id);
    }

    public Livro buscarLivroPorTitulo(String titulo) {
        return livroService.buscarPorTitulo(titulo);
    }

    public List<Livro> listarLivrosDisponiveis() {
        return livroService.listarDisponiveis();
    }

    public void atualizarLivro(Livro livro) {
        livroService.atualizar(livro);
    }

    public void deletarLivro(Long id) {
        livroService.deletar(id);
    }
}
