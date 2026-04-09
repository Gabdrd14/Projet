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
        // On ajoute le rectangle au modèle //
        plateau.ajouterFormePlacee(rectangle);
        System.out.println("Rectangle ajouté au plateau en position ("+ rectangle.getP1Point().getX()+ "," 
        + rectangle.getP1Point().getY() + ") et (" + rectangle.getP2Point().getX() + "," + rectangle.getP2Point().getY()+ ")");
    }

    @Override
    public void compensate() {
        // On supprime le rectangle du modèle //
        plateau.supprimerFormePlacee(rectangle);
    }
}