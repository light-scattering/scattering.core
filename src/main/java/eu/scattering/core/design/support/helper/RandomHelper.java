package eu.scattering.core.design.support.helper;

import eu.scattering.core.design.Factory;
import eu.scattering.core.design.main.algebra.engine.base.point.FPoint;
import eu.scattering.core.design.main.algebra.engine.base.vector.FVector;
import eu.scattering.core.design.main.algebra.type.complex.FComplex;
import eu.scattering.core.design.main.algebra.type.quaternion.FQuaternion;

public interface RandomHelper {

    void configure(Factory factory, double range, double separation);

    double getTestValue(double... exclusion);
    FPoint getTestPoint(FPoint... exclusion);
    FVector getTestVector(FVector... exclusion);
    FComplex getTestComplex(FComplex... exclusion);
    FQuaternion getTestQuaternion(FQuaternion... exclusion);
}
