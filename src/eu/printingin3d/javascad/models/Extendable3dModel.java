package eu.printingin3d.javascad.models;

import eu.printingin3d.javascad.coords.Boundaries3d;

public abstract class Extendable3dModel extends Abstract3dModel {
	protected Abstract3dModel baseModel;

	@Override
	protected Abstract3dModel innerCloneModel() {
		return baseModel.innerCloneModel();
	}

	@Override
	protected String innerToScad() {
		return baseModel.toScad();
	}

	@Override
	protected Boundaries3d getModelBoundaries() {
		return baseModel.getModelBoundaries();
	}

}