package eu.scattering.core.design.support.helper;

import eu.scattering.core.design.core.mutable.geometry.simple.point.FPoint;
import eu.scattering.core.design.core.mutable.geometry.simple.vector.FVector;
import eu.scattering.core.design.core.mutable.number.complex.FComplex;
import eu.scattering.core.design.core.mutable.number.quaternion.FQuaternion;

public interface RandomHelper {

    void setSpacing(double spacing);
    void setRange(double range);

    double getDouble(double... exclusion);

    FPoint getFPoint(FPoint... exclusion);

    FVector getFVector(FVector... exclusion);

    FComplex getFComplex(FComplex... exclusion);

    FQuaternion getFQuaternion(FQuaternion... exclusion);
}
