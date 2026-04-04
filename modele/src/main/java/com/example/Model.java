package com.example;

import java.util.ArrayList;
import java.util.List;

public class Model {

    private List<Drawable> shapes = new ArrayList<>();
    private int compteur_piece = 8;

    public float modificateur_score_cercele = 1.5f; // bonus pour les cercles
    public float modificateur_score_rectangle = 1.0f; // bonus pour les rectangles  


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
                if (CollisionUtil.intersects(shapes.get(i), shapes.get(j))) {
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



    // public int scoring_game() {
    //     if (compteur_piece == 0) {
    //         int score_joueur_1 = 0;
    //         int score_joueur_2 = 0;

    //         for (Drawable s : shapes) {
    //             if (s instanceof RectangleShape) {
    //                 RectangleShape r = (RectangleShape) s;
    //                 if (r.joueur.equals("joueur_1")) {
    //                     score_joueur_1 += modificateur_score_rectangle;
    //                 } else {
    //                     score_joueur_2++;
    //                 }
    //             } else if (s instanceof CircleShape) {
    //                 CircleShape c = (CircleShape) s;
    //                 if (c.joueur.equals("joueur_1")) {
    //                     score_joueur_1 += modificateur_score_rectangle;
    //                 } else {
    //                     score_joueur_2 += modificateur_score_cercele;
    //                 }
    //             }
    //         }

    //         if (score_joueur_1 > score_joueur_2) {
    //             return 1; // joueur 1 gagne
    //         } else if (score_joueur_2 > score_joueur_1) {
    //             return 2; // joueur 2 gagne
    //         } else {
    //             return 0; // égalité
    //         }
    //     }
    //     return -1; // partie en cours
    // }

    
}