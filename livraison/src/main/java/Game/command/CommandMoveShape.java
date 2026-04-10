package Game.command;

import Game.model.Plateau;
import Game.model.Form.Shape;

public class CommandMoveShape implements OperationCommand {

	private Shape selectedShape;
    private double dx, dy;
	private Plateau plateau;

    public CommandMoveShape(Shape selectedShape, double dx, double dy) {
        this.selectedShape = selectedShape;
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void operate() {
    	// On bouge la forme //
    	selectedShape.move(dx, dy);
    }

    @Override
    public void compensate() {
    	// On inverse le déplacement pour revenir à la position précédente //
    	selectedShape.move(-dx, -dy);
    }
}