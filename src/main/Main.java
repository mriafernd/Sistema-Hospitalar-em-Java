package main;

import hospital.banco.ColaboradorRepositorio;
import hospital.front.TelaPrincipal;


public class Main {
    public static void main(String[] args) {
        ColaboradorRepositorio repositorio = new ColaboradorRepositorio();
        repositorio.carregar();   //

        TelaPrincipal tela = new TelaPrincipal(repositorio);
        tela.setVisible(true);  
    }
}