# 🏥 Sistema Hospitalar em Java

Projeto desenvolvido em Java com interface gráfica (Swing) para cadastro, gerenciamento e monitoramento de saúde e bem-estar de colaboradores hospitalares

## 📋 Funcionalidades

* **Cadastro de Colaboradores:** Registro de matrícula, nome, idade, cargo, setor e turno de trabalho
* **Monitoramento de Saúde e Hábitos:** Acompanhamento de fatores como qualidade do sono, nível de estresse, prática de exercícios físicos, alimentação, tabagismo e indicadores emocionais (ansiedade, depressão, burnout, insônia)
* **Filtros e Consultas:** Visualização e filtragem dos registros cadastrados
* **Persistência de Dados:** Leitura e armazenamento automático das informações em arquivo CSV (`colaboradores.csv`)

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java (JDK)
* **Interface Gráfica:** Java Swing (`javax.swing`)
* **Armazenamento:** Manipulação de arquivos CSV
* **IDE:** Eclipse

## 📂 Estrutura do Projeto

* `src/main/Main.java`: Classe principal que inicializa o repositório e abre a interface gráfica
* `src/hospital/front/TelaPrincipal.java`: Tela principal da aplicação com os formulários e componentes visuais
* `src/hospital/banco/ColaboradorRepositorio.java`: Classe responsável por carregar e salvar os dados no arquivo CSV
* `colaboradores.csv` / `dados/`: Base de dados utilizada pelo sistema

## 🚀 Como Executar o Projeto

1. Clone este repositório ou faça o download do arquivo ZIP:
   ```bash
   git clone [https://github.com/mriafernd/Sistema-Hospitalar-em-Java.git](https://github.com/mriafernd/Sistema-Hospitalar-em-Java.git)
   ```
2. Abra a sua IDE Java de preferência (Eclipse, IntelliJ ou VS Code) e importe a pasta do projeto.
3. Certifique-se de que o arquivo `colaboradores.csv` esteja na raiz do projeto para que o carregamento inicial funcione corretamente
4. Execute a classe `Main.java` (localizada em `src/main/Main.java`)

---

Desenvolvido por **Maria Fernanda Silva Pereira**
