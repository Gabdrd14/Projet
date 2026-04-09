package Game.state;

import Game.model.Point;

import Game.model.Plateau;
import Game.model.Form.RectangleShape;
import Game.command.CommandHandler;
import Game.command.CommandCreateRectangle;

public class StateCreateRectangle implements StateController {

    private Plateau plateau;       
    private CommandHandler commandHandler;

    private RectangleShape currentRect; 
    private Point startPoint;           
    
    public StateCreateRectangle(Plateau plateau, CommandHandler commandHandler) {
        this.plateau = plateau;
        this.commandHandler = commandHandler;
    }

    @Override
    public void mousePressed(Point p) {
        
    	// On enregistre le point de départ et on crée un rectangle temporaire //
    	startPoint = p;
        currentRect = new RectangleShape(startPoint, startPoint);
    }

    @Override
    public void mouseDragged(Point p) {
        if (currentRect != null) {
            
        	// On met à jour dynamiquement le coin opposé du rectangle //
        	currentRect.setEndPoint(p); 
        }
    }

    @Override
    public void mouseReleased(Point p) {
        if (currentRect != null) {
            
        	// On fixe définitivement la taille du rectangle //
        	currentRect.setEndPoint(p);
            
        	// On transforme la création en commande pour undo/redo //
            commandHandler.handle(
                new CommandCreateRectangle(plateau, currentRect)
            );
        }
        
        // On réinitialise l’état après création //
        currentRect = null;
        startPoint = null;
    }
    
    public RectangleShape getCurrentRect() {
        return currentRect;
    }
}