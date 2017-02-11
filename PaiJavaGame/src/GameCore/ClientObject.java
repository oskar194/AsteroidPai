package GameCore;

public class ClientObject{
	String name;
	int id;
	PlayerShip ship;
	
	
	public ClientObject(String name, int id, PlayerShip ship){
		super();
		this.id = id;
		this.name = name;
		this.ship = ship;
	}
}
