package Game.command;

import Game.model.Plateau;
import Game.model.Form.Shape;

public class CommandDeleteShape implements OperationCommand {
    
	private Plateau plateau;
	private Shape selectedShape;

    public CommandDeleteShape(Plateau plateau, Shape selectedShape) {
        this.plateau = plateau;
        this.selectedShape = selectedShape;
    }

    @Override
    public void operate() {
    	// On supprime la forme du modèle //
        plateau.supprimerFormePlacee(selectedShape);
    }
    
    @Override
    public void compensate() {
    	// On ajoute la forme au modèle //
        plateau.ajouterFormePlacee(selectedShape);
    }
}
