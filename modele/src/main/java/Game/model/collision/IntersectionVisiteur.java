package Game.model.collision;

import Game.model.CircleShape;
import Game.model.RectangleShape;

public interface IntersectionVisiteur { // Interface pour le visiteur d'intersection, elle définit les méthodes de visite pour chaque type de forme, elle est utilisée par la classe CollisionVisiteur pour vérifier les collisions entre formes en utilisant le pattern visiteur

    void visit(RectangleShape c);
    void visit(CircleShape r);

}