package eu.scattering.core.logic.main.engine.base.vector;

import eu.scattering.core.logic.main.engine.base.Base;
import eu.scattering.core.logic.main.engine.Engine;
import eu.scattering.core.logic.dev.Dev;
import eu.scattering.core.logic.main.engine.base.point.FPoint;

public interface FVector extends FVectorAdvanced,
        Engine<FVector>, Base<FVector>, Dev<FVector>, Cloneable {

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
