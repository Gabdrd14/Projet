package Game.state;

import Game.model.Plateau;
import Game.model.Form.CircleShape;
import Game.command.CommandHandler;
import Game.command.CommandCreateCircle;
import Game.model.Point;

public class StateCreateCircle implements StateController {

    private Plateau plateau;
    private CommandHandler commandHandler;

    private CircleShape currentCircle; 
    private Point startPoint;         

    public StateCreateCircle(Plateau plateau, CommandHandler commandHandler) {
        this.plateau = plateau;
        this.commandHandler = commandHandler;
    }

    @Override
    public void mousePressed(Point p) {
        
    	// On enregistre le point de départ et on crée un cercle temporaire //
    	startPoint = p;
        currentCircle = new CircleShape(startPoint, 0);
    }

    @Override
    public void mouseDragged(Point p) {
        if (currentCircle != null && startPoint != null) {
            
        	// On calcule la distance entre le point initial et la position actuelle //
        	double dx = p.getX() - startPoint.getX();
            double dy = p.getY() - startPoint.getY();
            int radius = (int) Math.sqrt(dx * dx + dy * dy);
            
            // On met à jour le rayon du cercle en temps réel //
            currentCircle.setRadius(radius); 
        }
    }

    @Override
    public void mouseReleased(Point p) {
        if (currentCircle != null) {
            
        	// On recalcule le rayon final du cercle au relâchement //
        	double dx = p.getX() - startPoint.getX();
            double dy = p.getY() - startPoint.getY();
            int radius = (int) Math.sqrt(dx * dx + dy * dy);

            currentCircle.setRadius(radius);
            
            // On transforme l’action en commande pour gestion undo/redo //
            commandHandler.handle(
                new CommandCreateCircle(plateau, currentCircle)
            );
        }
        
        // On réinitialise l’état après création //
        currentCircle = null;
        startPoint = null;
    }
    
    public CircleShape getCurrentcirc() {
        return currentCircle;
    }
}