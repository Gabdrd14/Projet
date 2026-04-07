
package Game.view;

import Game.model.Form.CircleShape;
import Game.model.Form.RectangleShape;
import Game.model.Form.Shape;
import Game.state.*;
import Game.command.CommandHandler;
import Game.model.Plateau;

import javax.swing.*;
import java.awt.*;

import Game.model.Plateau;

public class GameFrame extends JFrame {

    private JPanel menuPanel;   // bande du haut
    //private JPanel gamePanel;
    
    private GamePanel gamePanel;   // surface de jeu //

    private Plateau plateau; // Modèle du jeu, il contient les formes placées sur le plateau, les scores des joueurs, et le compteur de pièces restantes, il est partagé entre la vue et le contrôleur pour permettre la communication et la synchronisation entre les deux
    // private Point p1, p2;
    // private Tool previewTool;

    // ajouter plateau  en paramètre du constructeur pour pouvoir l'associer à la vue et au contrôleur, et permettre la communication entre les deux, la vue observe le modèle pour se redessiner à chaque changement, et le contrôleur modifie le modèle en réponse aux actions de l'utilisateur
    
    
    private StateCreateRectangle stateCreateRectangle;
    private StateCreateCircle stateCreateCircle;
    private StateDeleteShape stateDeleteShape;
    private StateMoveShape stateMoveShape;
    private StateResizeShape stateResizeShape;
    private CommandHandler commandHandler;
    private StateController currentState;
    
    public GameFrame(Plateau plateau) {
        this.plateau = plateau; // Constructeur de la fenêtre principale du jeu, il prend en paramètre le modèle pour pouvoir l'associer à la vue et au contrôleur, il initialise les composants graphiques de la fenêtre (menu et surface de jeu), et configure les propriétés de la fenêtre (taille, titre, comportement à la fermeture)
        commandHandler = new CommandHandler();
        stateCreateRectangle = new StateCreateRectangle(plateau, commandHandler);
        stateCreateCircle = new StateCreateCircle(plateau, commandHandler);
        stateDeleteShape = new StateDeleteShape(plateau, commandHandler);
        stateMoveShape = new StateMoveShape(plateau, commandHandler);
        stateResizeShape = new StateResizeShape(plateau, commandHandler);
        
        setTitle("Shape Wars");
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // MENU DU HAUT //
        menuPanel = new JPanel(new BorderLayout());
        menuPanel.setPreferredSize(new Dimension(1000, 80));
        //menuPanel.setOpaque(true); 
        menuPanel.setBackground(new Color(242, 242, 242));
        
        
        // Création des boutons : //
        
        // Boutons à gauche
        JButton rectangleButton = createIconButton("src/main/java/Images/bouton_rectangle.png", 70, 40);
        JButton circleButton = createIconButton("src/main/java/Images/bouton_cercle.png", 40, 40);
        JButton resizeButton = createIconButton("src/main/java/Images/Bouton_resize.png", 40, 40);
        JButton moveButton = createIconButton("src/main/java/Images/Bouton_move.png", 50, 40);
        JButton deleteButton = createIconButton("src/main/java/Images/bouton_supprimer.png", 40, 40);

        JPanel leftButtons = createButtonPanel(FlowLayout.LEFT, rectangleButton, circleButton, resizeButton, moveButton, deleteButton);
        leftButtons.setOpaque(false);

        // Boutons à droite
        JButton undoButton = createIconButton("src/main/java/Images/undo.png", 40, 40);
        JButton redoButton = createIconButton("src/main/java/Images/redo.png", 40, 40);

        JPanel rightButtons = createButtonPanel(FlowLayout.RIGHT, undoButton, redoButton);
        rightButtons.setOpaque(false);

        menuPanel.add(leftButtons, BorderLayout.WEST);
        menuPanel.add(rightButtons, BorderLayout.EAST);        
        
        // SURFACE DE JEU //
        gamePanel = new GamePanel(this.plateau);

        add(menuPanel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);
        
        //menuPanel.revalidate();
        //menuPanel.repaint();
        
        // ---------------------------------------------------------------------------------- //
        // Gestion des boutons : un seul listener qui s'adapte au type du bouton //

        gamePanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent me) {
                if (currentState != null) currentState.mousePressed(me.getPoint());
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent me) {
                if (currentState != null) currentState.mouseReleased(me.getPoint());
                gamePanel.setPreview(null);
            }
        });

        gamePanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseDragged(java.awt.event.MouseEvent me) {
                if (currentState != null) currentState.mouseDragged(me.getPoint());
                
                if (currentState instanceof StateCreateRectangle)
                    gamePanel.setPreview(((StateCreateRectangle) currentState).getCurrentRect());
                
                else if (currentState instanceof StateCreateCircle)
                    gamePanel.setPreview(((StateCreateCircle) currentState).getCurrentcirc());
                
                else if (currentState instanceof StateMoveShape)
                	gamePanel.setPreview(((StateMoveShape) currentState).getCurrentShape());
                
                else if (currentState instanceof StateResizeShape)
                	gamePanel.setPreview(((StateResizeShape) currentState).getCurrentShape());
                
            }
        });
        
        rectangleButton.addActionListener(e -> {
            currentState = stateCreateRectangle;
            gamePanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

            // Test : //
            
            System.out.println(plateau.getFormePlacees());
        });

        circleButton.addActionListener(e -> {
            currentState = stateCreateCircle;
            gamePanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
            
            System.out.println(plateau.getFormePlacees());
        });
        
        deleteButton.addActionListener(e -> {
            currentState = stateDeleteShape;
            gamePanel.setCursor(Cursor.getDefaultCursor());
            
            System.out.println(plateau.getFormePlacees());
        });
        
        undoButton.addActionListener(e -> {
        	 currentState = null;
        	 commandHandler.undo();
             gamePanel.setCursor(Cursor.getDefaultCursor());
             gamePanel.repaint();
             
             System.out.println(plateau.getFormePlacees());
        });
        
        redoButton.addActionListener(e -> {
       	 	commandHandler.redo();
       	 	currentState = null;
            gamePanel.setCursor(Cursor.getDefaultCursor());
            gamePanel.repaint();
            
            System.out.println(plateau.getFormePlacees());
        });
        
        moveButton.addActionListener(e -> {
       	 	currentState = stateMoveShape;
       	    gamePanel.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
            gamePanel.repaint();
            
            System.out.println(plateau.getFormePlacees());
        });
        
        resizeButton.addActionListener(e -> {
       	 	currentState = stateResizeShape;
       	    gamePanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
            gamePanel.repaint();
            
            System.out.println(plateau.getFormePlacees());
        });
        
        revalidate();
        repaint();
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



















