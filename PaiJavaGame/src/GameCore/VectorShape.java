package GameCore;

import java.awt.Shape;

public class VectorShape {
	private Shape shape;
	private boolean alive;
	private double velX, velY;
	private double x, y;
	private double faceAngle, moveAngle;

	public Shape getShape() {
		return shape;
	}
	public void setShape(Shape shape) {
		this.shape = shape;
	}
	public boolean isAlive() {
		return alive;
	}
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	public double getVelX() {
		return velX;
	}
	public void setVelX(double velX) {
		this.velX = velX;
	}
	public void incVelX(double d){
		this.velX += d;
	}
	public double getVelY() {
		return velY;
	}
	public void setVelY(double velY) {
		this.velY = velY;
	}
	public void incVelY(double d){
		this.velY += d;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public void incX(double d){
		this.x += d;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public void incY(double d){
		this.y += d;
	}
	public double getFaceAngle() {
		return faceAngle;
	}
	public void setFaceAngle(double faceAngle) {
		this.faceAngle = faceAngle;
	}
	public void incFaceAngle(double angle) {
		this.faceAngle += angle;
	}
	public double getMoveAngle() {
		return moveAngle;
	}
	public void setMoveAngle(double moveAngle) {
		this.moveAngle = moveAngle;
	}
	public void incMoveAngle(double angle){
		this.moveAngle += angle;
	}
	public VectorShape() {
		setAlive(false);
		setFaceAngle(0);
		setMoveAngle(0);
		setShape(null);
		setVelX(0);
		setVelY(0);
		setX(0);
		setY(0);
	}
	
}
