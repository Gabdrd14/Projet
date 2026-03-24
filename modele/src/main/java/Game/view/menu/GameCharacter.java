package Game.view.menu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameCharacter {

    private JFrame frame;
    private JPanel mainPanel;
    private JTextField[] pseudoFields;
    private JButton[] characterButtons;
    private JButton[] cancelButtons;
    private JButton[] nextButtons;
    private JButton[] prevButtons;
    private JLabel[] characterImageLabels;
    private JLabel[] characterNames;  

    private static final String[] CHARACTER_IMAGES = {
        "src/Images/distance_unit.png",
        "src/Images/bomb_man.png",
        "src/Images/melee_unit.png"
    };

    private static final String[] CHARACTER_NAMES = { 
        "Distance Unit", 
        "Bombman", 
        "Melee Unit"
    };
    
    private static final int[][] CHARACTER_STATS = {
            {100, 0, 5, 15}, // Perso1: 100 de vie, 0 bombe, 5 mines, 15 tirs
            {150, 30, 3, 3},   // Perso2: 150 de vie, 30 bombes, 3 mines, 3 tirs
            {250, 5, 10, 5}  // Perso3: 250 de vie, 5 bombes, 10 mines, 5 tirs
    };
    
    private static String[] savedPseudos;
    private static boolean[] savedSelections;
    private static int[] savedCharacterIndices;

    private int[] currentCharacterIndices;

    public GameCharacter() {
        int nbPlayers = GameSettings.getNbPlayers();

        if (savedPseudos == null || savedSelections == null || savedCharacterIndices == null || savedPseudos.length != nbPlayers) {
            savedPseudos = new String[nbPlayers];
            savedSelections = new boolean[nbPlayers];
            savedCharacterIndices = new int[nbPlayers];
        }

        currentCharacterIndices = new int[nbPlayers];

        this.frame = new JFrame("Sélection des Personnages");
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        this.mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        this.pseudoFields = new JTextField[nbPlayers];
        this.characterButtons = new JButton[nbPlayers];
        this.cancelButtons = new JButton[nbPlayers];
        this.nextButtons = new JButton[nbPlayers];
        this.prevButtons = new JButton[nbPlayers];
        this.characterImageLabels = new JLabel[nbPlayers];
        this.characterNames = new JLabel[nbPlayers];  

        if (nbPlayers == 2) {
            JPanel playersPanel = new JPanel();
            playersPanel.setLayout(new GridLayout(1, 2, 30, 0));
            for (int i = 0; i < nbPlayers; i++) {
                playersPanel.add(createPlayerPanel(i));
            }
            this.mainPanel.add(playersPanel);
        } else {
            for (int i = 0; i < nbPlayers; i++) {
                this.mainPanel.add(createPlayerPanel(i));
            }
        }

        JButton validateButton = new JButton("Valider");
        validateButton.setFont(new Font("Arial", Font.BOLD, 14));
        validateButton.setBackground(new Color(0, 153, 51));
        validateButton.setForeground(Color.WHITE);
        validateButton.setFocusPainted(false);
        validateButton.setPreferredSize(new Dimension(150, 40));

        validateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        validateButton.addActionListener((ActionEvent e) -> {
            if (allCharactersSelected(nbPlayers)) {
                for (int i = 0; i < nbPlayers; i++) {
                    savedPseudos[i] = pseudoFields[i].getText();
                }
                JOptionPane.showMessageDialog(frame, "Sélections validées !");
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(frame, "Tous les joueurs doivent sélectionner un personnage !");
            }
        });

        this.mainPanel.add(Box.createVerticalStrut(20));
        this.mainPanel.add(validateButton);

        JComponent contentComponent;
        if (nbPlayers > 2) {
            JScrollPane scrollPane = new JScrollPane(mainPanel);
            scrollPane.getVerticalScrollBar().setUnitIncrement(16);
            contentComponent = scrollPane;
        } else {
            contentComponent = mainPanel;
        }

        this.frame.add(contentComponent);

        int width = (nbPlayers > 2) ? 600 : 1000;
        int height = 600;

        this.frame.setSize(width, height);
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
    }

    private JPanel createPlayerPanel(int playerIndex) {
        JPanel playerPanel = new JPanel(new GridBagLayout());
        playerPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY, 2),
            "Joueur " + (playerIndex + 1),
            TitledBorder.CENTER,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14),
            Color.DARK_GRAY
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        playerPanel.add(new JLabel("Pseudo :"), gbc);

        gbc.gridx = 1;
        this.pseudoFields[playerIndex] = new JTextField(savedPseudos[playerIndex] != null ? savedPseudos[playerIndex] : "Joueur " + (playerIndex + 1));
        this.pseudoFields[playerIndex].setPreferredSize(new Dimension(150, 30));
        playerPanel.add(this.pseudoFields[playerIndex], gbc);

        JPanel charSelectPanel = new JPanel(new GridBagLayout()); 
        JLabel characterImageLabel = new JLabel();
        characterImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        characterImageLabel.setPreferredSize(new Dimension(180, 180));
        characterImageLabels[playerIndex] = characterImageLabel;

        int savedIndex = savedSelections[playerIndex] ? savedCharacterIndices[playerIndex] : 0;
        String characterName = CHARACTER_NAMES[savedIndex];
        JLabel characterNameLabel = new JLabel(characterName);  
        characterNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        characterNames[playerIndex] = characterNameLabel;

        updateCharacterImage(playerIndex, savedIndex);
        charSelectPanel.add(characterImageLabel, createGridBagConstraints(0, 0, 1, 1, GridBagConstraints.CENTER));
        charSelectPanel.add(characterNameLabel, createGridBagConstraints(1, 1, 1, 1, GridBagConstraints.CENTER));

        JPanel statsPanel = new JPanel(new GridLayout(4, 1));
        String[] statLabels = {"Vie", "Bombes", "Mines", "Tirs"};
        int[] characterStats = CHARACTER_STATS[savedIndex];
        
        for (int i = 0; i < statLabels.length; i++) {
            String statText = statLabels[i] + ": " + characterStats[i];
            statsPanel.add(new JLabel(statText, SwingConstants.LEFT));
        }

        charSelectPanel.add(statsPanel, createGridBagConstraints(1, 0, 1, 2, GridBagConstraints.CENTER));

        JPanel navPanel = new JPanel();
        JButton prevButton = new JButton("<");
        JButton nextButton = new JButton(">");

        prevButton.setBackground(new Color(200, 200, 200));
        prevButton.setForeground(Color.RED);

        nextButton.setBackground(new Color(200, 200, 200));
        nextButton.setForeground(Color.RED);

        prevButton.addActionListener((ActionEvent e) -> {
            int currentIndex = currentCharacterIndices[playerIndex];
            currentIndex = (currentIndex - 1 + CHARACTER_IMAGES.length) % CHARACTER_IMAGES.length;
            currentCharacterIndices[playerIndex] = currentIndex;
            updateCharacterImage(playerIndex, currentIndex);
            characterNames[playerIndex].setText(CHARACTER_NAMES[currentIndex]); 
            updateCharacterStats(playerIndex, currentIndex);
        });

        nextButton.addActionListener((ActionEvent e) -> {
            int currentIndex = currentCharacterIndices[playerIndex];
            currentIndex = (currentIndex + 1) % CHARACTER_IMAGES.length;
            currentCharacterIndices[playerIndex] = currentIndex;
            updateCharacterImage(playerIndex, currentIndex);
            characterNames[playerIndex].setText(CHARACTER_NAMES[currentIndex]); 
            updateCharacterStats(playerIndex, currentIndex);
        });

        nextButtons[playerIndex] = nextButton;
        prevButtons[playerIndex] = prevButton;

        if (savedSelections[playerIndex]) {
            prevButton.setEnabled(false);
            nextButton.setEnabled(false);
        }

        navPanel.add(prevButton);
        navPanel.add(nextButton);
        charSelectPanel.add(navPanel, createGridBagConstraints(0, 2, 2, 1, GridBagConstraints.CENTER));

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        playerPanel.add(charSelectPanel, gbc);

        JButton confirmButton = new JButton(savedSelections[playerIndex] ? "Personnage Sélectionné" : "Choisir ce personnage");
        confirmButton.setEnabled(!savedSelections[playerIndex]);
        confirmButton.setFont(new Font("Arial", Font.BOLD, 14));
        confirmButton.setBackground(new Color(200, 200, 200));
        confirmButton.setFocusPainted(false);
        confirmButton.setBorderPainted(false);

        confirmButton.addActionListener((ActionEvent e) -> {
            if (!savedSelections[playerIndex]) {
                savedSelections[playerIndex] = true;
                savedCharacterIndices[playerIndex] = currentCharacterIndices[playerIndex];
                confirmButton.setText("Personnage Sélectionné");
                confirmButton.setEnabled(false);
                cancelButtons[playerIndex].setEnabled(true);
                cancelButtons[playerIndex].setForeground(Color.DARK_GRAY);
                nextButtons[playerIndex].setEnabled(false);
                prevButtons[playerIndex].setEnabled(false);
            }
        });

        this.characterButtons[playerIndex] = confirmButton;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        playerPanel.add(confirmButton, gbc);

        JButton cancelButton = new JButton("Annuler");
        cancelButton.setEnabled(savedSelections[playerIndex]);
        cancelButton.setBackground(new Color(255, 102, 102));
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.setFocusPainted(false);
        cancelButton.setBorderPainted(false);

        cancelButton.addActionListener((ActionEvent e) -> {
            savedSelections[playerIndex] = false;
            savedCharacterIndices[playerIndex] = 0;
            confirmButton.setText("Choisir ce personnage");
            confirmButton.setEnabled(true);
            cancelButton.setEnabled(false);
            nextButtons[playerIndex].setEnabled(true);
            prevButtons[playerIndex].setEnabled(true);
        });

        cancelButtons[playerIndex] = cancelButton;
        gbc.gridx = 1;
        playerPanel.add(cancelButton, gbc);

        return playerPanel;
    }

    private GridBagConstraints createGridBagConstraints(int gridx, int gridy, int gridwidth, int gridheight, int anchor) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;
        gbc.anchor = anchor;
        return gbc;
    }

    private void updateCharacterStats(int playerIndex, int characterIndex) {
        int[] characterStats = CHARACTER_STATS[characterIndex];
        JPanel statsPanel = new JPanel(new GridLayout(4, 1));
        String[] statLabels = {"Vie", "Bombes", "Mines", "Tirs"};

        for (int i = 0; i < statLabels.length; i++) {
            String statText = statLabels[i] + " : " + characterStats[i];
            statsPanel.add(new JLabel(statText, SwingConstants.LEFT));
        }

        JPanel charSelectPanel = (JPanel) characterImageLabels[playerIndex].getParent();  
        Component[] components = charSelectPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JPanel && ((JPanel) component).getLayout() instanceof GridLayout) {
                charSelectPanel.remove(component);  
                charSelectPanel.add(statsPanel, createGridBagConstraints(1, 0, 1, 2, GridBagConstraints.CENTER));  
                break;
            }
        }

        charSelectPanel.revalidate();
        charSelectPanel.repaint();
    }

    private void updateCharacterImage(int playerIndex, int characterIndex) {
        ImageIcon icon = new ImageIcon(CHARACTER_IMAGES[characterIndex]);
        Image image = icon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
        characterImageLabels[playerIndex].setIcon(new ImageIcon(image));
    }

    private boolean allCharactersSelected(int nbPlayers) {
        for (boolean selected : savedSelections) {
            if (!selected) {
                return false;
            }
        }
        return true;
    }
    
    public String getPlayerPseudo(int playerIndex) {
        if (playerIndex >= 0 && playerIndex < savedPseudos.length) {
            return savedPseudos[playerIndex];
        }
        throw new IllegalArgumentException("Index de joueur invalide : " + playerIndex);
    }
    
    public String getPlayerCharacterName(int playerIndex) {
        if (playerIndex >= 0 && playerIndex < savedCharacterIndices.length) {
            return CHARACTER_NAMES[savedCharacterIndices[playerIndex]];
        }
        throw new IllegalArgumentException("Index de joueur invalide : " + playerIndex);
    }
    
}
