package eu.scattering.core.design.helpers.random;

import eu.scattering.core.design.elements.algebra.geometry.primitive.point.FPoint;
import eu.scattering.core.design.elements.algebra.geometry.primitive.vector.FVector;
import eu.scattering.core.design.elements.algebra.number.complex.FComplex;
import eu.scattering.core.design.elements.algebra.number.quaternion.FQuaternion;

public interface FRandomHelper {

    void setSpacing(double spacing);
    void setRange(double range);

    double getDouble(double... exclusion);

    FPoint getFPoint(FPoint... exclusion);

    FVector getFVector(FVector... exclusion);

    FComplex getFComplex(FComplex... exclusion);

    FQuaternion getFQuaternion(FQuaternion... exclusion);
}
