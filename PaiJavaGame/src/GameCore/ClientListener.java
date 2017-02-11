package GameCore;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class ClientListener implements Runnable {
	
	private ObjectInputStream input;
	public static ArrayList<ClientObject> clientList;
	
	public ClientListener(ObjectInputStream input){
		this.input = input;
	}
	
	@Override
	public void run() {
		while(input != null){
			try {
				clientList = (ArrayList<ClientObject>) input.readObject();
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
