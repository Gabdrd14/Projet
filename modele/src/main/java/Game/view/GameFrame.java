package Game.view;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    private JPanel menuPanel;   // bande du haut
    private JPanel gamePanel;   // surface de jeu

    public GameFrame() {

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
        JButton rectangleButton = createIconButton("modele/src/main/java/Images/bouton_rectangle.png", 70, 40);
        JButton circleButton = createIconButton("modele/src/main/java/Images/bouton_cercle.png", 40, 40);
        JButton polygonButton = createTextButton("Polygone");

        JPanel leftButtons = createButtonPanel(FlowLayout.LEFT, rectangleButton, circleButton, polygonButton);

        // Boutons à droite
        JButton deleteButton = createIconButton("modele/src/main/java/Images/bouton_supprimer.png", 40, 40);
        JButton undoButton = createIconButton("modele/src/main/java/Images/undo.png", 40, 40);
        JButton redoButton = createIconButton("modele/src/main/java/Images/redo.png", 40, 40);

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

}
