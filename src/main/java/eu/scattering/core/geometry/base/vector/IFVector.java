package eu.scattering.core.geometry.base.vector;

import eu.scattering.core.geometry.base.IBaseAlgebra;
import eu.scattering.core.ICoreObjectFeatures;
import eu.scattering.core.geometry.base.point.IFPoint;

public interface IFVector extends IFVectorAdvanced, IBaseAlgebra<IFVector>, ICoreObjectFeatures<IFVector> {

    IFVector set(IFVector fVector);

    IFVector set(IFPoint base, IFPoint head);
    IFVector setRef(IFPoint baseRef, IFPoint headRef);

    IFPoint getBase();
    IFVector setBase(IFPoint base);
    IFVector setBaseRef(IFPoint baseRef);

    IFPoint getHead();
    IFVector setHead(IFPoint head);
    IFVector setHeadRef(IFPoint headRef);
}
