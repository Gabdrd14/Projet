package Game.model.Form;

import java.awt.Rectangle;
import Game.model.Point;
import Game.model.collision.IntersectionVisiteur;

public interface Shape {
    
	Rectangle getBounds();
    
    void move(double dx, double dy);
    
    boolean contains(Point p);
    
    double surface();
    
    void resize(Point lastPoint, Point newPoint); 

    void accept(IntersectionVisiteur  Visiteur);
    
}
