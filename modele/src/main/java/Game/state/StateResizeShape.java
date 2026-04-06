package Game.state;

import java.awt.Point;

import Game.command.CommandHandler;
import Game.command.CommandMoveShape;
import Game.command.CommandResizeShape;
import Game.model.Plateau;
import Game.model.Shape;

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
            
            commandHandler.handle(
            		new CommandResizeShape(selectedShape, lastPoint, newPoint)
            );
  
            selectedShape = null;
            lastPoint = null;
            newPoint = null;
        }
    }
}