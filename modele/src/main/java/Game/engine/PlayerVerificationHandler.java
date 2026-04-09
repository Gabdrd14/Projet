package Game.engine;

import Game.model.entity.Entity;

public class PlayerVerificationHandler extends GameHandler {
    
    @Override
    public void handle(GameSession session) {
        Entity currentPlayer = session.getCurrentPlayer();
        
        if (currentPlayer == null) {
            System.out.println("Aucun joueur disponible");
            return;
        }
             //
        // Vérifier que le joueur peut encore jouer
        if (session.isGameOver()) {
            System.out.println("Jeu déjà terminé - pas de placement possible");
            passToNext(session);
            return;
        }
        
        // Vérifier si le tour du joueur est complet
        if (session.getPiecesPlacedThisTurn() >= 4) {
            System.out.println("  ✓ " + currentPlayer.getName() + " a complété son tour (4/4 pièces)");
        } else {
            System.out.println("  → " + currentPlayer.getName() + " doit placer " + 
                             session.getPiecesRemaining() + " pièce(s) de plus");
        }
        
        // Passer au handler suivant
        passToNext(session);
    }
}
