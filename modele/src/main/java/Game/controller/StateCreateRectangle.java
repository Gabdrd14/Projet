package Game.controller;

import java.awt.Point;
import java.util.List;
import Game.model.Shape;
import Game.model.RectangleShape;

public class StateCreateRectangle extends StateController {

    private List<Shape> shapes; // Ajouter le plateau de jeu en paramètre //       
    private RectangleShape currentRect; 
    private Point startPoint;           
    
    public StateCreateRectangle(List<Shape> shapes) {
        this.shapes = shapes;
    }

    @Override
    public void mousePressed(Point p) {
        startPoint = p;
        currentRect = new RectangleShape(startPoint, startPoint);
        shapes.add(currentRect); // J'ajoute dans une instance de la liste forme qui appartient au plateau de jeu //
    }

    @Override
    public void mouseDragged(Point p) {
        if (currentRect != null && startPoint != null) {
            currentRect = new RectangleShape(startPoint, p);
            shapes.set(shapes.size() - 1, currentRect); // On remplace le rectangle temporaire //
        }
    }

    @Override
    public void mouseReleased(Point p) {
        currentRect = null;
        startPoint = null;
    }
}