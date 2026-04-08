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
            selectedShape.resize(lastPoint, p);
            newPoint = p; // On mémorise la nouvelle taille pour quand le drag se termine //
        }
    }

    @Override
    public void mouseReleased(Point p) {
        if (selectedShape != null) {
            newPoint = p; 
            
            // Appliquer temporairement le resize pour vérifier les collisions
            selectedShape.resize(lastPoint, newPoint);
            
            // Vérifier les collisions après le resize
            if (plateau.collision()) {
                // Annuler le resize
                selectedShape.resize(newPoint, lastPoint);
            } else {
                // Pas de collision, enregistrer la commande
                commandHandler.handle(new CommandResizeShape(selectedShape, lastPoint, newPoint));
            }
  
            selectedShape = null;
            lastPoint = null;
            newPoint = null;
        }
    }
    
    public Shape getCurrentShape() {
        return selectedShape;
    }
}