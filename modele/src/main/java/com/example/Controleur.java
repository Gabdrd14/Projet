package com.example;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Controleur extends MouseAdapter { // Contrôleur qui gère les interactions de l'utilisateur avec la vue et le modèle, implémente MouseAdapter pour pouvoir être utilisé comme écouteur de souris dans la vue

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
    public void mousePressed(MouseEvent e) { // Lorsque l'utilisateur appuie sur la souris, on enregistre le point de départ et on initialise le point courant pour le dessin en temps réel
        start = e.getPoint();
        current = start;
    }

    @Override
    public void mouseDragged(MouseEvent e) { // Lorsque l'utilisateur déplace la souris avec le bouton enfoncé, on met à jour le point courant et on demande à la vue de dessiner la forme en temps réel entre le point de départ et le point courant
        current = e.getPoint();
        vue.setPreview(start, current, tool);
        vue.repaint(); 
    }

    @Override
    public void mouseReleased(MouseEvent e) { // Lorsque l'utilisateur relâche le bouton de la souris, on crée la forme finale entre le point de départ et le point de fin, on l'ajoute au modèle, on efface la prévisualisation et on demande à la vue de se redessiner pour afficher la nouvelle forme
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