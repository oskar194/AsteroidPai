package GameCore;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Client implements Runnable{

	private String hostName;
	private int portNumber;
	Socket connetionSocket;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private boolean stopped = false;
	public ArrayList<ClientObject> clientList;
	int id;
	ClientObject co;

	public Client(String hostName, int portNumber){
		this.hostName = hostName;
		this.portNumber = portNumber;
		//clientList = new ArrayList<ClientObject>();
		this.co = null;
		try{
			this.connetionSocket = new Socket(this.hostName, this.portNumber);
		}catch(IOException e){
			throw new RuntimeException("Couldn't connect to server", e);
		}
		try{
			this.output = new ObjectOutputStream(this.connetionSocket.getOutputStream());
			this.output.flush();
			this.input = new ObjectInputStream(this.connetionSocket.getInputStream());
			this.id = getIdFromServer();
			System.out.println(id);
		}catch(IOException e){
			throw new RuntimeException("Couldn't get io from server", e);
		}
	}

	public boolean isStopped(){
		return this.stopped;
	}

	public void stop(){
		this.stopped = true;
		try{
			this.connetionSocket.close();
		}catch(IOException e){
			System.out.println("Couldn't close the socket");
		}
	}

	public void sendToServer(String message, PlayerShip ship){
			try {
				this.output.writeObject(new MessageObject(message, ship.getX(), ship.getY(), ship.getFaceAngle(), ship.isAlive()));
				this.output.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public int getIdFromServer(){
		int id = -1;
		try {
			id = (int)input.readObject();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}
	
	public void startListening(Socket connectionSocket){
		new Thread(new ClientListener(this.id, connectionSocket)).start();
	}
	
	public void readFromServer(){
		System.out.println("Slucham");
		try {
			System.out.println("Slucham");
//			this.clientList = (ArrayList<ClientObject>) this.input.readObject();
			co = (ClientObject)input.readObject();
			System.out.println("MAM");
			if(co != null){					
//				ClientObject co = clientList.get(this.id);
				System.out.println("X:" + co.x + " Y: " + co.y + " Alive: " + co.alive + " faceangle: " + co.faceangle);
				return;
			}else{
				System.out.println("Byl null");
				return;
			}
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	

	@Override
	public void run() {
		while(this.input != null){
			System.out.println("Slucham");
			try {
				System.out.println("Slucham");
//				this.clientList = (ArrayList<ClientObject>) this.input.readObject();
				this.co = (ClientObject)this.input.readObject();
				if(this.co != null){					
//					ClientObject co = clientList.get(this.id);
					System.out.println("X:" + co.x + " Y: " + co.y + " Alive: " + co.alive + " faceangle: " + co.faceangle);
				}
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
