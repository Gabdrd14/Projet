package Game.model.strategy_ia;

import Game.model.Plateau;
import Game.model.Form.Shape;

public interface PlacementStrategy {
    Shape placeShape(Plateau plateau);
}