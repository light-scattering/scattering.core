package eu.scattering.core.geometry.base.vector;

import eu.scattering.core.geometry.IGeometryAlgebra;
import eu.scattering.core.geometry.IGeometryBase;
import eu.scattering.core.geometry.base.point.IFPoint;

public interface IFVector extends IFVectorAdvanced, IGeometryAlgebra<IFVector>, IGeometryBase<IFVector> {

    IFVector set(IFPoint base, IFPoint head);
    IFVector setRef(IFPoint baseRef, IFPoint headRef);

    IFPoint getBase();
    IFVector setBase(IFPoint base);
    IFVector setBaseRef(IFPoint baseRef);

    IFPoint getHead();
    IFVector setHead(IFPoint head);
    IFVector setHeadRef(IFPoint headRef);
}
