package GameCore;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
	
	protected int portNumber = 8000;
	protected ServerSocket serverSocket = null;
	protected boolean stopped = false;
	protected Thread runningThread = null;
	
	public Server(int port){
		this.portNumber = port;
	}

	@Override
	public void run() {
		synchronized(this){
			this.runningThread = Thread.currentThread();
		}
		createServerSocket();
		while(! isStopped()){
			Socket clientSocket = null;
			try{
				clientSocket = this.serverSocket.accept();
			} catch (IOException e){
				if(isStopped()){
					System.out.println("Server stopped working");
					return;
				}
				throw new RuntimeException(
						"Cannot connect client", e);
			}
			new Thread(
					new Connection(
							clientSocket, "Server")).start();
			System.out.println("Server stopped");
		}
		
	}
	
	
	public static void main(String[] args) {
		Server server = new Server(9000);
		new Thread(server).start();

		try {
		    Thread.sleep(20 * 1000);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		System.out.println("Stopping Server");
		server.stop();
	}
	
	private synchronized void stop(){
		this.stopped = true;
		try{
			this.serverSocket.close();
		}catch(IOException e){
			throw new RuntimeException("Cannot close server", e);
		}
	}

	private void createServerSocket(){
		try{
			this.serverSocket = new ServerSocket(this.portNumber);
		} catch(IOException e){
			throw new RuntimeException("Cannot open port number " + this.portNumber, e);
		}
	}

	private synchronized boolean isStopped(){
		return this.stopped;
	}

}
