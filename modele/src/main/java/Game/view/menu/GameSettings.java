package Game.view.menu;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Cette Classe permet de configurer les paramètres du jeu à travers une interface graphique. //
 */
public class GameSettings {
    
	// Variables statiques représentant les paramètres par défaut de notre jeu. //
    private static int nbRedShape = 10;
    private static int Level = 1;
    private static int nbPlayers = 1;
    private static boolean HiddenChallenge = false;
    private static boolean AI = false;
    
    // Champs pour permettre la saisie des paramètres. //
    private JTextField nbRedShapeField;
    private JTextField LevelField;
    private JTextField nbPlayersField;  
    private JCheckBox hiddenChallengeCheck;
    private JCheckBox AICheck;
    private JButton nbRedMinusButton, nbRedPlusButton;
    
    public GameSettings(JFrame frame) {
    	
    	// On crée une fenêtre pour les paramètres de taille 500*500. //
        JFrame settingsFrame = new JFrame("Game settings");
        settingsFrame.setSize(500, 500);
        settingsFrame.setLocationRelativeTo(frame); // On centre notre fenêtre par rapport à la fenêtre principale. //
        
        // On crée un panneau principal. //
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BorderLayout());
        settingsFrame.add(settingsPanel);
        
        // On ajoute un titre en haut de la fenêtre. //
        JLabel titleLabel = new JLabel("Game settings", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.DARK_GRAY);
        settingsPanel.add(titleLabel, BorderLayout.NORTH);
        
