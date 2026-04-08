package Game.model.Form;

import Game.model.AbstractModeleEcoutable;
import Game.model.Point;
import Game.model.collision.IntersectionVisiteur;

public class RectangleShape extends AbstractModeleEcoutable implements Shape {

    private Point p1, p2;

    public RectangleShape(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    // Getter //
    public Point getP1Point() {
        return this.p1;
    }

    public Point getP2Point() {
        return this.p2;
    }

    public double getX() {
        return Math.min(p1.getX(), p2.getX());
    }

    public double getY() {
        return Math.min(p1.getY(), p2.getY());
    }

    public double getWidth() {
        return Math.abs(p1.getX() - p2.getX());
    }

    public double getHeight() {
        return Math.abs(p1.getY() - p2.getY());
    }



    // Setter //
    public void setEndPoint(Point end) {
        this.p2 = end;
    }

    @Override
    public void move(double dx, double dy) {
        p1.translation(dx, dy);
        p2.translation(dx, dy);
    }

    // Vérifie si un point est à l'intérieur du rectangle //
    @Override
    public boolean contains(Point p) {
        double xMin = getX();
        double yMin = getY();
        double xMax = xMin + getWidth();
        double yMax = yMin + getHeight();

        return p.getX() >= xMin && p.getX() <= xMax
            && p.getY() >= yMin && p.getY() <= yMax;
    }

    @Override
    public double surface() {
        return getWidth() * getHeight();
    }

    @Override
    public void resize(Point lastPoint, Point newPoint) {
        this.p2 = newPoint;
    }

    @Override
    public void accept(IntersectionVisiteur visitor) {
        visitor.visit(this);
    }
}