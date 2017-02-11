package GameCore;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Client {//implements Runnable{

	private String hostName;
	private int portNumber;
	Socket connetionSocket;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private boolean stopped = false;


	public Client(String hostName, int portNumber){
		this.hostName = hostName;
		this.portNumber = portNumber;
		try{
			this.connetionSocket = new Socket(this.hostName, this.portNumber);
		}catch(IOException e){
			throw new RuntimeException("Couldn't connect to server", e);
		}
		try{
			this.output = new ObjectOutputStream(this.connetionSocket.getOutputStream());
			this.output.flush();
			this.input = new ObjectInputStream(this.connetionSocket.getInputStream());
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
		if(message == "exit"){
			try {
				this.output.close();
				this.input.close();
				return;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			try {
				this.output.writeObject(new MessageObject(message, ship));
				this.output.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}
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
	
	public void startListening(){
		new Thread(new ClientListener(input)).start();
	}
	
	public ArrayList<ClientObject> readFromServer(){
		return ClientListener.clientList;
	}

	//	@Override
	//	public void run() {
	//		// TODO Auto-generated method stub
	//		
	//	}

}
