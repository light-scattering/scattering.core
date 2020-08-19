package eu.scattering.core.main.engine.base.point;

import eu.scattering.core.main.engine.base.IBase;
import eu.scattering.core.main.engine.IEngine;
import eu.scattering.core.dev.IDev;

public interface IFPoint extends IFPointAdvanced,
        IEngine<IFPoint>, IBase<IFPoint>, IDev<IFPoint>, Cloneable {

    IFPoint set(double x, double y, double z);

    double getX();
    IFPoint setX(double x);

    double getY();
    IFPoint setY(double y);

    double getZ();
    IFPoint setZ(double z);

    Object clone();
}
