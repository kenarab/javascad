package eu.printingin3d.javascad.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import eu.printingin3d.javascad.context.IScadGenerationContext;
import eu.printingin3d.javascad.coords.Boundaries3d;
import eu.printingin3d.javascad.coords.Coords3d;
import eu.printingin3d.javascad.coords.Triangle3d;
import eu.printingin3d.javascad.exceptions.IllegalValueException;
import eu.printingin3d.javascad.exceptions.NotImplementedException;
import eu.printingin3d.javascad.utils.AssertValue;
import eu.printingin3d.javascad.vrl.CSG;
import eu.printingin3d.javascad.vrl.FacetGenerationContext;

/**
 * <p>
 * Represents a set of triangles. It is good to know that some operations - such
 * as difference or intersection - are not always works perfectly with this
 * object.
 * </p>
 * 
 * @author Rob van der Veer
 */
public class Polyhedron extends Atomic3dModel {

	private final List<Triangle3d> triangles;

	/**
	 * Constructs the object with the given triangles.
	 * 
	 * @param triangles
	 *            the triangles used to create this object
	 * @throws IllegalValueException
	 *             thrown if the given list is empty
	 */
	public Polyhedron(List<Triangle3d> triangles) throws IllegalValueException {
		AssertValue.isNotEmpty(triangles,
				"The triangle list should not be empty!");

		this.triangles = triangles;
	}

	@Override
	protected Boundaries3d getModelBoundaries() {
		double minX = Double.MAX_VALUE;
		double minY = Double.MAX_VALUE;
		double minZ = Double.MAX_VALUE;
		double maxX = Double.MIN_VALUE;
		double maxY = Double.MIN_VALUE;
		double maxZ = Double.MIN_VALUE;

		for (final Coords3d p : getPoints()) {
			minX = Math.min(p.getX(), minX);
			minY = Math.min(p.getY(), minY);
			minZ = Math.min(p.getZ(), minZ);
			maxX = Math.max(p.getX(), maxX);
			maxY = Math.max(p.getY(), maxY);
			maxZ = Math.max(p.getZ(), maxZ);
		}
		Coords3d minCorner = new Coords3d(minX, minY, minZ);
		Coords3d maxCorner = new Coords3d(maxX, maxY, maxZ);
		return new Boundaries3d(minCorner, maxCorner);
	}

	private List<Coords3d> getPoints() {
		Set<Coords3d> result = new HashSet<>();
		for (Triangle3d triangle : triangles) {
			result.addAll(triangle.getPoints());
		}
		return new ArrayList<>(result);
	}

	@Override
	protected Abstract3dModel innerCloneModel() {
		return new Polyhedron(new ArrayList<>(triangles));
	}

	@Override
	protected SCAD innerToScad(IScadGenerationContext context) {
		List<Coords3d> points = getPoints();

		SCAD b = new SCAD("polyhedron(");
		b = addPoints(b, points)
				.append(",");
		b = addTriangles(b, points)
				.append("\n);");
		return b;
	}

	private SCAD addPoints(SCAD b, List<Coords3d> points) {
		b = b.append("\n  points=[");
		boolean first = true;
		for (Coords3d c : points) {
			if (first) {
				first = false;
			} else {
				b = b.append(", ");
			}
			b = b.append(c.toString());
		}
		return b.append("]");
	}

	private SCAD addTriangles(SCAD b, List<Coords3d> points) {
		b = b.append("\n  triangles=[");
		boolean first = true;
		for (Triangle3d c : triangles) {
			if (first) {
				first = false;
			} else {
				b = b.append(", ");
			}
			b = b.append(c.toTriangleString(points));
		}
		return b.append("]");
	}

	@Override
	protected CSG toInnerCSG(FacetGenerationContext context) {
		throw new NotImplementedException();
	}
}