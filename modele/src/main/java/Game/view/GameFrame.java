package Game.view;

import Game.model.Point;
import Game.model.Plateau;
import Game.state.StateCreateCircle;
import Game.state.StateCreateRectangle;
import Game.state.StateDeleteShape;
import Game.state.StateMoveShape;
import Game.state.StateResizeShape;
import Game.state.StateController;  
import Game.command.CommandHandler;
import Game.engine.GameSession;
import Game.view.menu.GameSettings;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;

public class GameFrame extends JFrame {

    private static GameFrame instance;

    private JPanel menuPanel; // bande du haut //
    
    private GamePanel gamePanel;   // surface de jeu //

    private Plateau plateau;
    private GameSession gameSession; // Gestionnaire des tours du jeu //

    private StateCreateRectangle stateCreateRectangle;
    private StateCreateCircle stateCreateCircle;
    private StateDeleteShape stateDeleteShape;
    private StateMoveShape stateMoveShape;
    private StateResizeShape stateResizeShape;
    private CommandHandler commandHandler;
    private StateController currentState;
    

    public static GameFrame getInstance() {
        return instance;
    }

    public GameFrame(Plateau plateau) {
        this(plateau, null);
    }

    public GameFrame(Plateau plateau, GameSession session) {
        GameFrame.instance = this;
        this.plateau = plateau; 
        this.gameSession = session;
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

        // On initialise la barre de menu du haut //
        menuPanel = new JPanel(new BorderLayout());
        menuPanel.setPreferredSize(new Dimension(1000, 80));
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
        
        // On crée la zone principale de jeu //
        gamePanel = new GamePanel(this.plateau);

        add(menuPanel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);
        
        // On lance un chrono si le hidden challenge est activé //
        if (GameSettings.isHiddenChallenge()) {
            gamePanel.startHiddenChallengeTimer();
        }
        
        // ---------------------------------------------------------------------------------- //
        
        // Gestion des boutons : un seul listener qui s'adapte au type du bouton //
        gamePanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent me) {
                if (currentState != null) currentState.mousePressed(new Point(me.getX(), me.getY()));
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent me) {
                if (currentState != null) currentState.mouseReleased(new Point(me.getX(), me.getY()));
                gamePanel.setPreview(null);
            }
        });

        // On gère les déplacements de souris pour les actions en cours //
        gamePanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseDragged(java.awt.event.MouseEvent me) {
                if (currentState != null) currentState.mouseDragged(new Point(me.getX(), me.getY()));
                
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
        
        // On active la création de rectangle //
        rectangleButton.addActionListener(e -> {
            currentState = stateCreateRectangle;
            gamePanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        });

        // On active la création de cercle //
        circleButton.addActionListener(e -> {
            currentState = stateCreateCircle;
            gamePanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        });
        
        // On passe en mode suppression //
        deleteButton.addActionListener(e -> {
            currentState = stateDeleteShape;
            gamePanel.setCursor(Cursor.getDefaultCursor());
        });
        
        // On annule la dernière action //
        undoButton.addActionListener(e -> {
        	 currentState = null;
        	 commandHandler.undo();
             gamePanel.setCursor(Cursor.getDefaultCursor());
             gamePanel.repaint();
        });
        
        // On rétablit une action annulée //
        redoButton.addActionListener(e -> {
       	 	commandHandler.redo();
       	 	currentState = null;
            gamePanel.setCursor(Cursor.getDefaultCursor());
            gamePanel.repaint();
        });
        
        // On active le déplacement des formes //
        moveButton.addActionListener(e -> {
       	 	currentState = stateMoveShape;
       	    gamePanel.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
            gamePanel.repaint();
        });
        
        // On active le redimensionnement des formes //
        resizeButton.addActionListener(e -> {
       	 	currentState = stateResizeShape;
       	    gamePanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
            gamePanel.repaint();
        });
        
        revalidate();
        repaint();
        setVisible(true);

        // Hidden Challenge : obstacles visibles 10s par joueur, puis cachés //
        if (GameSettings.isHiddenChallenge() && gameSession != null) {
            gamePanel.startHiddenChallengeTimer();
            gameSession.setOnTurnChanged(() -> gamePanel.startHiddenChallengeTimer());
        }
    }


    // Création d'un bouton avec icône //
    private JButton createIconButton(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        JButton button = new JButton(new ImageIcon(img));
        styleButton(button);
        return button;
    }

    // Application du style commun aux boutons //
    private void styleButton(JButton button) {
        button.setPreferredSize(new Dimension(100, 60));
        button.setBackground(new Color(242, 242, 242));
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        button.setFocusPainted(false);
    }

    // Création d'un panel de boutons avec alignement et espacement //
    private JPanel createButtonPanel(int alignment, JButton... buttons) {
        JPanel panel = new JPanel(new FlowLayout(alignment, 30, 10));
        panel.setOpaque(false);
        for (JButton b : buttons) {
            panel.add(b);
        }
        return panel;
    }

    public JPanel getGamePanel() {
        return gamePanel;
    }

    public void notifyPiecePlaced() {
        if (gameSession != null) {
            gameSession.onPiecePlaced();
        }
    }
    
    public GameSession getGameSession() {
        return gameSession;
    }

    public void setGameSession(GameSession session) {
        this.gameSession = session;
    }

}














