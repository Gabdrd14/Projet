package Game.model.stratGen;

import java.util.Random;

import Game.model.Form.CircleShape;
import Game.model.Form.RectangleShape;
import Game.model.Plateau;
import Game.model.Point;
/**
     * Stratégie de génération d'obstacles aléatoire pour le plateau de jeu.
     * Cette stratégie génère un nombre spécifié d'obstacles de manière aléatoire, en choisissant entre des rectangles et des cercles.
     * Les obstacles sont placés à des positions aléatoires sur le plateau, en respectant les dimensions du plateau.
     */
public class StratGen2 implements StrategiePlateau {

    protected int nbObstacles = 4;
    private Random random = new Random();

    public StratGen2(int nbObstacles) {
        this.nbObstacles = nbObstacles;
    }

    public void setNbObstacles(int nbObstacles) {
        this.nbObstacles = nbObstacles;
    }

    public int getNbObstacles() {
        return nbObstacles;
    }

    @Override
    public void genererObstacles(Plateau plateau) {
        // Récupération des dimensions du plateau pour les calculs de position aléatoire
        int largeurPlateau = plateau.getLargeur();
        int hauteurPlateau = plateau.getHauteur();

        // Limites de taille pour les rectangles
        int largeurMinRect = 20;
        int largeurMaxRect = 80;
        int hauteurMinRect = 20;
        int hauteurMaxRect = 80;

        // Limites de taille pour les cercles
        int rayonMin = 10;
        int rayonMax = 40;

        // Boucle de génération pour chaque obstacle
        for (int i = 0; i < getNbObstacles(); i++) {

            // Choix aléatoire : vrai pour rectangle, faux pour cercle
            boolean estRectangle = random.nextBoolean();

            if (estRectangle) {
                // Génération d'un RECTANGLE

                double rectWidth = largeurMinRect + random.nextInt(largeurMaxRect - largeurMinRect + 1);
                
                double rectHeight = hauteurMinRect + random.nextInt(hauteurMaxRect - hauteurMinRect + 1);

                double x1 = random.nextDouble() * (largeurPlateau - rectWidth);
                
                double y1 = random.nextDouble() * (hauteurPlateau - rectHeight);

                // Création des deux points du rectangle (coins opposés)
                Point p1 = new Point(x1, y1);
                Point p2 = new Point(x1 + rectWidth, y1 + rectHeight);

                // Création du rectangle avec les deux points
                RectangleShape rectangle = new RectangleShape(p1, p2);
                
                // Ajout du rectangle au plateau
                plateau.ajouterObstacle(rectangle);

            } else {
                // Génération d'un CERCLE aléatoire
 
                int rayon = rayonMin + random.nextInt(rayonMax - rayonMin + 1);

                double x = rayon + random.nextDouble() * (largeurPlateau - 2 * rayon);
                
                double y = rayon + random.nextDouble() * (hauteurPlateau - 2 * rayon);

                // Création du cercle avec le centre et le rayon
                CircleShape cercle = new CircleShape(new Point(x, y), rayon);
                
                // Ajout du cercle au plateau
                plateau.ajouterObstacle(cercle);
            }
        }
    }
}