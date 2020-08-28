package eu.scattering.core.design.main.algebra.type.quaternion;

import eu.scattering.core.design.development.Development;
import eu.scattering.core.design.main.algebra.Algebra;
import eu.scattering.core.design.main.algebra.type.Type;

public interface FQuaternion extends FQuaternionAdvanced,
        Type<FQuaternion>, Cloneable {

    FQuaternion set(FQuaternion fQuaternion);
    FQuaternion set(double re, double i, double j, double k);

    double getRe();
    FQuaternion setRe(double re);

    double getI();
    FQuaternion setI(double i);
    double getJ();
    FQuaternion setJ(double j);
    double getK();
    FQuaternion setK(double k);

    Object clone();
}
