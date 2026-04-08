package Game.state;

import Game.model.Point;

import Game.model.Plateau;
import Game.model.Form.Shape;
import Game.command.CommandHandler;
import Game.command.CommandMoveShape;

public class StateMoveShape implements StateController {

    private Plateau plateau;       
    private Shape selectedShape;
    private Point pressPoint;
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
                pressPoint = p;
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
        if (selectedShape != null && pressPoint != null && lastPoint != null) {
            double remainingDx = p.getX() - lastPoint.getX();
            double remainingDy = p.getY() - lastPoint.getY();
            if (remainingDx != 0 || remainingDy != 0) {
                selectedShape.move(remainingDx, remainingDy);
            }

            double totalDx = p.getX() - pressPoint.getX();
            double totalDy = p.getY() - pressPoint.getY();
            if (totalDx != 0 || totalDy != 0) {
                commandHandler.record(new CommandMoveShape(selectedShape, totalDx, totalDy));
            }
        }

        selectedShape = null;
        pressPoint = null;
        lastPoint = null;
    }
    
    public Shape getCurrentShape() {
        return selectedShape;
    }
}