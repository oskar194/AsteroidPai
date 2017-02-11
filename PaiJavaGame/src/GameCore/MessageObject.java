package GameCore;

import java.io.Serializable;

public class MessageObject implements Serializable{

	private static final long serialVersionUID = 1L;
	String message;
	PlayerShip ship;
	
	public MessageObject(String message, PlayerShip ship){
		this.message = message;
		this.ship = ship;
	}
}
