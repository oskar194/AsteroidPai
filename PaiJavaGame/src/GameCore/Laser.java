package GameCore;

import java.awt.Rectangle;
import java.io.Serializable;

public class Laser extends VectorShape implements Serializable {
	public Rectangle getBounds(){
		Rectangle r = new Rectangle((int)getX(), (int)getY(),2,2);
		return r;
	}
	
	public Laser(){
		setShape(new Rectangle(0,0,2,2));
		setAlive(false);
	}
}
