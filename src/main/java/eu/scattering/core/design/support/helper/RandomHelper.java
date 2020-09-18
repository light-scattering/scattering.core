package eu.scattering.core.design.support.helper;

import eu.scattering.core.design.main.mutable.geometry.base.point.FPoint;
import eu.scattering.core.design.main.mutable.geometry.base.vector.FVector;
import eu.scattering.core.design.main.mutable.number.complex.FComplex;
import eu.scattering.core.design.main.mutable.number.quaternion.FQuaternion;

public interface RandomHelper {

    void setSpacing(double spacing);
    void setRange(double range);

    double getDouble(double... exclusion);

    FPoint getFPoint(FPoint... exclusion);

    FVector getFVector(FVector... exclusion);

    FComplex getFComplex(FComplex... exclusion);

    FQuaternion getFQuaternion(FQuaternion... exclusion);
}
