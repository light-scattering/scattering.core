package eu.scattering.core.geometry.main.base.vector;

import eu.scattering.core.geometry.main.IGeometryAlgebra;
import eu.scattering.core.geometry.IGeometryBase;
import eu.scattering.core.debug.IDebug;
import eu.scattering.core.geometry.main.base.point.IFPoint;

public interface IFVector extends IFVectorAdvanced,
        IGeometryBase<IFVector>, IDebug<IFVector>, IGeometryAlgebra<IFVector> {

    IFVector set(IFPoint base, IFPoint head);
    IFVector setRef(IFPoint baseRef, IFPoint headRef);

    IFPoint getBase();
    IFVector setBase(IFPoint base);
    IFVector setBaseRef(IFPoint baseRef);

    IFPoint getHead();
    IFVector setHead(IFPoint head);
    IFVector setHeadRef(IFPoint headRef);
}
