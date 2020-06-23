package eu.scattering.core.geometry.support.line;

import eu.scattering.core.geometry.IGeometryAlgebra;
import eu.scattering.core.geometry.IGeometryBase;
import eu.scattering.core.geometry.IGeometryDebug;
import eu.scattering.core.geometry.IGeometryAssembly;
import eu.scattering.core.geometry.base.vector.IFVector;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public interface IFLine extends IGeometryBase<IFLine>, IGeometryDebug<IFLine>, IGeometryAlgebra<IFLine> {

    IFVector getOrigin();

    IFLine setOrigin(IFVector fVector);

    Consumer<IGeometryAssembly> project();

    Consumer<IGeometryAssembly> reflect();

    Function<IGeometryAssembly, List<Double>> getDistance();

    Function<IGeometryAssembly, List<Boolean>> isCloseTo();
}
