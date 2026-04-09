package Game.engine;

public abstract class GameHandler {
    
    protected GameHandler nextHandler;
    
    public void setNextHandler(GameHandler nextHandler) {
        this.nextHandler = nextHandler;
    }
    

    public abstract void handle(GameSession session);
    

    protected void passToNext(GameSession session) {
        if (nextHandler != null) {
            nextHandler.handle(session);
        }
    }
}
