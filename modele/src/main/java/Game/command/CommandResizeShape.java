package Game.command;

import java.awt.Point;
import Game.model.Shape;

public class CommandResizeShape implements OperationCommand {

    private Shape shape;
    private Point lastPoint;   
    private Point newPoint;    

    public CommandResizeShape(Shape shape, Point lastPoint, Point newPoint) {
        this.shape = shape;
        this.lastPoint = lastPoint;
        this.newPoint = newPoint;
    }

    @Override
    public void operate() {
        shape.resize(lastPoint, newPoint); // lastPoint -> newPoint //
    }

    @Override
    public void compensate() {
        shape.resize(newPoint, lastPoint); // On inverse pour undo //
    }
}