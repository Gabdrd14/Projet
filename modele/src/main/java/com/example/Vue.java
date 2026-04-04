package com.example;

import javax.swing.*;
import java.awt.*;

public class Vue extends JPanel {

    private Model model;

    private Point p1, p2;
    private Tool previewTool;

    public Vue(Model model) {
        this.model = model;
    }

    public void setPreview(Point p1, Point p2, Tool tool) {
        this.p1 = p1;
        this.p2 = p2;
        this.previewTool = tool;
    }

    public void clearPreview() {
        this.p1 = null;
        this.p2 = null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // formes finales
        for (Drawable s : model.getShapes()) {

            if(s instanceof RectangleShape) {
                RectangleShape r = (RectangleShape) s;
                // g.setColor(r.joueur.equals("Joueur 1") ? Color.BLUE : Color.RED);
                g.drawRect(r.getBounds().x, r.getBounds().y, r.getBounds().width, r.getBounds().height);
            } else if (s instanceof CircleShape) {
                CircleShape c = (CircleShape) s;
                // g.setColor(c.joueur.equals("Joueur 1") ? Color.BLUE : Color.RED);
                g.drawOval(c.getBounds().x, c.getBounds().y, c.getBounds().width, c.getBounds().height);
            }

            
            // s.draw(g);
        }

        if (p1 != null && p2 != null) {

            int x = Math.min(p1.x, p2.x);
            int y = Math.min(p1.y, p2.y);
            int w = Math.abs(p1.x - p2.x);
            int h = Math.abs(p1.y - p2.y);


            g.setColor(Color.GRAY);
            ((Graphics2D) g).setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL,0, new float[]{5}, 0));

            if (previewTool == Tool.RECTANGLE) {
                g.drawRect(x, y, w, h);
            } else {
                g.drawOval(x, y, w, h);
            }
        }
    }
}