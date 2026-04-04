package com.example;

import javax.swing.*;
import java.awt.*;

public class Vue extends JPanel { // Classe qui représente la vue du jeu, elle est responsable de l'affichage des formes à l'écran, elle observe le modèle pour se redessiner à chaque changement, elle gère également la prévisualisation des formes pendant le dessin en temps réel

    private Model model;

    private Point p1, p2;
    private Tool previewTool;

    public Vue(Model model) { // Constructeur de la vue, il prend en paramètre le modèle pour pouvoir observer les changements et se redessiner, il initialise les points de prévisualisation à null
        this.model = model;
    }

    public void setPreview(Point p1, Point p2, Tool tool) { // Méthode pour mettre à jour les points de prévisualisation et l'outil de dessin pendant le dessin en temps réel, elle est appelée par le contrôleur lors du drag de la souris, elle stocke les points et l'outil pour les utiliser dans la méthode paintComponent pour dessiner la forme en temps réel
        this.p1 = p1;
        this.p2 = p2;
        this.previewTool = tool;
    }

    public void clearPreview() { // Méthode pour effacer la prévisualisation après le dessin final, elle est appelée par le contrôleur lors du relâchement de la souris, elle remet les points de prévisualisation à null pour ne plus dessiner la forme en temps réel
        this.p1 = null;
        this.p2 = null;
    }

    @Override
    protected void paintComponent(Graphics g) { // Méthode pour dessiner la vue, elle est appelée automatiquement par Swing à chaque fois que la vue doit être redessinée, elle dessine d'abord les formes finales du modèle en parcourant la liste des formes et en dessinant chaque forme selon son type (rectangle ou cercle), puis elle dessine la forme de prévisualisation si les points de prévisualisation ne sont pas null, elle utilise une couleur grise et un style de trait en pointillés pour différencier la prévisualisation des formes finales
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

        if (p1 != null && p2 != null) { // prévisualisation de la forme en temps réel pendant le drag, elle est dessinée seulement si les points de prévisualisation ne sont pas null, elle calcule les coordonnées et les dimensions de la forme à partir des points de prévisualisation, puis elle dessine la forme selon l'outil de dessin sélectionné (rectangle ou cercle) avec une couleur grise et un style de trait en pointillés pour différencier la prévisualisation des formes finales

            int x = Math.min(p1.x, p2.x);
            int y = Math.min(p1.y, p2.y);
            int w = Math.abs(p1.x - p2.x);
            int h = Math.abs(p1.y - p2.y);


            g.setColor(Color.GRAY); // Couleur grise pour la prévisualisation
            ((Graphics2D) g).setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL,0, new float[]{5}, 0)); // Style de trait en pointillés pour la prévisualisation

            if (previewTool == Tool.RECTANGLE) {
                g.drawRect(x, y, w, h);
            } else {
                g.drawOval(x, y, w, h);
            }
        }
    }
}