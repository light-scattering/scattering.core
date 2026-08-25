package eu.scattering.core.design.component.geometry.shape.sphere;

import eu.scattering.core.design.component.geometry.shape.ShapeCommon;

public interface FSphere extends ShapeCommon<FSphere> {

    @Override
    FSphere setEpsilon(double epsilon);
    @Override
    FSphere setDelta(double delta);
    @Override
    FSphere setIndex(double index);
    @Override
    FSphere setMeta(String... meta);
}
