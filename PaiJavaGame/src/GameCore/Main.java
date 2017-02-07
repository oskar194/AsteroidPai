package GameCore;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Main extends Applet implements Runnable, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	double tVelX = 0;
	double tVelY = 0;
	double tTurnR = 0;
	double tTurnL = 0;
	boolean flagTurnL = false;
	boolean flagTurnR = false;
	boolean flagSpeed = false;


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
			flagSpeed = true;
			tVelX = 0;
			tVelY = 0;
			while( flagSpeed){
				tVelX += (calcAngleMoveX(ship.getMoveAngle()) * 0.1);
				tVelY += (calcAngleMoveY(ship.getMoveAngle()) * 0.1);
			}
			break;
		}
		case KeyEvent.VK_RIGHT:{
			flagTurnR = true;
			tTurnR = 0;
			while(flagTurnR){
				tTurnR += 5;
			}
			break;
		}
		case KeyEvent.VK_LEFT:{
			flagTurnL = true;
			tTurnL = 0;
			while(flagTurnL){
				tTurnL -= 5;
			}
			break;
		}
		}

	}

	@Override
	public void keyReleased(KeyEvent k) {
		int key = k.getKeyCode();
		switch(key){
		case KeyEvent.VK_UP:{
			flagSpeed = false;
			ship.setMoveAngle(ship.getFaceAngle() - 90);
			ship.incVelX(tVelX);
			ship.incVelY(tVelY);
			break;
		}
		case KeyEvent.VK_RIGHT:{
			flagTurnR = false;
			ship.incFaceAngle(tTurnR);
			if(ship.getFaceAngle() > 360){
				ship.setFaceAngle(5);
			}
			break;
		}
		case KeyEvent.VK_LEFT:{
			flagTurnL = false;
			ship.incFaceAngle(tTurnL);
			if(ship.getFaceAngle() < 0){
				ship.setFaceAngle(360-5);
			}
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
				System.out.println("X: " + ship.getX() + " Y: " + ship.getY());
				System.out.println("w: " + getSize().width + " h: " + getSize().height);
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}
			repaint();
		}
	}

	public void init(){
		this.resize(640, 480);
		backbuffer = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
		g2d = backbuffer.createGraphics();
		ship.setX(100);
		ship.setY(100);
		addKeyListener(this);
	}

	public void update(Graphics g){
		g2d.setTransform(identity);
		g2d.setPaint(Color.BLACK);
		g2d.fillRect(0,0,getSize().width, getSize().height);
		drawShip();
		paint(g);
	}

	public void drawShip(){
		g2d.setTransform(identity);
		g2d.translate(ship.getX(), ship.getY());
		g2d.rotate(Math.toRadians(ship.getFaceAngle()));
		g2d.setColor(Color.ORANGE);
		g2d.fill(ship.getShape());
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
	}

	public void updateShip(){
		ship.incX(ship.getVelX());
		if(ship.getX() < -10){
			ship.setX(getSize().width +10);
		}
		else if(ship.getX() > getSize().width +10 ){
			ship.setX(-10);
		}
		ship.incY(ship.getVelY());
		if(ship.getY() < -10){
			ship.setY(getSize().height + 10);
		}else if(ship.getY() > getSize().height + 10){
			ship.setY(-10);
		}
	}

	public double calcAngleMoveX(double angle) {
		return (double) (Math.cos(angle * Math.PI / 180));
	}

	public double calcAngleMoveY(double angle) {
		return (double) (Math.sin(angle * Math.PI / 180));
	}


}
