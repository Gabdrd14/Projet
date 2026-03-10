package com.example;

import java.awt.Graphics;
import java.awt.Point;

public class Shape_Circle extends Shape {


    public Shape_Circle(Point p1 , Point p2){

        super(p1, p2);
    }

    @Override
    public void draw(Graphics g) {

        g.drawOval(getX(), getY(), getWidth(), getHeight());

    }

}
