package eu.scattering.core.geometry.support.line.impl;

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

// https://math.stackexchange.com/questions/1905533/find-perpendicular-distance-from-point-to-line-in-3d.
public class FLine extends PresetGeometry<IFLine> implements IFLine {

    IFVector origin;

    private FLine() { }

    public static IFLine create(IFVector fVector) {
        return new FLine().setOrigin(fVector);
    }

    @Override
    public IFVector getOrigin() {
        return origin;
    }

    @Override
    public IFLine setOrigin(IFVector fVector) {

        if (fVector == null) {
            throw new NullPointerException("The reference IFVector cannot be null");
        }

        origin = fVector;

        return this;
    }

    @Override
    public Consumer<IGeometryAssembly> project() {

        return project(Mode.LINE);
    }

    @Override
    public Consumer<IGeometryAssembly> project(Mode mode) {

        return (e) -> e.disassemble()
                .forEach(this::projectIFPoint);
    }

    @Override
    public Consumer<IGeometryAssembly> reflect() {

        return reflect(Mode.LINE);
    }

    @Override
    public Consumer<IGeometryAssembly> reflect(Mode mode) {

        return (e) -> e.disassemble()
                .forEach(p -> p.reflect(projectIFPoint(p.copy())));
    }

    @Override
    public Function<IGeometryAssembly, List<Boolean>> isCloseTo() {

        return isCloseTo(Mode.LINE);
    }

    @Override
    public Function<IGeometryAssembly, List<Boolean>> isCloseTo(Mode mode) {

        return (e) -> e.disassemble().stream()
                .map(p -> p.getDistance(projectIFPoint(p.copy())) < jitter)
                .collect(Collectors.toList());
    }

    @Override
    public Function<IGeometryAssembly, List<Double>> getDistance() {

        return getDistance(Mode.LINE);
    }

    @Override
    public Function<IGeometryAssembly, List<Double>> getDistance(Mode mode) {

        return (e) -> e.disassemble().stream()
                .map(p -> p.getDistance(projectIFPoint(p.copy())))
                .collect(Collectors.toList());
    }

    @Override
    public IFLine copy() {

        return null;
    }

    @Override
    public IFLine self() {

        return this;
    }

    @Override
    public Object clone() {
        return null;
    }

    @Override
    public boolean equals(Object object) {

        if (object instanceof IFLine) {
            return isExact((IFLine) object);
        }

        return false;
    }

    private IFPoint projectIFPoint(IFPoint fPoint) {
        IFPoint opA = FactoryGeometry.getIFPoint(origin.getHead()).sub(origin.getBase()).div(origin.getRadius());
        IFPoint opB = FactoryGeometry.getIFPoint(fPoint).sub(origin.getBase());

        return fPoint.set(origin.getBase().copy().add(opA.mul(opB.dProd(opA))));
    }

}
