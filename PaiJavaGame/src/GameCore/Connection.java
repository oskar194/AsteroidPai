package GameCore;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection implements Runnable {

	private Socket clientSocket = null;
	private String name = null;
	private int id;
	MessageObject in;
	boolean stopped = false;
	ClientObject co;
	
	
	public Connection(Socket clientSocket, String name, int id, ClientObject co){
		this.clientSocket = clientSocket;
		this.name = name;
		this.id = id;
		this.co = co;
	}

	@Override
	public void run() {
		try{
			ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());
			output.flush();
			ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
			output.writeObject(this.id);
			while(!this.stopped){
			try {
				this.in = (MessageObject) input.readObject();
				if(in != null){
					if(in.message == "exit"){
//						output.close();
//						input.close();
//						this.stopped = true;
					}else if(in.message == "gameUpdate"){
						//updateClientInServer(in);
//						this.co.x = in.x;
//						this.co.y = in.y;
//						this.co.alive = in.alive;
//						this.co.faceangle = in.faceangle;
						output.writeObject(new ClientObject(this.name, this.id, in.x, in.y, in.faceangle, in.alive));
						output.flush();
					}
					System.out.println(in.message + " " + in.x + " " + in.y);					
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}				
		}
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void updateClientInServer(MessageObject in) {
		this.co.x = in.x;
		this.co.y = in.y;
		this.co.alive = in.alive;
		this.co.faceangle = in.faceangle;
//		Server.getListReference().add(this.id, this.co);
	}
}
