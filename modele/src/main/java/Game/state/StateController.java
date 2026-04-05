package Game.state;

import java.awt.Point;

public abstract class StateController {

    public abstract void mousePressed(Point p);

    public abstract void mouseDragged(Point p);

    public abstract void mouseReleased(Point p);
}