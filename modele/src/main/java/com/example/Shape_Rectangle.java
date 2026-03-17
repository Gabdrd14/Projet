package com.example;

import java.awt.Graphics;
import java.awt.Point;

public class Shape_Rectangle extends Shape{


    public Shape_Rectangle(Point p1 , Point p2){

        super(p1, p2);       

    }





    @Override
    public void draw(Graphics g) {
        g.drawRect(getX(), getY(), getWidth(), getHeight());
    }








}
