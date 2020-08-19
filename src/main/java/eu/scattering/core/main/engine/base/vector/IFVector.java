package eu.scattering.core.main.engine.base.vector;

import eu.scattering.core.main.engine.base.IBase;
import eu.scattering.core.main.engine.IEngine;
import eu.scattering.core.dev.IDev;
import eu.scattering.core.main.engine.base.point.IFPoint;

public interface IFVector extends IFVectorAdvanced,
        IEngine<IFVector>, IBase<IFVector>, IDev<IFVector>, Cloneable {

    IFVector set(IFPoint base, IFPoint head);
    IFVector setRef(IFPoint baseRef, IFPoint headRef);

    IFPoint getBase();
    IFVector setBase(IFPoint base);
    IFVector setBaseRef(IFPoint baseRef);

    IFPoint getHead();
    IFVector setHead(IFPoint head);
    IFVector setHeadRef(IFPoint headRef);

    Object clone();
}
