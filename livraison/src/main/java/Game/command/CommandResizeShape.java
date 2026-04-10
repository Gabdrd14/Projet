package Game.command;

import Game.model.Point;
import Game.model.Form.Shape;

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
    	// On applique le redimensionnement de la forme entre deux points //
        shape.resize(lastPoint, newPoint); 
    }

    @Override
    public void compensate() {
    	// On inverse les deux points pour annuler le redimensionnement //
        shape.resize(newPoint, lastPoint); 
    }
}