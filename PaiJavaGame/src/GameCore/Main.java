package GameCore;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Main extends Applet implements Runnable, KeyListener {

	private static final long serialVersionUID = 1L;

	boolean flagTurnL = false;
	boolean flagTurnR = false;
	boolean flagSpeed = false;
	
	private String hostName = "localhost";
	private int portNumber = 9000;
	Socket connetionSocket;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	public ArrayList<ClientObject> clientList;
	int id;
	ClientObject co;

	Thread gameloop;

	BufferedImage backbuffer;

	Graphics2D g2d;

	boolean showBounds = true;

	AffineTransform identity = new AffineTransform();

	PlayerShip ship = new PlayerShip();

	@Override
	public void keyPressed(KeyEvent k) {
		int key = k.getKeyCode();
		switch(key){
		case KeyEvent.VK_UP:{
			ship.setThursting(true);
			break;
		}

		case KeyEvent.VK_RIGHT:{
			ship.setTurning(3);
			break;
		}
		case KeyEvent.VK_LEFT:{
			ship.setTurning(-3);
			break;
		}
		case KeyEvent.VK_SPACE:{
			ship.shoot();
		}
	}}

	@Override
	public void keyReleased(KeyEvent k) {
		int key = k.getKeyCode();
		switch(key){
		case KeyEvent.VK_UP:{
			ship.setThursting(false);
			break;
		}
		case KeyEvent.VK_RIGHT:{
			ship.setTurning(0);
			break;
		}
		case KeyEvent.VK_LEFT:{
			ship.setTurning(0);
			break;
		}
		}

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		Thread t = Thread.currentThread();
		while (t == gameloop){
			try{
				gameUpdate();
				Thread.sleep(20);
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}
			repaint();
		}
	}

	public void init(){
		try {
			this.connetionSocket = new Socket(this.hostName, this.portNumber);
			this.output = new ObjectOutputStream(this.connetionSocket.getOutputStream());
			this.output.flush();
			this.input = new ObjectInputStream(this.connetionSocket.getInputStream());
			this.id = (int)this.input.readObject();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(id);

		
		this.resize(640, 480);
		backbuffer = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
		g2d = backbuffer.createGraphics();
		ship.setX(getSize().width / 2);
		ship.setY(getSize().height / 2);
		
		sendToServer("gameUpdate", this.ship);
		
		addKeyListener(this);
	}

	public void update(Graphics g){
		g2d.setTransform(identity);
		g2d.setPaint(Color.BLACK);
		g2d.fillRect(0,0,getSize().width, getSize().height);
		
		g2d.setColor(Color.WHITE);
		g2d.drawString("Ship X: " + Math.round(ship.getX()) + ", Y: " +
		Math.round(ship.getY()) , 5, 10);
		g2d.drawString("Ship VelX: " + Math.round(ship.getVelX()) + ",VelY: " +
		Math.round(ship.getVelY()) , 5, 65);
		g2d.drawString("Move angle: " + Math.round(
		ship.getMoveAngle()), 5, 25);
		g2d.drawString("Face angle: " + Math.round(
		ship.getFaceAngle()), 5, 40);
		
		drawShip();
		drawLasers();
		paint(g);
	}

	public void drawShip(){
		
		for (ClientObject clientObject : clientList) {
			if(clientObject != null){
				if(clientObject.id != this.id){
					if(clientObject.alive){					
						g2d.setTransform(identity);
						g2d.translate(clientObject.x, clientObject.y);
						g2d.rotate(Math.toRadians(clientObject.faceangle));
						g2d.setColor(Color.WHITE);
						g2d.fill(clientObject.shape);
					}
				}else if(this.ship.isAlive()){
					g2d.setTransform(identity);
					g2d.translate(ship.getX(), ship.getY());
					g2d.rotate(Math.toRadians(ship.getFaceAngle()));
					g2d.setColor(Color.ORANGE);
					g2d.fill(ship.getShape());						
				}
			}
		}
	}
	
	public void drawLasers(){
		for (ClientObject clientObject : clientList) {
			if(clientObject != null){
				if(clientObject.id != this.id){
					for(int i=0; i<PlayerShip.LQ; i++){
						if(clientObject.laseralive[i]){
							g2d.setTransform(identity);
							g2d.translate(clientObject.laserposx[i], clientObject.laserposy[i]);
							g2d.rotate(Math.toRadians(clientObject.laserangle[i]));
							g2d.setColor(Color.WHITE);
							g2d.fill(clientObject.lasershape[i]);				
						}
					}
				}else{
					for (Laser laser : ship.lasers) {
						if(laser.isAlive()){
							g2d.setTransform(identity);
							g2d.translate(laser.getX(), laser.getY());
							g2d.rotate(Math.toRadians(laser.getFaceAngle()));
							g2d.setColor(Color.WHITE);
							g2d.fill(laser.getShape());				
						}
					}

				}
			}
		}
	}

	public void paint(Graphics g){
		g.drawImage(backbuffer,0,0,this);
	}

	public void start(){
		gameloop = new Thread(this);
		gameloop.start();

	}

	public void stop(){
		gameloop = null;
	}

	public void gameUpdate(){
		updateShip();
		sendToServer("gameUpdate", this.ship);
		readFromServer();
		checkCollisions();
		updateLasers();
	}

	public void updateShip(){
		ship.thurst();
		ship.turn();
		ship.incX(ship.getVelX());
		wrapShip();		
		ship.setVelX(ship.getVelX()*0.99);
		ship.setVelY(ship.getVelY()*0.99);
	}

	public void wrapShip() {
		if(ship.getX() < -ship.getSize()){
			ship.setX(getSize().width + ship.getSize());
		}
		else if(ship.getX() > getSize().width + ship.getSize() ){
			ship.setX(-ship.getSize());
		}
		ship.incY(ship.getVelY());
		if(ship.getY() < -ship.getSize()){
			ship.setY(getSize().height + ship.getSize());
		}else if(ship.getY() > getSize().height + ship.getSize()){
			ship.setY(-ship.getSize());
		}
	}
	
	public void updateLasers(){
		for (Laser laser : ship.lasers) {
			if(laser.isAlive()){
				laser.incX(laser.getVelX());
				if(laser.getX() < 0 || laser.getX() > getSize().width){
					laser.setAlive(false);
				}
				laser.incY(laser.getVelY());
				if(laser.getY() < 0 || laser.getY() > getSize().height){
					laser.setAlive(false);
				}
			}
		}
	}
	
	public void checkCollisions(){
		for (ClientObject clientObject : clientList) {
			for(int i=0; i<PlayerShip.LQ; i++){
				if(clientObject.laseralive[i]){
					if(this.ship.getBounds().contains(clientObject.laserposx[i], clientObject.laserposy[i])){
						this.ship.setAlive(false);
					}
				}
				
			}
		}
	}

	public void sendToServer(String message, PlayerShip ship){
		try {
			this.output.writeObject(new MessageObject(message, ship.getX(), ship.getY(), ship.getFaceAngle(), ship.isAlive(), ship.getShape(), ship.lasers));
			this.output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void readFromServer(){
		try {
			this.clientList = (ArrayList<ClientObject>)this.input.readObject();
			if(clientList != null){
				for (ClientObject clientObject : clientList) {
					System.out.println("ClientObject id: " + clientObject.id + " X: " + clientObject.x + "Y: " + clientObject.y);
				}
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}




}
