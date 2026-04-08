package Game.command;

import Game.model.Plateau;
import Game.model.Form.CircleShape;

public class CommandCreateCircle implements OperationCommand {

    private Plateau plateau;
    private CircleShape circle;

    public CommandCreateCircle(Plateau plateau, CircleShape circle) {
        this.plateau = plateau;
        this.circle = circle;
    }

    @Override
    public void operate() {
        // On Ajoute le cercle au modèle //
        plateau.ajouterFormePlacee(circle);
        System.out.println("Cercle ajouté au plateau en position (" + circle.getCenter().getX() + "," + circle.getCenter().getY() + ") avec un rayon de " + circle.getRadius());
    }

    @Override
    public void compensate() {
        // On supprime le cercle du modèle //
        plateau.supprimerFormePlacee(circle);
        System.out.println("Cercle supprimé du plateau.");
    }
}