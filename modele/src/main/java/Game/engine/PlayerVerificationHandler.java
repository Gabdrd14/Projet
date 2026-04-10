package Game.engine;

import Game.model.entity.Entity;

/**
 * Handler de la chaîne de responsabilité chargé de vérifier l'état du joueur courant.
 * Vérifie si la partie est déjà terminée, puis indique si le joueur a complété son tour
 * (4 pièces placées) ou combien il lui en reste.
 * Passe ensuite la main au handler suivant (GameTerminationHandler).
 */
public class PlayerVerificationHandler extends GameHandler {

    @Override
    public void handle(GameSession session) {
        Entity currentPlayer = session.getCurrentPlayer();

        // Aucun joueur enregistré : on sort sans traitement
        if (currentPlayer == null) {
            System.out.println("Aucun joueur disponible");
            return;
        }

        // Si la partie est déjà finie, on passe directement au handler suivant
        if (session.isGameOver()) {
            System.out.println("Jeu déjà terminé - pas de placement possible");
            passToNext(session);
            return;
        }

        // Affiche l'avancement du tour du joueur courant
        if (session.getPiecesPlacedThisTurn() >= 4) {
            System.out.println("  ✓ " + currentPlayer.getName() + " a complété son tour (4/4 pièces)");
        } else {
            System.out.println("  → " + currentPlayer.getName() + " doit placer "
                    + session.getPiecesRemaining() + " pièce(s) de plus");
        }

        // Passe au handler suivant de la chaîne
        passToNext(session);
    }
}
