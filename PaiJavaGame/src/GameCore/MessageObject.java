package GameCore;

import java.io.Serializable;

public class MessageObject implements Serializable{

	private static final long serialVersionUID = 1L;
	String message;
	double x;
	double y;
	double faceangle;
	boolean alive;
	//PlayerShip ship;
	
//	public MessageObject(String message, PlayerShip ship){
//		this.message = message;
//		this.ship = ship;
//	}
	
	public MessageObject(String message, double x, double y, double faceangle, boolean alive){
		this.message = message;
		this.x = x;
		this.y = y;
		this.faceangle = faceangle;
		this.alive = alive;
	}
}
