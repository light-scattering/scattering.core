package eu.scattering.core.design.mutables.number.complex;

import eu.scattering.core.design.annotations.Extension;
import eu.scattering.core.design.annotations.Facade;
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

    FComplex set(FPos2D position);

    FPos2D toFPos2D();

    //--------------------------------------------------

    boolean isExact(double re, double im);
    boolean isSimilar(double re, double im);

    double getPhase();
    FComplex setPhase(double phase);

    FComplex add(double re, double im);
    FComplex addRe(double re);
    FComplex addIm(double im);

    FComplex sub(double re, double im);
    FComplex subRe(double re);
    FComplex subIm(double re);

    FComplex mul(double re, double im);
    FComplex mulRe(double re);
    FComplex mulIm(double im);

    FComplex div(double re, double im);
    FComplex divRe(double re);
    FComplex divIm(double im);

    //--------------------------------------------------

    @Extension
    FComplex apply(Consumer<FComplex> action);

    @Facade
    FComplex applyWithFixedState(Consumer<FComplex> action);

    @Termination
    double toDouble(Function<FComplex, Double> action);
    @Termination
    boolean toBoolean(Function<FComplex, Boolean> action);

    @Facade
    double toDoubleWithFixedState(Function<FComplex, Double> action);
    @Facade
    boolean toBooleanWithFixedState(Function<FComplex, Boolean> action);
}
