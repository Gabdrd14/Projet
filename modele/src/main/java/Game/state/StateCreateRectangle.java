package Game.state;

import java.awt.Point;

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
        startPoint = p;
        currentRect = new RectangleShape(startPoint, startPoint);
        // On n'ajoute plus au modèle à cet endroit //
    }

    @Override
    public void mouseDragged(Point p) {
        if (currentRect != null) {
            currentRect.setEndPoint(p); 
        }
    }

    @Override
    public void mouseReleased(Point p) {
        if (currentRect != null) {
            currentRect.setEndPoint(p);
            
            commandHandler.handle(
                new CommandCreateRectangle(plateau, currentRect)
            );
        }

        currentRect = null;
        startPoint = null;
    }
    
    public RectangleShape getCurrentRect() {
        return currentRect;
    }
}