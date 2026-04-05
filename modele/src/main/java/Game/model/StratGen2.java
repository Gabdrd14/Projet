package Game.model;

import java.util.Random;
import java.awt.Point;

public class StratGen2 implements StrategiePlateau {

    private Random random = new Random();

    @Override
    public void genererObstacles(Plateau plateau) {
        int largeurPlateau = plateau.getLargeur();
        int hauteurPlateau = plateau.getHauteur();

        for (int i = 0; i < 4; i++) {
            if (random.nextBoolean()) {
                int rayon = 10 + random.nextInt(30);
                int x = random.nextInt(largeurPlateau);
                int y = random.nextInt(hauteurPlateau);
                plateau.ajouterObstacle(new CircleShape(new Point(x, y), rayon));
            } else {
                int largeur = 20 + random.nextInt(40);
                int hauteur = 20 + random.nextInt(40);
                int x = random.nextInt(largeurPlateau);
                int y = random.nextInt(hauteurPlateau);
                plateau.ajouterObstacle(new RectangleShape(new Point(x, y), new Point(x,y)));
            }
        }
    }
}