package eu.printingin3d.javascad.coords;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import eu.printingin3d.javascad.exceptions.IllegalValueException;
import eu.printingin3d.javascad.utils.AssertValue;

/**
 * An implementation of a 3D triangle. It is used by the OpenSCAD rendering. 
 * This class is also used by the Polyhedron class as input.  
 *
 * @author ivivan <ivivan@printingin3d.eu>
 */
public class Triangle3d {
	private final Coords3d point1;
	private final Coords3d point2;
	private final Coords3d point3;

	/**
	 * Reads the given byte buffer and creates a triangle based on its content. It is the inverse 
	 * operation of {@link Triangle3d#toByteArray(ByteBuffer)}.
	 * @param bb the byte buffer the content will be read from
	 * @return a triangle created based on the content of the buffer
	 */
	public static Triangle3d fromByteArray(ByteBuffer bb) {
		return new Triangle3d(Coords3d.fromByteBuffer(bb), Coords3d.fromByteBuffer(bb), Coords3d.fromByteBuffer(bb));
	}
	
	/**
	 * Creates a list of triangles which covers the polygon defined by the given list of coordinates.
	 * The given list should contain at least three coordinates.
	 * @param coords the list of coordinates used to generate the triangles
	 * @return the triangles
	 * @throws IllegalValueException if the given coords list is null or the list contains less than three coordinates
	 */
	public static List<Triangle3d> getTriangles(List<Coords3d> coords) {
		AssertValue.isNotNull(coords, "coords should not be null");
		AssertValue.isTrue(coords.size() >= 3, 
				"There should be at least 3 verticies given, but was only "+coords.size());
		
    	List<Triangle3d> triangles = new ArrayList<>();
    	Coords3d firstVertex = coords.get(0);
        for (int i = 0; i < coords.size() - 2; i++) {
        	Triangle3d triangle = new Triangle3d(
        			firstVertex, 
        			coords.get(i + 1), 
        			coords.get(i + 2));
			triangles.add(triangle);
        }
        return triangles;
	}
	
	/**
	 * Creates a list of triangles which covers the polygon defined by the given list of coordinates.
	 * The given list should contain at least three coordinates.
	 * @param coords the list of coordinates used to generate the triangles
	 * @return the triangles
	 * @throws IllegalValueException if the given coords list is null or the list contains less than three coordinates
	 */
	public static List<Triangle3d> getTriangles(Coords3d... coords) {
		AssertValue.isNotNull(coords, "coords should not be null");
		
		return getTriangles(Arrays.asList(coords));
	}
	
	/**
	 * Created the triangle by defining the three corner.
	 * @param point1 the first corner
	 * @param point2 the second corner
	 * @param point3 the third corner
	 * @throws IllegalValueException if any of the parameters is null
	 */
	public Triangle3d(Coords3d point1, Coords3d point2, Coords3d point3) throws IllegalValueException {
		AssertValue.isNotNull(point1, "point1 should not be null");
		AssertValue.isNotNull(point2, "point2 should not be null");
		AssertValue.isNotNull(point3, "point3 should not be null");

		this.point1 = point1;
		this.point2 = point2;
		this.point3 = point3;
	}

	/**
	 * Returns with the corners of the triangle as a list. It always contains three points 
	 * in an order as it was given in the constructor.
	 * @return the list of the corners
	 */
	public List<Coords3d> getPoints() {
		return Arrays.asList(point1, point2, point3);
	}
	
	/**
	 * Renders the triangle as is used by OpenSCAD: three index of a list of points - 
	 * that's why this method requires a coordinate list as a parameter.
	 * @param coords the list of coordinates which is used to determine the indexes
	 * @return the OpenSCAD version of the triangle
	 * @throws IllegalValueException if any of the three corners of the triangle cannot be 
	 * found in the given list
	 */
	public String toTriangleString(List<Coords3d> coords) throws IllegalValueException {
		int p1 = coords.indexOf(point1);
		int p2 = coords.indexOf(point2);
		int p3 = coords.indexOf(point3);
		
		AssertValue.isNotNegative(p1, "point1 ("+point1+") cannot be found in the coordinate list!");
		AssertValue.isNotNegative(p2, "point2 ("+point2+") cannot be found in the coordinate list!");
		AssertValue.isNotNegative(p3, "point3 ("+point3+") cannot be found in the coordinate list!");
		
		return "[" + p1 + ',' + p2 + ',' + p3 + ']';
	}

	/**
	 * Writes the byte array representation of this triangle to the given buffer.
	 * @param byteBuffer the byte buffer the representation of this vertex will be written to
	 */
	public void toByteArray(ByteBuffer byteBuffer) {
		point1.toByteArray(byteBuffer);
		point2.toByteArray(byteBuffer);
		point3.toByteArray(byteBuffer);

	}

}
