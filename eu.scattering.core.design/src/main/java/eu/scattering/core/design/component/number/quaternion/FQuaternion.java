package eu.scattering.core.design.component.number.quaternion;

import eu.scattering.core.design.util.annotation.Extension;
import eu.scattering.core.design.util.annotation.Fragment;
import eu.scattering.core.design.util.annotation.Terminator;
import eu.scattering.core.design.component.number.Number;
import eu.scattering.core.transfer.container.storage.FPos4D.FPos4D;

import java.util.function.Consumer;
import java.util.function.Function;

public interface FQuaternion extends Number<FQuaternion> {

    FQuaternion set(double re, double i, double j, double k);

    double getRe();
    FQuaternion setRe(double re);

    double getI();
    FQuaternion setI(double i);
    double getJ();
    FQuaternion setJ(double j);
    double getK();
    FQuaternion setK(double k);

    //--------------------------------------------------

    FQuaternion applyStateFrom(FPos4D position);

    FPos4D toFPos4D();

    //--------------------------------------------------

    boolean isExact(double re, double i, double j, double k);
    boolean isExact(FPos4D arg);

    boolean isSimilar(double re, double i, double j, double k);
    boolean isSimilar(FPos4D arg);

    FQuaternion add(double re, double i, double j, double k);
    FQuaternion add(FPos4D arg);
    FQuaternion addRe(double re);
    FQuaternion addIm(double i, double j, double k);
    FQuaternion addI(double i);
    FQuaternion addJ(double j);
    FQuaternion addK(double k);

    FQuaternion sub(double re, double i, double j, double k);
    FQuaternion sub(FPos4D arg);
    FQuaternion subRe(double re);
    FQuaternion subIm(double i, double j, double k);
    FQuaternion subI(double i);
    FQuaternion subJ(double j);
    FQuaternion subK(double k);

    FQuaternion mul(double re, double i, double j, double k);
    FQuaternion mulRe(double re);
    FQuaternion mulI(double i);
    FQuaternion mulJ(double j);
    FQuaternion mulK(double k);

    FQuaternion div(double re, double i, double j, double k);
    FQuaternion divRe(double re);
    FQuaternion divI(double i);
    FQuaternion divJ(double j);
    FQuaternion divK(double k);

    double getDistance(double re, double i, double j, double k);
    double getDistance(FPos4D arg);

    FQuaternion setDistance(double re, double i, double j, double k, double distance);
    FQuaternion setDistance(FPos4D arg, double distance);

    //--------------------------------------------------

    @Fragment
    double getMagnitudeP2();
    @Fragment
    double getDistanceP2(double re, double i, double j, double k);
    @Fragment
    double getDistanceP2(FQuaternion arg);
    @Fragment
    double getDistanceP2(FPos4D arg);

    @Extension
    FQuaternion apply(Consumer<FQuaternion> action);

    @Terminator
    double toDouble(Function<FQuaternion, Double> action);
    @Terminator
    boolean toBoolean(Function<FQuaternion, Boolean> action);
}
