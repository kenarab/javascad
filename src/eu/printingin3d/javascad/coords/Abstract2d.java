package eu.printingin3d.javascad.coords;

import eu.printingin3d.javascad.enums.Language;
import eu.printingin3d.javascad.utils.DoubleUtils;

/**
 * The base class of all 3D related coordinate classes such as dimensions, angles or coordinates.
 *
 * @author ivivan <ivivan@printingin3d.eu>
 */
public class Abstract2d {
	protected final double x;
	protected final double y;
	
	protected Abstract2d(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Checks if the X coordinate is zero.
	 * @return return true if and only if the X coordinate is zero
	 */
	public final boolean isXZero() {
		return DoubleUtils.equalsEps(x, 0.0);
	}
	
	/**
	 * Checks if the Y coordinate is zero.
	 * @return return true if and only if the Y coordinate is zero
	 */
	public final boolean isYZero() {
		return DoubleUtils.equalsEps(y, 0.0);
	}
	
	/**
	 * Checks if all three coordinates are zero, which means it is equals to 
	 * isXZero() && isYZero() && isZZero().
	 * @return return true if and only if all three coordinates are zero
	 */
	public final boolean isZero() {
		return isXZero() && isYZero();
	}
	
	@Override
	public String toString() {
		return new StringBuilder().append(Language.getCurrent().getVectorStartChar()).
				append(DoubleUtils.formatDouble(x)).append(',').
				append(DoubleUtils.formatDouble(y)).append(Language.getCurrent().getVectorEndChar()).
				toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Abstract2d other = (Abstract2d) obj;
		
		return DoubleUtils.equalsEps(x, other.x) &&
				DoubleUtils.equalsEps(y, other.y);
	}
}