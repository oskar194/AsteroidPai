package GameCore;

import java.awt.Polygon;
import java.awt.Rectangle;

public class PlayerShip extends VectorShape{
	private int size = 20;
	static final int LQ = 20;
	private int [] shipx ={-size, 0, size};
	private int [] shipy ={size, -size, size};
	private boolean thursting = false;
	private double turning = 0;
	public Laser [] lasers = new Laser[LQ];
	private int currentLaser = 0;

	public Rectangle getBounds(){
		Rectangle r;
		r = new Rectangle((int)this.getX(), (int)this.getY(), this.size*2, this.size*2);
		return r;
	}

	public PlayerShip(){
		setShape(new Polygon(shipx, shipy, shipx.length));
		setAlive(true);
		for(int i=0; i < LQ; i++)
		lasers[i] = new Laser();
	}
	
	public int getSize(){
		return this.size;
	}
	
	public void thurst(){
		if(this.isThursting()){			
			this.setMoveAngle(this.getFaceAngle() - 90);
			this.incVelX((calcAngleMoveX(this.getMoveAngle()) * 0.1));
			this.incVelY((calcAngleMoveY(this.getMoveAngle()) * 0.1));	
		}
	}
	
	public void turn(){
		this.incFaceAngle(this.turning);
		if(this.getFaceAngle() < 0){
			this.setFaceAngle(360 - this.turning);
		}else if(this.getFaceAngle() > 360){
			this.setFaceAngle(0);
		}
	}
	
	public void setTurning(double angle){
		this.turning = angle;
	}
	
	public boolean isThursting(){
		return this.thursting;
	}
	
	public void setThursting(boolean t){
		this.thursting = t;
	}
	
	public void shoot(){
		if(this.currentLaser > this.lasers.length -1){
			this.currentLaser = 0;
		}
		this.lasers[currentLaser].setX(this.getX());
		this.lasers[currentLaser].setY(this.getY());
		this.lasers[currentLaser].setFaceAngle(this.getFaceAngle());
		this.lasers[currentLaser].setMoveAngle(this.lasers[currentLaser].getFaceAngle() - 90);
		this.lasers[currentLaser].setVelX((this.calcAngleMoveX(this.lasers[currentLaser].getMoveAngle()) * 5));
		this.lasers[currentLaser].setVelY((this.calcAngleMoveY(this.lasers[currentLaser].getMoveAngle()) * 5));
		this.lasers[currentLaser].setAlive(true);
		this.currentLaser++;
	}
	
	public double calcAngleMoveX(double angle) {
		return (double) (Math.cos(angle * Math.PI / 180));
	}

	public double calcAngleMoveY(double angle) {
		return (double) (Math.sin(angle * Math.PI / 180));
	}
}
