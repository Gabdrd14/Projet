package com.example;

import java.util.ArrayList;
import java.util.List;

public class Model { // Classe qui représente le modèle de données du jeu, elle contient les listes de formes pour chaque joueur, la liste des obstacles fixes, le compteur de pièces restantes pour chaque joueur, et les méthodes pour ajouter des formes, vérifier les collisions, obtenir les formes du joueur actuel et obtenir le nom du joueur actuel

    private List<Drawable> shapes = new ArrayList<>(); // Liste des formes du joueur 1
    private List<Drawable> shapes2 = new ArrayList<>(); // Liste des formes du joueur 2
    private List<Drawable> obstacle = new ArrayList<>(); // Liste des obstacles fixes a comparer pour les collisions


    private int compteur_piece = 8; // Compteur de pièces restantes pour chaque joueur, il commence à 8 et diminue à chaque ajout de forme, lorsque le compteur atteint 0, la partie est terminée

    public void addShape(Drawable s) { // ajoute une forme au modèle et vérifie les collisions, si collision -> suppression de la forme ajoutée 

        boolean isPlayer1 = compteur_piece > 4;

        List<Drawable> current = isPlayer1 ? shapes : shapes2;

        current.add(s);
        compteur_piece--;

        if (collision(isPlayer1)) {
            current.remove(current.size() - 1);
            System.out.println("Collision -> suppression");
        }

        if (compteur_piece == 0) {
            System.out.println("FINISH");
        }
    }

    public boolean collision(boolean isPlayer1) { // true si collision, false sinon

        List<Drawable> current = isPlayer1 ? shapes : shapes2;
        List<Drawable> other   = isPlayer1 ? shapes2 : shapes;


        for (int i = 0; i < current.size(); i++) {
            Drawable a = current.get(i);

            for (int j = i + 1; j < current.size(); j++) {
                if (CollisionUtil.intersects(a, current.get(j))) {
                    return true;
                }
            }

            for (Drawable b : other) {
                if (CollisionUtil.intersects(a, b)) {
                    return true;
                }
            
            
            }
        }

        return false;
    }

    public List<Drawable> getShapes() { // retourne la liste des formes du joueur actuel
        return (compteur_piece > 4) ? shapes : shapes2;
    }

    public String getCurrentPlayer() {   // retourne le nom du joueur actuel
        return (compteur_piece > 4) ? "joueur_1" : "joueur_2";
    }
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