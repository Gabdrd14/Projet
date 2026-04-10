package Game.state;

import Game.model.Point;

public interface StateController {
	
	// On déclenche l’action au clic souris //
    public void mousePressed(Point p);

    // On met à jour l’action pendant le déplacement de la souris //
    public void mouseDragged(Point p);

    // On finalise l’action au relâchement du clic //
    public void mouseReleased(Point p);
}