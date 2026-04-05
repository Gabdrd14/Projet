package Game.model;


import java.awt.Point;


//  a modifier pour interagir avec les boutons de dessin, elle utilise une énumération Tool pour représenter les différents outils de dessin disponibles (RECTANGLE et CIRCLE), et elle fournit une méthode statique createShape qui prend en paramètre l'outil de dessin, les points de départ et de fin, et le nom du joueur, et qui retourne une instance de la forme correspondante (RectangleShape ou CircleShape)

public class ShapeFactory { // Classe factory pour créer les formes à partir des outils de dessin, elle fournit une méthode statique createShape qui prend en paramètre l'outil de dessin, les points de départ et de fin, et le nom du joueur, et qui retourne une instance de la forme correspondante (RectangleShape ou CircleShape)

    public static Shape createShape(Tool tool, Point p1, Point p2, String joueur) {

        switch (tool) {
            case RECTANGLE:
                return new RectangleShape(p1, p2);
            case CIRCLE:

                int distance = (int) Math.sqrt(Math.pow((p2.x - p1.x), 2) + Math.pow((p2.y - p1.y), 2));
                return new CircleShape(p1, distance);
            default:
                throw new IllegalArgumentException("Unknown tool");
        }
    }
}