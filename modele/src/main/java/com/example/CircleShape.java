package com.example;

import java.awt.*;

public class CircleShape implements Drawable {

    private Point p1, p2;
    private String joueur;

    public CircleShape(Point p1, Point p2, String joueur) {
        this.p1 = p1;
        this.p2 = p2;
        this.joueur = joueur;
    }

    private int getX() { return Math.min(p1.x, p2.x); }
    private int getY() { return Math.min(p1.y, p2.y); }
    private int getWidth() { return Math.abs(p1.x - p2.x); }
    private int getHeight() { return Math.abs(p1.y - p2.y); }

    // @Override
    // public void draw(Graphics g) {
    //     g.drawOval(getX(), getY(), getWidth(), getHeight());
    // }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }



    @Override
    public void accept(IntersectionVisiteur visitor) {
        visitor.visit(this);
    }
}