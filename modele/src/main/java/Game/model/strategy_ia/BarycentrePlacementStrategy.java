package Game.model.strategy_ia;

import Game.model.Form.RectangleShape;
import Game.model.Form.Shape;
import Game.model.Point;
import Game.model.Plateau;
import java.util.List;

public class BarycentrePlacementStrategy implements PlacementStrategy { 

    @Override
    public Shape placeShape(Plateau plateau) {
        
        List<Shape> formesPlacees = plateau.getFormePlacees();

        if (formesPlacees.isEmpty()) {
            // Centre du plateau
            int centerX = plateau.getHauteur() / 2;
            int centerY = plateau.getLargeur() / 2;

            // Rectangle 1x1 centré
            Point p1 = new Point(centerX, centerY);
            Point p2 = new Point(centerX + 1, centerY + 1);

            return new RectangleShape(p1, p2);

        } else {
            // Calcul barycentre
            double sumX = 0;
            double sumY = 0;

            for (Shape shape : formesPlacees) {
                sumX += shape.getX() + shape.getWidth() / 2.0;
                sumY += shape.getY() + shape.getHeight() / 2.0;
            }

            double barycentreX = sumX / formesPlacees.size();
            double barycentreY = sumY / formesPlacees.size();

            int newX = (int) Math.round(barycentreX);
            int newY = (int) Math.round(barycentreY);

            Point p1 = new Point(newX, newY);
            Point p2 = new Point(newX + 1, newY + 1);

            return new RectangleShape(p1, p2);
        }
    }
}