package Game.engine;

import Game.model.Plateau;
import Game.model.StratGen1;
import Game.model.StratGen2;
import Game.model.StrategiePlateau;
import Game.model.ConfigFileSelector;
import Game.model.entity.Entity;
import Game.model.entity.ia;
import Game.model.history_game.save_game;
import Game.view.GameFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.util.List;
import java.util.Map;

public class GameTerminationHandler extends GameHandler {

    @Override
    public void handle(GameSession session) {
        if (session.isGameOver()) {
            handleGameEnd(session);
        }
        passToNext(session);
    }

    private void handleGameEnd(GameSession session) {
        if (session.isGameEndHandled()) return;
        session.setGameEndHandled(true);
        System.out.println("\n PARTIE TERMINÉE!");
        System.out.println("  Total pièces placées: " + session.getTotalPiecesPlaced() + "/8");
        System.out.println(buildScoreSummary(session));

        // Enregistrement dans l'historique + calcul de la moyenne à la 10e partie
        Map<String, Double> averages = save_game.checkAndSave(session.getPlayers());
        if (averages != null) {
            // 10e partie atteinte : afficher les moyennes et ne pas relancer
            StringBuilder avgMsg = new StringBuilder("=== Moyenne sur les 10 dernières parties ===\n");
            for (Map.Entry<String, Double> e : averages.entrySet()) {
                avgMsg.append("  ").append(e.getKey()).append(" : ")
                      .append(String.format("%.2f", e.getValue())).append("\n");
            }
            String msg = avgMsg.toString().trim();
            System.out.println(msg);
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(GameFrame.getInstance(), msg,
                        "Scores moyens — 10 parties", JOptionPane.INFORMATION_MESSAGE);
                if (GameFrame.getInstance() != null) {
                    GameFrame.getInstance().dispose();
                }
            });
            return; // ne pas proposer de rejouer
        }

        StrategiePlateau strategie = session.getStrategie();

        // Condition : relancer uniquement si la stratégie est reconnue (StratGen1 ou StratGen2)
        if (strategie instanceof StratGen1 || strategie instanceof StratGen2) {
            SwingUtilities.invokeLater(() -> proposeRestart(session, strategie));
        }
    }

    private void proposeRestart(GameSession session, StrategiePlateau strategie) {
        String stratName;
        if (strategie instanceof StratGen1) {
            String currentConfig = ConfigFileSelector.getInstance().getCurrentConfigPath();
            stratName = "Level 1 - Fichier actuel : " + currentConfig;
        } else {
            stratName = "Level 2 (aléatoire)";
        }
        
        Object[] options = {"Rejouer", "Quitter"};
        int choice = JOptionPane.showOptionDialog(
                GameFrame.getInstance(),
                buildScoreSummary(session) + "\n\nStratégie : " + stratName + "\nVoulez-vous rejouer avec les mêmes paramètres ?",
                "Partie terminée",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == JOptionPane.YES_OPTION) {
            restartGame(session, strategie);
        }
        
        else {
        	System.exit(0); 	
        }
    }

    private void restartGame(GameSession session, StrategiePlateau strategie) {
        List<Entity> players = session.getPlayers();

        // Réinitialise les scores et les formes des joueurs
        for (Entity p : players) {
            p.getShapes().clear();
            p.setScore(0);
        }

        // Si c'est StratGen1, passe au fichier de configuration suivant
        if (strategie instanceof StratGen1) {
            ConfigFileSelector.getInstance().nextConfig();
            System.out.println("Prochain fichier de configuration : " + 
                              ConfigFileSelector.getInstance().getCurrentConfigPath());
        }

        Plateau nouveauPlateau = new Plateau(strategie, players);

        // Mettre à jour le plateau de l'IA si présent
        for (Entity p : players) {
            if (p instanceof ia) {
                ((ia) p).setPlateau(nouveauPlateau);
            }
        }

        GameSession nouvelleSession = new GameSession(nouveauPlateau, players, strategie);

        // Ferme l'ancienne fenêtre si elle existe
        if (GameFrame.getInstance() != null) {
            GameFrame.getInstance().dispose();
        }

        // Le constructeur GameFrame gère déjà isHiddenChallenge automatiquement
        new GameFrame(nouveauPlateau, nouvelleSession);
    }

    private String buildScoreSummary(GameSession session) {
        StringBuilder sb = new StringBuilder("Scores finaux :\n");
        for (Entity player : session.getPlayers()) {
            sb.append("  ").append(player.getName()).append(" : ")
              .append(String.format("%.2f", player.getScore()));
            //   .append(" (").append(player.getShapes().size()).append(" formes)\n");
        }
        return sb.toString().trim();
    }
}

