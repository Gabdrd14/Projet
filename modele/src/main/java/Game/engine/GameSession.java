package Game.engine;

import Game.model.Plateau;
import Game.model.StrategiePlateau;
import Game.model.entity.Entity;
import Game.model.entity.ia;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;

/**
 * Classe représentant la session de jeu.
 */
public class GameSession {
    
    private Plateau plateau;
    private List<Entity> players;
    private StrategiePlateau strategie;
    private int currentPlayerIndex = 0;
    private int piecesPlacedThisTurn = 0;
    private int totalPiecesPlaced = 0;
    private final int PIECES_PER_TURN = 4;
    private final int MAX_PIECES_GAME = 8;
    private boolean gameEndHandled = false;
    
    // Chain of Responsibility handler
    private GameHandler handlerChain;

    // Callback appelé à chaque changement de tour (ex. Hidden Challenge)
    private Runnable onTurnChanged;

    public void setOnTurnChanged(Runnable callback) {
        this.onTurnChanged = callback;
    }
    
    /** 
     * Initialise le jeu avec le plateau et les joueurs
     */
    public GameSession(Plateau plateau, List<Entity> players, StrategiePlateau strategie) {
        this.plateau = plateau;
        this.players = players;
        this.strategie = strategie;
        this.currentPlayerIndex = 0;
        this.piecesPlacedThisTurn = 0;
        this.totalPiecesPlaced = 0;
        
        // Initialise handler chaine
        buildHandlerChain();
        
        if (!players.isEmpty()) {
            plateau.setJoueurCourant(players.get(0));
            System.out.println(">>> " + players.get(0).getName() + " tour commence");

        }
    }
    
    /**
     * Lance le tour de l'IA en différé (invokeLater) pour permettre le rendu.
     */
    private void playIfAI(Entity player) {
        if (player instanceof ia) {
            ia aiPlayer = (ia) player;
            SwingUtilities.invokeLater(() -> {
                aiPlayer.jouer(); // place les formes + fireChange → repaint
                totalPiecesPlaced += PIECES_PER_TURN;
                System.out.println(player.getName() + " (IA) a placé " + PIECES_PER_TURN +
                        " pièces (Total: " + totalPiecesPlaced + "/" + MAX_PIECES_GAME + ")");
                if (handlerChain != null) {
                    handlerChain.handle(GameSession.this);
                }
                if (!isGameOver()) {
                    piecesPlacedThisTurn = 0;
                    currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
                    Entity next = getCurrentPlayer();
                    plateau.setJoueurCourant(next);
                    System.out.println(">>> " + player.getName() + " tour terminé\n");
                    System.out.println(">>> " + next.getName() + " tour commence");
                    if (onTurnChanged != null) onTurnChanged.run();
                    playIfAI(next);
                }
            });
        }
    }
    
 
    private void buildHandlerChain() {
        PlayerVerificationHandler playerHandler = new PlayerVerificationHandler();
        GameTerminationHandler gameEndHandler = new GameTerminationHandler();
        
        // Link le handlers
        playerHandler.setNextHandler(gameEndHandler);
        
        // declare le premier handler
        this.handlerChain = playerHandler;
    }
    
    /**
     * Notifie si une piece est placer , prévient si la tour se finie
     */
    public void onPiecePlaced() {
        if (totalPiecesPlaced >= MAX_PIECES_GAME) {
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
            currentPlayer.getShapes().clear(); // clear les formes du joueur 1 dans sa liste respective 
            
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

        if (onTurnChanged != null) {
            onTurnChanged.run();
        }

        playIfAI(nextPlayer);
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

    public boolean isGameEndHandled() {
        return gameEndHandled;
    }

    public void setGameEndHandled(boolean v) {
        gameEndHandled = v;
    }
    
    public int getPiecesRemainingInGame() {
        return MAX_PIECES_GAME - totalPiecesPlaced;
    }
    
    public void verifyGameState() {
        if (handlerChain != null) {
            handlerChain.handle(this);
        }
    }

    public StrategiePlateau getStrategie() {
        return strategie;
    }

    public Plateau getPlateau() {
        return plateau;
    }
}