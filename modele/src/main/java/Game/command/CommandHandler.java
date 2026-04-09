package Game.command;

import java.util.ArrayList;
import java.util.List;

import Game.view.GameFrame;

public class CommandHandler {

    private List<OperationCommand> undoList = new ArrayList<>();
    private List<OperationCommand> redoList = new ArrayList<>();

    // On exécute une commande et on l’enregistre dans l’historique //
    public void handle(OperationCommand cmd) {
        cmd.operate();   
        
        // On notifie la vue qu’une action a été effectuée //
        GameFrame.getInstance().notifyPiecePlaced();
        undoList.add(cmd);        
        
        // On efface le redo car une nouvelle action invalide l’historique futur //
        redoList.clear();

    }

    // On enregistre une commande sans l’exécuter : Pour le move d"une forme // 
    public void record(OperationCommand cmd) {
        undoList.add(cmd);
        redoList.clear();
    }

    // On annule la dernière action effectuée //
    public void undo() {
        if (!undoList.isEmpty()) {
            OperationCommand dernierElt = undoList.remove(undoList.size() - 1);
            dernierElt.compensate();   
            redoList.add(dernierElt); 
        } else {
            System.out.println("Rien à annuler !");
        }
    }

    // On réapplique une action annulée //
    public void redo() {
        if (!redoList.isEmpty()) {
            OperationCommand cmd = redoList.remove(redoList.size() - 1);
            cmd.operate();            
            undoList.add(cmd);       
        } else {
            System.out.println("Rien à refaire !");
        }
    }
    
    public void printStatus() {
        System.out.println("Undo list size: " + undoList.size());
        System.out.println("Redo list size: " + redoList.size());
    }
}