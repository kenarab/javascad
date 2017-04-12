package eu.printingin3d.javascad.models;

import java.io.File;

import org.junit.Test;

import eu.printingin3d.javascad.basic.Angle;
import eu.printingin3d.javascad.coords.Boundaries3d;
import eu.printingin3d.javascad.coords.Boundaries3dTest;
import eu.printingin3d.javascad.coords.Boundary;
import eu.printingin3d.javascad.coords.Coords3d;
import eu.printingin3d.javascad.coords2d.Boundaries2d;
import eu.printingin3d.javascad.testutils.AssertEx;
import eu.printingin3d.javascad.testutils.Test2dModel;

public class LinearExtrudeTest {
    public static final File mavenTargetPath = new File(System.getProperty("basedir"), "target");
    
	@Test
	public void testToScad() {
		Abstract3dModel testSubject = new LinearExtrude(new Test2dModel("(model)", 
				new Boundaries2d(new Boundary(-5, 5), new Boundary(-5, 5))), 10, Angle.ofDegree(20), 2);
		AssertEx.assertEqualsWithoutWhiteSpaces("linear_extrude(height=10, center=true, convexity=10, twist=20, scale=2) (model)", 
				testSubject);
	}
	
	@Test
	public void testBoundariesWithZeroTwist() {
		Abstract3dModel testSubject = new LinearExtrude(new Test2dModel("(model)", 
				new Boundaries2d(new Boundary(-5, 0), new Boundary(-10, 5))), 30, Angle.ZERO, 2);
		Boundaries3dTest.assertBoundariesEquals(
				new Boundaries3d(new Coords3d(-5, -10, -15), new Coords3d(0, 5, 15)), testSubject.getBoundaries());
	}
	
	@Test
	public void testBoundariesWithNonZeroTwist() {
		Abstract3dModel testSubject = new LinearExtrude(new Test2dModel("(model)", 
				new Boundaries2d(new Boundary(-5, 0), new Boundary(-10, 5))), 30, Angle.ofDegree(20), 2);
		Boundaries3dTest.assertBoundariesEquals(
				new Boundaries3d(new Coords3d(-10, -10, -15), new Coords3d(10, 10, 15)), testSubject.getBoundaries());
	}
	
//	@Test
//	public void test() throws IOException {
//		Abstract2dModel union = new Union(Arrays.<Abstract2dModel>asList(
//				new Square(new Dims2d(10, 3)),
//				new Square(new Dims2d(3, 10)).move(Coords2d.yOnly(3.5)))
//			);
///*		Abstract2dModel union = new Union(Arrays.asList(
//				new Circle(5).move(Coords2d.xOnly(-5)),
//				new Circle(5), 
//				new Circle(5).move(Coords2d.xOnly(5))));*/
//		Abstract3dModel test = new LinearExtrude(union, 10, Angle.A45)
//				.addModelTo(Side.TOP_OUT, new Cube(10))
//				.addModelTo(Side.LEFT_OUT, new Cube(10))
//				;
//		
//		new SaveScadFiles(mavenTargetPath)
//			.addModel("test.scad", test)
//			.saveScadFiles();
//		
//		FacetGenerationContext context = new FacetGenerationContext(null, null, 0);
//		context.setFsAndFa(1, 12);
//		
//		long start = System.currentTimeMillis();
//		List<Facet> facets = test.toCSG(context).toFacets();
//		FileExporterFactory.createExporter(new File(mavenTargetPath, "test.stl"))
//			.writeToFile(facets);
//		
//		System.out.println(facets.size()+" in "+(System.currentTimeMillis()-start));
//	}
}
