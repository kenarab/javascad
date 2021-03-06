package eu.printingin3d.javascad.coords;

import org.junit.Assert;
import org.junit.Test;

public class EdgeCrossSolverTest {
	@Test
	public void testTrivialCrossing1() {
		Coords3d c1 = new Coords3d(+1,0,0);
		Coords3d c2 = new Coords3d(-1.25,0,0);
		
		Coords3d d1 = new Coords3d(0,+1,0);
		Coords3d d2 = new Coords3d(0,-1,0);
		
		Coords3d intersection = EdgeCrossSolver.findIntersection(c1, c2, d1, d2);
		
		Assert.assertNotNull(intersection);
		Assert.assertEquals(Coords3d.ZERO, intersection);
	}
	
	@Test
	public void testTrivialCrossing2() {
		Coords3d c1 = new Coords3d(+1.55,+1.55,+1.55);
		Coords3d c2 = new Coords3d(-1.25,-1.25,-1.25);
		
		Coords3d d1 = new Coords3d(0,+1,0);
		Coords3d d2 = new Coords3d(0,-1,0);
		
		Coords3d intersection = EdgeCrossSolver.findIntersection(c1, c2, d1, d2);
		
		Assert.assertNotNull(intersection);
		Assert.assertEquals(Coords3d.ZERO, intersection);
	}
	
	@Test
	public void testTrivialNonCrossing() {
		Coords3d c1 = new Coords3d(+1,0,-1);
		Coords3d c2 = new Coords3d(-1.25,0,1);
		
		Coords3d d1 = new Coords3d(0,+1,0);
		Coords3d d2 = new Coords3d(0,-1,0);
		
		Coords3d intersection = EdgeCrossSolver.findIntersection(c1, c2, d1, d2);
		
		Assert.assertNull(intersection);
	}
	
	@Test
	public void shouldNotReturnWithOneOfTheEndPoints() {
		// [0,0,-10] between [2,0,-10] and [0,0,-10] ([2,0,-10.5], [2,0,10.5])
		
		Coords3d c1 = new Coords3d(2,0,-10);
		Coords3d c2 = new Coords3d(0,0,-10);
		
		Coords3d d1 = new Coords3d(2,0,-10.5);
		Coords3d d2 = new Coords3d(2,0,+10.5);
		
		Coords3d intersection = EdgeCrossSolver.findIntersection(c1, c2, d1, d2);
		
		Assert.assertNotNull(intersection);
		Assert.assertEquals(new Coords3d(2, 0, -10), intersection);
	}
	/*
	@Test
	public void testRealCase() {
		//Added new vertex: [2,0,-10] between [2,0,-10] and [1.9848,0.2458,-10] ([2,0,-10], [5,0,-10])
		
		Coords3d c1 = new Coords3d(-0.7092,1.87,-10.5);
		Coords3d c2 = new Coords3d(-0.7092,1.87, 10.5);
		
		Coords3d d1 = new Coords3d(-1.0554,1.828,10);
		Coords3d d2 = new Coords3d(-0.3377,1.9151,10);
		
		Coords3d intersection = EdgeCrossSolver.findIntersection(c1, c2, d1, d2);
		
		Assert.assertNotNull(intersection);
		
		Assert.assertEquals(new Coords3d(-0.7092,1.87,10), intersection);
	}*/
}
