package eu.scattering.core.geometry.support.line;

import eu.scattering.core.exception.ProjectionException;
import eu.scattering.core.geometry.IGeometryBase;
import eu.scattering.core.debug.IDebug;
import eu.scattering.core.geometry.main.IBaseExtensionAssembly;
import eu.scattering.core.geometry.support.ISupport;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public interface IFLine extends IGeometryBase<IFLine>, IDebug<IFLine>, ISupport<IFLine> {

    enum Mode {LINE, RAY, SEGMENT}

    Consumer<IBaseExtensionAssembly> project(Mode mode) throws ProjectionException;

    Consumer<IBaseExtensionAssembly> reflect(Mode mode) throws ProjectionException;

    Function<IBaseExtensionAssembly, List<Boolean>> isCloseTo(Mode mode) throws ProjectionException;

    Function<IBaseExtensionAssembly, List<Double>> getDistance(Mode mode) throws ProjectionException;

}
