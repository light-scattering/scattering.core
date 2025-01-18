package eu.scattering.core.design.mutables.number.quaternion;

import eu.scattering.core.design.annotations.Extension;
import eu.scattering.core.design.annotations.Facade;
import eu.scattering.core.design.annotations.Termination;
import eu.scattering.core.design.mutables.number.Number;
import eu.scattering.core.transfer.containers.position.FPos4D.FPos4D;

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

    FQuaternion set(FPos4D position);

    FPos4D toFPos4D();

    //--------------------------------------------------

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
    FQuaternion mulI(double i);
    FQuaternion mulJ(double j);
    FQuaternion mulK(double k);

    FQuaternion div(double re, double i, double j, double k);
    FQuaternion divRe(double re);
    FQuaternion divI(double i);
    FQuaternion divJ(double j);
    FQuaternion divK(double k);

    //--------------------------------------------------

    @Extension
    FQuaternion apply(Consumer<FQuaternion> action);

    @Facade
    FQuaternion applyWithFixedState(Consumer<FQuaternion> action);

    @Termination
    double toDouble(Function<FQuaternion, Double> action);
    @Termination
    boolean toBoolean(Function<FQuaternion, Boolean> action);

    @Facade
    double toDoubleWithFixedState(Function<FQuaternion, Double> action);
    @Facade
    boolean toBooleanWithFixedState(Function<FQuaternion, Boolean> action);
}
