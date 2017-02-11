package GameCore;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

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
			//output.write(("Connected " + this.serverMessage).getBytes());
			//this.open = true;
			output.writeObject(this.id);
			while(!this.stopped){
			try {
				this.in = (MessageObject) input.readObject();
				if(in.message == "exit"){
					output.close();
					input.close();
					this.stopped = true;
				}else if(in.message == "gameUpdate"){
					co.ship = in.ship;
					Server.clientList.set(id, co);
					output.writeObject(Server.clientList);	
				}
				System.out.println(in.message + " " + in.ship.getX() + " "+ in.ship.getY());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}				
		}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
