package eu.scattering.core.geometry.support.line.impl;

import eu.scattering.core.exception.ProjectionException;
import eu.scattering.core.factory.FactoryGeometry;
import eu.scattering.core.geometry.main.IGeometryAssembly;
import eu.scattering.core.geometry.main.base.point.IFPoint;
import eu.scattering.core.geometry.main.base.vector.IFVector;
import eu.scattering.core.geometry.support.PresetGeometry;
import eu.scattering.core.geometry.support.line.IFLine;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static eu.scattering.core.Configuration.jitter;

public class FLine extends PresetGeometry<IFLine> implements IFLine {

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    IFVector origin;

    private FLine() { }

    public static IFLine create() {

        return new FLine().setOriginRef(FactoryGeometry.getIFVector());
    }

    @Override
    public IFVector getOrigin() {

        return origin;
    }

    @Override
    public IFLine setOriginRef(IFVector fVector) {

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
    public IFLine copy() {

        return FactoryGeometry.getIFLine(origin.copy());
    }

    @Override
    public IFLine self() {

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public boolean equals(Object object) {

        if (object instanceof IFLine) {
            return isExact((IFLine) object);
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

        return project(Mode.LINE);
    }

    @Override
    public Consumer<IGeometryAssembly> project(Mode mode) {

        return (e) -> e.disassemble()
                .forEach(p -> projectIFPoint(p, mode));
    }

    @Override
    public Consumer<IGeometryAssembly> reflect() {

        return reflect(Mode.LINE);
    }

    @Override
    public Consumer<IGeometryAssembly> reflect(Mode mode) {

        return (e) -> e.disassemble()
                .forEach(p -> p.reflect(projectIFPoint(p.copy(), mode)));
    }

    @Override
    public Function<IGeometryAssembly, List<Boolean>> isCloseTo() {

        return isCloseTo(Mode.LINE);
    }

    @Override
    public Function<IGeometryAssembly, List<Boolean>> isCloseTo(Mode mode) {

        return (e) -> e.disassemble().stream()
                .map(p -> p.getDistance(projectIFPoint(p.copy(), mode)) < jitter)
                .collect(Collectors.toList());
    }

    @Override
    public Function<IGeometryAssembly, List<Double>> getDistance() {

        return getDistance(Mode.LINE);
    }

    @Override
    public Function<IGeometryAssembly, List<Double>> getDistance(Mode mode) {

        return (e) -> e.disassemble().stream()
                .map(p -> p.getDistance(projectIFPoint(p.copy(), mode)))
                .collect(Collectors.toList());
    }

    // -------------------------------------------------------------------------------------------------

    private IFPoint projectIFPoint(IFPoint fPoint, Mode mode) {
        IFPoint opA = FactoryGeometry.getIFPoint(origin.getHead()).sub(origin.getBase()).div(origin.getRadius());
        IFPoint opB = FactoryGeometry.getIFPoint(fPoint).sub(origin.getBase());

        fPoint.set(origin.getBase().copy().add(opA.mul(opB.dProd(opA))));

        switch (mode) {
            case LINE:
                return fPoint;
            case RAY:
                return validateProjectionOnRay(fPoint);
            case SEGMENT:
                return validateProjectionOnSegment(fPoint);
        }

        throw new IllegalArgumentException("The mode is not defined correctly");
    }

    private IFPoint validateProjectionOnRay(IFPoint projection) {
        double magnitude = getOrigin().getRadius();

        double distanceBase = getOrigin().getBase().getDistance(projection);
        double distanceHead = getOrigin().getHead().getDistance(projection);

        if ((distanceBase < magnitude + jitter) && (distanceHead < magnitude + jitter)) {
            return projection;
        }

        if (distanceHead < distanceBase + jitter) {
            return projection;
        }

        throw new ProjectionException("The IFPoint cannot be projected on the ray");
    }

    private IFPoint validateProjectionOnSegment(IFPoint projection) {
        double magnitude = getOrigin().getRadius();

        double distanceBase = getOrigin().getBase().getDistance(projection);
        double distanceHead = getOrigin().getHead().getDistance(projection);

        if ((distanceBase < magnitude + jitter) && (distanceHead < magnitude + jitter)) {
            return projection;
        }

        throw new ProjectionException("The IFPoint cannot be projected on the segment");
    }

}

// https://math.stackexchange.com/questions/1905533/find-perpendicular-distance-from-point-to-line-in-3d.