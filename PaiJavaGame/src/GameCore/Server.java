package GameCore;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class Server implements Runnable {
	
	protected int portNumber = 8000;
	protected static ServerSocket serverSocket = null;
	protected static boolean stopped = false;
	protected static Thread runningThread = null;
	private static ArrayList<ClientObject> clientList = null;
	protected int counter;
	private static ArrayList<Thread> threadList;
	
	protected Server(int port){
		try {
			this.portNumber = new ConfigParser("src/GameCore/config.xml").getPort();
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clientList = getListReference();
		this.counter = 0;
		threadList = new ArrayList<Thread>();
	}

	@Override
	public void run() {
		synchronized(this){
			runningThread = Thread.currentThread();
		}
		createServerSocket();
		while(! isStopped()){
			Socket clientSocket = null;
			try{
				clientSocket = serverSocket.accept();
			} catch (IOException e){
				if(isStopped()){
					System.out.println("Server stopped working");
					return;
				}
				throw new RuntimeException(
						"Cannot connect client", e);
			}
			ClientObject co = new ClientObject("Player"+counter, counter, 0, 0, 0, false,null);
			clientList.add(counter, co);
			threadList.add(new Thread(
					new Connection(
							clientSocket, "Player"+counter, counter, co)));
			threadList.get(this.counter).start();
			this.counter++;
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
		if(getListReference().isEmpty()){
			stop();
		}
//		server.stop();
	}
	
	private synchronized static void stop(){
		stopped = true;
		try{
			serverSocket.close();
		}catch(IOException e){
			throw new RuntimeException("Cannot close server", e);
		}
		try {
			runningThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void createServerSocket(){
		try{
			serverSocket = new ServerSocket(this.portNumber);
		} catch(IOException e){
			throw new RuntimeException("Cannot open port number " + this.portNumber, e);
		}
	}

	private synchronized boolean isStopped(){
		return stopped;
	}
	
	public static ArrayList<ClientObject> getListReference(){
		if(clientList == null){
			clientList = new ArrayList<ClientObject>();
		}
		return clientList;
	}
	
	public static void changeList(int id, ClientObject co){
		getListReference().set(id, co);
	}
	
	public static void deleteUser(int id){
		getListReference().set(id, null);
		try {
			threadList.get(id).join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
