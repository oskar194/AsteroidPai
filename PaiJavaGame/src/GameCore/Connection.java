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
	private String serverMessage = null;
	MessageObject in;
	boolean stopped = false;
	
	
	public Connection(Socket clientSocket, String serverMessage){
		this.clientSocket = clientSocket;
		this.serverMessage = serverMessage;
		System.out.println("Fajnie");
	}

	@Override
	public void run() {
		try{
			ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());
			output.flush();
			ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
			//output.write(("Connected " + this.serverMessage).getBytes());
			//this.open = true;
			while(!this.stopped){
			try {
				this.in = (MessageObject) input.readObject();
				if(in.message == "exit"){
					output.close();
					input.close();
					this.stopped = true;
				}
				System.out.println(in.message + in.x + in.y);
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
