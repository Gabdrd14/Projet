package com.example;

import java.util.ArrayList;
import java.util.List;

public class Model {

    private List<Drawable> shapes = new ArrayList<>();
    private int compteur_piece = 8;

    public void addShape(Drawable s) {

        shapes.add(s);
        compteur_piece--;

        if (collision()) {
            shapes.remove(shapes.size() - 1);
            System.out.println("Collision -> suppression");
        }

        if (compteur_piece == 0) {
            System.out.println("FINISH");
        }
    }

    public boolean collision() {
        for (int i = 0; i < shapes.size(); i++) {
            for (int j = i + 1; j < shapes.size(); j++) {
                if (shapes.get(i).intersects(shapes.get(j))) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<Drawable> getShapes() {
        return shapes;
    }

    public String getCurrentPlayer() {
        return (compteur_piece > 4) ? "joueur_1" : "joueur_2";
    }




    
}