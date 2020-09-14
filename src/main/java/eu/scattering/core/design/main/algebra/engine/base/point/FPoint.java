package eu.scattering.core.design.main.algebra.engine.base.point;

import eu.scattering.core.design.main.algebra.engine.base.Base;
import eu.scattering.core.design.main.algebra.Algebra;
import eu.scattering.core.design.development.Development;

public interface FPoint extends FPointAdvanced,
        Algebra<FPoint>, Base<FPoint>, Development<FPoint> {

    FPoint set(double x, double y, double z);

    double getX();
    FPoint setX(double x);

    double getY();
    FPoint setY(double y);

    double getZ();
    FPoint setZ(double z);
}
