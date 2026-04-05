package Game.model;

import java.awt.*;
import java.lang.Math;

public class CircleShape implements Shape {

    private Point center;   
    private int radius;    
    
    public CircleShape(Point center, int radius) {
        this.center = center;
        this.radius = radius;
    }
    
    // Getter : //
    
    // Hitbox //
    @Override
    public Rectangle getBounds() {
        return new Rectangle(center.x - radius, center.y - radius, 2 * radius, 2 * radius);
    }
    
    public Point getCenter() {
        return center;
    }
    
    public int getRadius() {
        return radius;
    }
    
    // Setter : //
    public void setRadius(int radius) {
        this.radius = radius;
    }

    
    @Override
    public void move(int dx, int dy) {
        center.x += dx;
        center.y += dy;
        // firechange
    }

    // On vérifie si un point est à l'intérieur du cercle //
    @Override
    public boolean contains(Point p) {
        int dx = p.x - center.x;
        int dy = p.y - center.y;
        return dx*dx + dy*dy <= radius*radius;
    }

	@Override
	public double surface() {
		return Math.PI * (radius * radius);
	}
	
	@Override
	public void resize(Point lastPoint, Point newPoint) {
		int dx = newPoint.x - center.x;
		int dy = newPoint.y - center.y;
		this.radius = (int) Math.sqrt(dx * dx + dy * dy);
	}
}