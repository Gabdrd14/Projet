// package Game.model;

// import java.lang.management.PlatformLoggingMXBean;
// import java.util.Random;
// import Game.model.Form.CircleShape;
// import Game.model.Form.RectangleShape;

// import Game.model.Point;


// public class StratGen2 implements StrategiePlateau {
//     protected int nbObstacles = 4;
//     private Random random = new Random();

//     public StratGen2(int nbObstacles) {
//         this.nbObstacles = nbObstacles;
//     }



//     public void setNbObstacles(int nbObstacles) {
//         this.nbObstacles = nbObstacles;
//     }
//     public int getNbObstacles() {
//         return nbObstacles;
//     }

//     // @Override
//     // public void genererObstacles(Plateau plateau) {
//     //     int largeurPlateau = plateau.getLargeur();
//     //     int hauteurPlateau = plateau.getHauteur();

//     //     for (int i = 0; i < 4; i++) {
//     //         if (random.nextBoolean()) {
//     //             int rayon = 10 + random.nextInt(30);
//     //             int x = random.nextInt(largeurPlateau);
//     //             int y = random.nextInt(hauteurPlateau);
//     //             plateau.ajouterObstacle(new CircleShape(new Point(x, y), rayon));
//     //         } else {
//     //             int largeur = 20 + random.nextInt(40);
//     //             int hauteur = 20 + random.nextInt(40);
//     //             int x = random.nextInt(largeurPlateau);
//     //             int y = random.nextInt(hauteurPlateau);
//     //             plateau.ajouterObstacle(new RectangleShape(new Point(x, y), new Point(x,y)));
//     //         }
//     //     }
//     // }



// public void genererObstacles(Plateau plateau) {
//     Random random = new Random();

//     int largeurMinRect = 20;
//     int largeurMaxRect = 80;
//     int hauteurMinRect = 20;
//     int hauteurMaxRect = 80;

//     int largeurPlateau = plateau.getLargeur();
//     int hauteurPlateau = plateau.getHauteur();

//     for (int i = 0; i < getNbObstacles(); i++) {

//         double rectWidth = largeurMinRect + random.nextInt(largeurMaxRect - largeurMinRect + 1);
//         double rectHeight = hauteurMinRect + random.nextInt(hauteurMaxRect - hauteurMinRect + 1);

//         double x1 = random.nextDouble() * (largeurPlateau - rectWidth);
//         double y1 = random.nextDouble() * (hauteurPlateau - rectHeight);

//         Point p1 = new Point(x1, y1);
//         Point p2 = new Point(x1 + rectWidth, y1 + rectHeight);

//         RectangleShape rectangle = new RectangleShape(p1, p2);

//         plateau.ajouterObstacle(rectangle); // ou ajouterFormePlacee(rectangle) selon ton besoin
//     }
// }
// }

package Game.model;

import java.util.Random;

import Game.model.Form.CircleShape;
import Game.model.Form.RectangleShape;
import Game.model.Point;

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
        int largeurPlateau = plateau.getLargeur();
        int hauteurPlateau = plateau.getHauteur();

        int largeurMinRect = 20;
        int largeurMaxRect = 80;
        int hauteurMinRect = 20;
        int hauteurMaxRect = 80;

        int rayonMin = 10;
        int rayonMax = 40;

        for (int i = 0; i < getNbObstacles(); i++) {

            boolean estRectangle = random.nextBoolean();

            if (estRectangle) {
                double rectWidth = largeurMinRect + random.nextInt(largeurMaxRect - largeurMinRect + 1);
                double rectHeight = hauteurMinRect + random.nextInt(hauteurMaxRect - hauteurMinRect + 1);

                double x1 = random.nextDouble() * (largeurPlateau - rectWidth);
                double y1 = random.nextDouble() * (hauteurPlateau - rectHeight);

                Point p1 = new Point(x1, y1);
                Point p2 = new Point(x1 + rectWidth, y1 + rectHeight);

                RectangleShape rectangle = new RectangleShape(p1, p2);
                plateau.ajouterObstacle(rectangle);

            } else {
                int rayon = rayonMin + random.nextInt(rayonMax - rayonMin + 1);

                double x = rayon + random.nextDouble() * (largeurPlateau - 2 * rayon);
                double y = rayon + random.nextDouble() * (hauteurPlateau - 2 * rayon);

                CircleShape cercle = new CircleShape(new Point(x, y), rayon);
                plateau.ajouterObstacle(cercle);
            }
        }
    }
}