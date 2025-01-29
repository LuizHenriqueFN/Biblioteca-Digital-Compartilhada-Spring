package com.projeto.controller;

import com.projeto.model.Emprestimo;
import com.projeto.service.EmprestimoService;

import java.util.List;

public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    public EmprestimoController() {
        this.emprestimoService = new EmprestimoService();
    }

    public void salvarEmprestimo(Emprestimo emprestimo) {
        emprestimoService.salvar(emprestimo);
    }

    public List<Emprestimo> listarEmprestimos() {
        return emprestimoService.buscarTodos();
    }

    public List<Emprestimo> listarEmprestimosPorUsuario(Long usuarioId) {
        return emprestimoService.listarPorUsuario(usuarioId);
    }

    public void atualizarEmprestimo(Emprestimo emprestimo) {
        emprestimoService.atualizar(emprestimo);
    }

    public void deletarEmprestimo(Long id) {
        emprestimoService.deletar(id);
    }
}
