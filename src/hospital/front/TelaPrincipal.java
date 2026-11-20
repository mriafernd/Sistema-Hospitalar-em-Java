package hospital.front;

import hospital.banco.ColaboradorRepositorio;
import hospital.colaborador.Colaborador;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

// Janela principal do sistema.
// "extends JFrame" = a classe É uma janela.
// "implements ActionListener" = a classe sabe reagir a cliques de botão.
public class TelaPrincipal extends JFrame implements ActionListener {

    private ColaboradorRepositorio repositorio;

    // ---------- Campos de texto ----------
    private JTextField txtMatricula = new JTextField();
    private JTextField txtNome = new JTextField();
    private JTextField txtIdade = new JTextField();
    private JTextField txtCargo = new JTextField();
    private JTextField txtSetor = new JTextField();

    // ---------- Listas de opções (a primeira opção é sempre "Selecione...") ----------
    private JComboBox<String> cmbTurno = new JComboBox<>(
            new String[]{"Selecione...", "Diurno", "Noturno", "Plantão 12h", "Plantão 24h", "Misto"});
    private JComboBox<String> cmbSono = new JComboBox<>(
            new String[]{"Selecione...", "Menos de 5 horas", "5 a 6 horas", "7 a 8 horas", "Mais de 8 horas"});
    private JComboBox<String> cmbEstresse = new JComboBox<>(
            new String[]{"Selecione...", "Baixo", "Médio", "Alto", "Muito alto"});
    private JComboBox<String> cmbExercicio = new JComboBox<>(
            new String[]{"Selecione...", "Sim", "Não"});
    private JComboBox<String> cmbFumante = new JComboBox<>(
            new String[]{"Selecione...", "Sim", "Não"});
    private JComboBox<String> cmbAlimentacao = new JComboBox<>(
            new String[]{"Selecione...", "Ruim", "Regular", "Boa", "Ótima"});

    // ---------- Caixinhas de marcar (saúde mental) ----------
    private JCheckBox chkAnsiedade = new JCheckBox("Ansiedade");
    private JCheckBox chkDepressao = new JCheckBox("Depressão");
    private JCheckBox chkBurnout = new JCheckBox("Burnout");
    private JCheckBox chkEstresse = new JCheckBox("Estresse crônico");
    private JCheckBox chkInsonia = new JCheckBox("Insônia");
    private JCheckBox chkOutra = new JCheckBox("Outra");

    // ---------- Filtro ----------
    private JComboBox<String> cmbFiltro = new JComboBox<>();
    private JButton btnFiltrar = new JButton("Filtrar");

    // ---------- Botões ----------
    private JButton btnCadastrar = new JButton("Cadastrar");
    private JButton btnAtualizar = new JButton("Atualizar");
    private JButton btnRemover = new JButton("Remover");
    private JButton btnEditar = new JButton("Editar selecionado");
    private JButton btnLimpar = new JButton("Limpar");

    // ---------- Tabela ----------
    private DefaultTableModel modelo;
    private JTable tabela;

