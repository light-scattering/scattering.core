package eu.scattering.core.geometry.support.line;

import eu.scattering.core.exception.ProjectionException;
import eu.scattering.core.geometry.IGeometryBase;
import eu.scattering.core.debug.IDebug;
import eu.scattering.core.geometry.main.IGeometryAssembly;
import eu.scattering.core.geometry.support.IGeometrySupport;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public interface IFLine extends IGeometryBase<IFLine>, IDebug<IFLine>, IGeometrySupport<IFLine> {

    enum Mode {LINE, RAY, SEGMENT}

    Consumer<IGeometryAssembly> project(Mode mode) throws ProjectionException;

    Consumer<IGeometryAssembly> reflect(Mode mode) throws ProjectionException;

    Function<IGeometryAssembly, List<Boolean>> isCloseTo(Mode mode) throws ProjectionException;

    Function<IGeometryAssembly, List<Double>> getDistance(Mode mode) throws ProjectionException;

}
