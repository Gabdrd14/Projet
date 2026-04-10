package Game.engine;

import Game.model.Plateau;
import Game.model.entity.Entity;
import Game.model.entity.ia;
import Game.model.stratGen.StrategiePlateau;

import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;

/**
 * Gère l'état d'une partie : joueurs, tours de jeu, comptage des pièces placées
 * et déclenchement de la chaîne de responsabilité à chaque placement.
 *
 * <p>Patron utilisé : <b>Chain of Responsibility</b> (PlayerVerificationHandler → GameTerminationHandler).</p>
 *
 * <p>Le nombre maximum de pièces est calculé dynamiquement : 4 pièces × nombre de joueurs
 * (4 pour un joueur solo, 8 pour deux joueurs).</p>
 */
public class GameSession {

    /** Plateau de jeu partagé entre la session et la vue. */
    private Plateau plateau;

    /** Liste des joueurs participant à la partie. */
    private List<Entity> players;

    /** Stratégie de génération d'obstacles utilisée pour cette session. */
    private StrategiePlateau strategie;

    /** Index du joueur dont c'est le tour. */
    private int currentPlayerIndex = 0;

    /** Nombre de pièces placées par le joueur courant lors de ce tour. */
    private int piecesPlacedThisTurn = 0;

    /** Nombre total de pièces placées depuis le début de la partie. */
    private int totalPiecesPlaced = 0;

    /** Nombre de pièces qu'un joueur doit placer par tour. */
    private final int PIECES_PER_TURN = 4;

    /** Nombre total de pièces avant fin de partie (4 × nbJoueurs). */
    private final int MAX_PIECES_GAME;

    /** Verrou pour éviter un double déclenchement de la fin de partie. */
    private boolean gameEndHandled = false;

    /** Premier maillon de la chaîne de responsabilité. */
    private GameHandler handlerChain;

    /** Callback déclenché à chaque changement de tour (ex. Hidden Challenge). */
    private Runnable onTurnChanged;

    /**
     * Enregistre un callback appelé à chaque changement de tour.
     * @param callback action à exécuter lors du changement de tour
     */
    public void setOnTurnChanged(Runnable callback) {
        this.onTurnChanged = callback;
    }
    
    /**
     * Crée une nouvelle session de jeu.
     * @param plateau   le plateau de jeu (obstacles + formes placées)
     * @param players   la liste des joueurs (humains ou IA)
     * @param strategie la stratégie de génération d'obstacles
     */
    public GameSession(Plateau plateau, List<Entity> players, StrategiePlateau strategie) {
        this.plateau = plateau;
        this.players = players;
        this.strategie = strategie;
        this.currentPlayerIndex = 0;
        this.piecesPlacedThisTurn = 0;
        this.totalPiecesPlaced = 0;
        this.MAX_PIECES_GAME = players.size() * 4;
        
        // Initialise handler chaine
        buildHandlerChain();
        
        if (!players.isEmpty()) {
            plateau.setJoueurCourant(players.get(0));
            System.out.println(">>> " + players.get(0).getName() + " tour commence");

        }
    }
    
    /**
     * Si le joueur donné est une IA, exécute son tour via SwingUtilities.invokeLater
     * afin de laisser l'EDT rendre les formes avant le placement.
     * Après le placement, met à jour les compteurs et passe le tour suivant.
     * @param player le joueur dont c'est le tour
     */
    private void playIfAI(Entity player) {
        if (player instanceof ia) {
            ia aiPlayer = (ia) player;
            SwingUtilities.invokeLater(() -> { // invokeLater permet de s'assurer que le placement de l'IA se fait après le rendu des formes placées
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
    
 
    /**
     * Construit la chaîne de responsabilité :
     * PlayerVerificationHandler → GameTerminationHandler.
     */
    private void buildHandlerChain() {
        PlayerVerificationHandler playerHandler = new PlayerVerificationHandler();
        GameTerminationHandler gameEndHandler = new GameTerminationHandler();
        playerHandler.setNextHandler(gameEndHandler);
        this.handlerChain = playerHandler;
    }
    
    /**
     * Appelée par CommandHandler à chaque fois qu'un joueur humain place une pièce.
     * Incrémente les compteurs, déclenche le changement de tour si le joueur
     * a atteint 4 pièces, puis active la chaîne de responsabilité.
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
     * Passe au joueur suivant : réinitialise le compteur de pièces du tour,
     * met à jour le joueur courant sur le plateau et déclenche le tour de l'IA
     * si le joueur suivant en est une.
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
    
    /** @return le joueur dont c'est actuellement le tour, ou null si aucun joueur. */
    public Entity getCurrentPlayer() {
        if (players.isEmpty()) return null;
        return players.get(currentPlayerIndex);
    }

    /** @return le nombre de pièces placées par le joueur courant ce tour. */
    public int getPiecesPlacedThisTurn() {
        return piecesPlacedThisTurn;
    }

    /** @return le nombre de pièces qu'il reste à placer au joueur courant ce tour. */
    public int getPiecesRemaining() {
        return PIECES_PER_TURN - piecesPlacedThisTurn;
    }

    /** @return une copie de la liste des joueurs. */
    public List<Entity> getPlayers() {
        return new ArrayList<>(players);
    }

    /** @return l'index du joueur courant dans la liste. */
    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    /** @return le nombre total de pièces placées depuis le début de la partie. */
    public int getTotalPiecesPlaced() {
        return totalPiecesPlaced;
    }

    /** @return true si toutes les pièces autorisées ont été placées. */
    public boolean isGameOver() {
        return totalPiecesPlaced >= MAX_PIECES_GAME;
    }

    /** @return true si la fin de partie a déjà été traitée (anti double-déclenchement). */
    public boolean isGameEndHandled() {
        return gameEndHandled;
    }

    /**
     * Marque la fin de partie comme traitée ou non.
     * @param v true pour indiquer que la fin a été gérée
     */
    public void setGameEndHandled(boolean v) {
        gameEndHandled = v;
    }

    /** @return le nombre de pièces qu'il reste à placer avant la fin de la partie. */
    public int getPiecesRemainingInGame() {
        return MAX_PIECES_GAME - totalPiecesPlaced;
    }

    /** Déclenche manuellement la chaîne de responsabilité sur la session courante. */
    public void verifyGameState() {
        if (handlerChain != null) {
            handlerChain.handle(this);
        }
    }

    /** @return la stratégie de génération d'obstacles utilisée dans cette session. */
    public StrategiePlateau getStrategie() {
        return strategie;
    }

    public Plateau getPlateau() {
        return plateau;
    }
}