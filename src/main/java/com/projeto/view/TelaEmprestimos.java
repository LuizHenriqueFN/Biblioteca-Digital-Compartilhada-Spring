package com.projeto.view;

import com.projeto.controller.EmprestimoController;
import com.projeto.controller.LivroController;
import com.projeto.controller.UsuarioController;
import com.projeto.model.Emprestimo;
import com.projeto.model.Livro;
import com.projeto.model.Usuario;
import com.projeto.util.NonEditableTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.util.List;

public class TelaEmprestimos extends JFrame {

    private EmprestimoController emprestimoController = new EmprestimoController();
    private UsuarioController usuarioController = new UsuarioController();
    private LivroController livroController = new LivroController();
    private DefaultTableModel tabelaModelo;
    private JTable tabelaEmprestimos;
    private JTextField campoFiltroUsuarioSolicitante, campoFiltroUsuarioProprietario, campoFiltroLivro,
            campoFiltroDataEmprestimo, campoFiltroDataDevolucao;
    private TableRowSorter<DefaultTableModel> sorter;

    public TelaEmprestimos() {
        setTitle("Gerenciamento de Empréstimos");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel painelPrincipal = new JPanel(new BorderLayout());
        add(painelPrincipal, BorderLayout.CENTER);

        JPanel painelFormulario = new JPanel(new GridLayout(6, 2, 10, 10));
        painelPrincipal.add(painelFormulario, BorderLayout.NORTH);

        JTextField campoUsuarioSolicitante = new JTextField();
        JTextField campoUsuarioProprietario = new JTextField();
        JTextField campoLivro = new JTextField();
        JTextField campoDataEmprestimo = new JTextField(LocalDate.now().toString());
        JTextField campoDataDevolucao = new JTextField();

        painelFormulario.add(new JLabel("Usuário Solicitante (Login):"));
        painelFormulario.add(campoUsuarioSolicitante);
        painelFormulario.add(new JLabel("Usuário Proprietário (Login):"));
        painelFormulario.add(campoUsuarioProprietario);
        painelFormulario.add(new JLabel("Livro (Nome):"));
        painelFormulario.add(campoLivro);
        painelFormulario.add(new JLabel("Data de Empréstimo (YYYY-MM-DD):"));
        painelFormulario.add(campoDataEmprestimo);
        painelFormulario.add(new JLabel("Data de Devolução (YYYY-MM-DD):"));
        painelFormulario.add(campoDataDevolucao);

        JButton btnSalvar = new JButton("Salvar");
        painelFormulario.add(btnSalvar);

        JButton btnVoltar = new JButton("Voltar");
        painelFormulario.add(btnVoltar);
        btnVoltar.addActionListener(e -> setVisible(false));

        JPanel painelFiltroETabela = new JPanel(new BorderLayout());
        painelPrincipal.add(painelFiltroETabela, BorderLayout.CENTER);

        JPanel painelFiltro = new JPanel(new GridLayout(3, 4, 10, 10));
        painelFiltro.setBorder(BorderFactory.createTitledBorder("Filtrar Empréstimos"));

        campoFiltroUsuarioSolicitante = new JTextField();
        campoFiltroUsuarioProprietario = new JTextField();
        campoFiltroLivro = new JTextField();
        campoFiltroDataEmprestimo = new JTextField();
        campoFiltroDataDevolucao = new JTextField();

        painelFiltro.add(new JLabel("Usuário Solicitante (Nome):"));
        painelFiltro.add(campoFiltroUsuarioSolicitante);
        painelFiltro.add(new JLabel("Usuário Proprietário (Nome):"));
        painelFiltro.add(campoFiltroUsuarioProprietario);
        painelFiltro.add(new JLabel("Livro (Nome):"));
        painelFiltro.add(campoFiltroLivro);
        painelFiltro.add(new JLabel("Data de Empréstimo (YYYY-MM-DD):"));
        painelFiltro.add(campoFiltroDataEmprestimo);
        painelFiltro.add(new JLabel("Data de Devolução (YYYY-MM-DD):"));
        painelFiltro.add(campoFiltroDataDevolucao);

        painelFiltroETabela.add(painelFiltro, BorderLayout.NORTH);

        tabelaModelo = new NonEditableTableModel(
                new Object[] { "ID", "Usuário Solicitante", "Usuário Proprietário", "Livro", "Data Empréstimo",
                        "Data Devolução" },
                0);
        tabelaEmprestimos = new JTable(tabelaModelo);
        tabelaEmprestimos.getTableHeader().setReorderingAllowed(false);
        sorter = new TableRowSorter<>(tabelaModelo);
        tabelaEmprestimos.setRowSorter(sorter);
        JScrollPane scrollPane = new JScrollPane(tabelaEmprestimos);
        painelFiltroETabela.add(scrollPane, BorderLayout.CENTER);

        configurarFiltros();

        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String loginSolicitante = campoUsuarioSolicitante.getText();
                    String loginProprietario = campoUsuarioProprietario.getText();
                    String tituloLivro = campoLivro.getText();
                    LocalDate dataEmprestimo = LocalDate.parse(campoDataEmprestimo.getText());
                    LocalDate dataDevolucao = campoDataDevolucao.getText().isEmpty()
                            ? null
                            : LocalDate.parse(campoDataDevolucao.getText());

                    Usuario usuarioSolicitante = usuarioController.obterUsuarioPorLogin(loginSolicitante);
                    Usuario usuarioProprietario = usuarioController.obterUsuarioPorLogin(loginProprietario);
                    if (usuarioSolicitante == null || usuarioProprietario == null) {
                        JOptionPane.showMessageDialog(null, "Usuário não encontrado!", "Erro",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    Livro livro = livroController.buscarLivroPorTitulo(tituloLivro);
                    if (livro == null) {
                        JOptionPane.showMessageDialog(null, "Livro não encontrado!", "Erro",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    Emprestimo emprestimo = new Emprestimo(null, usuarioSolicitante, usuarioProprietario, livro,
                            dataEmprestimo, dataDevolucao);
                    emprestimoController.salvarEmprestimo(emprestimo);

                    livro.setDisponivel(false);
                    livroController.atualizarLivro(livro);

                    atualizarTabela();
                    limparCampos(campoUsuarioSolicitante, campoUsuarioProprietario, campoLivro, campoDataDevolucao);
                    campoDataEmprestimo.setText(LocalDate.now().toString());

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao salvar o empréstimo: " + ex.getMessage(), "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }
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

        campoFiltroUsuarioSolicitante.addKeyListener(filtroListener);
        campoFiltroUsuarioProprietario.addKeyListener(filtroListener);
        campoFiltroLivro.addKeyListener(filtroListener);
        campoFiltroDataEmprestimo.addKeyListener(filtroListener);
        campoFiltroDataDevolucao.addKeyListener(filtroListener);
    }

    private void filtrarTabela() {
        RowFilter<DefaultTableModel, Object> filtro = new RowFilter<DefaultTableModel, Object>() {
            public boolean include(Entry<? extends DefaultTableModel, ? extends Object> entry) {
                String usuarioSolicitante = campoFiltroUsuarioSolicitante.getText().toLowerCase();
                String usuarioProprietario = campoFiltroUsuarioProprietario.getText().toLowerCase();
                String livro = campoFiltroLivro.getText().toLowerCase();
                String dataEmprestimo = campoFiltroDataEmprestimo.getText().toLowerCase();
                String dataDevolucao = campoFiltroDataDevolucao.getText().toLowerCase();

                String usuarioSolicitanteTabela = entry.getStringValue(1).toLowerCase();
                String usuarioProprietarioTabela = entry.getStringValue(2).toLowerCase();
                String livroTabela = entry.getStringValue(3).toString().toLowerCase();
                String dataEmprestimoTabela = entry.getStringValue(4).toString().toLowerCase();
                String dataDevolucaoTabela = entry.getStringValue(5).toString().toLowerCase();

                return usuarioSolicitanteTabela.contains(usuarioSolicitante) &&
                        usuarioProprietarioTabela.contains(usuarioProprietario) &&
                        livroTabela.contains(livro) &&
                        dataEmprestimoTabela.contains(dataEmprestimo) &&
                        dataDevolucaoTabela.contains(dataDevolucao);
            }
        };

        sorter.setRowFilter(filtro);
    }

    private void atualizarTabela() {
        tabelaModelo.setRowCount(0);
        List<Emprestimo> emprestimos = emprestimoController.listarEmprestimos();
        for (Emprestimo e : emprestimos) {
            tabelaModelo.addRow(new Object[] {
                    e.getId(),
                    e.getUsuarioSolicitante().getNome(),
                    e.getUsuarioProprietario().getNome(),
                    e.getLivro().getTitulo(),
                    e.getDataEmprestimo(),
                    e.getDataDevolucao()
            });
        }
    }

    private void limparCampos(JTextField... campos) {
        for (JTextField campo : campos) {
            campo.setText("");
        }
    }

    public static void main(String[] args) {
        new TelaEmprestimos().setVisible(true);
    }
}