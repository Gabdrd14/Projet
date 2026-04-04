package com.example;

import java.awt.Point;

public class ai_player {




    public Drawable choose_move(Model model) { // Méthode pour choisir le meilleur coup à jouer pour l'IA, elle utilise une stratégie simple basée sur le score du jeu, elle génère tous les coups possibles (formes à ajouter) et évalue le score de chaque coup en utilisant la méthode score_game du modèle, elle retourne le coup qui maximise le score pour l'IA (joueur 2) ou minimise le score pour l'adversaire (joueur 1)
        Drawable best_move = null;
        int best_score = Integer.MIN_VALUE;

        // Générer tous les coups possibles (formes à ajouter)
        for (int x1 = 0; x1 < 500; x1 += 50) {
            for (int y1 = 0; y1 < 500; y1 += 50) {
                for (int x2 = x1 + 10; x2 < 500; x2 += 50) {
                    for (int y2 = y1 + 10; y2 < 500; y2 += 50) {
                        Drawable move = ShapeFactory.createShape(Tool.RECTANGLE, new Point(x1, y1), new Point(x2, y2), "joueur_2");

                        model.addShape(move);
                        int score = model.getscorejoueur2() ;
                        if (score > best_score) {
                            best_score = score;
                            best_move = move;
                        }
                        model.removeShape(move); // Annuler le coup pour tester le suivant
                    }
                }
            }
        }

        return best_move;
    }


    public void play(Model model , int level_ia) { // Méthode pour jouer le coup choisi par l'IA, elle appelle la méthode choose_move pour obtenir le meilleur coup à jouer, puis elle ajoute ce coup au modèle en utilisant la méthode addShape du modèle, elle peut être appelée par le contrôleur après le tour de l'adversaire pour faire jouer l'IA
      
        int bias = 70 - (5* level_ia ); // Augmenter le biais pour les niveaux de difficulté plus élevés


        Drawable move = choose_move(model); // Niveau de difficulté de l'IA
        if (move != null) {

           Drawable bias_move = ShapeFactory.createShape(Tool.RECTANGLE, new Point(move.getBounds().x + bias, move.getBounds().y + bias), new Point(move.getBounds().x + move.getBounds().width + bias, move.getBounds().y + move.getBounds().height + bias), "joueur_2");
            model.addShape(bias_move); // Ajouter le coup choisi au modèle avec un biais pour les niveaux de difficulté plus élevés
        }
    }




}
