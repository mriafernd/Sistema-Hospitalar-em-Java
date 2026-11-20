package hospital.banco;

import hospital.colaborador.Colaborador;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

// Guarda a lista de colaboradores na memória e cuida do arquivo CSV.
public class ColaboradorRepositorio {

    // O arquivo fica na pasta do projeto
    private String caminho = "colaboradores.csv";

    // Lista que guarda todos os colaboradores enquanto o programa está aberto
    private ArrayList<Colaborador> lista = new ArrayList<>();

    // LEITURA: lê o CSV e coloca os dados na lista (chamado ao abrir o programa)
    public void carregar() {
        File arquivo = new File(caminho);

        // Se o arquivo ainda não existe (primeira vez), não tem o que ler
        if (!arquivo.exists()) {
            return;
        }

        try {
            Scanner leitor = new Scanner(arquivo);

            // Pula a primeira linha (o cabeçalho)
            if (leitor.hasNextLine()) {
                leitor.nextLine();
            }

            // Lê uma linha por vez, até acabar o arquivo
            while (leitor.hasNextLine()) {
                String linha = leitor.nextLine();

                if (!linha.isEmpty()) {
                    // Separa a linha nos pontos e vírgulas: p[0] = matrícula, p[1] = nome...
                    String[] p = linha.split(";");

                    Colaborador c = new Colaborador(p[0], p[1], Integer.parseInt(p[2]), p[3], p[4],
                            p[5], p[6], p[7], p[8], p[9], p[10], p[11]);
                    lista.add(c);
                }
            }
            leitor.close();

        } catch (Exception erro) {
            System.out.println("Erro ao ler o arquivo: " + erro.getMessage());
        }
    }

    // ESCRITA: grava todos os colaboradores da lista no CSV
    public void salvar() {
        try {
            PrintWriter escritor = new PrintWriter(new FileWriter(caminho));

            // Primeira linha: o cabeçalho
            escritor.println("matricula;nome;idade;cargo;setor;turno;horas_sono;nivel_estresse;"
                    + "exercicio;fumante;alimentacao;saude_mental");

            // Uma linha para cada colaborador
            for (Colaborador c : lista) {
                escritor.println(c.getMatricula() + ";" + c.getNome() + ";" + c.getIdade() + ";"
                        + c.getCargo() + ";" + c.getSetor() + ";" + c.getTurno() + ";"
                        + c.getHorasSono() + ";" + c.getNivelEstresse() + ";" + c.getExercicio() + ";"
                        + c.getFumante() + ";" + c.getAlimentacao() + ";" + c.getSaudeMental());
            }
            escritor.close();

        } catch (Exception erro) {
            System.out.println("Erro ao salvar o arquivo: " + erro.getMessage());
        }
    }

    // Adiciona na lista e já salva no arquivo
    public void adicionar(Colaborador c) {
        lista.add(c);
        salvar();
    }

    // Procura um colaborador pela matrícula (devolve null se não achar)
    public Colaborador buscar(String matricula) {
        for (Colaborador c : lista) {
            if (c.getMatricula().equals(matricula)) {
                return c;
            }
        }
        return null;
    }

    // Troca o colaborador que tem a mesma matrícula. Devolve true se conseguiu.
    public boolean atualizar(Colaborador novo) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getMatricula().equals(novo.getMatricula())) {
                lista.set(i, novo);
                salvar();
                return true;
            }
        }
        return false;
    }

    // Remove o colaborador pela matrícula e salva
    public void remover(String matricula) {
        Colaborador c = buscar(matricula);
        if (c != null) {
            lista.remove(c);
            salvar();
        }
    }

    public ArrayList<Colaborador> getLista() {
        return lista;
    }
}