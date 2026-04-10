package Game.state;

import Game.model.Plateau;
import Game.model.Point;
import Game.model.Form.Shape;
import Game.command.CommandHandler;
import Game.command.CommandMoveShape;

public class StateMoveShape implements StateController {

    private Plateau plateau;       
    private Shape selectedShape;
    private Point pressPoint;
    private Point lastPoint;
    private CommandHandler commandHandler;

    public StateMoveShape(Plateau plateau, CommandHandler commandHandler) {
        this.plateau = plateau;
        this.commandHandler = commandHandler;
    }

    @Override
    public void mousePressed(Point p) {
        
    	// On cherche la première forme qui contient le point cliqué //
    	selectedShape = null;
        for (Shape s : plateau.getFormePlacees()) {
            if (s.contains(p)) {
                selectedShape = s;
                pressPoint = p;
                lastPoint = p;
                break;
            }
        }
    }

    @Override
    public void mouseDragged(Point p) {
        
    	 // On déplace la forme en suivant le déplacement de la souris //
    	if (selectedShape != null && lastPoint != null) {
            double dx = p.getX() - lastPoint.getX();
            double dy = p.getY() - lastPoint.getY();
            selectedShape.move(dx, dy);
            lastPoint = p;
        }
    }

    @Override
    public void mouseReleased(Point p) {
        if (selectedShape != null && pressPoint != null && lastPoint != null) {
            
        	// On applique le dernier ajustement de déplacement //
        	double remainingDx = p.getX() - lastPoint.getX();
            double remainingDy = p.getY() - lastPoint.getY();
            
            if (remainingDx != 0 || remainingDy != 0) {
                selectedShape.move(remainingDx, remainingDy);
            }
            
            // On calcule le déplacement total effectué //
            double totalDx = p.getX() - pressPoint.getX();
            double totalDy = p.getY() - pressPoint.getY();
            if (totalDx != 0 || totalDy != 0) {
                
            	// On Vérifie les collisions après le mouvement //
                if (plateau.collision()) {
                    
                	// On annule le mouvement en cas de collision //
                    selectedShape.move(-totalDx, -totalDy);
                
                } else {
                    
                	// On enregistre le déplacement dans l’historique undo/redo //
                	// On évite de compter un déplacement comme une nouvelle forme créée //
                    commandHandler.record(new CommandMoveShape(selectedShape, totalDx, totalDy));
                    
                    plateau.notifyObservers();
                    System.out.println("Mouvement de forme réalisé par le " + plateau.getNameJoueurCourant());
                    
                }
            }
        }

        // On réinitialise l’état après déplacement //
        selectedShape = null;
        pressPoint = null;
        lastPoint = null;
    }
    
    public Shape getCurrentShape() {
        return selectedShape;
    }
}