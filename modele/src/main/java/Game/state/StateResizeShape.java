package Game.state;

import Game.model.Point;

import Game.command.CommandHandler;
import Game.command.CommandResizeShape;
import Game.model.Plateau;
import Game.model.Form.Shape;

public class StateResizeShape implements StateController {

    private Plateau plateau;       
    private Shape selectedShape;
    private Point lastPoint;
    private Point newPoint;
    private CommandHandler commandHandler;

    public StateResizeShape(Plateau plateau, CommandHandler commandHandler) {
        this.plateau = plateau;
        this.commandHandler = commandHandler;
    }

    @Override
    public void mousePressed(Point p) {
        
    	// On sélectionne la forme à redimensionner si elle contient le point cliqué //
    	selectedShape = null;
        for (Shape s : plateau.getFormePlacees()) {
            if (s.contains(p)) {
                selectedShape = s;
                lastPoint = p;  // On mémorise le point de départ pour le resize //
                break;
            }
        }
    }

    @Override
    public void mouseDragged(Point p) {
        if (selectedShape != null) {
            
        	// On applique un redimensionnement en temps réel pendant le drag //
        	selectedShape.resize(lastPoint, p);
            
        	// On garde la dernière position de la souris
        	newPoint = p; 
        }
    }

    @Override
    public void mouseReleased(Point p) {
        if (selectedShape != null) {
            newPoint = p; 
            
            // On applique le redimensionnement final avant vérification //
            selectedShape.resize(lastPoint, newPoint);
            
            // On vérifie si le resize entraîne une collision //
            if (plateau.collision()) {
            	
            	// On annule le redimensionnement en cas de collision
                selectedShape.resize(newPoint, lastPoint);
            } 
            
            else {
            	// On enregistre l’action dans l’historique undo/redo //
            	// On évite de compter un déplacement comme une nouvelle forme créée //
                commandHandler.record(new CommandResizeShape(selectedShape, lastPoint, newPoint));
                plateau.notifyObservers();
                System.out.println("Modification de taille réalisé par le" + plateau.getNameJoueurCourant());
            }
            
            // On réinitialise l’état après redimensionnement //
            selectedShape = null;
            lastPoint = null;
            newPoint = null;
        }
    }
    
    public Shape getCurrentShape() {
        return selectedShape;
    }
}