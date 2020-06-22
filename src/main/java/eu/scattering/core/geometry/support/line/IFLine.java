package eu.scattering.core.geometry.support.line;

import eu.scattering.core.geometry.IGeometryAlgebra;
import eu.scattering.core.geometry.IGeometryBase;
import eu.scattering.core.geometry.IGeometryDebug;
import eu.scattering.core.geometry.IGeometryAssembly;
import eu.scattering.core.geometry.base.vector.IFVector;

import java.util.function.Consumer;
import java.util.function.Function;

public interface IFLine extends IGeometryBase<IFLine>, IGeometryDebug<IFLine>, IGeometryAlgebra<IFLine> {

    IFVector getOrigin();

    IFLine setOrigin(IFVector fVector);

    Consumer<IGeometryAssembly> project();

    Consumer<IGeometryAssembly> reflect();

//    Consumer<IGeometryAssembly> rotate();

    Function<IGeometryAssembly, Double> getDistance();

    Function<IGeometryAssembly, Boolean> belongsTo();

    Function<IGeometryAssembly, Boolean> isCloseTo();
}
