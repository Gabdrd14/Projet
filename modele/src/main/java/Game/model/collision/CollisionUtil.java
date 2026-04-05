package Game.model.collision;

import Game.model.Shape;

public class CollisionUtil { // Classe utilitaire pour vérifier les collisions entre formes

    public static boolean intersects(Shape a, Shape b) {
        CollisionVisiteur visitor = new CollisionVisiteur(b);
        a.accept(visitor);
        return visitor.getResult();
    }
}