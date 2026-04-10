package Game.model;
import java.util.ArrayList;
import java.util.List;
import java.awt.Dimension;
import java.awt.Toolkit;
import Game.model.Form.CircleShape;
import Game.model.Form.RectangleShape;
import Game.model.Form.Shape;
import Game.model.collision.CollisionUtil;
import Game.model.entity.Entity;
import Game.model.observer.AbstractModeleEcoutable;
import Game.model.stratGen.StrategiePlateau;
import Game.model.stratGen.StrategiePlateau;
import Game.model.observer.AbstractModeleEcoutable;

/**
     * Classe représentant le plateau de jeu
     * Elle fournit des méthodes pour gérer les joueurs, les obstacles, les collisions, le score
     * et la génération d'obstacles selon une stratégie définie.
     * Elle notifie également les observateurs des changements de plateau.
     * 
     */

public class Plateau extends AbstractModeleEcoutable {
    private int largeur;
    private int hauteur;
    private List<Entity> joueurs; // liste des joueurs
    private Entity joueurCourant; // joueur courant
    private List<Shape> liste_obstacle ; // liste des obstacles fixes sur le plateau  
    protected int compteur_piece ; // Compteur de pièces restantes pour chaque joueur, il commence à 8 et diminue à chaque ajout de forme, lorsque le compteur atteint 0, la partie est terminée
    private StrategiePlateau strategieDeGen; // stratégie de génération d'obstacles


