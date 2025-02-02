package eu.scattering.core.impl.engines.prot;

import eu.scattering.core.design.engines.prot.FProtEngine;
import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;
import eu.scattering.core.design.mutables.geometry.primitive.vector.FVector;
import eu.scattering.core.design.mutables.number.complex.FComplex;
import eu.scattering.core.design.mutables.number.quaternion.FQuaternion;

import java.util.function.Consumer;
import java.util.function.Function;

public class FProtEngineDef implements FProtEngine {

    private FProtEngineDef() {}

    public static FProtEngineDef create() {

        return new FProtEngineDef();
    }

    //--------------------------------------------------

    @Override
    public FComplex applyWithFixedState(FComplex in, Consumer<FComplex> action) {
        double memoRe = in.getRe();
        double memoIm = in.getIm();

        action.accept(in);

        return in.set(memoRe, memoIm);
    }

    @Override
    public double toDoubleWithFixedState(FComplex in, Function<FComplex, Double> action) {
        double memoRe = in.getRe();
        double memoIm = in.getIm();

        double results = action.apply(in);

        in.set(memoRe, memoIm);

        return results;
    }

    @Override
    public boolean toBooleanWithFixedState(FComplex in, Function<FComplex, Boolean> action) {
        double memoRe = in.getRe();
        double memoIm = in.getIm();

        boolean results = action.apply(in);

        in.set(memoRe, memoIm);

        return results;
    }

    //--------------------------------------------------

    @Override
    public FQuaternion applyWithFixedState(FQuaternion in, Consumer<FQuaternion> action) {
        double memoRe = in.getRe();
        double memoI = in.getI();
        double memoJ = in.getJ();
        double memoK = in.getK();

        action.accept(in);

        return in.applyStateFrom(memoRe, memoI, memoJ, memoK);
    }

    @Override
    public double toDoubleWithFixedState(FQuaternion in, Function<FQuaternion, Double> action) {
        double memoRe = in.getRe();
        double memoI = in.getI();
        double memoJ = in.getJ();
        double memoK = in.getK();

        double results = action.apply(in);

        in.applyStateFrom(memoRe, memoI, memoJ, memoK);

        return results;
    }

    @Override
    public boolean toBooleanWithFixedState(FQuaternion in, Function<FQuaternion, Boolean> action) {
        double memoRe = in.getRe();
        double memoI = in.getI();
        double memoJ = in.getJ();
        double memoK = in.getK();

        boolean results = action.apply(in);

        in.applyStateFrom(memoRe, memoI, memoJ, memoK);

        return results;
    }

    //--------------------------------------------------

    @Override
    public FPoint applyWithFixedState(FPoint in, Consumer<FPoint> action) {
        double memoX = in.getX();
        double memoY = in.getY();
        double memoZ = in.getZ();

        action.accept(in);

        return in.set(memoX, memoY, memoZ);
    }

    @Override
    public FPoint applyWithFixedMagnitude(FPoint in, Consumer<FPoint> action) {
        double memoMag = in.getMagnitude();

        action.accept(in);

        return in.setMagnitude(memoMag);
    }

    @Override
    public double toDoubleWithFixedState(FPoint in, Function<FPoint, Double> action) {
        double memoX = in.getX();
        double memoY = in.getY();
        double memoZ = in.getZ();

        double results = action.apply(in);

        in.set(memoX, memoY, memoZ);

        return results;
    }

    @Override
    public boolean toBooleanWithFixedState(FPoint in, Function<FPoint, Boolean> action) {
        double memoX = in.getX();
        double memoY = in.getY();
        double memoZ = in.getZ();

        boolean results = action.apply(in);

        in.set(memoX, memoY, memoZ);

        return results;
    }

    //--------------------------------------------------

    @Override
    public FVector applyWithFixedState(FVector in, Consumer<FVector> action) {
        double memoBX = in.getBaseX();
        double memoBY = in.getBaseY();
        double memoBZ = in.getBaseZ();
        double memoHX = in.getHeadX();
        double memoHY = in.getHeadY();
        double memoHZ = in.getHeadZ();

        action.accept(in);

        return in.set(memoBX, memoBY, memoBZ, memoHX, memoHY, memoHZ);
    }

    @Override
    public FVector applyWithFixedMagnitude(FVector in, Consumer<FVector> action) {
        double magnitude = in.getMagnitude();

        action.accept(in);

        return in.setMagnitude(magnitude);
    }

    @Override
    public FVector applyWithCenteredPosition(FVector in, Consumer<FVector> action) {
        double memoBX = in.getBaseX();
        double memoBY = in.getBaseY();
        double memoBZ = in.getBaseZ();

        in.moveBaseToCenter();

        action.accept(in);

        return in.moveBase(memoBX, memoBY, memoBZ);
    }

    @Override
    public double toDoubleWithFixedState(FVector in, Function<FVector, Double> action) {
        double memoBX = in.getBaseX();
        double memoBY = in.getBaseY();
        double memoBZ = in.getBaseZ();
        double memoHX = in.getHeadX();
        double memoHY = in.getHeadY();
        double memoHZ = in.getHeadZ();

        double res = action.apply(in);

        in.set(memoBX, memoBY, memoBZ, memoHX, memoHY, memoHZ);

        return res;
    }

    @Override
    public boolean toBooleanWithFixedState(FVector in, Function<FVector, Boolean> action) {
        double memoBX = in.getBaseX();
        double memoBY = in.getBaseY();
        double memoBZ = in.getBaseZ();
        double memoHX = in.getHeadX();
        double memoHY = in.getHeadY();
        double memoHZ = in.getHeadZ();

        boolean res = action.apply(in);

        in.set(memoBX, memoBY, memoBZ, memoHX, memoHY, memoHZ);

        return res;
    }
}
