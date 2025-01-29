package com.projeto.service;

import com.projeto.model.Emprestimo;
import com.projeto.repository.EmprestimoRepository;

import java.time.LocalDate;
import java.util.List;

public class EmprestimoService extends BaseService<Emprestimo, Long> {
    private EmprestimoRepository emprestimoRepository;

    public EmprestimoService() {
        super(new EmprestimoRepository());
        this.emprestimoRepository = new EmprestimoRepository();
    }

    public List<Emprestimo> listarPorUsuario(Long usuarioId) {
        if (usuarioId == null) {
            System.out.println("ID do usuário não pode ser nulo");
            return null;
        }
        return emprestimoRepository.listarPorUsuario(usuarioId);
    }

    public List<Emprestimo> listarPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        if (dataInicio == null || dataFim == null) {
            System.out.println("Datas de início e fim não podem ser nulas");
            return null;
        }
        if (dataInicio.isAfter(dataFim)) {
            System.out.println("A data de início não pode ser posterior à data de fim");
        }
        return emprestimoRepository.listarPorPeriodo(dataInicio, dataFim);
    }
}
