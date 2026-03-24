package Game.model;

import java.awt.*;

public interface Shape {
    Rectangle getBounds();
    void move(int dx, int dy);
    boolean contains(Point p);
    double surface();
}
