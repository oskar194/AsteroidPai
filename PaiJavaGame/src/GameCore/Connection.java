package GameCore;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Connection implements Runnable {

	private Socket clientSocket = null;
	private String serverMessage = null;
	
	
	public Connection(Socket clientSocket, String serverMessage){
		this.clientSocket = clientSocket;
		this.serverMessage = serverMessage;
	}

	@Override
	public void run() {
		try{
			InputStream input = clientSocket.getInputStream();
			OutputStream output = clientSocket.getOutputStream();
			output.write(("Connected " + this.serverMessage).getBytes());
			output.close();
			input.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