        // On crée un panneau central pour les champs de saisie et leurs contrôles. //
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new GridLayout(5, 3, 10, 10)); // Grille de 5 lignes et 3 colonnes. //
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Marges. //
        settingsPanel.add(fieldsPanel, BorderLayout.CENTER);
        
        // On ajoute des champs de saisie et de leurs libellés. //
        fieldsPanel.add(new JLabel("<html>Number of red shapes :<br>Available for Level 2</html>"));
        nbRedShapeField = new JTextField(String.valueOf(nbRedShape)); // Champ avec valeur par défaut. //
        configureField(nbRedShapeField);
        fieldsPanel.add(nbRedShapeField);
        fieldsPanel.add(createControlPanel(nbRedShapeField)); // Boutons "+" et "-". //
        
        // Désactivation par défaut car Level = 1
        nbRedShapeField.setEditable(false);
        nbRedMinusButton.setEnabled(false);
        nbRedPlusButton.setEnabled(false);
        
        fieldsPanel.add(new JLabel("Level :"));
        LevelField = new JTextField(String.valueOf(Level));
        configureField(LevelField);
        fieldsPanel.add(LevelField);
        fieldsPanel.add(createControlPanel(LevelField));

        fieldsPanel.add(new JLabel("Number of players :"));
        nbPlayersField = new JTextField(String.valueOf(nbPlayers));
        configureField(nbPlayersField);
        fieldsPanel.add(nbPlayersField);
        fieldsPanel.add(createControlPanel(nbPlayersField));
        
        
        // Hidden Challenge //
        fieldsPanel.add(new JLabel("Hidden Challenge :"));
        hiddenChallengeCheck = new JCheckBox("Enable");
        hiddenChallengeCheck.setSelected(HiddenChallenge);
        
        hiddenChallengeCheck.setFont(new Font("Arial", Font.BOLD, 11));
        hiddenChallengeCheck.setForeground(new Color(50, 50, 50));
        hiddenChallengeCheck.setFocusPainted(false);
        hiddenChallengeCheck.setOpaque(false); // fond transparent
        
        fieldsPanel.add(hiddenChallengeCheck);
        fieldsPanel.add(new JLabel("")); // vide pour garder la grille alignée
        
        // Mode AI //
        fieldsPanel.add(new JLabel("Mode AI :"));
        AICheck = new JCheckBox("Enable");
        AICheck.setSelected(AI);
        
        AICheck.setFont(new Font("Arial", Font.BOLD, 11));
        AICheck.setForeground(new Color(50, 50, 50));
        AICheck.setFocusPainted(false);
        AICheck.setOpaque(false); // fond transparent
        
        fieldsPanel.add(AICheck);
        fieldsPanel.add(new JLabel("")); // vide pour garder la grille alignée
        
        
        // On grise le paramètre du nombre de formes lorsque l'on est au Level 1, car la stratégie 1 //
        // est basée sur des ensembles de formes définis dans des fichiers. //

        // Dès que l'on passe au Level 2, on peut choisir le nombre de formes rouges qui apparaissent sur le plateau, //
        // puisque la stratégie 2 est basée sur un placement aléatoire des formes. //
        LevelField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            private void updateNbRedShapeState() {
                try {
                	boolean editable = false;
                   
                    int level = Integer.parseInt(LevelField.getText());

                    if (level >= 2) {
                    	editable = true;
                    }
                   
                    nbRedShapeField.setEditable(editable);
                    nbRedMinusButton.setEnabled(editable);
                    nbRedPlusButton.setEnabled(editable);
                
                } catch (NumberFormatException e) {
                    nbRedShapeField.setEditable(false);
                    nbRedMinusButton.setEnabled(false);
                    nbRedPlusButton.setEnabled(false);
                }
            }

            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { 
            	updateNbRedShapeState(); 
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { 
            	updateNbRedShapeState();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { 
            	updateNbRedShapeState(); 
            }
        });    
        
        
        
        // On crée un panneau pour les boutons Confirmer et Réinitialiser. //
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        
        // Mise en place d'un bouton de confirmation pour valider les paramètres. //
        JButton confirmButton = new JButton("Confirm");
        configureButton(confirmButton, new Color(0, 153, 51));
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isValid = true; 
                
                // On s'assure que nos paramètres sont valides. //
                isValid &= validateField(nbRedShapeField, "5", "the number of red shapes", frame);
                isValid &= validateField(LevelField, "1", "Level", frame);
                isValid &= validateField(nbPlayersField, "2", "the number of players", frame);

                if (!isValid) {
                    return;  
                }
                
                // Si c'est le cas alors on récupère leurs valeurs. //
                nbRedShape = Integer.parseInt(nbRedShapeField.getText());
                Level = Integer.parseInt(LevelField.getText());
                nbPlayers = Integer.parseInt(nbPlayersField.getText());
                HiddenChallenge = hiddenChallengeCheck.isSelected();
                AI = AICheck.isSelected();
                
                // On effectue des vérifications supplémentaires. //
                if (nbPlayers < 1) {
                    JOptionPane.showMessageDialog(frame, "There must be at least 2 players");
                    nbPlayersField.setText("1");
                    return;
                }

                if (Level < 0) {
                    JOptionPane.showMessageDialog(frame, "Grid size too small");
                    LevelField.setText("1");
                    return;
                }
                
                settingsFrame.dispose();
            }
        });
        
        // Mise en place d'un bouton de réinitialisation. //
        JButton resetButton = new JButton("Reset");
        configureButton(resetButton, new Color(204, 102, 0));
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            	// Nos paramètres reprennent leurs valeurs par défaut. //
            	nbRedShapeField.setText(String.valueOf(10)); 
                LevelField.setText(String.valueOf(1)); 
                nbPlayersField.setText(String.valueOf(1));
                hiddenChallengeCheck.setSelected(false);
                AICheck.setSelected(false);
            }
        });
        
        buttonPanel.add(confirmButton);
        buttonPanel.add(resetButton);
        settingsPanel.add(buttonPanel, BorderLayout.SOUTH);

        settingsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        settingsFrame.setVisible(true);
    }
    
    /**
     * Cette méthode configure les champs de saisies. //
     */
    private void configureField(JTextField field) {
        field.setHorizontalAlignment(JTextField.CENTER);
        field.setFont(new Font("Arial", Font.BOLD, 14));
    }
    
    /**
     * Cette méthode permet de créer un panneau avec les boutons "-" et "+". //
     */
    private JPanel createControlPanel(JTextField field) {
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        JButton minusButton = new JButton("-");
        JButton plusButton = new JButton("+");
        configureControlButton(minusButton);
        configureControlButton(plusButton);
        
        minusButton.addActionListener(e -> updateFieldValue(field, -1)); // On décrémente de 1 si on appuie sur le "-". //
        plusButton.addActionListener(e -> updateFieldValue(field, 1)); // On incrémente de 1 si on appuie sur le "+". //
        
        if (field == nbRedShapeField) {
            nbRedMinusButton = minusButton;
            nbRedPlusButton = plusButton;
        }

        controlPanel.add(minusButton);
        controlPanel.add(plusButton);
        
        return controlPanel;
    }
    
    /**
     * Cette méthode permet de configurer les boutons "+" et "-". //
     */
    private void configureControlButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(50, 40));
        button.setBackground(new Color(200, 200, 200));
        button.setForeground(Color.DARK_GRAY);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
    }
    
    /**
     * Cette méthode permet de configurer les boutons "Confirmer" et "Réinitialiser". //
     */
    private void configureButton(JButton button, Color backgroundColor) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(150, 40));
    }
    
    /**
     * Cette méthode permet de valider la saisie d'un champ. //
     */
    private boolean validateField(JTextField field, String defaultValue, String fieldName, JFrame frame) {
        try {
            int value = Integer.parseInt(field.getText());

            if (value < 0) {
                JOptionPane.showMessageDialog(frame, fieldName + " cannot be negative.");
                field.setText(defaultValue);  
                return false;  
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid numeric value for " + fieldName + " !");
            field.setText(defaultValue);  
            return false; 
        }
        return true; 
    }
    
    
    /**
     * Cette méthode permet d'incrémenter ou décrémenter la valeur d'un champ. //
     */
    private void updateFieldValue(JTextField field, int increment) {
        try {
            int value = Integer.parseInt(field.getText());
            int newValue = value + increment;
            
            if (field == nbRedShapeField  && newValue < 1) {
            	newValue = 1; 
            }
            
            if (field == LevelField && newValue < 1) {
                newValue = 1;  
            }
            
            if (field == LevelField && newValue > 3) {
                newValue = 3;  
            }
            
            if (field == nbPlayersField && newValue < 1) {
            	newValue = 1;  
            }
            
            field.setText(String.valueOf(newValue));
        } catch (NumberFormatException ex) {
            field.setText("0"); 
        }
        
    }
    
    
    // Accesseurs pour les paramètres. //
    public static int getnbRedShape() {
        return nbRedShape;
    }
    
    public static int getLevel() {
        return Level;
    }

    public static int getNbPlayers() {
        return nbPlayers;
    }
    
    public static boolean isHiddenChallenge() {
        return HiddenChallenge;
    }
    
    public static boolean ActiveAI() {
        return AI;
    }
    
}
