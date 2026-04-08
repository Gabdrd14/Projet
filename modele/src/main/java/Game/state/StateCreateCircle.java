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
        startPoint = p;
        currentCircle = new CircleShape(startPoint, 0);
        // On n'ajoute plus au modèle à cet endroit //
    }

    @Override
    public void mouseDragged(Point p) {
        if (currentCircle != null && startPoint != null) {
            double dx = p.getX() - startPoint.getX();
            double dy = p.getY() - startPoint.getY();
            int radius = (int) Math.sqrt(dx * dx + dy * dy);

            currentCircle.setRadius(radius); 
        }
    }

    @Override
    public void mouseReleased(Point p) {
        if (currentCircle != null) {
            double dx = p.getX() - startPoint.getX();
            double dy = p.getY() - startPoint.getY();
            int radius = (int) Math.sqrt(dx * dx + dy * dy);

            currentCircle.setRadius(radius);

            commandHandler.handle(
                new CommandCreateCircle(plateau, currentCircle)
            );
        }

        currentCircle = null;
        startPoint = null;
    }
    
    public CircleShape getCurrentcirc() {
        return currentCircle;
    }
}