package Game.view.menu;

import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.*;
import Game.view.GameFrame;
import Game.engine.GameSession;
import Game.model.Plateau;
import Game.model.StratGen1;
import Game.model.StratGen2;
import Game.model.StrategiePlateau;
import Game.model.entity.Entity;
import Game.model.entity.HumanPlayer;

public class GameMenu {
    
    private static Clip clip;
    private static boolean isPaused = false;
    private static boolean transitionShown = false;
    private static Plateau plateau;
    
    public static void main(String[] args) {
    	new GameMenu();
    }
    
    public GameMenu() {
        
        JFrame frame = new JFrame("Home menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        
        JPanel loadingPanel = new JPanel();
        loadingPanel.setLayout(new BorderLayout());
        
        JLabel loadingLabel = new JLabel("Loading in progress . . . ");
        try {
            Font PermanentMarker = Font.createFont(Font.TRUETYPE_FONT, 
            new File("src/main/java/Fonts/PermanentMarker-Regular.ttf")).deriveFont(44f);
            loadingLabel.setFont(PermanentMarker);
        } catch (FontFormatException | IOException e) {
        }

        loadingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loadingLabel.setVerticalAlignment(SwingConstants.CENTER);
        loadingPanel.add(loadingLabel, BorderLayout.CENTER);
        
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setIndeterminate(true);
        progressBar.setForeground(Color.RED);
        progressBar.setBackground(Color.WHITE);
        loadingPanel.add(progressBar, BorderLayout.SOUTH);
        
        frame.setContentPane(loadingPanel);
        frame.setLocationRelativeTo(null);  
        frame.setVisible(true);
   
        new Timer(3000, e -> showTransitionScreen(frame)).start(); 
        
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
        }
    }

