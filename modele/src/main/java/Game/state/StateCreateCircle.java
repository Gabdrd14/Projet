package Game.state;

import java.awt.Point;

import Game.model.Plateau;
import Game.model.RectangleShape;
import Game.model.CircleShape;
import Game.command.CommandHandler;
import Game.command.CommandCreateCircle;

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
            int dx = p.x - startPoint.x;
            int dy = p.y - startPoint.y;
            int radius = (int) Math.sqrt(dx * dx + dy * dy);

            currentCircle.setRadius(radius); 
        }
    }

    @Override
    public void mouseReleased(Point p) {
        if (currentCircle != null) {
            int dx = p.x - startPoint.x;
            int dy = p.y - startPoint.y;
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