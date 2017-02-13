package GameCore;

import static org.junit.Assert.*;

import java.awt.Rectangle;

import org.junit.Test;

public class LaserTest {

	@Test
	public void testGetBounds() {
		Laser l = new Laser();
		assertEquals(new Rectangle(0,0,2,2),l.getBounds());
	}

	@Test
	public void testLaser() {
		Laser l = new Laser();
		assertNotEquals(new Laser(),l);
	}

}
