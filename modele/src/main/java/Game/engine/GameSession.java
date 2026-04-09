package Game.engine;

import Game.model.Plateau;
import Game.model.entity.Entity;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant la session de jeu.
 */
public class GameSession {
    
    private Plateau plateau;
    private List<Entity> players;
    private int currentPlayerIndex = 0;
    private int piecesPlacedThisTurn = 0;
    private int totalPiecesPlaced = 0;
    private final int PIECES_PER_TURN = 4;
    private final int MAX_PIECES_GAME = 8;
    
    // Chain of Responsibility handlers
    private GameHandler handlerChain;
    
    /**
     * Initialise le jeu avec le plateau et les joueurs
     */
    public GameSession(Plateau plateau, List<Entity> players) {
        this.plateau = plateau;
        this.players = players;
        this.currentPlayerIndex = 0;
        this.piecesPlacedThisTurn = 0;
        this.totalPiecesPlaced = 0;
        
        // Initialise handler chain
        buildHandlerChain();
        
        if (!players.isEmpty()) {
            plateau.setJoueurCourant(players.get(0));
            System.out.println(">>> " + players.get(0).getName() + " tour commence");
        }
    }
    
    /**
     * Build Chain of Responsibility handlers
     */
    private void buildHandlerChain() {
        PlayerVerificationHandler playerHandler = new PlayerVerificationHandler();
        GameTerminationHandler gameEndHandler = new GameTerminationHandler();
        
        // Link handlers
        playerHandler.setNextHandler(gameEndHandler);
        
        // Set first handler
        this.handlerChain = playerHandler;
    }
    
    /**
     * Notifie si une piece est placer , prévient si la tour se finie
     */
    public void onPiecePlaced() {if (totalPiecesPlaced >= MAX_PIECES_GAME) {
            System.out.println("Partie terminée! Max " + MAX_PIECES_GAME + " pièces atteint");
            return;
        }
        
        piecesPlacedThisTurn++;
        totalPiecesPlaced++;
        
        Entity currentPlayer = getCurrentPlayer();
        System.out.println(currentPlayer.getName() + " a placé une pièce: " + 
                         piecesPlacedThisTurn + "/" + PIECES_PER_TURN + 
                         " (Total: " + totalPiecesPlaced + "/" + MAX_PIECES_GAME + ")");
        
        if (piecesPlacedThisTurn >= PIECES_PER_TURN) {
            nextTurn();
        }


        if (handlerChain != null) {
            handlerChain.handle(this);
        }
    }
    
    /**
     * change de joueur pour le tour
     */
    public void nextTurn() {
        Entity currentPlayer = getCurrentPlayer();
        System.out.println(">>> " + currentPlayer.getName() + " tour terminé\n");
        
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        piecesPlacedThisTurn = 0;
        
        Entity nextPlayer = getCurrentPlayer();
        plateau.setJoueurCourant(nextPlayer);
        System.out.println(">>> " + nextPlayer.getName() + " tour commence");
    }
    

    public Entity getCurrentPlayer() {
        if (players.isEmpty()) return null;
        return players.get(currentPlayerIndex);
    }
    
    public int getPiecesPlacedThisTurn() {
        return piecesPlacedThisTurn;
    }
    
    public int getPiecesRemaining() {
        return PIECES_PER_TURN - piecesPlacedThisTurn;
    }
    

    public List<Entity> getPlayers() {
        return new ArrayList<>(players);
    }
    
    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public int getTotalPiecesPlaced() {
        return totalPiecesPlaced;
    }

    public boolean isGameOver() {
        return totalPiecesPlaced >= MAX_PIECES_GAME;
    }
    
    public int getPiecesRemainingInGame() {
        return MAX_PIECES_GAME - totalPiecesPlaced;
    }
    
    public void verifyGameState() {
        if (handlerChain != null) {
            handlerChain.handle(this);
        }
    }
}