    public Plateau(StrategiePlateau strategieDeGen,List<Entity> joueurs) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        this.largeur = screenSize.width;
        this.hauteur = screenSize.height;
        this.liste_obstacle = new ArrayList<>();
        this.joueurs = joueurs;
        this.joueurCourant = joueurs.get(0); // Initialiser le joueur courant au premier joueur ajouté
        this.compteur_piece = 8 ;
        this.strategieDeGen = strategieDeGen;
        this.genererObs();

    }

    
    public String getNameJoueurCourant() { // Méthode pour définir le joueur courant, elle est utilisée par la classe Game pour changer le joueur courant à chaque tour, elle définit le joueur courant
        return this.joueurCourant.getName();
    }
    
    public void setJoueurCourant(Entity joueur) { // Méthode pour définir le joueur courant, elle est utilisée par la classe Game pour changer le joueur courant à chaque tour, elle définit le joueur courant
        this.joueurCourant = joueur;
    }



    /**
     * Vérifie s'il existe une collision entre les formes du joueur courant et d'autres objets du plateau.
     * @return true si une collision est détectée, false sinon
     */
    public boolean collision() {
        // Récupère la liste des formes du joueur courant
        List<Shape> formesCourantes = joueurCourant.getShapes();

        for (int i = 0; i < formesCourantes.size(); i++) {
            Shape a = formesCourantes.get(i);

            // Vérifie les collisions entre les formes du joueur courant
            // On commence à j = i+1 pour éviter les comparaisons redondantes (a ne s'intersecte pas avec elle-même)
            for (int j = i + 1; j < formesCourantes.size(); j++) {
                if (CollisionUtil.intersects(a, formesCourantes.get(j))) {
                    return true; // Une collision trouvée, la mise en place est invalide
                }
            }

            // Vérifie les collisions avec les formes des autres joueurs
            for (Entity joueur : joueurs) {
                if (joueur == joueurCourant) {
                    continue; // On saute le joueur courant puisqu'on l'a déjà traité
                }

                // Boucle sur les formes de cet autre joueur
                for (Shape b : joueur.getShapes()) {
                    if (CollisionUtil.intersects(a, b)) {
                        return true; // Une collision trouvée avec un autre joueur
                    }
                }
            }
            
            // COLLISION AVEC LES OBSTACLES : Vérifie les collisions avec les obstacles fixes du plateau
            for (Shape obstacle : liste_obstacle) {
                if (CollisionUtil.intersects(a, obstacle)) {
                    return true; // Une collision trouvée avec un obstacle
                }
            }
        }

        // Aucune collision détectée parmi tous les contrôles effectués
        return false;
    }




    protected void score_game(Entity joueur) { // calcule  de score d'un joueur 

        for (Shape s : joueur.getShapes()) {
            if (s instanceof RectangleShape) {

                Double forme_valeur = s.surface(); // Calcul de la valeur de la forme en fonction de sa taille (aire du rectangle)
                joueur.setScore(joueur.getScore() + forme_valeur); 
            } else if (s instanceof CircleShape) {
                Double forme_valeur = s.surface(); // Calcul de la valeur de la forme en fonction de sa taille (aire du cercle)
                joueur.setScore(joueur.getScore() + forme_valeur); 
            }
        }

}




    public String getCurrentPlayer() {   // retourne le nom du joueur actuel
        return (compteur_piece > 4) ? "joueur_1" : "joueur_2";

    }
    

    public void ajouterObstacle(Shape obstacle) { // Méthode pour ajouter un obstacle au modèle, elle est utilisée par la stratégie de génération d'obstacles pour ajouter des obstacles fixes sur le plateau, elle ajoute l'obstacle à la liste des obstacles
        liste_obstacle.add(obstacle);
    }

    
    public List<Shape> getObstacles() { // Méthode pour obtenir la liste des obstacles du modèle, elle est utilisée par la vue pour dessiner les obstacles sur le plateau, elle retourne la liste des obstacles
         return liste_obstacle;
     }

    
    public void viderObstacles() { // Méthode pour vider la liste des obstacles du modèle, elle est utilisée par la stratégie de génération d'obstacles pour réinitialiser les obstacles avant d'en générer de nouveaux, elle vide la liste des obstacles
        liste_obstacle.clear();

    }

    /*
        * Méthode pour générer les obstacles sur le plateau en utilisant la stratégie de génération définie.
    */
    public void genererObs() {
        if (strategieDeGen == null) {
            throw new IllegalStateException("Aucune stratégie de génération définie.");
        }

        liste_obstacle.clear();
        strategieDeGen.genererObstacles(this);
        //firechange(); // Notifier les observateurs du changement de plateau
    }

    //methode pour ajouter une forme cree par un joueur.
    public void ajouterFormePlacee(Shape forme){  
        System.out.println("joueur courant : " + joueurCourant.getName() + " ajoute la forme : " + forme.toString());        //boolean isObstacle = compteur_piece > 8 ; // si compteur > 8 alors on ajoute un obstacle, sinon on ajoute une forme de joueur
        
        List<Shape> current = this.joueurCourant.getShapes();
        
        current.add(forme);

        if (collision()) {
            current.remove(current.size() - 1);
            System.out.println("Collision -> suppression");
        }
        score_game(joueurCourant);
        fireChange();

    }



    // methode qui supprime une forme placée par un joueur .
    public void supprimerFormePlacee(Shape forme){ // test 
        for (Shape s : joueurCourant.getShapes()) {
            if (s.equals(forme)) {
                joueurCourant.getShapes().remove(s);
                System.out.println("supression d'une forme de" + joueurCourant.getName() + " :" + forme);
                return;
            }
        }

        fireChange();

    }


    // Méthode pour obtenir la liste de toutes les formes placées par tous les joueurs.
    public List<Shape> getFormePlacees() {
        List<Shape> allShape = new ArrayList<>();
        for (Entity joueur : joueurs) {
            allShape.addAll(joueur.getShapes());
        }

        return allShape;
    }



    public int getLargeur() {
        return largeur;
    }

    public int getHauteur() {
        return hauteur;
    }



    public void setStrategieDeGen(StrategiePlateau strategieDeGen) {
        this.strategieDeGen = strategieDeGen;
        fireChange(); // Notifier les observateurs du changement de stratégie
    }

}