package Game.model.collision;

import Game.model.Form.CircleShape;
import Game.model.Form.RectangleShape;
import Game.model.Form.Shape;

public class CollisionVisiteur implements IntersectionVisiteur { // Visiteur pour vérifier les collisions entre formes, implémente l'interface IntersectionVisiteur pour pouvoir être utilisé dans les formes

    private Shape other;
    private boolean result;

    public CollisionVisiteur(Shape other) { // Constructeur qui prend l'autre forme à comparer
        this.other = other;
    }

    public boolean getResult() { // Retourne le résultat de la collision après avoir visité la forme
        return result;
    }

    @Override
    public void visit(RectangleShape r1) { // Vérifie les collisions entre un RectangleShape et l'autre forme
        if (other instanceof RectangleShape) {
            RectangleShape r2 = (RectangleShape) other;
            result = !(r1.getX() + r1.getWidth() <= r2.getX() || r2.getX() + r2.getWidth() <= r1.getX() ||
                      r1.getY() + r1.getHeight() <= r2.getY() || r2.getY() + r2.getHeight() <= r1.getY());

        } else if (other instanceof CircleShape) {
            CircleShape c = (CircleShape) other;
            result = intersectCircleRectangle(c, r1);
        }
    }

    @Override
    public void visit(CircleShape c1) { // Vérifie les collisions entre un CircleShape et l'autre forme
        if (other instanceof CircleShape) {
            CircleShape c2 = (CircleShape) other;
            result = intersectCircleCircle(c1, c2);

        } else if (other instanceof RectangleShape) {
            RectangleShape r = (RectangleShape) other;
            result = intersectCircleRectangle(c1, r);
        }
    }

    private boolean intersectCircleCircle(CircleShape c1, CircleShape c2) { // Vérifie les collisions entre deux cercles en utilisant la formule de distance entre les centres et les rayons
        double x1 = c1.getX() + c1.getWidth() / 2;
        double y1 = c1.getY() + c1.getHeight() / 2;
        double r1 = c1.getWidth() / 2;

        double x2 = c2.getX() + c2.getWidth() / 2;
        double y2 = c2.getY() + c2.getHeight() / 2;
        double r2 = c2.getWidth() / 2;

        double dx = x1 - x2;
        double dy = y1 - y2;

        return dx * dx + dy * dy <= (r1 + r2) * (r1 + r2);
    }

    private boolean intersectCircleRectangle(CircleShape c, RectangleShape r) { // Vérifie les collisions entre un cercle et un rectangle en utilisant la formule de distance entre le centre du cercle et le point le plus proche du rectangle
        double cx = c.getX() + c.getWidth() / 2;
        double cy = c.getY() + c.getHeight() / 2;
        double radius = c.getWidth() / 2;

        double closestX = Math.max(r.getX(), Math.min(cx, r.getX() + r.getWidth()));
        double closestY = Math.max(r.getY(), Math.min(cy, r.getY() + r.getHeight()));

        double dx = cx - closestX;
        double dy = cy - closestY;

        return dx * dx + dy * dy <= radius * radius;
    }
}