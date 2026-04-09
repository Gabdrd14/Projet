package Game.command;

import java.util.ArrayList;
import java.util.List;

import Game.view.GameFrame;

public class CommandHandler {

    private List<OperationCommand> undoList = new ArrayList<>();
    private List<OperationCommand> redoList = new ArrayList<>();

  
    public void handle(OperationCommand cmd) {
        cmd.operate();   
        GameFrame.getInstance().notifyPiecePlaced();
        undoList.add(cmd);        
        redoList.clear();

    }

    public void record(OperationCommand cmd) {
        undoList.add(cmd);
        redoList.clear();
    }

    public void undo() {
        if (!undoList.isEmpty()) {
            OperationCommand dernierElt = undoList.remove(undoList.size() - 1);
            dernierElt.compensate();   
            redoList.add(dernierElt); 
        } else {
            System.out.println("Rien à annuler !");
        }
    }

  
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