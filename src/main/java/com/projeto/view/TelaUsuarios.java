package com.projeto.view;

import com.projeto.controller.EmprestimoController;
import com.projeto.controller.UsuarioController;
import com.projeto.model.Emprestimo;
import com.projeto.model.Usuario;
import com.projeto.util.NonEditableTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class TelaUsuarios extends JFrame {

    private UsuarioController usuarioController = new UsuarioController();
    private EmprestimoController emprestimoController = new EmprestimoController();
    private DefaultTableModel tabelaModelo;
    private JTable tabelaUsuarios;
    private JTextField campoFiltroNome, campoFiltroEndereco, campoFiltroEmail, campoFiltroLogin;
    private TableRowSorter<DefaultTableModel> sorter;
    private JTextField campoNome, campoEndereco, campoEmail, campoLogin;
    private JPasswordField campoSenha;
    private JButton btnSalvar, btnEditar, btnDeletar;
    private Usuario usuarioSelecionado;
    private Boolean ehEdicao = false;

    public TelaUsuarios() {
        setTitle("Gerenciamento de Usuários");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel painelPrincipal = new JPanel(new BorderLayout());
        add(painelPrincipal, BorderLayout.CENTER);

        JPanel painelFormulario = new JPanel(new GridLayout(7, 2, 10, 10));
        painelPrincipal.add(painelFormulario, BorderLayout.NORTH);

        campoNome = new JTextField();
        campoEndereco = new JTextField();
        campoEmail = new JTextField();
        campoLogin = new JTextField();
        campoSenha = new JPasswordField();

        painelFormulario.add(new JLabel("Nome:"));
        painelFormulario.add(campoNome);
        painelFormulario.add(new JLabel("Endereço:"));
        painelFormulario.add(campoEndereco);
        painelFormulario.add(new JLabel("Email:"));
        painelFormulario.add(campoEmail);
        painelFormulario.add(new JLabel("Login:"));
        painelFormulario.add(campoLogin);
        painelFormulario.add(new JLabel("Senha:"));
        painelFormulario.add(campoSenha);

        btnSalvar = new JButton("Salvar");
        painelFormulario.add(btnSalvar);

        btnEditar = new JButton("Editar");
        painelFormulario.add(btnEditar);
        btnEditar.setEnabled(false);

        btnDeletar = new JButton("Deletar");
        painelFormulario.add(btnDeletar);
        btnDeletar.setEnabled(false);

        JButton btnVoltar = new JButton("Voltar");
        painelFormulario.add(btnVoltar);
        btnVoltar.addActionListener(e -> setVisible(false));

        JPanel painelFiltroETabela = new JPanel(new BorderLayout());
        painelPrincipal.add(painelFiltroETabela, BorderLayout.CENTER);

        JPanel painelFiltro = new JPanel(new GridLayout(2, 4, 10, 10));
        painelFiltro.setBorder(BorderFactory.createTitledBorder("Filtrar Usuários"));

        campoFiltroNome = new JTextField();
        campoFiltroEndereco = new JTextField();
        campoFiltroEmail = new JTextField();
        campoFiltroLogin = new JTextField();

        painelFiltro.add(new JLabel("Nome:"));
        painelFiltro.add(campoFiltroNome);
        painelFiltro.add(new JLabel("Endereço:"));
        painelFiltro.add(campoFiltroEndereco);
        painelFiltro.add(new JLabel("E-mail:"));
        painelFiltro.add(campoFiltroEmail);
        painelFiltro.add(new JLabel("Login:"));
        painelFiltro.add(campoFiltroLogin);

        painelFiltroETabela.add(painelFiltro, BorderLayout.NORTH);

        tabelaModelo = new NonEditableTableModel(new Object[] { "ID", "Nome", "Endereço", "Email", "Login" }, 0);
        tabelaUsuarios = new JTable(tabelaModelo);
        tabelaUsuarios.getTableHeader().setReorderingAllowed(false);
        sorter = new TableRowSorter<>(tabelaModelo);
        tabelaUsuarios.setRowSorter(sorter);
        JScrollPane scrollPane = new JScrollPane(tabelaUsuarios);
        painelFiltroETabela.add(scrollPane, BorderLayout.CENTER);

        configurarFiltros();

        btnSalvar.addActionListener(e -> {
            String nome = campoNome.getText();
            String endereco = campoEndereco.getText();
            String email = campoEmail.getText();
            String login = campoLogin.getText();
            String senha = new String(campoSenha.getPassword());

            if (nome.isEmpty() || endereco.isEmpty() || email.isEmpty() || login.isEmpty()
                    || (senha.isEmpty() && !ehEdicao)) {
                JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos!", "Erro",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (usuarioSelecionado == null) {
                if (senha.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "A senha é obrigatória para cadastrar um novo usuário!", "Erro",
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
            } else {
                usuarioSelecionado.setNome(nome);
                usuarioSelecionado.setEndereco(endereco);
                usuarioSelecionado.setEmail(email);
                usuarioSelecionado.setLogin(login);

                usuarioController.editarPerfil(usuarioSelecionado);
                usuarioSelecionado = null;
                btnEditar.setEnabled(false);
                ehEdicao = false;
                campoSenha.setEnabled(true);
            }

            atualizarTabela();
            limparCampos();
        });

        btnEditar.addActionListener(e -> {
            int selectedRow = tabelaUsuarios.getSelectedRow();
            if (selectedRow >= 0) {
                usuarioSelecionado = usuarioController
                        .obterUsuarioPorId((Long) tabelaModelo.getValueAt(selectedRow, 0));
                preencherCampos(usuarioSelecionado);
                campoSenha.setEnabled(false);
                btnEditar.setEnabled(true);
                ehEdicao = true;
            }
        });

        btnDeletar.addActionListener(e -> {
            int selectedRow = tabelaUsuarios.getSelectedRow();
            if (selectedRow >= 0) {
                Long idUsuario = (Long) tabelaModelo.getValueAt(selectedRow, 0);
                usuarioController.deletarUsuario(idUsuario);
                atualizarTabela();
                limparCampos();
                btnDeletar.setEnabled(false);
            }
        });

        tabelaUsuarios.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tabelaUsuarios.getSelectedRow();
                btnEditar.setEnabled(selectedRow >= 0);
                List<Emprestimo> emprestimos = emprestimoController
                        .listarEmprestimosPorUsuario((Long) tabelaModelo.getValueAt(selectedRow, 0));
                Boolean naoPossuiLivro = emprestimos.isEmpty() || emprestimos == null;
                btnDeletar.setEnabled(selectedRow >= 0 && naoPossuiLivro);
            }
        });

        atualizarTabela();
    }

    private void configurarFiltros() {
        KeyAdapter filtroListener = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filtrarTabela();
            }
        };

        campoFiltroNome.addKeyListener(filtroListener);
        campoFiltroEndereco.addKeyListener(filtroListener);
        campoFiltroEmail.addKeyListener(filtroListener);
        campoFiltroLogin.addKeyListener(filtroListener);
    }

    private void filtrarTabela() {
        RowFilter<DefaultTableModel, Object> filtro = new RowFilter<DefaultTableModel, Object>() {
            public boolean include(Entry<? extends DefaultTableModel, ? extends Object> entry) {
                String nome = campoFiltroNome.getText().toLowerCase();
                String endereco = campoFiltroEndereco.getText().toLowerCase();
                String email = campoFiltroEmail.getText().toLowerCase();
                String login = campoFiltroLogin.getText().toLowerCase();

                String nomeTabela = entry.getStringValue(1).toLowerCase();
                String enderecoTabela = entry.getStringValue(2).toLowerCase();
                String emailTabela = entry.getStringValue(3).toLowerCase();
                String loginTabela = entry.getStringValue(4).toLowerCase();

                return nomeTabela.contains(nome) &&
                        enderecoTabela.contains(endereco) &&
                        emailTabela.contains(email) &&
                        loginTabela.contains(login);
            }
        };

        sorter.setRowFilter(filtro);
    }

    private void atualizarTabela() {
        tabelaModelo.setRowCount(0);
        List<Usuario> usuarios = usuarioController.obterUsuarios();
        for (Usuario u : usuarios) {
            tabelaModelo.addRow(new Object[] { u.getId(), u.getNome(), u.getEndereco(), u.getEmail(), u.getLogin() });
        }
    }

    private void preencherCampos(Usuario usuario) {
        campoNome.setText(usuario.getNome());
        campoEndereco.setText(usuario.getEndereco());
        campoEmail.setText(usuario.getEmail());
        campoLogin.setText(usuario.getLogin());
        campoSenha.setText("");
    }

    private void limparCampos() {
        campoNome.setText("");
        campoEndereco.setText("");
        campoEmail.setText("");
        campoLogin.setText("");
        campoSenha.setText("");
    }

    public static void main(String[] args) {
        new TelaUsuarios().setVisible(true);
    }
}