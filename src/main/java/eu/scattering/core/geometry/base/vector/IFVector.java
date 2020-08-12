package eu.scattering.core.geometry.base.vector;

import eu.scattering.core.geometry.base.IBase;
import eu.scattering.core.geometry.IGeometry;
import eu.scattering.core.debug.IDev;
import eu.scattering.core.geometry.base.point.IFPoint;

public interface IFVector extends IFVectorAdvanced,
        IGeometry<IFVector>, IDev<IFVector>, IBase<IFVector> {

    IFVector set(IFPoint base, IFPoint head);
    IFVector setRef(IFPoint baseRef, IFPoint headRef);

    IFPoint getBase();
    IFVector setBase(IFPoint base);
    IFVector setBaseRef(IFPoint baseRef);

    IFPoint getHead();
    IFVector setHead(IFPoint head);
    IFVector setHeadRef(IFPoint headRef);
}
