package Game.model.Form;

import Game.model.Point;
import Game.model.collision.IntersectionVisiteur;
import Game.model.observer.AbstractModeleEcoutable;

public class CircleShape extends AbstractModeleEcoutable implements Shape {

    private Point center;
    private int radius;

    public CircleShape(Point center, int radius) {
        this.center = center;
        this.radius = radius;
    }

    // Getter //
    @Override
    public double getX() {
        return center.getX() - radius;
    }

    @Override
    public double getY() {
        return center.getY() - radius;
    }

    @Override
    public double getWidth() {
        return 2 * radius;
    }

    @Override
    public double getHeight() {
        return 2 * radius;
    }

    public Point getCenter() {
        return center;
    }

    public int getRadius() {
        return radius;
    }

    
    // Setter //
    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    public void move(double dx, double dy) {
    	// On déplace le centre du cercle //
    	center.translation(dx, dy);
    }

    // On vérifie si un point est dans le cercle //
    @Override
    public boolean contains(Point p) {
        
    	// On calcule la distance entre le point et le centre //
    	double dx = p.getX() - center.getX();
        double dy = p.getY() - center.getY();
        return dx * dx + dy * dy <= radius * radius;
    }

    @Override
    public double surface() {
        return Math.PI * radius * radius;
    }

    @Override
    public void resize(Point lastPoint, Point newPoint) {
        
    	// On recalcule le rayon à partir de la distance entre le centre et la souris //
    	double dx = newPoint.getX() - center.getX();
        double dy = newPoint.getY() - center.getY();
        this.radius = (int) Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public void accept(IntersectionVisiteur visitor) {
        visitor.visit(this);
    }
}