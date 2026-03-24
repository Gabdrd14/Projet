package Game.model;

import java.awt.*;

public class RectangleShape implements Shape {

    private Point p1, p2;
    private String joueur;

    public RectangleShape(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    private int getX() { return Math.min(p1.x, p2.x); }
    private int getY() { return Math.min(p1.y, p2.y); }
    private int getWidth() { return Math.abs(p1.x - p2.x); }
    private int getHeight() { return Math.abs(p1.y - p2.y); }
    
   
    @Override
    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void move(int dx, int dy) {
        p1.x += dx;
        p1.y += dy;
        p2.x += dx;
        p2.y += dy;
    }

    // Vérifie si un point est à l'intérieur du rectangle //
    @Override
    public boolean contains(Point p) {
        int xMin = getX();
        int yMin = getY();
        int xMax = xMin + getWidth();
        int yMax = yMin + getHeight();

        return p.x >= xMin && p.x <= xMax && p.y >= yMin && p.y <= yMax;
    }

	@Override
	public double surface() {
		 return getWidth() * getHeight();
	}
	
	
	
}