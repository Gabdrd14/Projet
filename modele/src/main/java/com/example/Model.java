package com.example;

import java.util.ArrayList;
import java.util.List;

public class Model {


    private List<Shape> shapes = new ArrayList<>() ; 



    public void collision(){


        for (int i= 0 ; i < shapes.size(); i++){
        
            for (int j = i + 1; j < shapes.size(); j++){


                Shape s1 = shapes.get(i);
                Shape s2 = shapes.get(j);

                if ( s1.intersects(s2)){
                    System.out.println("COLLISION");
                }
            }


        }



    }




    public void addShape(Shape s){
        
        shapes.add(s) ;
        collision();


    }




    public List<Shape> getShapes(){
        return shapes ;
    }

    
 




}
