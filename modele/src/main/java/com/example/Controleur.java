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
            contains(p1); // check si le click se trouve dans une forme
        

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

            vue.paintComponent(vue.getGraphics());



        }
    

    }
        
    public void contains(Point point){



            for (int i = 0 ; i < model.getShapes().size() ; i++  ){

                Shape shape = model.getShapes().get(i);

                if (shape.getBounds().contains(point)) {
                    System.out.println("click souris sur la forme " + i + " Type :"+ shape.getBounds().getClass());}
                    System.out.println("Location rectangle (tjr le point en haut a gauche)"+shape.getBounds().getLocation());   
                    System.out.println("dimension de la forme " + shape.getBounds().getSize());
            }
        } 
    

  
    



}



