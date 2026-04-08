package Game.command;

import Game.model.Form.Shape;

public class CommandMoveShape implements OperationCommand {

	private Shape selectedShape;
    private double dx, dy;

    public CommandMoveShape(Shape selectedShape, double dx, double dy) {
        this.selectedShape = selectedShape;
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void operate() {
    	selectedShape.move(dx, dy);
    }

    @Override
    public void compensate() {
    	selectedShape.move(-dx, -dy);
    }
}