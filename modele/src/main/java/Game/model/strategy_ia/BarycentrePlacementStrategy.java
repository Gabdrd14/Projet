package Game.model.strategy_ia;

import Game.model.Form.RectangleShape;
import Game.model.Form.Shape;
import Game.model.Point;
import Game.model.Plateau;
import java.util.List;
import java.util.Random;

public class BarycentrePlacementStrategy implements PlacementStrategy {

    private static final int SHAPE_W = 100;
    private static final int SHAPE_H = 80;
    private static final int MAX_TRIES = 50;
    private final Random random = new Random();

    @Override
    public Shape placeShape(Plateau plateau) {

        int largeur = plateau.getLargeur();   // horizontal (X)
        int hauteur = plateau.getHauteur();   // vertical   (Y)

        List<Shape> formesPlacees = plateau.getFormePlacees();

        double targetX, targetY;

        if (formesPlacees.isEmpty()) {
            // Centre du plateau
            targetX = largeur / 2.0;
            targetY = hauteur / 2.0;
        } else {
            // Barycentre de toutes les formes existantes
            double sumX = 0, sumY = 0;
            for (Shape shape : formesPlacees) {
                sumX += shape.getX() + shape.getWidth()  / 2.0;
                sumY += shape.getY() + shape.getHeight() / 2.0;
            }
            targetX = sumX / formesPlacees.size();
            targetY = sumY / formesPlacees.size();
        }

        // Essayer de placer la forme autour du barycentre avec un offset aléatoire
        // croissant jusqu'à trouver une position sans collision
        int spread = SHAPE_W;
        for (int attempt = 0; attempt < MAX_TRIES; attempt++) {
            int offsetX = (random.nextInt(2 * spread + 1) - spread);
            int offsetY = (random.nextInt(2 * spread + 1) - spread);

            int x = (int) Math.round(targetX + offsetX) - SHAPE_W / 2;
            int y = (int) Math.round(targetY + offsetY) - SHAPE_H / 2;

            // Garder dans les bornes du plateau
            x = Math.max(0, Math.min(x, largeur  - SHAPE_W));
            y = Math.max(0, Math.min(y, hauteur - SHAPE_H));

            Point p1 = new Point(x, y);
            Point p2 = new Point(x + SHAPE_W, y + SHAPE_H);

            RectangleShape candidate = new RectangleShape(p1, p2);

            // Vérifier collision manuellement avant de retourner
            boolean collision = false;
            for (Shape existing : formesPlacees) {
                if (candidate.getX() < existing.getX() + existing.getWidth()  &&
                    candidate.getX() + SHAPE_W > existing.getX()              &&
                    candidate.getY() < existing.getY() + existing.getHeight() &&
                    candidate.getY() + SHAPE_H > existing.getY()) {
                    collision = true;
                    break;
                }
            }

            if (!collision) {
                return candidate;
            }

            // Augmenter la plage de recherche si collision
            spread += SHAPE_W;
        }

        // Fallback : position aléatoire sur le plateau
        int x = random.nextInt(Math.max(1, largeur  - SHAPE_W));
        int y = random.nextInt(Math.max(1, hauteur - SHAPE_H));
        return new RectangleShape(new Point(x, y), new Point(x + SHAPE_W, y + SHAPE_H));
    }
}
