package com.example;

import java.awt.*;

public interface Drawable {
    void draw(Graphics g);
    Rectangle getBounds();
    boolean intersects(Drawable other);
}