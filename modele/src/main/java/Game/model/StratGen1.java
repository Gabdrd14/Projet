package Game.model;

import java.awt.Point;

import Game.model.Form.CircleShape;
import Game.model.Form.RectangleShape;

public class StratGen1 implements StrategiePlateau {

    @Override
    public void genererObstacles(Plateau plateau) {

        plateau.ajouterObstacle(new CircleShape(new Point(50, 50), 20));

        plateau.ajouterObstacle(new RectangleShape(
                new Point(100, 80),
                new Point(140, 140)
        ));

        plateau.ajouterObstacle(new CircleShape(new Point(200, 150), 30));
    }
}