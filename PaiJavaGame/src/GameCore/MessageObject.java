package GameCore;

import java.io.Serializable;

public class MessageObject implements Serializable{

	private static final long serialVersionUID = 1L;
	String message;
	double x;
	double y;
	
	public MessageObject(String message, double x, double y){
		this.message = message;
		this.x = x;
		this.y = y;
	}
}
