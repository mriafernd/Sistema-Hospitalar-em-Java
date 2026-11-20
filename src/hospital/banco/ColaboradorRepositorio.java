package hospital.banco;

import hospital.colaborador.Colaborador;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


public class ColaboradorRepositorio {

  
    private String caminho = "colaboradores.csv";

   
    private ArrayList<Colaborador> lista = new ArrayList<>();

   
    public void carregar() {
        File arquivo = new File(caminho);

      
        if (!arquivo.exists()) {
            return;
        }

        try {
            Scanner leitor = new Scanner(arquivo);

           
            if (leitor.hasNextLine()) {
                leitor.nextLine();
            }

          
            while (leitor.hasNextLine()) {
                String linha = leitor.nextLine();

                if (!linha.isEmpty()) {
                    
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

   
    public void salvar() {
        try {
            PrintWriter escritor = new PrintWriter(new FileWriter(caminho));

       
            escritor.println("matricula;nome;idade;cargo;setor;turno;horas_sono;nivel_estresse;"
                    + "exercicio;fumante;alimentacao;saude_mental");

         
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

  
    public void adicionar(Colaborador c) {
        lista.add(c);
        salvar();
    }

  
    public Colaborador buscar(String matricula) {
        for (Colaborador c : lista) {
            if (c.getMatricula().equals(matricula)) {
                return c;
            }
        }
        return null;
    }

   
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