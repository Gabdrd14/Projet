package com.example;

public class CollisionUtil {

    public static boolean intersects(Drawable a, Drawable b) {
        CollisionVisiteur visitor = new CollisionVisiteur(b);
        a.accept(visitor);
        return visitor.getResult();
    }
}