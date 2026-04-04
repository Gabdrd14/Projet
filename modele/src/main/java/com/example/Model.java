package com.example;

import java.util.ArrayList;
import java.util.List;

public class Model { // Classe qui représente le modèle de données du jeu, elle contient les listes de formes pour chaque joueur, la liste des obstacles fixes, le compteur de pièces restantes pour chaque joueur, et les méthodes pour ajouter des formes, vérifier les collisions, obtenir les formes du joueur actuel et obtenir le nom du joueur actuel

    private List<Drawable> shapes = new ArrayList<>(); // Liste des formes du joueur 1
    private List<Drawable> shapes2 = new ArrayList<>(); // Liste des formes du joueur 2
    private List<Drawable> obstacle = new ArrayList<>(); // Liste des obstacles fixes a comparer pour les collisions


    private int compteur_piece = 12; // Compteur de pièces restantes pour chaque joueur, il commence à 8 et diminue à chaque ajout de forme, lorsque le compteur atteint 0, la partie est terminée
    private int score_joueur1 = 0; // Score du joueur 1,
    private int score_joueur2 = 0; // Score du joueur 2, 

    public void addShape(Drawable s) { // ajoute une forme au modèle et vérifie les collisions, si collision -> suppression de la forme ajoutée 
        
        boolean isObstacle = compteur_piece > 8 ; // si compteur > 8 alors on ajoute un obstacle, sinon on ajoute une forme de joueur

        boolean isPlayer1 = compteur_piece > 4; // si compteur > 4 alors c'est le joueur 1 qui joue, sinon c'est le joueur 2

        List<Drawable> current = isObstacle ? obstacle : (isPlayer1 ? shapes : shapes2);
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






    protected void score_game() { // retourne le score du jeu, il est calculé en fonction du nombre de formes de chaque type pour chaque joueur, le score est égal au nombre de rectangles du joueur 1 moins le nombre de rectangles du joueur 2 plus le nombre de cercles du joueur 1 moins le nombre de cercles du joueur 2, un score positif signifie que le joueur 1 est en avance, un score négatif signifie que le joueur 2 est en avance, un score nul signifie que les deux joueurs sont à égalité


        for (Drawable s : shapes) {
            if (s instanceof RectangleShape) {

                int forme_valeur = s.getBounds().getSize().width * s.getBounds().getSize().height; // Calcul de la valeur de la forme en fonction de sa taille (aire du rectangle)
                score_joueur1 += forme_valeur; // Ajout de la valeur de la forme au score du joueur 1

            } else if (s instanceof CircleShape) {
                int forme_valeur = (int) (Math.PI * Math.pow(s.getBounds().getSize().width / 2, 2)); // Calcul de la valeur de la forme en fonction de sa taille (aire du cercle)²
                score_joueur1 += forme_valeur; // Ajout de la valeur de la forme au score du joueur 1
            }
        

        for (Drawable y : shapes2) {
            if (y instanceof RectangleShape) {
                int forme_valeur = y.getBounds().getSize().width * y.getBounds().getSize().height; // Calcul de la valeur de la forme en fonction de sa taille (aire du rectangle)
                score_joueur2 += forme_valeur; // Ajout de la valeur de la forme au score du joueur 2
            } else if (y instanceof CircleShape) {
                int forme_valeur = (int) (Math.PI * Math.pow(y.getBounds().getSize().width / 2, 2)); // Calcul de la valeur de la forme en fonction de sa taille (aire du cercle)
                score_joueur2 += forme_valeur; // Ajout de la valeur de la forme au score du joueur 2
            } 
        }}

    }


    public List<Drawable> getShapes() { // retourne la liste des formes du joueur actuel
        return (compteur_piece > 4) ? shapes : shapes2;
    }

    public String getCurrentPlayer() {   // retourne le nom du joueur actuel
        return (compteur_piece > 4) ? "joueur_1" : "joueur_2";

    }
    

    public int getscorejoueur1() { // retourne le score du joueur 1
        return score_joueur1;
    }

    public int getscorejoueur2() { // retourne le score du joueur 2
        return score_joueur2;
    }


    public void removeShape(Drawable s) { // Méthode pour supprimer une forme du modèle, elle est utilisée par l'IA pour annuler les coups testés lors de la recherche du meilleur coup, elle supprime la forme de la liste correspondante (shapes ou shapes2) en fonction du joueur actuel
        if (shapes.contains(s)) {
            shapes.remove(s);
        } else if (shapes2.contains(s)) {
            shapes2.remove(s);
        }
    }

}