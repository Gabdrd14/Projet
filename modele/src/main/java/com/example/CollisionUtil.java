package com.example;

public class CollisionUtil { // Classe utilitaire pour vérifier les collisions entre formes

    public static boolean intersects(Drawable a, Drawable b) {
        CollisionVisiteur visitor = new CollisionVisiteur(b);
        a.accept(visitor);
        return visitor.getResult();
    }
}