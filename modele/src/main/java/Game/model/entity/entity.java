package Game.model.entity;


import Game.model.Form.Shape;
import java.util.List;
import java.util.ArrayList;

public abstract class entity {

    private List<Shape> liste; // Liste d'objets que l'entité peut porter
    private int score;
    private int compteur_piece ; // Compteur de pièces restantes pour chaque joueur, il commence à 8 et diminue à chaque ajout de forme, lorsque le compteur atteint 0, la partie est terminée

    public entity(){


        this.liste = new ArrayList<>();
        this.score = 0;
        this.compteur_piece = 4;



        
    }
    
}
