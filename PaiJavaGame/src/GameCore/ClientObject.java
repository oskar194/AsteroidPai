package GameCore;

import java.awt.Shape;
import java.io.Serializable;

public class ClientObject implements Serializable{
	private static final long serialVersionUID = 1L;
	String name;
	int id;
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
//	PlayerShip ship;
	
	
	public ClientObject(String name, int id, double x, double y, double faceangle, boolean alive, Shape shape){
//		super();
//		this.ship = ship;
		this.id = id;
		this.name = name;
		this.x = x;
		this.y = y;
		this.faceangle = faceangle;
		this.alive = alive;
		this.shape = shape;
//		for (int i = 0; i < PlayerShip.LQ; i++) {
//			this.laserangle[i] = laserangle[i];
//			this.laserposx[i] = laserposx[i];
//			this.laserposy[i] = laserposy[i];
//			this.laseralive[i] = laseralive[i];
//			this.lasershape[i] = lasershape[i];
//		}
	}
	
//	public void addLasers(double laserposx[], double laserposy[], double laserangle[], boolean laseralive[], Shape lasershape[]){
//		for (int i = 0; i < PlayerShip.LQ; i++) {
//			this.laserangle[i] = laserangle[i];
//			this.laserposx[i] = laserposx[i];
//			this.laserposy[i] = laserposy[i];
//			this.laseralive[i] = laseralive[i];
//			this.lasershape[i] = lasershape[i];
//		}
//	}
}
