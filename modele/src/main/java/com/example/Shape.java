package com.example;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public abstract class Shape {


    protected Point p1 = null ;

    protected Point p2 = null ;

    protected String id_joueur ;



    public  Shape(Point p1, Point p2){

        this.p1 = p1 ; 
        this.p2 = p2 ;
        this.id_joueur = id_joueur ;

        
    }



    protected int getX(){

        return Math.min(p1.x, p2.x);

    }


    protected int getY(){

        return Math.min(p1.y, p2.y);

    }

    protected int getWidth(){

        return Math.abs(p1.x - p2.x);

    }

    protected int getHeight(){

        return Math.abs(p1.y - p2.y);

    }



    public Rectangle getBounds(){

        return new Rectangle(getX(),getY(),getWidth(),getHeight());

    }


    public boolean intersects(Shape o){

        return this.getBounds().intersects(o.getBounds());

    }

    public abstract void draw(Graphics g) ;






}
