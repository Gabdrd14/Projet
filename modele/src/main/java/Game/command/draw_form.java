package Game.command;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public abstract class draw_form  extends MouseAdapter { // Classe abstraite qui gère les interactions de dessin de formes, elle est étendue par les classes spécifiques de dessin de rectangles et de cercles, elle utilise MouseAdapter pour pouvoir être utilisée comme écouteur de souris dans la vue

        int x, y, x2, y2;


        public void setStartPoint(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void setEndPoint(int x, int y) {
            x2 = (x);
            y2 = (y);
        }

    

    public void mousePressed(MouseEvent e) {        // Lorsque l'utilisateur appuie sur la souris, on enregistre le point de départ pour le dessin
        setStartPoint(e.getX(), e.getY());
    }

    public void mouseDragged(MouseEvent e) {       // Lorsque l'utilisateur déplace la souris avec le bouton enfoncé, on met à jour le point courant pour le dessin en temps réel
        setEndPoint(e.getX(), e.getY());
    }

    public void mouseReleased(MouseEvent e) {     // Lorsque l'utilisateur relâche le bouton de la souris, on crée la forme finale entre le point de départ et le point de fin, on l'ajoute au modèle, on efface la prévisualisation et on demande à la vue de se redessiner pour afficher la nouvelle forme
        setEndPoint(e.getX(), e.getY());
    }

    public void paintComponent(Graphics g){};     // Méthode abstraite pour dessiner la forme en temps réel pendant le drag, elle est implémentée dans les classes spécifiques de dessin de rectangles et de cercles

}
