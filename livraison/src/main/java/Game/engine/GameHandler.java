package Game.engine;

/**
 * Classe abstraite représentant un maillon de la chaîne de responsabilité (Chain of Responsibility).
 * Chaque handler peut traiter une requête liée à la session de jeu,
 * puis passer la main au handler suivant dans la chaîne.
 */
public abstract class GameHandler {

    /** Handler suivant dans la chaîne (null si dernier maillon). */
    protected GameHandler nextHandler;

    /**
     * Définit le handler suivant dans la chaîne.
     * @param nextHandler le prochain GameHandler à appeler
     */
    public void setNextHandler(GameHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    /**
     * Traite la session de jeu. Chaque sous-classe implémente sa propre logique.
     * @param session la session de jeu courante
     */
    public abstract void handle(GameSession session);

    /**
     * Passe la session au handler suivant dans la chaîne, s'il existe.
     * @param session la session de jeu courante
     */
    protected void passToNext(GameSession session) {
        if (nextHandler != null) {
            nextHandler.handle(session);
        }
    }
}
