package GameCore;

import java.awt.Shape;
import java.io.Serializable;

public class MessageObject implements Serializable{

	private static final long serialVersionUID = 1L;
	String message;
	double x;
	double y;
	double faceangle;
	boolean alive;
	Shape shape;
	double laserposx[] = new double[PlayerShip.LQ];
	double laserposy[] = new double[PlayerShip.LQ];
	double laserangle[] = new double[PlayerShip.LQ];
	boolean laseralive[] = new boolean[PlayerShip.LQ];
	Shape lasershape[] = new Shape[PlayerShip.LQ];
	//PlayerShip ship;
	
//	public MessageObject(String message, PlayerShip ship){
//		this.message = message;
//		this.ship = ship;
//	}
	
	public MessageObject(String message, double x, double y, double faceangle, boolean alive, Shape shape, Laser laserTable[]){
		this.message = message;
		this.x = x;
		this.y = y;
		this.faceangle = faceangle;
		this.alive = alive;
		this.shape = shape;
		int i = 0;
		for (Laser laser : laserTable) {
			this.laserangle[i] = laser.getFaceAngle();
			this.laserposx[i] = laser.getX();
			this.laserposy[i] = laser.getY();
			this.laseralive[i] = laser.isAlive();
			this.lasershape[i] = laser.getShape();
			i++;
		}
	}
}
