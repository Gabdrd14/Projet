package Game.state;

import java.awt.Point;
import Game.model.Shape;
import Game.model.Plateau;
import Game.command.CommandDeleteShape;
import Game.command.CommandHandler;
import Game.command.CommandMoveShape;

public class StateDeleteShape implements StateController {

    private Plateau plateau;
    private CommandHandler commandHandler;

    public StateDeleteShape(Plateau plateau, CommandHandler commandHandler) {
        this.plateau = plateau;
        this.commandHandler = commandHandler;
    }

    @Override
    public void mousePressed(Point p) {
        Shape selectedShape = null;
        for (Shape s : plateau.getFormePlacees()) {
            if (s.contains(p)) {
                selectedShape = s;
                break;
            }
        }

        if (selectedShape != null) {
        	 commandHandler.handle(
        			 new CommandDeleteShape(plateau, selectedShape)
             );
        }
    }

    // Pas besoin d'implémenter le mouseDragged et le mouseReleased. //
    
	@Override
	public void mouseDragged(Point p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(Point p) {
		// TODO Auto-generated method stub
		
	}
    
}
