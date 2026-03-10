package com.example;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Controleur extends MouseAdapter {

    private Model model ;
    private Vue vue ;
    private Tool t  = Tool.RECTANGLE ;


    private Point p1  = null ;

    public Controleur(Model model , Vue vue){

        this.model = model ;
        this.vue = vue ;

    }

    public void setTool(Tool t){

        this.t = t ;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (p1 == null) {

            p1 = e.getPoint();
        

        } else {
                Point p2 = e.getPoint();

                Shape shape ;

            if (t == Tool.RECTANGLE){

                 shape = new Shape_Rectangle(p1, p2);  // a changer. avec le choix des boutons pour la forme

            } else {

                 shape = new Shape_Circle(p1, p2);  // a changer. avec le choix des boutons pour la forme


            }
            model.addShape(shape);
            


            p1 = null ; 

            vue.repaint();



        }
    }




}
