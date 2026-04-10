package Game.engine;

import Game.model.Plateau;
import Game.model.ConfigFileSelector;
import Game.model.entity.Entity;
import Game.model.entity.ia;
import Game.model.history_game.save_game;
import Game.model.stratGen.StratGen1;
import Game.model.stratGen.StratGen2;
import Game.model.stratGen.StrategiePlateau;
import Game.view.GameFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.util.List;
import java.util.Map;

/**
 * Handler de fin de partie dans la chaîne de responsabilité.
 * Détecte la fin de la partie (toutes les pièces placées), enregistre les scores,
 * propose de rejouer ou affiche les moyennes après 10 parties.
 */
public class GameTerminationHandler extends GameHandler {

    /**
     * Si la partie est terminée, déclenche la logique de fin.
     * Passe ensuite la main au handler suivant.
     */
    @Override
    public void handle(GameSession session) {
        if (session.isGameOver()) {
            handleGameEnd(session);
        }
        passToNext(session);
    }

    /**
     * Gère la fin de partie : sauvegarde des scores, affichage des moyennes
     * à la 10e partie ou proposition de rejouer.
     * Le flag gameEndHandled évite un double déclenchement (ex. tour IA).
     */
    private void handleGameEnd(GameSession session) {
        if (session.isGameEndHandled()) return;
        session.setGameEndHandled(true);

        System.out.println("\n PARTIE TERMINÉE!");
        System.out.println("  Total pièces placées: " + session.getTotalPiecesPlaced());
        System.out.println(buildScoreSummary(session));

        // Enregistre les scores et retourne les moyennes si c'est la 10e partie
        Map<String, Double> averages = save_game.checkAndSave(session.getPlayers());
        if (averages != null) {
            // Affiche le résumé des moyennes sur 10 parties puis ferme le jeu
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
            return; // ne pas proposer de rejouer après les 10 parties
        }

        StrategiePlateau strategie = session.getStrategie();

        // Propose de rejouer uniquement pour les stratégies connues
        if (strategie instanceof StratGen1 || strategie instanceof StratGen2) {
            SwingUtilities.invokeLater(() -> proposeRestart(session, strategie));
        }
    }

    /**
     * Affiche une boîte de dialogue proposant de rejouer ou de quitter.
     */
    private void proposeRestart(GameSession session, StrategiePlateau strategie) {
        String stratName;
        if (strategie instanceof StratGen1) {
            // Affiche le fichier de configuration actuellement utilisé
            String currentConfig = ConfigFileSelector.getInstance().getCurrentConfigPath();
            stratName = "Level 1 - Fichier actuel : " + currentConfig;
        } else {
            stratName = "Level 2 (aléatoire)";
        }

        Object[] options = {"Rejouer", "Quitter"};
        int choice = JOptionPane.showOptionDialog(
                GameFrame.getInstance(),
                buildScoreSummary(session) + "\n\nStratégie : " + stratName
                        + "\nVoulez-vous rejouer avec les mêmes paramètres ?",
                "Partie terminée",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == JOptionPane.YES_OPTION) {
            restartGame(session, strategie);
        } else {
            System.exit(0);
        }
    }

    /**
     * Réinitialise les joueurs, crée un nouveau plateau et une nouvelle session,
     * puis ouvre une nouvelle fenêtre de jeu.
     * Met également à jour le plateau de l'IA si un joueur IA est présent.
     */
    private void restartGame(GameSession session, StrategiePlateau strategie) {
        List<Entity> players = session.getPlayers();

        // Réinitialise les scores et les formes de tous les joueurs
        for (Entity p : players) {
            p.getShapes().clear();
            p.setScore(0);
        }

        // En Level 1, avance au fichier de configuration suivant
        if (strategie instanceof StratGen1) {
            ConfigFileSelector.getInstance().nextConfig();
            System.out.println("Prochain fichier de configuration : "
                    + ConfigFileSelector.getInstance().getCurrentConfigPath());
        }

        Plateau nouveauPlateau = new Plateau(strategie, players);

        // Met à jour la référence au plateau pour chaque joueur IA
        for (Entity p : players) {
            if (p instanceof ia) {
                ((ia) p).setPlateau(nouveauPlateau);
            }
        }

        GameSession nouvelleSession = new GameSession(nouveauPlateau, players, strategie);

        // Ferme la fenêtre courante avant d'en ouvrir une nouvelle
        if (GameFrame.getInstance() != null) {
            GameFrame.getInstance().dispose();
        }

        new GameFrame(nouveauPlateau, nouvelleSession);
    }

    /**
     * Construit un résumé textuel des scores finaux de tous les joueurs.
     */
    private String buildScoreSummary(GameSession session) {
        StringBuilder sb = new StringBuilder("Scores finaux :\n");
        for (Entity player : session.getPlayers()) {
            sb.append("  ").append(player.getName()).append(" : ")
              .append(String.format("%.2f", player.getScore()));
        }
        return sb.toString().trim();
    }
}

