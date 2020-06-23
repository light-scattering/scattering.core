package eu.scattering.core.geometry.support.line;

import eu.scattering.core.geometry.main.IGeometryAlgebra;
import eu.scattering.core.geometry.IGeometryBase;
import eu.scattering.core.geometry.IGeometryDebug;
import eu.scattering.core.geometry.main.IGeometryAssembly;
import eu.scattering.core.geometry.main.base.vector.IFVector;
import eu.scattering.core.geometry.support.IGeometrySupport;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public interface IFLine extends IGeometryBase<IFLine>, IGeometryDebug<IFLine>, IGeometrySupport<IFLine> {

    enum Mode {LINE, RAY, SEGMENT}

    Consumer<IGeometryAssembly> project(Mode mode);

    Consumer<IGeometryAssembly> reflect(Mode mode);

    Function<IGeometryAssembly, List<Double>> getDistance(Mode mode);

    Function<IGeometryAssembly, List<Boolean>> isCloseTo(Mode mode);

}
