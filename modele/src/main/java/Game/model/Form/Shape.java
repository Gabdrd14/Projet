package Game.model.Form;

import java.awt.*;

import Game.model.collision.IntersectionVisiteur;

public interface Shape {
    
	Rectangle getBounds();
    
    void move(int dx, int dy);
    
    boolean contains(Point p);
    
    double surface();
    
    void resize(Point lastPoint, Point newPoint); 

    void accept(IntersectionVisiteur  Visiteur);

    
}
