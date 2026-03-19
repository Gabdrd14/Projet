package com.example;

import java.awt.Point;

public class ShapeFactory {

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