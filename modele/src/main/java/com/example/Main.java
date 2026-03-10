package com.example;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {


        Model model  = new Model() ;

        Vue vue = new Vue(model);

        Controleur controleur = new Controleur(model, vue) ;


        vue.addMouseListener(controleur);


        JFrame frame  = new JFrame("TEST DESSIN");
        frame.add(vue);

        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);

    }
    
}
