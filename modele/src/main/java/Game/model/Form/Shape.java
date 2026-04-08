package Game.model.Form;

import Game.model.Point;
import Game.model.collision.IntersectionVisiteur;

public interface Shape {
    
    
    double getX();
    
    double getY();
    
    double getWidth();
    
    double getHeight();
    
    void move(double dx, double dy);
    
    boolean contains(Point p);
    
    double surface();
    
    void resize(Point lastPoint, Point newPoint); 

    void accept(IntersectionVisiteur  Visiteur);
    
}
