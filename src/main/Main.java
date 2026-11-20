package main;

import hospital.banco.ColaboradorRepositorio;
import hospital.front.TelaPrincipal;

// Classe que inicia o programa
public class Main {
    public static void main(String[] args) {
        ColaboradorRepositorio repositorio = new ColaboradorRepositorio();
        repositorio.carregar();   // lê o CSV automaticamente ao abrir

        TelaPrincipal tela = new TelaPrincipal(repositorio);
        tela.setVisible(true);    // mostra a janela
    }
}