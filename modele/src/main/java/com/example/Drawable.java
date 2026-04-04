package com.example;

import java.awt.*;

public interface Drawable {
    // void draw(Graphics g);
    Rectangle getBounds();

    void accept(IntersectionVisiteur  Visiteur);



}