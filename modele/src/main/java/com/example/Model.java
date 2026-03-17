package com.example;

import java.util.ArrayList;
import java.util.List;

public class Model {

    private List<Shape> shapes = new ArrayList<>() ; 

    public int compteur_piece = 8 ; 


    public boolean collision(){

        boolean result = false ;


        for (int i= 0 ; i < shapes.size(); i++){
        
            for (int j = i + 1; j < shapes.size(); j++){


                Shape s1 = shapes.get(i);
                Shape s2 = shapes.get(j);
                

                result = s1.intersects(s2) ;       
            }
        }
        return result;
     


    }




    public void addShape(Shape s){


        if (compteur_piece > 4){
                   s.id_joueur = "joueur_1";
        }
        if(compteur_piece <=4){

                   s.id_joueur = "joueur_2";
        }
        if(compteur_piece == 0){

        System.out.println("FINISH");

        }


        
        shapes.add(s) ;
        compteur_piece -= 1 ;

        System.out.println(collision());
        if(collision() == true){
            shapes.remove(shapes.size()-1);
            System.out.println("Suppression objet");

        }
    }




    public List<Shape> getShapes(){
        return shapes ;
    }

}
 