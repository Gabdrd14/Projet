package com.example;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Controleur extends MouseAdapter {

    private Model model;
    private Vue vue;
    private Tool tool = Tool.RECTANGLE; // a change selon l'outil sélectionné dans l'interface graphique (à implémenter) 

    private Point start = null;
    private Point current = null;

    public Controleur(Model model, Vue vue) {
        this.model = model;
        this.vue = vue;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        start = e.getPoint();
        current = start;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        current = e.getPoint();
        vue.setPreview(start, current, tool);
        vue.repaint(); 
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        if (start == null) return;

        Point end = e.getPoint();

        if (end == null) return; // sécurité bonus

        Drawable shape = ShapeFactory.createShape(
                tool,
                start,
                end,
                model.getCurrentPlayer()
        );

        model.addShape(shape);

        vue.clearPreview();

        start = null;

        vue.repaint();
    }
    }