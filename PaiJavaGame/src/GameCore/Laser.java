package GameCore;

import java.awt.Rectangle;

public class Laser extends VectorShape {
	public Rectangle getBounds(){
		Rectangle r = new Rectangle((int)getX(), (int)getY(),2,2);
		return r;
	}
	
	public Laser(){
		setShape(new Rectangle(0,0,2,2));
		setAlive(false);
	}
}
