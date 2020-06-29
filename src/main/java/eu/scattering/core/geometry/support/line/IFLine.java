package eu.scattering.core.geometry.support.line;

import eu.scattering.core.exception.ProjectionException;
import eu.scattering.core.geometry.IGeometryBase;
import eu.scattering.core.debug.IDebug;
import eu.scattering.core.geometry.main.IBaseExtensionAssembly;
import eu.scattering.core.geometry.main.base.point.IFPoint;
import eu.scattering.core.geometry.support.ISupport;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public interface IFLine extends IGeometryBase<IFLine>, IDebug<IFLine>, ISupport<IFLine> {

    enum Mode {LINE, RAY, SEGMENT}

    Consumer<IBaseExtensionAssembly> project(Mode mode) throws ProjectionException;

    Consumer<IBaseExtensionAssembly> reflect(Mode mode) throws ProjectionException;

    Function<IBaseExtensionAssembly, List<Boolean>> isPartOf(Mode mode) throws ProjectionException;

    Function<IBaseExtensionAssembly, List<Double>> getDistance(Mode mode) throws ProjectionException;

    Consumer<IBaseExtensionAssembly> setDistance(double distance, Mode mode)
            throws ProjectionException, IllegalArgumentException;

    Consumer<IBaseExtensionAssembly> translate(double distance);

    IFPoint getCuttingIFPoint();

}
