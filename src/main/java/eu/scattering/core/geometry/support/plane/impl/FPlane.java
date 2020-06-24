package eu.scattering.core.geometry.support.plane.impl;

import eu.scattering.core.geometry.main.IGeometryAssembly;
import eu.scattering.core.geometry.main.base.vector.IFVector;
import eu.scattering.core.geometry.support.PresetGeometry;
import eu.scattering.core.geometry.support.line.IFLine;
import eu.scattering.core.geometry.support.plane.IFPlane;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class FPlane extends PresetGeometry<IFPlane> implements IFPlane {

    @Override
    public Object clone() {
        return null;
    }

    @Override
    public boolean equals(Object object) {
        return false;
    }

    @Override
    public Function<IGeometryAssembly, List<Boolean>> isInHalfspace(IFLine.Mode mode) {
        return null;
    }

    @Override
    public IFPlane copy() {
        return null;
    }

    @Override
    public IFPlane self() {
        return null;
    }

    @Override
    public IFPlane setOriginRef(IFVector origin) {
        return null;
    }

    @Override
    public IFVector getOrigin() {
        return null;
    }

    @Override
    public Consumer<IGeometryAssembly> project() {
        return null;
    }

    @Override
    public Consumer<IGeometryAssembly> reflect() {
        return null;
    }

    @Override
    public Function<IGeometryAssembly, List<Boolean>> isCloseTo() {
        return null;
    }

    @Override
    public Function<IGeometryAssembly, List<Double>> getDistance() {
        return null;
    }
}
