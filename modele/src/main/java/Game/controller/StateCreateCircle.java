package Game.controller;

import java.awt.Point;
import java.util.List;
import Game.model.Shape;
import Game.model.CircleShape;

public class StateCreateCircle extends StateController {

    private List<Shape> shapes;  
    private CircleShape currentCircle; 
    private Point startPoint;         

    public StateCreateCircle(List<Shape> shapes) {
        this.shapes = shapes;
    }

    @Override
    public void mousePressed(Point p) {
        startPoint = p;
        currentCircle = new CircleShape(startPoint, 0, "Joueur1");
        shapes.add(currentCircle);
    }

    @Override
    public void mouseDragged(Point p) {
        if (currentCircle != null && startPoint != null) {
            int dx = p.x - startPoint.x;
            int dy = p.y - startPoint.y;
            int radius = (int) Math.sqrt(dx * dx + dy * dy);
            currentCircle = new CircleShape(startPoint, radius, "Joueur1");
            shapes.set(shapes.size() - 1, currentCircle); // on remplace le cercle temporaire //
        }
    }

    @Override
    public void mouseReleased(Point p) {
        currentCircle = null;
        startPoint = null;
    }
}