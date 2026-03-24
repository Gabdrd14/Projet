package Game.model;

import java.awt.*;
import java.lang.Math;

public class CircleShape implements Shape {

    private Point center;   
    private int radius;    
    private String joueur;  

    public CircleShape(Point center, int radius, String joueur) {
        this.center = center;
        this.radius = radius;
        this.joueur = joueur;
    }

    // Hitbox //
    @Override
    public Rectangle getBounds() {
        return new Rectangle(center.x - radius, center.y - radius, 2 * radius, 2 * radius);
    }

    @Override
    public void move(int dx, int dy) {
        center.x += dx;
        center.y += dy;
    }

    // Vérifie si un point est à l'intérieur du cercle //
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
    
}