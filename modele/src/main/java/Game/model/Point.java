package Game.model;

public class Point extends AbstractModeleEcoutable {
	
	private double x;
	private double y;
	
	public Point(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void translation(double dx, double dy) {
		 x += dx;
		 y += dy;
		 fireChange();
	}
	
	//public getPoint

}