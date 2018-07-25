package eu.printingin3d.javascad.tranzitions;

import eu.printingin3d.javascad.context.IColorGenerationContext;
import eu.printingin3d.javascad.context.IScadGenerationContext;
import eu.printingin3d.javascad.coords.Angles3d;
import eu.printingin3d.javascad.coords.Boundaries3d;
import eu.printingin3d.javascad.coords.Coords3d;
import eu.printingin3d.javascad.exceptions.IllegalValueException;
import eu.printingin3d.javascad.models.Abstract3dModel;
import eu.printingin3d.javascad.models.Complex3dModel;
import eu.printingin3d.javascad.models.SCAD;
import eu.printingin3d.javascad.tranform.TransformationFactory;
import eu.printingin3d.javascad.utils.AssertValue;
import eu.printingin3d.javascad.vrl.CSG;
import eu.printingin3d.javascad.vrl.FacetGenerationContext;

/**
 * This represents a rotate with an angle
 *
 * @author kenarab <ken4rab@gmail.com>
 */
public class RotateAxe extends Complex3dModel {
    private final Abstract3dModel model;
    private final double angle;
    private final Coords3d vector;

    /**
     * Creates a rotation operation.
     * 
     * @param model
     *            the model to be rotated
     * @param angle
     *            the angle used by the rotation operation
     * @param vector
     *            the axis of rotation used by the rotation operation
     * 
     * @throws IllegalValueException
     *             if either of the two parameters is null
     */
    public RotateAxe(Abstract3dModel model, double angle, Coords3d vector)
            throws IllegalValueException {
        AssertValue.isNotNull(model,
                "The model should not be null for a rotation!");
        AssertValue.isNotNull(angle,
                "The angle should not be null for a rotation!");
        AssertValue.isNotNull(vector,
                "The vector should not be null for a rotation!");

        this.model = model;
        this.angle = angle;
        this.vector = vector;
    }

    /**
     * This method is used internally by the {@link Abstract3dModel} - do not
     * use it!
     * 
     * @param angle
     *            the angle used by the rotate operation
     * @param vector
     *            the angle used by the rotate operation
     * @return the string which represents the rotation in OpenSCAD
     */
    public static String getRotate(double angle, Coords3d vector) {
        return "rotate(a=" + angle + ", v=" + vector + ")";
    }

    @Override
    protected SCAD innerToScad(IColorGenerationContext context) {
        return new SCAD(getRotate(angle, vector)).append(model.toScad(context));
    }

    @Override
    protected Boundaries3d getModelBoundaries() {
        // FIXME does not apply rotation
        // return model.getBoundaries().rotate(angles);
        return model.getBoundaries();
    }

    @Override
    protected Abstract3dModel innerCloneModel() {
        return new RotateAxe(model, angle, vector);
    }

    @Override
    protected CSG toInnerCSG(FacetGenerationContext context) {
        // FIXME does not apply rotation
        // return
        // model.toCSG(context).transformed(TransformationFactory.getRotationMatrix(angles));
        return model.toCSG(context);
    }

    @Override
    protected Abstract3dModel innerSubModel(IScadGenerationContext context) {
        Abstract3dModel subModel = model.subModel(context);
        return subModel == null ? null : new RotateAxe(subModel, angle, vector);
    }
}
