package Game.command;

import Game.model.Shape;

public class CommandMoveShape implements OperationCommand {

	private Shape selectedShape;
    private int dx, dy;

    public CommandMoveShape(Shape selectedShape, int dx, int dy) {
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