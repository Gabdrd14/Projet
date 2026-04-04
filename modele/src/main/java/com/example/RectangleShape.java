package com.example;

import java.awt.*;

public class RectangleShape implements Drawable { // Classe qui représente une forme de rectangle, elle implémente l'interface Drawable pour pouvoir être utilisée dans le modèle et la vue, elle contient les points de départ et de fin du rectangle ainsi que le joueur qui l'a dessiné, elle fournit des méthodes pour obtenir les limites du rectangle et pour accepter un visiteur d'intersection

    private Point p1, p2;
    private String joueur;

    public RectangleShape(Point p1, Point p2, String joueur) {
        this.p1 = p1;
        this.p2 = p2;
        this.joueur = joueur;
    }

    private int getX() { return Math.min(p1.x, p2.x); }
    private int getY() { return Math.min(p1.y, p2.y); }
    private int getWidth() { return Math.abs(p1.x - p2.x); }
    private int getHeight() { return Math.abs(p1.y - p2.y); }


    @Override
    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }



    @Override
    public void accept(IntersectionVisiteur visitor) {
        visitor.visit(this);
    }
}