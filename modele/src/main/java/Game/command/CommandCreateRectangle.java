package Game.command;

import Game.model.Plateau;
import Game.model.Form.RectangleShape;

public class CommandCreateRectangle implements OperationCommand {

    private Plateau plateau;
    private RectangleShape rectangle;

    public CommandCreateRectangle(Plateau plateau, RectangleShape rectangle) {
        this.plateau = plateau;
        this.rectangle = rectangle;
    }

    @Override
    public void operate() {
        // On Ajoute le rectangle au modèle //
        plateau.ajouterFormePlacee(rectangle);
    }

    @Override
    public void compensate() {
        // On supprime le rectangle du modèle //
        plateau.supprimerFormePlacee(rectangle);
    }
}
