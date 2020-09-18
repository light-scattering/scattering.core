package eu.scattering.core.design.main.algebra.type.quaternion;

import eu.scattering.core.design.main.algebra.type.Type;

public interface FQuaternion extends Type<FQuaternion> {

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

    boolean isExact(double re, double i, double j, double k);
    boolean isSimilar(double re, double i, double j, double k);

    FQuaternion add(double re, double i, double j, double k);
    FQuaternion addRe(double re);
    FQuaternion addIm(double i, double j, double k);
    FQuaternion addI(double i);
    FQuaternion addJ(double j);
    FQuaternion addK(double k);

    FQuaternion sub(double re, double i, double j, double k);
    FQuaternion subRe(double re);
    FQuaternion subIm(double i, double j, double k);
    FQuaternion subI(double i);
    FQuaternion subJ(double j);
    FQuaternion subK(double k);

    FQuaternion mul(double re, double i, double j, double k);
    FQuaternion mulRe(double re);
    FQuaternion mulIm(double i, double j, double k);
    FQuaternion mulI(double i);
    FQuaternion mulJ(double j);
    FQuaternion mulK(double k);

    FQuaternion div(double re, double i, double j, double k);
    FQuaternion divRe(double re);
    FQuaternion divIm(double i, double j, double k);
    FQuaternion divI(double i);
    FQuaternion divJ(double j);
    FQuaternion divK(double k);
}
