package Game.engine;

import Game.model.entity.Entity;


public class GameTerminationHandler extends GameHandler {
    
    @Override
    public void handle(GameSession session) {
        // Vérifier si la partie est terminée
        if (session.isGameOver()) {
            handleGameEnd(session);
        } 
        
        // Passer au handler suivant (si y en a)
        passToNext(session); 
    }
    

    private void handleGameEnd(GameSession session) {
        System.out.println("\n PARTIE TERMINÉE!");
        System.out.println("  Total pièces placées: " + session.getTotalPiecesPlaced() + "/8");        
        displayFinalScores(session);
    }
    
    private void displayFinalScores(GameSession session) {
        System.out.println("\n Scores finaux:");
        for (Entity player : session.getPlayers()) {
            System.out.println("    " + player.getName() + ": " + 
                             String.format("%.2f", player.getScore()) + 
                             " (" + player.getShapes().size() + " formes)");
        }
    }
    

    
}
