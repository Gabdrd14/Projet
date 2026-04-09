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
    private JButton[] validateButtons;
    private JButton[] cancelButtons;

    private static String[] savedPseudos;
    private static boolean[] savedSelections;

    public static int nbPlayers;

    public GameCharacter() {
        GameCharacter.nbPlayers = GameSettings.getNbPlayers();

        if (savedPseudos == null || savedSelections == null || savedPseudos.length != nbPlayers) {
            savedPseudos = new String[nbPlayers];
            savedSelections = new boolean[nbPlayers];
        }

        this.frame = new JFrame("Saisie des Pseudos");
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        this.mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        this.pseudoFields = new JTextField[nbPlayers];
        this.validateButtons = new JButton[nbPlayers];
        this.cancelButtons = new JButton[nbPlayers];
        
        int width;
        int height;

        if (nbPlayers == 2) {
            width = 700;  
            height = 500;

            JPanel playersPanel = new JPanel(new GridLayout(1, 2, 30, 0));
            for (int i = 0; i < nbPlayers; i++) {
                playersPanel.add(createPlayerPanel(i));
            }
            this.mainPanel.add(playersPanel);

        } else {
            width = 600;
            height = 400;

            for (int i = 0; i < nbPlayers; i++) {
                this.mainPanel.add(createPlayerPanel(i));
            }
        }

        this.frame.setSize(width, height);
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);

        JButton finalValidateButton = new JButton("Valider");
        finalValidateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        finalValidateButton.setFont(new Font("Arial", Font.BOLD, 14));
        finalValidateButton.setBackground(new Color(0, 153, 51));
        finalValidateButton.setForeground(Color.WHITE);
        finalValidateButton.setFocusPainted(false);
        finalValidateButton.setPreferredSize(new Dimension(150, 40));

        finalValidateButton.addActionListener((ActionEvent e) -> {
            if (allPseudosValidated()) {
                for (int i = 0; i < nbPlayers; i++) {
                    savedPseudos[i] = pseudoFields[i].getText();
                }
                JOptionPane.showMessageDialog(frame, "Pseudos validés !");
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(frame, "Tous les joueurs doivent valider leur pseudo !");
            }
        });

        this.mainPanel.add(Box.createVerticalStrut(20));
        this.mainPanel.add(finalValidateButton);
        this.frame.add(mainPanel);
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

        // Label pseudo //
        gbc.gridx = 0;
        gbc.gridy = 0;
        playerPanel.add(new JLabel("Pseudo :"), gbc);

        // Champ pseudo //
        gbc.gridx = 1;
        pseudoFields[playerIndex] = new JTextField(
                savedPseudos[playerIndex] != null ? savedPseudos[playerIndex] : "Joueur " + (playerIndex + 1)
        );
        pseudoFields[playerIndex].setPreferredSize(new Dimension(150, 30));
        playerPanel.add(pseudoFields[playerIndex], gbc);

        // Bouton valider pseudo //
        JButton validateButton = new JButton(
                savedSelections[playerIndex] ? "Pseudo validé" : "Valider le pseudo"
        );
        validateButton.setEnabled(!savedSelections[playerIndex]);
        
        validateButton.setFont(new Font("Arial", Font.BOLD, 14));
        validateButton.setBackground(new Color(200, 200, 200));
        validateButton.setFocusPainted(false);
        validateButton.setBorderPainted(false);

        validateButton.addActionListener((ActionEvent e) -> {
            if (!savedSelections[playerIndex]) {
                savedSelections[playerIndex] = true;
                validateButton.setText("Pseudo validé");
                validateButton.setEnabled(false);

                pseudoFields[playerIndex].setEnabled(false); // on grise le champ //

                cancelButtons[playerIndex].setEnabled(true);
            }
        });

        this.validateButtons[playerIndex] = validateButton;

        gbc.gridx = 0;
        gbc.gridy = 1;
        playerPanel.add(validateButton, gbc);

        // Bouton annuler //
        JButton cancelButton = new JButton("Annuler");
        cancelButton.setEnabled(savedSelections[playerIndex]);
        cancelButton.setBackground(new Color(255, 102, 102));
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.setFocusPainted(false);
        cancelButton.setBorderPainted(false);

        cancelButton.addActionListener((ActionEvent e) -> {
            savedSelections[playerIndex] = false;

            validateButton.setText("Valider le pseudo");
            validateButton.setEnabled(true);

            pseudoFields[playerIndex].setEnabled(true); // on réactive le champ //

            cancelButton.setEnabled(false);
        });

        this.cancelButtons[playerIndex] = cancelButton;

        gbc.gridx = 1;
        playerPanel.add(cancelButton, gbc);

        return playerPanel;
    }

    private boolean allPseudosValidated() {
        for (boolean selected : savedSelections) {
            if (!selected) return false;
        }
        return true;
    }

    public static String getPlayerPseudo(int playerIndex) {
        if (playerIndex >= 0 && playerIndex < savedPseudos.length) {
            return savedPseudos[playerIndex];
        }
        throw new IllegalArgumentException("Index de joueur invalide : " + playerIndex);
    }
}