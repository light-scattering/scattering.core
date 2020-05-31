package eu.scattering.core.geometry.d1.fvector;

import eu.scattering.core.geometry.IBaseAlgebra;
import eu.scattering.core.geometry.IBaseObject;
import eu.scattering.core.geometry.d0.IFPoint;

public interface IFVector extends IFVectorAdvanced, IBaseAlgebra<IFVector>, IBaseObject<IFVector> {

    IFVector replace(IFVector fVector);
    IFVector replace(IFPoint initial, IFPoint terminal);

    IFPoint getInitial();
    IFVector setInitial(IFPoint fPoint);

    IFPoint getTerminal();
    IFVector setTerminal(IFPoint fPoint);

    IFVector add(IFVector fVector);
    IFVector sub(IFVector fVector);
}
