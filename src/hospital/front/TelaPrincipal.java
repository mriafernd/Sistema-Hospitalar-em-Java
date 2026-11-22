package hospital.front;

import hospital.banco.ColaboradorRepositorio;
import hospital.colaborador.Colaborador;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

// Janela principal do sistema.
// "extends JFrame" = a classe É uma janela.
// "implements ActionListener" = a classe sabe reagir a cliques de botão.
public class TelaPrincipal extends JFrame implements ActionListener {

    private ColaboradorRepositorio repositorio;

    // ---------- Campos de digitar ----------
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
    private JButton btnRemover = new JButton("Remover");
    private JButton btnLimpar = new JButton("Limpar");

    // ---------- Tabela ----------
    private DefaultTableModel modelo;
    private JTable tabela;

    // Construtor: configura a janela e coloca as 3 partes dentro dela
    public TelaPrincipal(ColaboradorRepositorio repositorio) {
        this.repositorio = repositorio;

        setTitle("Sistema Hospitalar - Cadastro de Colaboradores");
        setSize(1250, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);   // fechar a janela encerra o programa
        setLocationRelativeTo(null);               // abre no centro da tela
        setLayout(new BorderLayout());

        add(criarFormulario(), BorderLayout.NORTH);    // formulário em cima
        add(criarTabela(), BorderLayout.CENTER);       // tabela no meio
        add(criarBotoes(), BorderLayout.SOUTH);        // botões embaixo

        atualizarTela();   // mostra os dados que vieram do CSV
    }

    // ---------- Montagem da tela (uma parte por método) ----------

    // Parte de cima: campos do formulário + caixinhas de saúde mental
    private JPanel criarFormulario() {
        // Os campos ficam em uma grade de 4 colunas: rótulo, campo, rótulo, campo
        JPanel painelCampos = new JPanel(new GridLayout(0, 4, 8, 8));
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
        painelCampos.add(new JLabel(""));   // espaços vazios só para completar a última linha
        painelCampos.add(new JLabel(""));

        // O texto e as caixinhas ficam lado a lado em um painel
        JPanel painelSaude = new JPanel();
        painelSaude.add(new JLabel("Saúde mental (marque se você tem ou já teve):"));
        painelSaude.add(chkAnsiedade);
        painelSaude.add(chkDepressao);
        painelSaude.add(chkBurnout);
        painelSaude.add(chkEstresse);
        painelSaude.add(chkInsonia);
        painelSaude.add(chkOutra);

        // Junta os dois painéis: campos em cima, saúde mental embaixo
        JPanel painelTopo = new JPanel(new BorderLayout());
        painelTopo.add(painelCampos, BorderLayout.CENTER);
        painelTopo.add(painelSaude, BorderLayout.SOUTH);
        return painelTopo;
    }

    // Parte do meio: filtro + tabela
    private JPanel criarTabela() {
        String[] colunas = {"Matrícula", "Nome", "Idade", "Cargo", "Setor", "Turno", "Sono",
                "Estresse", "Exercício", "Fumante", "Alimentação", "Saúde mental"};
        modelo = new DefaultTableModel(colunas, 0);   // guarda os dados (0 linhas no começo)
        tabela = new JTable(modelo);                  // mostra os dados do modelo
        tabela.setDefaultEditor(Object.class, null);  // não deixa editar direto na tabela

        JPanel painelFiltro = new JPanel();
        painelFiltro.add(new JLabel("Filtrar por matrícula:"));
        painelFiltro.add(cmbFiltro);
        painelFiltro.add(btnFiltrar);
        btnFiltrar.addActionListener(this);

        JPanel painelTabela = new JPanel(new BorderLayout());
        painelTabela.add(painelFiltro, BorderLayout.NORTH);
        painelTabela.add(new JScrollPane(tabela), BorderLayout.CENTER);
        return painelTabela;
    }

    // Parte de baixo: os 3 botões
    private JPanel criarBotoes() {
        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnRemover);
        painelBotoes.add(btnLimpar);

        // "this" = esta janela vai tratar os cliques
        btnCadastrar.addActionListener(this);
        btnRemover.addActionListener(this);
        btnLimpar.addActionListener(this);
        return painelBotoes;
    }

    // ---------- Cliques ----------

    // Chamado automaticamente quando um botão é clicado.
    // "e.getSource()" diz qual botão foi clicado.
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnCadastrar) {
            cadastrar();
        } else if (e.getSource() == btnRemover) {
            remover();
        } else if (e.getSource() == btnLimpar) {
            limparFormulario();
        } else if (e.getSource() == btnFiltrar) {
            filtrar();
        }
    }

    private void cadastrar() {
        Colaborador novo = lerFormulario();
        if (novo == null) {
            return;   // dados inválidos (a mensagem de erro já foi mostrada)
        }

        // Não permite duas matrículas iguais
        if (repositorio.buscar(novo.getMatricula()) != null) {
            JOptionPane.showMessageDialog(this, "Já existe um colaborador com essa matrícula.");
            return;
        }

        repositorio.adicionar(novo);
        atualizarTela();
        limparFormulario();
        JOptionPane.showMessageDialog(this, "Colaborador cadastrado com sucesso!");
    }

    private void remover() {
        int linha = tabela.getSelectedRow();   // -1 significa que nenhuma linha foi escolhida
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um colaborador na tabela.");
            return;
        }

        String matricula = (String) modelo.getValueAt(linha, 0);   // coluna 0 = matrícula
        int resposta = JOptionPane.showConfirmDialog(this,
                "Deseja remover o colaborador " + matricula + "?",
                "Confirmar", JOptionPane.YES_NO_OPTION);

        if (resposta == JOptionPane.YES_OPTION) {
            repositorio.remover(matricula);
            atualizarTela();
        }
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
            JOptionPane.showMessageDialog(this, "Preencha matrícula, nome, cargo e setor.");
            return null;
        }

        int idade;
        try {
            idade = Integer.parseInt(txtIdade.getText().trim());
        } catch (NumberFormatException erro) {
            JOptionPane.showMessageDialog(this, "A idade deve ser um número inteiro.");
            return null;
        }

        if (idade < 18 || idade > 80) {
            JOptionPane.showMessageDialog(this, "A idade deve estar entre 18 e 80 anos.");
            return null;
        }

        // Posição 0 da lista = "Selecione...", ou seja, a pessoa não escolheu nada
        if (cmbTurno.getSelectedIndex() == 0 || cmbSono.getSelectedIndex() == 0
                || cmbEstresse.getSelectedIndex() == 0 || cmbExercicio.getSelectedIndex() == 0
                || cmbFumante.getSelectedIndex() == 0 || cmbAlimentacao.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Escolha uma opção em todas as listas.");
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
        chkAnsiedade.setSelected(false);
        chkDepressao.setSelected(false);
        chkBurnout.setSelected(false);
        chkEstresse.setSelected(false);
        chkInsonia.setSelected(false);
        chkOutra.setSelected(false);
    }

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