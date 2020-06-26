package eu.scattering.core.geometry.support.plane.impl;

import eu.scattering.core.factory.FactoryGeometry;
import eu.scattering.core.geometry.main.IGeometryAssembly;
import eu.scattering.core.geometry.main.base.point.IFPoint;
import eu.scattering.core.geometry.main.base.vector.IFVector;
import eu.scattering.core.geometry.support.PresetGeometry;
import eu.scattering.core.geometry.support.plane.IFPlane;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static eu.scattering.core.Configuration.jitter;

public class FPlane extends PresetGeometry<IFPlane> implements IFPlane {

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    IFVector origin;

    private FPlane() { }

    public static IFPlane create() {

        return new FPlane().setOriginRef(FactoryGeometry.getIFVector());
    }

    @Override
    public IFVector getOrigin() {

        return origin;
    }

    @Override
    public IFPlane setOriginRef(IFVector fVector) {

        if (fVector == null) {
            throw new NullPointerException("The reference IFVector cannot be null");
        }

        origin = fVector;

        return this;
    }

    // -------------------------------------------------------------------------------------------------
    // The following fields do not have to modified while extending the class.
    // Their behaviour should be correct, however, it is not guaranteed that the current implementation is optimal.
    // -------------------------------------------------------------------------------------------------

    @Override
    public IFPlane copy() {

        return FactoryGeometry.getIFPlane(getOrigin().copy());
    }

    @Override
    public IFPlane self() {

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public boolean equals(Object object) {

        if (object instanceof IFPlane) {
            return isExact((IFPlane) object);
        }

        return false;
    }

    @Override
    public Object clone() {

        return copy();
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public Consumer<IGeometryAssembly> project() {

        return (e) -> e.disassemble()
                .forEach(this::projectOnPlane);
    }

    @Override
    public Consumer<IGeometryAssembly> reflect() {

        return (e) -> e.disassemble()
                .forEach(p -> p.reflect(projectOnPlane(p.copy())));
    }

    @Override
    public Function<IGeometryAssembly, List<Boolean>> isCloseTo() {

        return (e) -> e.disassemble().stream()
                .map(p -> p.getDistance(projectOnPlane(p.copy())) < jitter)
                .collect(Collectors.toList());
    }

    @Override
    public Function<IGeometryAssembly, List<Double>> getDistance() {

        return (e) -> e.disassemble().stream()
                .map(p -> p.getDistance(projectOnPlane(p.copy())))
                .collect(Collectors.toList());
    }

    @Override
    public Function<IGeometryAssembly, List<Boolean>> isInHalfSpace() {

        return (e) -> e.disassemble().stream()
                .map(p -> isInHalfSpace(projectOnLine(p.copy())))
                .collect(Collectors.toList());
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public boolean isCutting(IGeometryAssembly assembly) {

        List<Boolean> isInHalfSpace = assembly.disassemble().stream()
                .map(p -> isInHalfSpace(projectOnLine(p.copy())))
                .collect(Collectors.toList());

        boolean conditionTrue = isInHalfSpace.stream().anyMatch(e -> e);
        boolean conditionFalse = isInHalfSpace.stream().anyMatch(e -> !e);

        return conditionTrue && conditionFalse;
    }

    // -------------------------------------------------------------------------------------------------

    private IFPoint projectOnPlane(IFPoint fPoint) {
        IFPoint opA = FactoryGeometry.getIFPoint(getOrigin().getHead())
                .sub(getOrigin().getBase())
                .div(getOrigin().getRadius());

        IFPoint opB = FactoryGeometry.getIFPoint(fPoint)
                .sub(getOrigin().getBase());

        IFPoint opC = FactoryGeometry.getIFPoint()
                .set(getOrigin().getBase().copy().add(opA.mul(opB.dProd(opA))));

        IFVector translation = FactoryGeometry.getIFVector(opC, fPoint.copy())
                .relocateBase(getOrigin().getBase());

        return fPoint.set(translation.getHead());
    }

    private IFPoint projectOnLine(IFPoint fPoint) {
        IFPoint opA = FactoryGeometry.getIFPoint(getOrigin().getHead())
                .sub(getOrigin().getBase())
                .div(getOrigin().getRadius());

        IFPoint opB = FactoryGeometry.getIFPoint(fPoint)
                .sub(getOrigin().getBase());

        return fPoint.set(origin.getBase().copy().add(opA.mul(opB.dProd(opA))));
    }

    private boolean isInHalfSpace(IFPoint projection) {
        double magnitude = getOrigin().getRadius();

        double distanceBase = getOrigin().getBase().getDistance(projection);
        double distanceHead = getOrigin().getHead().getDistance(projection);

        if ((distanceBase < magnitude + jitter) && (distanceHead < magnitude + jitter)) {
            return true;
        }

        return distanceHead < distanceBase + jitter;
    }
}
