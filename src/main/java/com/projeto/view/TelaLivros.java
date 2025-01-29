package com.projeto.view;

import com.projeto.controller.LivroController;
import com.projeto.model.Livro;
import com.projeto.util.NonEditableTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class TelaLivros extends JFrame {

    private LivroController livroController = new LivroController();
    private DefaultTableModel tabelaModelo;
    private JTable tabelaLivros;
    private JTextField campoFiltroTitulo, campoFiltroAutor, campoFiltroAno;
    private TableRowSorter<DefaultTableModel> sorter;
    private JTextField campoTitulo, campoAutor, campoAnoPublicacao;
    private JButton btnSalvar, btnEditar, btnDeletar;
    private Livro livroSelecionado;

    public TelaLivros() {
        setTitle("Gerenciamento de Livros");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel painelPrincipal = new JPanel(new BorderLayout());
        add(painelPrincipal, BorderLayout.CENTER);

        JPanel painelFormulario = new JPanel(new GridLayout(5, 2, 10, 10));
        painelPrincipal.add(painelFormulario, BorderLayout.NORTH);

        campoTitulo = new JTextField();
        campoAutor = new JTextField();
        campoAnoPublicacao = new JTextField();

        painelFormulario.add(new JLabel("Título:"));
        painelFormulario.add(campoTitulo);
        painelFormulario.add(new JLabel("Autor:"));
        painelFormulario.add(campoAutor);
        painelFormulario.add(new JLabel("Ano de Publicação:"));
        painelFormulario.add(campoAnoPublicacao);

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

        JPanel painelFiltro = new JPanel(new GridLayout(3, 2, 10, 10));
        painelFiltro.setBorder(BorderFactory.createTitledBorder("Filtrar Livros"));

        campoFiltroTitulo = new JTextField();
        campoFiltroAutor = new JTextField();
        campoFiltroAno = new JTextField();

        painelFiltro.add(new JLabel("Título:"));
        painelFiltro.add(campoFiltroTitulo);
        painelFiltro.add(new JLabel("Autor:"));
        painelFiltro.add(campoFiltroAutor);
        painelFiltro.add(new JLabel("Ano:"));
        painelFiltro.add(campoFiltroAno);

        painelFiltroETabela.add(painelFiltro, BorderLayout.NORTH);

        tabelaModelo = new NonEditableTableModel(new Object[] { "ID", "Título", "Autor", "Ano", "Disponível" }, 0);
        tabelaLivros = new JTable(tabelaModelo);
        tabelaLivros.getTableHeader().setReorderingAllowed(false);
        sorter = new TableRowSorter<>(tabelaModelo);
        tabelaLivros.setRowSorter(sorter);
        JScrollPane scrollPane = new JScrollPane(tabelaLivros);
        painelFiltroETabela.add(scrollPane, BorderLayout.CENTER);

        configurarFiltros();

        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titulo = campoTitulo.getText();
                String autor = campoAutor.getText();
                int anoPublicacao = Integer.parseInt(campoAnoPublicacao.getText());

                if (titulo.isEmpty() || autor.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos!", "Erro",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (livroSelecionado == null) {
                    Livro livro = new Livro(null, titulo, autor, anoPublicacao, true);
                    livroController.salvarLivro(livro);
                } else {
                    livroSelecionado.setTitulo(titulo);
                    livroSelecionado.setAutor(autor);
                    livroSelecionado.setAnoPublicacao(anoPublicacao);

                    livroController.atualizarLivro(livroSelecionado);
                    livroSelecionado = null;
                    btnEditar.setEnabled(false);
                }

                atualizarTabela();
                limparCampos();
            }
        });

        btnEditar.addActionListener(e -> {
            int selectedRow = tabelaLivros.getSelectedRow();
            if (selectedRow >= 0) {
                livroSelecionado = livroController
                        .buscarLivroPorId((Long) tabelaModelo.getValueAt(selectedRow, 0));
                preencherCampos(livroSelecionado);
                btnEditar.setEnabled(true);
            }
        });

        btnDeletar.addActionListener(e -> {
            int selectedRow = tabelaLivros.getSelectedRow();
            if (selectedRow >= 0) {
                Long idLivro = (Long) tabelaModelo.getValueAt(selectedRow, 0);
                livroController.deletarLivro(idLivro);
                atualizarTabela();
                limparCampos();
                btnDeletar.setEnabled(false);
            }
        });

        tabelaLivros.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tabelaLivros.getSelectedRow();
                btnEditar.setEnabled(selectedRow >= 0);
                btnDeletar.setEnabled(selectedRow >= 0 && (Boolean) tabelaModelo.getValueAt(selectedRow, 4));
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

        campoFiltroTitulo.addKeyListener(filtroListener);
        campoFiltroAutor.addKeyListener(filtroListener);
        campoFiltroAno.addKeyListener(filtroListener);
    }

    private void filtrarTabela() {
        RowFilter<DefaultTableModel, Object> filtro = new RowFilter<DefaultTableModel, Object>() {
            public boolean include(Entry<? extends DefaultTableModel, ? extends Object> entry) {
                String titulo = campoFiltroTitulo.getText().toLowerCase();
                String autor = campoFiltroAutor.getText().toLowerCase();
                String ano = campoFiltroAno.getText().toLowerCase();

                String tituloTabela = entry.getStringValue(1).toLowerCase();
                String autorTabela = entry.getStringValue(2).toLowerCase();
                String anoTabela = entry.getStringValue(3).toString().toLowerCase();

                return tituloTabela.contains(titulo) &&
                        autorTabela.contains(autor) &&
                        anoTabela.contains(ano);
            }
        };

        sorter.setRowFilter(filtro);
    }

    private void atualizarTabela() {
        tabelaModelo.setRowCount(0);
        List<Livro> livros = livroController.obterLivros();
        for (Livro l : livros) {
            tabelaModelo.addRow(
                    new Object[] { l.getId(), l.getTitulo(), l.getAutor(), l.getAnoPublicacao(), l.isDisponivel() });
        }
    }

    private void preencherCampos(Livro livro) {
        campoTitulo.setText(livro.getTitulo());
        campoAutor.setText(livro.getAutor());
        campoAnoPublicacao.setText(String.valueOf(livro.getAnoPublicacao()));
    }

    private void limparCampos() {
        campoTitulo.setText("");
        campoAutor.setText("");
        campoAnoPublicacao.setText("");
    }

    public static void main(String[] args) {
        new TelaLivros().setVisible(true);
    }
}