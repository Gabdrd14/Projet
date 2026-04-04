package com.example;

import java.awt.Rectangle;

public class CollisionVisiteur implements IntersectionVisiteur { // Visiteur pour vérifier les collisions entre formes, implémente l'interface IntersectionVisiteur pour pouvoir être utilisé dans les formes

    private Drawable other;
    private boolean result;

    public CollisionVisiteur(Drawable other) { // Constructeur qui prend l'autre forme à comparer
        this.other = other;
    }

    public boolean getResult() { // Retourne le résultat de la collision après avoir visité la forme
        return result;
    }

    @Override
    public void visit(RectangleShape r1) { // Vérifie les collisions entre un RectangleShape et l'autre forme
        if (other instanceof RectangleShape) {
            RectangleShape r2 = (RectangleShape) other;
            result = r1.getBounds().intersects(r2.getBounds());

        } else if (other instanceof CircleShape) {
            CircleShape c = (CircleShape) other;
            result = intersectCircleRectangle(c, r1);
        }
    }

    @Override
    public void visit(CircleShape c1) { // Vérifie les collisions entre un CircleShape et l'autre forme
        if (other instanceof CircleShape) {
            CircleShape c2 = (CircleShape) other;
            result = intersectCircleCircle(c1, c2);

        } else if (other instanceof RectangleShape) {
            RectangleShape r = (RectangleShape) other;
            result = intersectCircleRectangle(c1, r);
        }
    }

    private boolean intersectCircleCircle(CircleShape c1, CircleShape c2) { // Vérifie les collisions entre deux cercles en utilisant la formule de distance entre les centres et les rayons
        int x1 = c1.getBounds().x + c1.getBounds().width / 2;
        int y1 = c1.getBounds().y + c1.getBounds().height / 2;
        int r1 = c1.getBounds().width / 2;

        int x2 = c2.getBounds().x + c2.getBounds().width / 2;
        int y2 = c2.getBounds().y + c2.getBounds().height / 2;
        int r2 = c2.getBounds().width / 2;

        int dx = x1 - x2;
        int dy = y1 - y2;

        return dx * dx + dy * dy <= (r1 + r2) * (r1 + r2);
    }

    private boolean intersectCircleRectangle(CircleShape c, RectangleShape r) { // Vérifie les collisions entre un cercle et un rectangle en utilisant la formule de distance entre le centre du cercle et le point le plus proche du rectangle
        Rectangle rect = r.getBounds(); 
        Rectangle circ = c.getBounds();

        int cx = circ.x + circ.width / 2;
        int cy = circ.y + circ.height / 2;
        int radius = circ.width / 2;

        int closestX = Math.max(rect.x, Math.min(cx, rect.x + rect.width));
        int closestY = Math.max(rect.y, Math.min(cy, rect.y + rect.height));

        int dx = cx - closestX;
        int dy = cy - closestY;

        return dx * dx + dy * dy <= radius * radius;
    }
}