package Game.state;

import Game.model.Point;

public interface StateController {

    public void mousePressed(Point p);

    public void mouseDragged(Point p);

    public void mouseReleased(Point p);
}