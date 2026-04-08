package Game.state;

import Game.model.Point;

import Game.model.Plateau;
import Game.model.Form.Shape;
import Game.command.CommandHandler;
import Game.command.CommandMoveShape;

public class StateMoveShape implements StateController {

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
            double dx = p.getX() - lastPoint.getX();
            double dy = p.getY() - lastPoint.getY();
            selectedShape.move(dx, dy); 
            lastPoint = p;
        }
    }

    @Override
    public void mouseReleased(Point p) {
        if (selectedShape != null && lastPoint != null) {
            double dx = p.getX()- lastPoint.getX();
            double dy = p.getY() - lastPoint.getY();
            
       
            commandHandler.handle(
            		new CommandMoveShape(selectedShape, dx, dy)
            );
        }

        selectedShape = null;
        lastPoint = null;
    }
    
    public Shape getCurrentShape() {
        return selectedShape;
    }
}