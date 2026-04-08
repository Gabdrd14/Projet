package Game.model;
import Game.model.Form.CircleShape;
import Game.model.Form.RectangleShape;

public class StratGen1 implements StrategiePlateau {

    @Override
    public void genererObstacles(Plateau plateau) {

        plateau.ajouterObstacle(new CircleShape(new Point(93, 460), 30));

        plateau.ajouterObstacle(new CircleShape(new Point(359, 339), 48));

        plateau.ajouterObstacle(new CircleShape(new Point(1110, 318), 20));
        plateau.ajouterObstacle(new CircleShape(new Point(478, 529), 30));
        plateau.ajouterObstacle(new RectangleShape(
                new Point(619, 258),
                new Point(802, 327)
        ));

        plateau.ajouterObstacle(new CircleShape(new Point(400, 150), 30));

        plateau.ajouterObstacle(new RectangleShape(
                new Point(1327, 562),
                new Point(1360, 611)
        ));

        plateau.ajouterObstacle(new RectangleShape(
                new Point(1280, 13),
                new Point(1361, 57)
        ));

        plateau.ajouterObstacle(new RectangleShape(
                new Point(1, 571),
                new Point(83, 618)
        ));

        plateau.ajouterObstacle(new RectangleShape(
                new Point(1, 7),
                new Point(53,74)
        ));

    }
}