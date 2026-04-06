package Game.state;

import java.awt.Point;

public interface StateController {

    public void mousePressed(Point p);

    public void mouseDragged(Point p);

    public void mouseReleased(Point p);
}