package com.projeto.view;

import com.projeto.controller.UsuarioController;
import com.projeto.model.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaLogin extends JFrame {
    private UsuarioController usuarioController = new UsuarioController();

    public TelaLogin() {
        MenuPrincipal menuPrincipal = new MenuPrincipal();
        menuPrincipal.setVisible(false);

        setTitle("Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel();
        painel.setLayout(null);
        add(painel);

        JLabel lblLogin = new JLabel("Login:");
        lblLogin.setBounds(50, 30, 80, 25);
        painel.add(lblLogin);

        JTextField campoLogin = new JTextField();
        campoLogin.setBounds(150, 30, 150, 25);
        painel.add(campoLogin);

        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setBounds(50, 70, 80, 25);
        painel.add(lblSenha);

        JPasswordField campoSenha = new JPasswordField();
        campoSenha.setBounds(150, 70, 150, 25);
        painel.add(campoSenha);

        JButton btnLogin = new JButton("Entrar");
        btnLogin.setBounds(150, 110, 100, 25);
        painel.add(btnLogin);

        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setBounds(150, 150, 100, 25);
        painel.add(btnCadastrar);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login = campoLogin.getText();
                String senha = new String(campoSenha.getPassword());

                Usuario usuario = usuarioController.autenticarUsuario(login, senha);
                if (usuario != null) {
                    JOptionPane.showMessageDialog(null, "Bem-vindo, " + usuario.getNome() + "!");
                    dispose();
                    menuPrincipal.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Login ou senha inválidos!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirTelaCadastro();
            }
        });
    }

    private void abrirTelaCadastro() {
        JFrame telaCadastro = new JFrame("Cadastro de Usuário");
        telaCadastro.setSize(400, 300);
        telaCadastro.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        telaCadastro.setLocationRelativeTo(null);
        telaCadastro.setLayout(null);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(50, 30, 80, 25);
        telaCadastro.add(lblNome);

        JTextField campoNome = new JTextField();
        campoNome.setBounds(150, 30, 150, 25);
        telaCadastro.add(campoNome);

        JLabel lblEndereco = new JLabel("Endereço:");
        lblEndereco.setBounds(50, 70, 80, 25);
        telaCadastro.add(lblEndereco);

        JTextField campoEndereco = new JTextField();
        campoEndereco.setBounds(150, 70, 150, 25);
        telaCadastro.add(campoEndereco);

        JLabel lblEmail = new JLabel("E-mail:");
        lblEmail.setBounds(50, 110, 80, 25);
        telaCadastro.add(lblEmail);

        JTextField campoEmail = new JTextField();
        campoEmail.setBounds(150, 110, 150, 25);
        telaCadastro.add(campoEmail);

        JLabel lblLogin = new JLabel("Login:");
        lblLogin.setBounds(50, 150, 80, 25);
        telaCadastro.add(lblLogin);

        JTextField campoLogin = new JTextField();
        campoLogin.setBounds(150, 150, 150, 25);
        telaCadastro.add(campoLogin);

        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setBounds(50, 190, 80, 25);
        telaCadastro.add(lblSenha);

        JPasswordField campoSenha = new JPasswordField();
        campoSenha.setBounds(150, 190, 150, 25);
        telaCadastro.add(campoSenha);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(150, 230, 100, 25);
        telaCadastro.add(btnSalvar);

        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = campoNome.getText();
                String endereco = campoEndereco.getText();
                String email = campoEmail.getText();
                String login = campoLogin.getText();
                String senha = new String(campoSenha.getPassword());

                if (nome.isEmpty() || endereco.isEmpty() || email.isEmpty() || login.isEmpty() || senha.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos!", "Erro",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Usuario usuario = new Usuario(null, nome, endereco, email, login, senha);
                usuario = usuarioController.cadastrarUsuario(usuario);
                if (usuario == null) {
                    JOptionPane.showMessageDialog(null, "Usuário já cadastrado", "Erro",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
                telaCadastro.dispose();
            }
        });

        telaCadastro.setVisible(true);
    }

    public static void main(String[] args) {
        new TelaLogin().setVisible(true);
    }
}