    private static void showTransitionScreen(JFrame frame) {
        if (transitionShown) return;  
        transitionShown = true;  

        JLabel transitionLabel1 = new JLabel("Welcome to");
        try {
            Font PermanentMarker = Font.createFont(Font.TRUETYPE_FONT, 
            new File("src/main/java/Fonts/PermanentMarker-Regular.ttf")).deriveFont(34f);
            transitionLabel1.setFont(PermanentMarker);
        } catch (FontFormatException | IOException e) {
        }
        
     // Charger l'image
        ImageIcon logoImage = new ImageIcon("src/main/java/Images/Shape_Wars_logo.png");
        Image image = logoImage.getImage().getScaledInstance(900, 600, Image.SCALE_SMOOTH);
        ImageIcon resizedLogo = new ImageIcon(image);
        JLabel logoLabel = new JLabel(resizedLogo);
        logoLabel.setLayout(new BorderLayout());

        frame.setContentPane(logoLabel);  

        JPanel transitionPanel = new JPanel();
        transitionPanel.setLayout(new BorderLayout());
        transitionPanel.add(logoLabel, BorderLayout.CENTER);
        
        JLabel transitionLabel2 = new JLabel("Press any key on the keyboard to continue . . . ");
        try {
            Font PermanentMarker = Font.createFont(Font.TRUETYPE_FONT, 
            new File("src/main/java/Fonts/PermanentMarker-Regular.ttf")).deriveFont(34f);
            transitionLabel2.setFont(PermanentMarker);
        } catch (FontFormatException | IOException e) {
        }

        transitionLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        transitionLabel1.setVerticalAlignment(SwingConstants.CENTER);
        transitionPanel.add(transitionLabel1, BorderLayout.NORTH);
        
        transitionLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        transitionLabel2.setVerticalAlignment(SwingConstants.CENTER);
        transitionPanel.add(transitionLabel2, BorderLayout.SOUTH);

        frame.setContentPane(transitionPanel);  
        frame.setVisible(true);

        frame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                showMainMenu(frame);  
                playBackgroundMusic();
                frame.removeKeyListener(this);  
            }
        });

        frame.requestFocusInWindow(); 
    }

    private static void showMainMenu(JFrame frame) {
    	
    	final boolean[] characterClicked = {false};
        
    	ImageIcon backgroundImage = new ImageIcon("src/main/java/Images/fond_menu_2.png");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setLayout(new BorderLayout());
        frame.setContentPane(backgroundLabel);  

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        buttonPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        JButton playButton = new JButton("Play");

        JButton characterButton = new JButton("Characters");
    
        JButton settingsButton = new JButton("Settings");

        playButton.setPreferredSize(new Dimension(300, 60)); 
        characterButton.setPreferredSize(new Dimension(300, 60)); 
        settingsButton.setPreferredSize(new Dimension(300, 60));

        try {
            Font PermanentMarker = Font.createFont(Font.TRUETYPE_FONT, 
            new File("src/main/java/Fonts/PermanentMarker-Regular.ttf")).deriveFont(30f);
            playButton.setFont(PermanentMarker);
            characterButton.setFont(PermanentMarker);
            settingsButton.setFont(PermanentMarker);
        } catch (FontFormatException | IOException e) {
        }

        playButton.setBackground(new Color(200, 200, 200));
        playButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        characterButton.setBackground(new Color(200, 200, 200));
        characterButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        settingsButton.setBackground(new Color(200, 200, 200));
        settingsButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        gbc.anchor = GridBagConstraints.CENTER;
       
        buttonPanel.add(playButton, gbc);
        gbc.gridy = 1; 
        buttonPanel.add(characterButton, gbc);
        gbc.gridy = 2; 
        buttonPanel.add(settingsButton, gbc);

        backgroundLabel.add(buttonPanel, BorderLayout.CENTER);
        
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(300, 25, 0, 0));

        JButton quitButton = new JButton(new ImageIcon("src/main/java/Images/Bouton_exit.png"));
        quitButton.setPreferredSize(new Dimension(60, 60)); 
        quitButton.setBackground(new Color(200, 200, 200));
        quitButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); 

        quitButton.addActionListener(e -> {
            Object[] options = {"Yes", "No"};
            int response = JOptionPane.showOptionDialog(
                frame,
                "Do you really want to leave the game ?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options, 
                options[1]
            );

            if (response == JOptionPane.YES_OPTION) {
                System.exit(0);  
            }
        });

        JPanel quitPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        quitPanel.setOpaque(false);
        quitPanel.add(quitButton); 

        backgroundLabel.add(quitPanel, BorderLayout.SOUTH);

        // Bouton "Pause musique"
        JButton musicBreakButton = new JButton(new ImageIcon("src/main/java/Images/Bouton_pause.png"));
        musicBreakButton.setPreferredSize(new Dimension(60, 60)); 
        musicBreakButton.setBackground(new Color(200, 200, 200));
        musicBreakButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); 

        musicBreakButton.addActionListener(e -> MusicPause());

        JPanel musicBreakPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        musicBreakPanel.setOpaque(false);  
        musicBreakPanel.add(musicBreakButton);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.add(musicBreakPanel, BorderLayout.WEST);
        bottomPanel.add(quitPanel, BorderLayout.EAST);

        backgroundLabel.add(bottomPanel, BorderLayout.SOUTH);

        settingsButton.addActionListener(e -> {
            new GameSettings(frame); 
        });

        characterButton.addActionListener(e -> {
        	 new GameCharacter();
        	 characterClicked[0] = true;
        });
        
        playButton.addActionListener(e -> {
        	 
        	// Il faut d'abord choisir nos personnages avant de lancer le jeu. //
        	if (!characterClicked[0]) {
                  JOptionPane.showMessageDialog(frame, "Please select your characters before starting the game", 
                                                "Missing character selection", JOptionPane.WARNING_MESSAGE);
                  return;  
            }
        	
           	if (GameSettings.getNbPlayers() != GameCharacter.nbPlayers) {
                JOptionPane.showMessageDialog(frame, "Please reselect characters after changing the number of players.");
                return;
            }
        	
        	frame.dispose();
            clip.stop(); 
        	
            try {
	        	 // On récupère tous les paramètres mis à jour. : //	
            	 int nbRedShape = GameSettings.getnbRedShape();
            	 int Level = GameSettings.getLevel();
                 int nbPlayers = GameSettings.getNbPlayers();
	        	 boolean hidden = GameSettings.isHiddenChallenge();
	        	 	        	 
	        	 List<Entity> joueurs = new ArrayList<>();

	        	 for (int i = 0; i < nbPlayers; i++) {
	        	     String pseudo = GameCharacter.getPlayerPseudo(i);
	        	     Entity player = new HumanPlayer();
	        	     player.setName(pseudo);
	        	     joueurs.add(player); 
	        	 }

	        	 StrategiePlateau strategie;

	        	 if (Level == 1) {
	        	     strategie = new StratGen1();
	        	     System.out.println("Strat 1 choisi");
	        	 } else {
	        	     strategie = new StratGen2();
	        	     System.out.println("Strat 2 choisi");
	        	 }
	        	 plateau = new Plateau(1920,1080,strategie,joueurs);
	        	 
                // test

	        	 // Créer GameSession pour gérer les tours

	        	 GameSession gameSession = new GameSession(plateau, joueurs);
                 System.out.println("GameSession created with " + joueurs.size() + " players.");
	        	 
	        	 // Passer GameSession au GameFrame
	        	 new GameFrame(plateau, gameSession);
	        	    	 

	        	 
             } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error when starting the game : " + ex.getMessage(),
                                              "Error", JOptionPane.ERROR_MESSAGE);
             }
        });
  
        frame.revalidate();
        frame.repaint();
    }

    private static void MusicPause() {
        if (clip != null) {
            if (isPaused) {
                clip.start(); 
            } else {
                clip.stop(); 
            }
            isPaused = !isPaused; 
        }
        
    }
    
    private static void playBackgroundMusic() {
        try {
            File audioFile = new File("src/main/java/Musique/musique_jeu.wav"); 
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip(); 
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY); 
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace(); 
        }
    }
}