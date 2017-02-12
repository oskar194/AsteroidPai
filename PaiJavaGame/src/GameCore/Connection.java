package GameCore;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection implements Runnable {

	private Socket clientSocket = null;
//	private String name = null;
	private int id;
	MessageObject in;
	boolean stopped = false;
	ClientObject co;
	
	
	public Connection(Socket clientSocket, String name, int id, ClientObject co){
		this.clientSocket = clientSocket;
//		this.name = name;
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
			output.flush();
			while(!this.stopped){
			try {
				this.in = (MessageObject) input.readObject();
				if(this.in != null){
					if(this.in.message.equals("exit")){
						output.close();
						input.close();
						this.stopped = true;
						Server.deleteUser(this.id);
						return;
					}else{
						updateClientInServer(in);
						output.reset();
						output.writeObject(Server.getListReference());
						output.flush();
					}
					System.out.println(this.in.message + " " + this.in.x + " " + this.in.y);					
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
		if(!this.stopped){			
			this.co.x = in.x;
			this.co.y = in.y;
			this.co.alive = in.alive;
			this.co.faceangle = in.faceangle;
			this.co.shape = in.shape;
			this.co.laserangle = in.laserangle;
			this.co.laserposx = in.laserposx;
			this.co.laserposy = in.laserposy;
			this.co.laseralive = in.laseralive;
			this.co.lasershape = in.lasershape;
			this.co.laserbounds = in.laserbounds;
			this.co.bounds = in.bounds;
			this.co.score = in.score;
			Server.changeList(this.co.id, this.co);
		}
	}
}
