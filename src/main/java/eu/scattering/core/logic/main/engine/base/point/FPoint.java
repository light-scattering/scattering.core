package eu.scattering.core.logic.main.engine.base.point;

import eu.scattering.core.logic.main.engine.base.Base;
import eu.scattering.core.logic.main.engine.Engine;
import eu.scattering.core.logic.dev.Dev;

public interface FPoint extends FPointAdvanced,
        Engine<FPoint>, Base<FPoint>, Dev<FPoint>, Cloneable {

    FPoint set(double x, double y, double z);

    double getX();
    FPoint setX(double x);

    double getY();
    FPoint setY(double y);

    double getZ();
    FPoint setZ(double z);

    Object clone();
}
