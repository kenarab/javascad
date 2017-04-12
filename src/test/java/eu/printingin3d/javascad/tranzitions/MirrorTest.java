package eu.printingin3d.javascad.tranzitions;

import static eu.printingin3d.javascad.testutils.AssertEx.assertEqualsWithoutWhiteSpaces;
import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

import eu.printingin3d.javascad.context.ScadGenerationContextFactory;
import eu.printingin3d.javascad.coords.Boundaries3d;
import eu.printingin3d.javascad.coords.Boundary;
import eu.printingin3d.javascad.exceptions.IllegalValueException;
import eu.printingin3d.javascad.models.Abstract3dModel;
import eu.printingin3d.javascad.testutils.Test3dModel;

public class MirrorTest {
	private static final double MAX = 50.8;
	private static final double MIN = 10.2;
	
	private static final double EPSILON = 0.001;
	private static final Test3dModel TEST_MODEL = new Test3dModel("(model)", 
			new Boundaries3d(new Boundary(MIN, MAX), new Boundary(MIN, MAX), new Boundary(MIN, MAX)));

	@Test(expected=IllegalValueException.class)
	public void mirrorXshouldThrowIllegalValueExceptionIfModelIsNull() {
		Mirror.mirrorX(null);
	}
	
	@Test(expected=IllegalValueException.class)
	public void mirrorYshouldThrowIllegalValueExceptionIfModelIsNull() {
		Mirror.mirrorY(null);
	}
	
	@Test(expected=IllegalValueException.class)
	public void mirrorZshouldThrowIllegalValueExceptionIfModelIsNull() {
		Mirror.mirrorZ(null);
	}
	
	@Test
	public void testMirrorX() {
		assertEqualsWithoutWhiteSpaces("mirror([1,0,0]) (model)", 
				Mirror.mirrorX(TEST_MODEL));
	}

	@Test
	public void testMirrorY() {
		assertEqualsWithoutWhiteSpaces("mirror([0,1,0]) (model)", 
				Mirror.mirrorY(TEST_MODEL));
	}

	@Test
	public void testMirrorZ() {
		assertEqualsWithoutWhiteSpaces("mirror([0,0,1]) (model)", 
				Mirror.mirrorZ(TEST_MODEL));
	}

	@Test
	public void testBoundaryWithMirrorX() {
		Boundaries3d boundaries = Mirror.mirrorX(TEST_MODEL).getBoundaries();
		assertEquals(-MAX, boundaries.getX().getMin(), EPSILON);
		assertEquals(-MIN, boundaries.getX().getMax(), EPSILON);
		assertEquals(MIN, boundaries.getY().getMin(), EPSILON);
		assertEquals(MAX, boundaries.getY().getMax(), EPSILON);
		assertEquals(MIN, boundaries.getZ().getMin(), EPSILON);
		assertEquals(MAX, boundaries.getZ().getMax(), EPSILON);
	}
	
	@Test
	public void testBoundaryWithMirrorY() {
		Boundaries3d boundaries = Mirror.mirrorY(TEST_MODEL).getBoundaries();
		assertEquals(MIN, boundaries.getX().getMin(), EPSILON);
		assertEquals(MAX, boundaries.getX().getMax(), EPSILON);
		assertEquals(-MAX, boundaries.getY().getMin(), EPSILON);
		assertEquals(-MIN, boundaries.getY().getMax(), EPSILON);
		assertEquals(MIN, boundaries.getZ().getMin(), EPSILON);
		assertEquals(MAX, boundaries.getZ().getMax(), EPSILON);
	}
	
	@Test
	public void testBoundaryWithMirrorZ() {
		Boundaries3d boundaries = Mirror.mirrorZ(TEST_MODEL).getBoundaries();
		assertEquals(MIN, boundaries.getX().getMin(), EPSILON);
		assertEquals(MAX, boundaries.getX().getMax(), EPSILON);
		assertEquals(MIN, boundaries.getY().getMin(), EPSILON);
		assertEquals(MAX, boundaries.getY().getMax(), EPSILON);
		assertEquals(-MAX, boundaries.getZ().getMin(), EPSILON);
		assertEquals(-MIN, boundaries.getZ().getMax(), EPSILON);
	}
	
	@Test
	public void subModuleShouldInclude() {
		Abstract3dModel testSubject = Mirror.mirrorX(
				new Test3dModel("(model11)").withTag(11)
				).withTag(12);
		
		assertEqualsWithoutWhiteSpaces("mirror([1,0,0]) (model11)",  
				testSubject.subModel(new ScadGenerationContextFactory().include(12).create()));
	}
	
	@Test
	public void subModuleShouldInclude2() {
		Abstract3dModel testSubject = Mirror.mirrorY(
				new Test3dModel("(model11)").withTag(11)
				).withTag(12);
		
		assertEqualsWithoutWhiteSpaces("mirror([0,1,0]) (model11)",  
				testSubject.subModel(new ScadGenerationContextFactory().include(11).create()));
	}
	
	@Test
	public void subModuleShouldExclude() {
		Abstract3dModel testSubject = Mirror.mirrorZ(
				new Test3dModel("(model11)").withTag(11)
				).withTag(12);
		
		Assert.assertNull(testSubject.subModel(new ScadGenerationContextFactory().exclude(11).create()));
	}
	
	@Test
	public void subModuleShouldExclude2() {
		Abstract3dModel testSubject = Mirror.mirrorY(
				new Test3dModel("(model11)").withTag(11)
				).withTag(12);
		
		Assert.assertNull(testSubject.subModel(new ScadGenerationContextFactory().exclude(12).create()));
	}
}
