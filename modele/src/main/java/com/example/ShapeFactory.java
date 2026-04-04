package com.example;

import java.awt.Point;

public class ShapeFactory { // Classe factory pour créer les formes à partir des outils de dessin, elle fournit une méthode statique createShape qui prend en paramètre l'outil de dessin, les points de départ et de fin, et le nom du joueur, et qui retourne une instance de la forme correspondante (RectangleShape ou CircleShape)

    public static Drawable createShape(Tool tool, Point p1, Point p2, String joueur) {

        switch (tool) {
            case RECTANGLE:
                return new RectangleShape(p1, p2, joueur);
            case CIRCLE:
                return new CircleShape(p1, p2, joueur);
            default:
                throw new IllegalArgumentException("Unknown tool");
        }
    }
}