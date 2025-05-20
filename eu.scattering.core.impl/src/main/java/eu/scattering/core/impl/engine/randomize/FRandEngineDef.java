package eu.scattering.core.impl.engine.randomize;

import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.number.complex.FComplex;
import eu.scattering.core.design.component.number.quaternion.FQuaternion;
import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.design.engine.randomize.FRandEngine;
import eu.scattering.core.transfer.container.storage.FPairPos2D.FPairPos2D;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.container.storage.FPairPos4D.FPairPos4D;
import eu.scattering.core.transfer.container.storage.FPos2D.FPos2D;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;
import eu.scattering.core.transfer.container.storage.FPos4D.FPos4D;

import java.util.Arrays;

public class FRandEngineDef implements FRandEngine {
    private final FRandGenerator core;

    private FRandEngineDef(FRandGenerator core) {

        this.core = core;
    }

    public static FRandEngine create(FRandGenerator core) {

        return new FRandEngineDef(core);
    }

    //--------------------------------------------------

    @Override
    public FComplex rndPos(FComplex in, FPairPos2D range) {

        in.applyStateFrom(core.nextDouble2D(range));

        return in;
    }

    @Override
    public FComplex rndPosInCircle(FComplex in, double radius) {

        in.applyStateFrom(core.nextDoubleInCircle(radius));

        return in;
    }

    @Override
    public FComplex rndPosOnCircle(FComplex in, double radius) {

        in.applyStateFrom(core.nextDoubleOnCircle(radius));

        return in;
    }

    @Override
    public FQuaternion rndPos(FQuaternion in, FPairPos4D range) {

        in.applyStateFrom(core.nextDouble4D(range));

        return in;
    }

    //--------------------------------------------------

    @Override
    public FPoint rndAngle(FPoint in) {
        double radius = in.getMagnitude();

        in.applyStateFrom(core.nextDoubleOnSphere(radius));

        return in;
    }

    @Override
    public FPoint rndPosInRange(FPoint in, FPairPos3D range) {

        in.applyStateFrom(core.nextDouble3D(range));

        return in;
    }

    @Override
    public FPoint rndPosInSphere(FPoint in, double radius) {

        in.applyStateFrom(core.nextDoubleInSphere(radius));

        return in;
    }

    @Override
    public FPoint rndPosOnSphere(FPoint in, double radius) {

        in.applyStateFrom(core.nextDoubleOnSphere(radius));

        return in;
    }

    @Override
    public FPoint rndPosInCircle(FPoint in, FPoint dir, double radius) {
        FPos2D base = core.nextDoubleInCircle(radius);

        in.set(base.getD0(), base.getD1(), 0);

//        in.orth

        return null;
    }

    @Override
    public FVector rndAngle(FVector in) {
        double memoOBX = in.getBaseX();
        double memoOBY = in.getBaseY();
        double memoOBZ = in.getBaseZ();

        in.moveBaseToCenter();
        rndAngle(in.getRefHead());
        in.moveBase(memoOBX, memoOBY, memoOBZ);

        return in;
    }

    @Override
    public FVector rndPos(FVector in, FPairPos3D range) {

        rndPosInRange(in.getRefBase(), range);
        rndPosInRange(in.getRefHead(), range);

        return in;
    }

    @Override
    public FVector rndPosInSphere(FVector in, double radius) {

        rndPosInSphere(in.getRefBase(), radius);
        rndPosInSphere(in.getRefHead(), radius);

        return in;
    }

    @Override
    public FVector rndPosOnSphere(FVector in, double radius) {

        rndPosOnSphere(in.getRefBase(), radius);
        rndPosOnSphere(in.getRefHead(), radius);

        return in;
    }
}
