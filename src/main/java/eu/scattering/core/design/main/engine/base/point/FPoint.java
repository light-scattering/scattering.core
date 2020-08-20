package eu.scattering.core.design.main.engine.base.point;

import eu.scattering.core.design.main.engine.base.Base;
import eu.scattering.core.design.main.engine.Engine;
import eu.scattering.core.design.development.Development;

public interface FPoint extends FPointAdvanced,
        Engine<FPoint>, Base<FPoint>, Development<FPoint>, Cloneable {

    FPoint set(double x, double y, double z);

    double getX();
    FPoint setX(double x);

    double getY();
    FPoint setY(double y);

    double getZ();
    FPoint setZ(double z);

    Object clone();
}
