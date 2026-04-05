package Game.state;

import java.awt.Point;
import Game.model.Shape;
import Game.model.Plateau;
import Game.command.CommandCreateRectangle;
import Game.command.CommandHandler;
import Game.command.CommandMoveShape;

public class StateMoveShape extends StateController {

    private Plateau plateau;       
    private Shape selectedShape;
    private Point lastPoint;
    private CommandHandler commandHandler;

    public StateMoveShape(Plateau plateau, CommandHandler commandHandler) {
        this.plateau = plateau;
        this.commandHandler = commandHandler;
    }

    @Override
    public void mousePressed(Point p) {
        selectedShape = null;
        for (Shape s : plateau.getFormePlacees()) {
            if (s.contains(p)) {
                selectedShape = s;
                lastPoint = p;
                break;
            }
        }
    }

    @Override
    public void mouseDragged(Point p) {
        if (selectedShape != null && lastPoint != null) {
            int dx = p.x - lastPoint.x;
            int dy = p.y - lastPoint.y;
            selectedShape.move(dx, dy); 
            lastPoint = p;
        }
    }

    @Override
    public void mouseReleased(Point p) {
        if (selectedShape != null && lastPoint != null) {
            int dx = p.x - lastPoint.x;
            int dy = p.y - lastPoint.y;
            
       
            commandHandler.handle(
            		new CommandMoveShape(selectedShape, dx, dy)
            );
        }

        selectedShape = null;
        lastPoint = null;
    }
}