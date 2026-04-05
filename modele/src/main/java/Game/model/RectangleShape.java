package Game.model;

import java.awt.*;

public class RectangleShape implements Shape {

    private Point p1, p2;
    private String joueur;

    public RectangleShape(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    // Getter //
    public int getX() { 
    	return Math.min(p1.x, p2.x); 
    }
    
    public int getY() { 
    	return Math.min(p1.y, p2.y); 
    }
    
    public int getWidth() { 
    	return Math.abs(p1.x - p2.x); 
    }
    
    public int getHeight() { 
    	return Math.abs(p1.y - p2.y); 
    }
    
    @Override
    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
    
    // Setter //
    
    // Méthode pour changer la position finale //
    public void setEndPoint(Point end) {
        this.p2 = end;
    }
    

    @Override
    public void move(int dx, int dy) {
        p1.x += dx;
        p1.y += dy;
        p2.x += dx;
        p2.y += dy;
        // firechange 
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

	@Override
	public void resize(Point lastPoint, Point newPoint) {
	    int dx = newPoint.x - lastPoint.x;
	    int dy = newPoint.y - lastPoint.y;

	    p2.x += dx;
	    p2.y += dy;
	}
	
}