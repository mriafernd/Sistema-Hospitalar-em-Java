package hospital.colaborador;


public class Colaborador {

    private String matricula;
    private String nome;
    private int idade;
    private String cargo;
    private String setor;
    private String turno;
    private String horasSono;
    private String nivelEstresse;
    private String exercicio;     
    private String fumante;       
    private String alimentacao;
    private String saudeMental;   

  
    public Colaborador(String matricula, String nome, int idade, String cargo, String setor,
                       String turno, String horasSono, String nivelEstresse, String exercicio,
                       String fumante, String alimentacao, String saudeMental) {
        this.matricula = matricula;
        this.nome = nome;
        this.idade = idade;
        this.cargo = cargo;
        this.setor = setor;
        this.turno = turno;
        this.horasSono = horasSono;
        this.nivelEstresse = nivelEstresse;
        this.exercicio = exercicio;
        this.fumante = fumante;
        this.alimentacao = alimentacao;
        this.saudeMental = saudeMental;
    }

  
    public String getMatricula() { return matricula; }
    public String getNome() { return nome; }
    public int getIdade() { return idade; }
    public String getCargo() { return cargo; }
    public String getSetor() { return setor; }
    public String getTurno() { return turno; }
    public String getHorasSono() { return horasSono; }
    public String getNivelEstresse() { return nivelEstresse; }
    public String getExercicio() { return exercicio; }
    public String getFumante() { return fumante; }
    public String getAlimentacao() { return alimentacao; }
    public String getSaudeMental() { return saudeMental; }
}