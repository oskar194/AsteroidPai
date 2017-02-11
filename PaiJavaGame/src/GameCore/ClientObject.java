package GameCore;

import java.io.Serializable;

public class ClientObject implements Serializable{
	String name;
	int id;
	double x;
	double y;
	double faceangle;
	boolean alive;
//	PlayerShip ship;
	
	
	public ClientObject(String name, int id, double x, double y, double faceangle, boolean alive){
//		super();
//		this.ship = ship;
		this.id = id;
		this.name = name;
		this.x = x;
		this.y = y;
		this.faceangle = faceangle;
		this.alive = alive;
	}
}
