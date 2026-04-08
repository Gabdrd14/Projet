package Game.model.entity;

import Game.model.Plateau;
import Game.model.Form.Shape;
import Game.model.strategy_ia.PlacementStrategy;

public class ia {

    private Plateau plateau;
    private PlacementStrategy placementStrategy;

    public ia(PlacementStrategy placementStrategy) {
        this.placementStrategy = placementStrategy;
    }

    public void setPlateau(Plateau plateau) {
        this.plateau = plateau;
    }

    public void setPlacementStrategy(PlacementStrategy placementStrategy) {
        this.placementStrategy = placementStrategy;
    }

    public void jouer() {
        if (plateau != null && placementStrategy != null) {
            Shape shape = placementStrategy.placeShape(plateau);
            plateau.ajouterFormePlacee(shape);
        }
    }
}


// ia ai = new ia(new HeuristiquePlacementStrategy());
// ai.setPlateau(plateau);
// ai.jouer();  // Uses heuristic placement

// ai.setPlacementStrategy(new AleatoirePlacementStrategy());
// ai.jouer(); 