
package Game.view;

import Game.model.Shape; 
import Game.model.Tool ;
import Game.model.CircleShape;
import Game.model.Plateau;
import Game.model.RectangleShape;
import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    private JPanel menuPanel;   // bande du haut
    private JPanel gamePanel;   // surface de jeu

    private Plateau plateau ; // Modèle du jeu, il contient les formes placées sur le plateau, les scores des joueurs, et le compteur de pièces restantes, il est partagé entre la vue et le contrôleur pour permettre la communication et la synchronisation entre les deux
    // private Point p1, p2;
    // private Tool previewTool;


    // ajouter plateau  en paramètre du constructeur pour pouvoir l'associer à la vue et au contrôleur, et permettre la communication entre les deux, la vue observe le modèle pour se redessiner à chaque changement, et le contrôleur modifie le modèle en réponse aux actions de l'utilisateur
    public GameFrame() { // Constructeur de la fenêtre principale du jeu, il prend en paramètre le modèle pour pouvoir l'associer à la vue et au contrôleur, il initialise les composants graphiques de la fenêtre (menu et surface de jeu), et configure les propriétés de la fenêtre (taille, titre, comportement à la fermeture)
        // this.plateau = plateau;

        setTitle("Shape Wars");
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // MENU DU HAUT //
        menuPanel = new JPanel(new BorderLayout());
        menuPanel.setPreferredSize(new Dimension(1000, 80));
        menuPanel.setBackground(new Color(242, 242, 242));

        // Boutons à gauche
        JButton rectangleButton = createIconButton("src/main/java/Images/bouton_rectangle.png", 70, 40);
        JButton circleButton = createIconButton("src/main/java/Images/bouton_cercle.png", 40, 40);
        JButton polygonButton = createTextButton("Polygone");

        JPanel leftButtons = createButtonPanel(FlowLayout.LEFT, rectangleButton, circleButton, polygonButton);

        // Boutons à droite
        JButton deleteButton = createIconButton("src/main/java/Images/bouton_supprimer.png", 40, 40);
        JButton undoButton = createIconButton("src/main/java/Images/undo.png", 40, 40);
        JButton redoButton = createIconButton("src/main/java/Images/redo.png", 40, 40);

        JPanel rightButtons = createButtonPanel(FlowLayout.RIGHT, deleteButton, undoButton, redoButton);

        menuPanel.add(leftButtons, BorderLayout.WEST);
        menuPanel.add(rightButtons, BorderLayout.EAST);
        
        
        // SURFACE DE JEU //
        gamePanel = new JPanel();
        gamePanel.setBackground(new Color(191, 191, 191));

        add(menuPanel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);

        setVisible(true);
    }


    // Création d'un bouton avec icône
    private JButton createIconButton(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        JButton button = new JButton(new ImageIcon(img));
        styleButton(button);
        return button;
    }

    // Création d'un bouton avec texte
    private JButton createTextButton(String text) {
        JButton button = new JButton(text);
        styleButton(button);
        return button;
    }

    // Application du style commun aux boutons
    private void styleButton(JButton button) {
        button.setPreferredSize(new Dimension(100, 60));
        button.setBackground(new Color(242, 242, 242));
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        button.setFocusPainted(false);
    }

    // Création d'un panel de boutons avec alignement et espacement
    private JPanel createButtonPanel(int alignment, JButton... buttons) {
        JPanel panel = new JPanel(new FlowLayout(alignment, 30, 10));
        panel.setOpaque(false);
        for (JButton b : buttons) {
            panel.add(b);
        }
        return panel;
    }

    // Accesseur pour la surface de jeu
    public JPanel getGamePanel() {
        return gamePanel;
    }








    // public void setPreview(Point p1, Point p2, Tool tool) { // Méthode pour mettre à jour les points de prévisualisation et l'outil de dessin pendant le dessin en temps réel, elle est appelée par le contrôleur lors du drag de la souris, elle stocke les points et l'outil pour les utiliser dans la méthode paintComponent pour dessiner la forme en temps réel
    //     this.p1 = p1;
    //     this.p2 = p2;
    //     this.previewTool = tool;
    // }

    // public void clearPreview() { // Méthode pour effacer la prévisualisation après le dessin final, elle est appelée par le contrôleur lors du relâchement de la souris, elle remet les points de prévisualisation à null pour ne plus dessiner la forme en temps réel
    //     this.p1 = null;
    //     this.p2 = null;
    // }

    // // @Override
    // protected void paintComponent(Graphics g) { // Méthode pour dessiner la vue, elle est appelée automatiquement par Swing à chaque fois que la vue doit être redessinée, elle dessine d'abord les formes finales du modèle en parcourant la liste des formes et en dessinant chaque forme selon son type (rectangle ou cercle), puis elle dessine la forme de prévisualisation si les points de prévisualisation ne sont pas null, elle utilise une couleur grise et un style de trait en pointillés pour différencier la prévisualisation des formes finales
    //     // super.paintComponent(g);

    //     // formes finales
    //     for (Shape s : plateau.getShapes()) {

    //         if(s instanceof RectangleShape) {
    //             RectangleShape r = (RectangleShape) s;
    //             // g.setColor(r.joueur.equals("Joueur 1") ? Color.BLUE : Color.RED);
    //             g.drawRect(r.getBounds().x, r.getBounds().y, r.getBounds().width, r.getBounds().height);
    //         } else if (s instanceof CircleShape) {
    //             CircleShape c = (CircleShape) s;
    //             // g.setColor(c.joueur.equals("Joueur 1") ? Color.BLUE : Color.RED);
    //             g.drawOval(c.getBounds().x, c.getBounds().y, c.getBounds().width, c.getBounds().height);
    //         }

            
    //         // s.draw(g);
    //     }

    //     if (p1 != null && p2 != null) { // prévisualisation de la forme en temps réel pendant le drag, elle est dessinée seulement si les points de prévisualisation ne sont pas null, elle calcule les coordonnées et les dimensions de la forme à partir des points de prévisualisation, puis elle dessine la forme selon l'outil de dessin sélectionné (rectangle ou cercle) avec une couleur grise et un style de trait en pointillés pour différencier la prévisualisation des formes finales

    //         int x = Math.min(p1.x, p2.x);
    //         int y = Math.min(p1.y, p2.y);
    //         int w = Math.abs(p1.x - p2.x);
    //         int h = Math.abs(p1.y - p2.y);


    //         g.setColor(Color.GRAY); // Couleur grise pour la prévisualisation
    //         ((Graphics2D) g).setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL,0, new float[]{5}, 0)); // Style de trait en pointillés pour la prévisualisation

    //         if (previewTool == Tool.RECTANGLE) {
    //             g.drawRect(x, y, w, h);
    //         } else {
    //             g.drawOval(x, y, w, h);
    //         }
    //     }
    // }
}



















