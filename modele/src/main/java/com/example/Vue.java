package com.example;

import java.awt.Graphics;
import javax.swing.JPanel;

public class Vue extends JPanel{


    private Model model ;


    public Vue(Model model){


        this.model  = model ;


    }


    protected void paintComponent(Graphics g){

        super.paintComponent(getGraphics());

        
        for (Shape s : model.getShapes()){

            s.draw(g);
        }

    }


    





    

}
