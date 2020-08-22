package eu.scattering.core.design.main.algebra.type.quaternion;

public interface FQuaternionAdvanced {

    double getMagnitude();
    double setMagnitude(double magnitude);

    FQuaternion add(FQuaternion fQuaternion);
    FQuaternion add(double re, double i, double j, double k);
    FQuaternion add(double factor);
    FQuaternion addRe(double re);
    FQuaternion addIm(double i, double j, double k);

    FQuaternion sub(FQuaternion fQuaternion);
    FQuaternion sub(double re, double i, double j, double k);
    FQuaternion sub(double factor);
    FQuaternion subRe(double re);
    FQuaternion subIm(double i, double j, double k);

    FQuaternion mul(FQuaternion fQuaternion);
    FQuaternion mul(double re, double i, double j, double k);
    FQuaternion mul(double factor);
    FQuaternion mulRe(double re);
    FQuaternion mulIm(double i, double j, double k);

    FQuaternion divL(FQuaternion fQuaternion);
    FQuaternion devR(FQuaternion fQuaternion);
    FQuaternion div(double re, double i, double j, double k);
    FQuaternion div(double factor);
    FQuaternion divRe(double re);
    FQuaternion divIm(double i, double j, double k);

    FQuaternion pow(int n);
    FQuaternion[] root(int n);

    FQuaternion inverse();
    FQuaternion conjugate();
    FQuaternion normalize();

    FQuaternion imprint(FQuaternion fQuaternion);

    boolean isZero();
}
