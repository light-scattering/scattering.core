package eu.scattering.core.design.main.algebra.engine.base.vector;

import eu.scattering.core.design.main.algebra.engine.base.Base;
import eu.scattering.core.design.main.algebra.Algebra;
import eu.scattering.core.design.development.Development;
import eu.scattering.core.design.main.algebra.engine.base.point.FPoint;

public interface FVector extends FVectorAdvanced,
        Algebra<FVector>, Base<FVector>, Development<FVector>, Cloneable {

    FVector set(FPoint base, FPoint head);
    FVector setRef(FPoint baseRef, FPoint headRef);

    FPoint getBase();
    FVector setBase(FPoint base);
    FVector setBaseRef(FPoint baseRef);

    FPoint getHead();
    FVector setHead(FPoint head);
    FVector setHeadRef(FPoint headRef);

    Object clone();
}
