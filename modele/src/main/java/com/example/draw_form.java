package com.example;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public abstract class draw_form  extends MouseAdapter {

        int x, y, x2, y2;


        public void setStartPoint(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void setEndPoint(int x, int y) {
            x2 = (x);
            y2 = (y);
        }

    

    public void mousePressed(MouseEvent e) {
        setStartPoint(e.getX(), e.getY());
    }

    public void mouseDragged(MouseEvent e) {
        setEndPoint(e.getX(), e.getY());
    }

    public void mouseReleased(MouseEvent e) {
        setEndPoint(e.getX(), e.getY());
    }

    public void paintComponent(Graphics g){};

}

