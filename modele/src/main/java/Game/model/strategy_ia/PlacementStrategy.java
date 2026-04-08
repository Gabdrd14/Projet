package Game.model.strategy_ia;

import Game.model.Form.Shape;
import Game.model.Plateau;

public interface PlacementStrategy {
    Shape placeShape(Plateau plateau);
}