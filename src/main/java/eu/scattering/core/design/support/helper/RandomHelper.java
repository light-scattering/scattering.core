package eu.scattering.core.design.support.helper;

import eu.scattering.core.design.main.algebra.engine.base.point.FPoint;
import eu.scattering.core.design.main.algebra.engine.base.vector.FVector;
import eu.scattering.core.design.main.algebra.type.complex.FComplex;
import eu.scattering.core.design.main.algebra.type.quaternion.FQuaternion;

public interface RandomHelper {

    double getTestValue(double... exclude);
    FPoint getTestPoint(FPoint... exclude);
    FVector getTestVector(FVector... exclude);
    FComplex getTestComplex(FComplex... exclude);
    FQuaternion getTestQuaternion(FQuaternion... exclude);
}
