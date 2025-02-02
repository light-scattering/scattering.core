package eu.scattering.core.design.mutables.number.complex;

import eu.scattering.core.design.annotations.Extension;
import eu.scattering.core.design.annotations.Fragment;
import eu.scattering.core.design.annotations.Termination;
import eu.scattering.core.design.mutables.number.Number;
import eu.scattering.core.transfer.containers.position.FPos2D.FPos2D;

import java.util.function.Consumer;
import java.util.function.Function;

public interface FComplex extends Number<FComplex> {

    FComplex set(double re, double im);

    double getRe();
    FComplex setRe(double re);

    double getIm();
    FComplex setIm(double im);

    //--------------------------------------------------

    FComplex applyStateFrom(FPos2D position);

    FPos2D toFPos2D();

    //--------------------------------------------------

    boolean isExact(double re, double im);
    boolean isExact(FPos2D arg);

    boolean isSimilar(double re, double im);
    boolean isSimilar(FPos2D arg);

    double getPhase();
    FComplex setPhase(double phase);

    FComplex add(double re, double im);
    FComplex add(FPos2D arg);
    FComplex addRe(double re);
    FComplex addIm(double im);

    FComplex sub(double re, double im);
    FComplex sub(FPos2D arg);
    FComplex subRe(double re);
    FComplex subIm(double re);

    FComplex mul(double re, double im);
    FComplex mulRe(double re);
    FComplex mulIm(double im);

    FComplex div(double re, double im);
    FComplex divRe(double re);
    FComplex divIm(double im);

    double getDistance(double re, double im);
    double getDistance(FPos2D arg);

    FComplex setDistance(double re, double im, double distance);
    FComplex setDistance(FPos2D arg, double distance);

    //--------------------------------------------------

    @Fragment
    double getMagnitudeP2();
    @Fragment
    double getDistanceP2(double re, double im);
    @Fragment
    double getDistanceP2(FComplex arg);
    @Fragment
    double getDistanceP2(FPos2D arg);

    @Extension
    FComplex apply(Consumer<FComplex> action);

    @Termination
    double toDouble(Function<FComplex, Double> action);
    @Termination
    boolean toBoolean(Function<FComplex, Boolean> action);
}
