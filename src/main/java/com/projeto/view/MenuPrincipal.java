package com.projeto.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipal extends JFrame {

    public MenuPrincipal() {
        TelaUsuarios telaUsuarios = new TelaUsuarios();
        telaUsuarios.setVisible(false);
        TelaLivros telaLivros = new TelaLivros();
        telaLivros.setVisible(false);
        TelaEmprestimos telaEmprestimos = new TelaEmprestimos();
        telaEmprestimos.setVisible(false);

        setTitle("Biblioteca Digital Compartilhada");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JPanel painelTitulo = new JPanel();
        painelTitulo.add(new JLabel("Bem-vindo à Biblioteca Digital Compartilhada", SwingConstants.CENTER));
        add(painelTitulo, BorderLayout.NORTH);

        JPanel painel = new JPanel();
        add(painel, BorderLayout.CENTER);

        JButton btnUsuarios = new JButton("Gerenciar Usuários");
        btnUsuarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                telaUsuarios.setVisible(true);
            }
        });

        JButton btnLivros = new JButton("Gerenciar Livros");
        btnLivros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                telaLivros.setVisible(true);
            }
        });

        JButton btnEmprestimos = new JButton("Gerenciar Empréstimos");
        btnEmprestimos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                telaEmprestimos.setVisible(true);
            }
        });

        JButton btnSair = new JButton("Sair");
        btnSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        painel.add(btnUsuarios);
        painel.add(btnLivros);
        painel.add(btnEmprestimos);
        painel.add(btnSair);

        setVisible(true);
    }

    public static void main(String[] args) {
        new MenuPrincipal();
    }
}