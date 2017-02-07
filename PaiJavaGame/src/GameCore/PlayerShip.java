package GameCore;

import java.awt.Polygon;
import java.awt.Rectangle;

public class PlayerShip extends VectorShape {
	private int size = 20;
	private int [] shipx ={-size, 0, size};
	private int [] shipy ={0, -size, 0};

	public Rectangle getBounds(){
		Rectangle r;
		r = new Rectangle((int)getX(), (int)getY(), size, size);
		return r;
	}

	public PlayerShip(){
		setShape(new Polygon(shipx, shipy, shipx.length));
		setAlive(true);
	}
}
