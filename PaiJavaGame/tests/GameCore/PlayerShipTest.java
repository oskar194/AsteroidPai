package GameCore;

import static org.junit.Assert.*;

import java.awt.Rectangle;

import org.junit.Test;

public class PlayerShipTest {

	@Test
	public void testGetBounds() {
		PlayerShip p = new PlayerShip();
		assertEquals(new Rectangle(0,0,p.getSize()*2,p.getSize()*2), p.getBounds());
	}

	@Test
	public void testPlayerShip() {
		//PlayerShip p = new PlayerShip();
		assertNotSame(new PlayerShip(), new PlayerShip());
	}

	@Test
	public void testThurst() {
		PlayerShip p = new PlayerShip();
		p.setThursting(true);
		p.thurst();
		assertNotEquals(p.getFaceAngle(),p.getMoveAngle());
		assertNotEquals(0,p.getVelX());
		assertNotEquals(0,p.getVelY());
	}

	@Test
	public void testTurn() {
		PlayerShip p = new PlayerShip();
		p.setTurning(180);
		p.turn();
		assertEquals(180,p.getFaceAngle(),0.01);
	}
	
	@Test
	public void testTurnMoreThan360() {
		PlayerShip p = new PlayerShip();
		p.setTurning(370);
		p.turn();
		assertEquals(0,p.getFaceAngle(),0.01);
	}
	
	@Test
	public void testTurnLessThan0() {
		PlayerShip p = new PlayerShip();
		p.setTurning(-10);
		p.turn();
		assertEquals(370,p.getFaceAngle(),0.01);
	}

	@Test
	public void testShoot() {
		PlayerShip p = new PlayerShip();
		p.shoot();
		assertTrue(p.lasers[0].isAlive());
	}

	@Test
	public void testCalcAngleMoveX() {
		PlayerShip p = new PlayerShip();
		assertEquals(1, p.calcAngleMoveX(0),0.01);
		
	}

	@Test
	public void testCalcAngleMoveY() {
		PlayerShip p = new PlayerShip();
		assertEquals(0, p.calcAngleMoveY(0),0.01);
	}

}