    // Construtor: monta a janela
    public TelaPrincipal(ColaboradorRepositorio repositorio) {
        this.repositorio = repositorio;

        // Configurações da janela
        setTitle("Sistema Hospitalar - Cadastro de Colaboradores");
        setSize(1250, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);   // fechar a janela encerra o programa
        setLocationRelativeTo(null);               // abre no centro da tela
        setLayout(new BorderLayout());

        // ----- Formulário: rótulo + campo, em 4 colunas -----
        JPanel painelCampos = new JPanel(new GridLayout(0, 4, 8, 8));
        painelCampos.setBorder(BorderFactory.createTitledBorder("Dados do colaborador"));
        painelCampos.add(new JLabel("Matrícula:"));
        painelCampos.add(txtMatricula);
        painelCampos.add(new JLabel("Nome:"));
        painelCampos.add(txtNome);
        painelCampos.add(new JLabel("Idade:"));
        painelCampos.add(txtIdade);
        painelCampos.add(new JLabel("Cargo:"));
        painelCampos.add(txtCargo);
        painelCampos.add(new JLabel("Setor:"));
        painelCampos.add(txtSetor);
        painelCampos.add(new JLabel("Turno de trabalho:"));
        painelCampos.add(cmbTurno);
        painelCampos.add(new JLabel("Horas de sono por noite:"));
        painelCampos.add(cmbSono);
        painelCampos.add(new JLabel("Estresse no trabalho:"));
        painelCampos.add(cmbEstresse);
        painelCampos.add(new JLabel("Pratica exercício físico?"));
        painelCampos.add(cmbExercicio);
        painelCampos.add(new JLabel("É fumante?"));
        painelCampos.add(cmbFumante);
        painelCampos.add(new JLabel("Alimentação:"));
        painelCampos.add(cmbAlimentacao);
        painelCampos.add(new JLabel(""));   // espaços vazios só para completar a linha
        painelCampos.add(new JLabel(""));

        // ----- Saúde mental: um texto + as caixinhas -----
        JPanel linhaCaixas = new JPanel(new FlowLayout(FlowLayout.LEFT));
        linhaCaixas.add(chkAnsiedade);
        linhaCaixas.add(chkDepressao);
        linhaCaixas.add(chkBurnout);
        linhaCaixas.add(chkEstresse);
        linhaCaixas.add(chkInsonia);
        linhaCaixas.add(chkOutra);

        JPanel painelSaude = new JPanel(new GridLayout(2, 1));
        painelSaude.setBorder(BorderFactory.createTitledBorder("Saúde mental"));
        painelSaude.add(new JLabel("Marque se você tem ou já teve alguma das condições abaixo, "
                + "ou se já fez tratamento. Se não se aplica, deixe sem marcar."));
        painelSaude.add(linhaCaixas);

        // Junta o formulário e a saúde mental no painel de cima
        JPanel painelTopo = new JPanel(new BorderLayout());
        painelTopo.add(painelCampos, BorderLayout.CENTER);
        painelTopo.add(painelSaude, BorderLayout.SOUTH);

        // ----- Tabela -----
        String[] colunas = {"Matrícula", "Nome", "Idade", "Cargo", "Setor", "Turno", "Sono",
                "Estresse", "Exercício", "Fumante", "Alimentação", "Saúde mental"};
        modelo = new DefaultTableModel(colunas, 0);
        tabela = new JTable(modelo);
        tabela.setDefaultEditor(Object.class, null);   // não deixa editar direto na tabela

        // ----- Filtro (em cima da tabela) -----
        JPanel painelFiltro = new JPanel();
        painelFiltro.add(new JLabel("Filtrar por matrícula:"));
        painelFiltro.add(cmbFiltro);
        painelFiltro.add(btnFiltrar);

        JPanel painelTabela = new JPanel(new BorderLayout());
        painelTabela.add(painelFiltro, BorderLayout.NORTH);
        painelTabela.add(new JScrollPane(tabela), BorderLayout.CENTER);

        // ----- Botões (embaixo) -----
        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnRemover);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnLimpar);

        // ----- Liga os botões a esta classe (ela vai tratar os cliques) -----
        btnCadastrar.addActionListener(this);
        btnAtualizar.addActionListener(this);
        btnRemover.addActionListener(this);
        btnEditar.addActionListener(this);
        btnLimpar.addActionListener(this);
        btnFiltrar.addActionListener(this);

        // ----- Coloca os painéis na janela -----
        add(painelTopo, BorderLayout.NORTH);
        add(painelTabela, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        atualizarTela();   // mostra os dados que vieram do CSV
    }

    // Este método é chamado automaticamente quando um botão é clicado.
    // O "e.getSource()" diz qual botão foi clicado.
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnCadastrar) {
            cadastrar();
        } else if (e.getSource() == btnAtualizar) {
            atualizar();
        } else if (e.getSource() == btnRemover) {
            remover();
        } else if (e.getSource() == btnEditar) {
            editarSelecionado();
        } else if (e.getSource() == btnLimpar) {
            limparFormulario();
        } else if (e.getSource() == btnFiltrar) {
            filtrar();
        }
    }

    // ---------- O que cada botão faz ----------

    private void cadastrar() {
        Colaborador novo = lerFormulario();
        if (novo == null) {
            return;   // dados inválidos (a mensagem de erro já foi mostrada)
        }

        // Não permite duas matrículas iguais
        if (repositorio.buscar(novo.getMatricula()) != null) {
            JOptionPane.showMessageDialog(this, "Já existe um colaborador com essa matrícula.",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        repositorio.adicionar(novo);
        atualizarTela();
        limparFormulario();
        JOptionPane.showMessageDialog(this, "Colaborador cadastrado com sucesso!");
    }

    private void atualizar() {
        Colaborador atualizado = lerFormulario();
        if (atualizado == null) {
            return;
        }

        boolean achou = repositorio.atualizar(atualizado);
        if (achou) {
            atualizarTela();
            limparFormulario();
            JOptionPane.showMessageDialog(this, "Colaborador atualizado com sucesso!");
        } else {
            JOptionPane.showMessageDialog(this,
                    "Matrícula não encontrada. Use \"Editar selecionado\" e não mude a matrícula.",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void remover() {
        int linha = tabela.getSelectedRow();   // -1 significa que nenhuma linha foi escolhida
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um colaborador na tabela.",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String matricula = (String) modelo.getValueAt(linha, 0);   // coluna 0 = matrícula
        int resposta = JOptionPane.showConfirmDialog(this,
                "Deseja remover o colaborador " + matricula + "?",
                "Confirmar", JOptionPane.YES_NO_OPTION);

        if (resposta == JOptionPane.YES_OPTION) {
            repositorio.remover(matricula);
            atualizarTela();
            limparFormulario();
        }
    }

    // Copia os dados da linha escolhida na tabela para o formulário
    private void editarSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um colaborador na tabela.",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String matricula = (String) modelo.getValueAt(linha, 0);
        Colaborador c = repositorio.buscar(matricula);

        txtMatricula.setText(c.getMatricula());
        txtNome.setText(c.getNome());
        txtIdade.setText(String.valueOf(c.getIdade()));
        txtCargo.setText(c.getCargo());
        txtSetor.setText(c.getSetor());
        cmbTurno.setSelectedItem(c.getTurno());
        cmbSono.setSelectedItem(c.getHorasSono());
        cmbEstresse.setSelectedItem(c.getNivelEstresse());
        cmbExercicio.setSelectedItem(c.getExercicio());
        cmbFumante.setSelectedItem(c.getFumante());
        cmbAlimentacao.setSelectedItem(c.getAlimentacao());
        marcarSaudeMental(c.getSaudeMental());
    }

    // Mostra na tabela só a matrícula escolhida no filtro (ou todas)
    private void filtrar() {
        String escolhida = (String) cmbFiltro.getSelectedItem();
        atualizarTabela(escolhida);
    }

    // ---------- Formulário ----------

    // Lê os campos da tela, confere se estão corretos e devolve um Colaborador.
    // Se algo estiver errado, mostra uma mensagem e devolve null.
    private Colaborador lerFormulario() {
        // trim() tira espaços sobrando; replace troca ";" por "," para não quebrar o CSV
        String matricula = txtMatricula.getText().trim().replace(";", ",");
        String nome = txtNome.getText().trim().replace(";", ",");
        String cargo = txtCargo.getText().trim().replace(";", ",");
        String setor = txtSetor.getText().trim().replace(";", ",");

        if (matricula.isEmpty() || nome.isEmpty() || cargo.isEmpty() || setor.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha matrícula, nome, cargo e setor.",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        int idade;
        try {
            idade = Integer.parseInt(txtIdade.getText().trim());
        } catch (NumberFormatException erro) {
            JOptionPane.showMessageDialog(this, "A idade deve ser um número inteiro.",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        if (idade < 16 || idade > 100) {
            JOptionPane.showMessageDialog(this, "A idade deve estar entre 16 e 100 anos.",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        // Posição 0 da lista = "Selecione...", ou seja, a pessoa não escolheu nada
        if (cmbTurno.getSelectedIndex() == 0 || cmbSono.getSelectedIndex() == 0
                || cmbEstresse.getSelectedIndex() == 0 || cmbExercicio.getSelectedIndex() == 0
                || cmbFumante.getSelectedIndex() == 0 || cmbAlimentacao.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Escolha uma opção em todas as listas.",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        return new Colaborador(matricula, nome, idade, cargo, setor,
                (String) cmbTurno.getSelectedItem(),
                (String) cmbSono.getSelectedItem(),
                (String) cmbEstresse.getSelectedItem(),
                (String) cmbExercicio.getSelectedItem(),
                (String) cmbFumante.getSelectedItem(),
                (String) cmbAlimentacao.getSelectedItem(),
                lerSaudeMental());
    }

    private void limparFormulario() {
        tabela.clearSelection();
        txtMatricula.setText("");
        txtNome.setText("");
        txtIdade.setText("");
        txtCargo.setText("");
        txtSetor.setText("");
        cmbTurno.setSelectedIndex(0);
        cmbSono.setSelectedIndex(0);
        cmbEstresse.setSelectedIndex(0);
        cmbExercicio.setSelectedIndex(0);
        cmbFumante.setSelectedIndex(0);
        cmbAlimentacao.setSelectedIndex(0);
        marcarSaudeMental("");
    }

    // ---------- Saúde mental ----------

    // Junta as caixinhas marcadas em um texto. Ex.: "Ansiedade, Burnout"
    private String lerSaudeMental() {
        String texto = "";
        if (chkAnsiedade.isSelected()) {
            texto = texto + "Ansiedade, ";
        }
        if (chkDepressao.isSelected()) {
            texto = texto + "Depressão, ";
        }
        if (chkBurnout.isSelected()) {
            texto = texto + "Burnout, ";
        }
        if (chkEstresse.isSelected()) {
            texto = texto + "Estresse crônico, ";
        }
        if (chkInsonia.isSelected()) {
            texto = texto + "Insônia, ";
        }
        if (chkOutra.isSelected()) {
            texto = texto + "Outra, ";
        }

        if (texto.isEmpty()) {
            return "Nenhuma";
        }
        return texto.substring(0, texto.length() - 2);   // tira a última ", "
    }

    // Faz o contrário: marca as caixinhas que aparecem no texto salvo
    private void marcarSaudeMental(String texto) {
        chkAnsiedade.setSelected(texto.contains("Ansiedade"));
        chkDepressao.setSelected(texto.contains("Depressão"));
        chkBurnout.setSelected(texto.contains("Burnout"));
        chkEstresse.setSelected(texto.contains("Estresse crônico"));
        chkInsonia.setSelected(texto.contains("Insônia"));
        chkOutra.setSelected(texto.contains("Outra"));
    }

    // ---------- Tabela e filtro ----------

    // Atualiza o filtro e mostra todos na tabela
    private void atualizarTela() {
        atualizarFiltro();
        atualizarTabela("Todas");
    }

    // Recarrega a lista de matrículas do filtro
    private void atualizarFiltro() {
        cmbFiltro.removeAllItems();
        cmbFiltro.addItem("Todas");
        for (Colaborador c : repositorio.getLista()) {
            cmbFiltro.addItem(c.getMatricula());
        }
    }

    // Preenche a tabela. Se o filtro for "Todas", mostra todo mundo;
    // senão, mostra só o colaborador com aquela matrícula.
    private void atualizarTabela(String filtro) {
        modelo.setRowCount(0);   // apaga as linhas atuais

        for (Colaborador c : repositorio.getLista()) {
            if (filtro.equals("Todas") || c.getMatricula().equals(filtro)) {
                modelo.addRow(new Object[]{
                        c.getMatricula(), c.getNome(), c.getIdade(), c.getCargo(), c.getSetor(),
                        c.getTurno(), c.getHorasSono(), c.getNivelEstresse(),
                        c.getExercicio(), c.getFumante(), c.getAlimentacao(), c.getSaudeMental()});
            }
        }
    }
}