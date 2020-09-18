package eu.scattering.core.design.support.helper;

import eu.scattering.core.design.main.algebra.engine.base.point.FPoint;
import eu.scattering.core.design.main.algebra.engine.base.vector.FVector;
import eu.scattering.core.design.main.algebra.type.complex.FComplex;
import eu.scattering.core.design.main.algebra.type.quaternion.FQuaternion;

public interface RandomHelper {

    void setSpacing(double spacing);
    void setRange(double range);

    double getDouble(double... exclusion);

    FPoint getFPoint(FPoint... exclusion);

    FVector getFVector(FVector... exclusion);

    FComplex getFComplex(FComplex... exclusion);

    FQuaternion getFQuaternion(FQuaternion... exclusion);
}
