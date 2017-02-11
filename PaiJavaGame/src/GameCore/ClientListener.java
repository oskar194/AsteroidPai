package GameCore;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientListener implements Runnable {
	
	int id;
	private ObjectInputStream input;
	public static ArrayList<ClientObject> clientList;
	
	public ClientListener(int id, Socket connectionSocket){
		try {
			this.input = new ObjectInputStream(connectionSocket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.id = id;
		clientList = new ArrayList<ClientObject>();
	}
	
	@Override
	public void run() {
		while(input != null){
			System.out.println("Slucham");
			try {
				clientList = (ArrayList<ClientObject>) input.readObject();
				if(clientList != null){					
					ClientObject co = clientList.get(id);
					System.out.println("X:" + co.x + " Y: " + co.y + " Alive: " + co.alive + " faceangle: " + co.faceangle);
				}
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
