package eu.scattering.core.impl.engine.randomize;

import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.number.complex.FComplex;
import eu.scattering.core.design.component.number.quaternion.FQuaternion;
import eu.scattering.core.design.engine.randomize.processor.FRandProcessor;
import eu.scattering.core.design.engine.randomize.FRandEngine;
import eu.scattering.core.transfer.container.storage.FPairPos2D.FPairPos2D;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.container.storage.FPairPos4D.FPairPos4D;
import eu.scattering.core.transfer.container.storage.FPos2D.FPos2D;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;
import eu.scattering.core.transfer.container.storage.FPos4D.FPos4D;

import java.util.Arrays;

public class FRandEngineDef implements FRandEngine {
    private final FRandProcessor core;

    private FRandEngineDef(FRandProcessor core) {

        this.core = core;
    }

    public static FRandEngine create(FRandProcessor core) {

        return new FRandEngineDef(core);
    }

    //--------------------------------------------------

    @Override
    public FComplex rndPos(FComplex in, FPairPos2D range, FComplex... exclusion) {
        FPos2D[] exc = Arrays.stream(exclusion).map(FComplex::toFPos2D).toArray(FPos2D[]::new);

        in.applyStateFrom(core.nextDouble2D(range, exc));

        return in;
    }

    @Override
    public FComplex rndPosInCircle(FComplex in, double radius, FComplex... exclusion) {
        FPos2D[] exc = Arrays.stream(exclusion).map(FComplex::toFPos2D).toArray(FPos2D[]::new);

        in.applyStateFrom(core.nextDoubleInCircle(radius, exc));

        return in;
    }

    @Override
    public FComplex rndPosOnCircle(FComplex in, double radius, FComplex... exclusion) {
        FPos2D[] exc = Arrays.stream(exclusion).map(FComplex::toFPos2D).toArray(FPos2D[]::new);

        in.applyStateFrom(core.nextDoubleOnCircle(radius, exc));

        return in;
    }

    @Override
    public FQuaternion rndPos(FQuaternion in, FPairPos4D range, FQuaternion... exclusion) {
        FPos4D[] exc = Arrays.stream(exclusion).map(FQuaternion::toFPos4D).toArray(FPos4D[]::new);

        in.applyStateFrom(core.nextDouble4D(range, exc));

        return in;
    }

    //--------------------------------------------------

    @Override
    public FPoint rndAngle(FPoint in, FPoint... exclusion) {
        double radius = in.getMagnitude();

        in.applyStateFrom(core.nextDoubleOnSphere(radius));

        return in;
    }

    @Override
    public FPoint rndPos(FPoint in, FPairPos3D range, FPoint... exclusion) {
        FPos3D[] exc = Arrays.stream(exclusion).map(FPoint::toFPos3D).toArray(FPos3D[]::new);

        in.applyStateFrom(core.nextDouble3D(range, exc));

        return in;
    }

    @Override
    public FPoint rndPosInSphere(FPoint in, double radius, FPoint... exclusion) {
        FPos3D[] exc = Arrays.stream(exclusion).map(FPoint::toFPos3D).toArray(FPos3D[]::new);

        in.applyStateFrom(core.nextDoubleInSphere(radius, exc));

        return in;
    }

    @Override
    public FPoint rndPosOnSphere(FPoint in, double radius, FPoint... exclusion) {
        FPos3D[] exc = Arrays.stream(exclusion).map(FPoint::toFPos3D).toArray(FPos3D[]::new);

        in.applyStateFrom(core.nextDoubleOnSphere(radius, exc));

        return in;
    }

    @Override
    public FVector rndAngle(FVector in, FPoint... exclusion) {
        FVector fCopyLocal = in.copy().moveBaseToCenter();

        FPoint[] exc = new FPoint[exclusion.length];

        for (int i = 0; i < exclusion.length ; i++ ) {
            exc[i] = exclusion[i].copy().subXYZ(in.getRefBase());
        }

        rndAngle(fCopyLocal.getRefHead(), exc);

        fCopyLocal.moveBase(in.getRefBase());

        in.applyStateFrom(fCopyLocal);

        return in;
    }

    @Override
    public FVector rndPos(FVector in, FPairPos3D range, FPoint... exclusion) {

        rndPos(in.getRefBase(), range, exclusion);

        FPoint[] exc = new FPoint[exclusion.length + 1];

        int i = 0;
        for (; i < exclusion.length ; i++ ) {
            exc[i] = exclusion[i];
        }

        exc[i] = in.getRefBase();

        rndPos(in.getRefHead(), range, exc);

        return in;
    }

    @Override
    public FVector rndPosInSphere(FVector in, double radius, FPoint... exclusion) {

        rndPosInSphere(in.getRefBase(), radius, exclusion);

        FPoint[] exc = new FPoint[exclusion.length + 1];

        int i = 0;
        for (; i < exclusion.length ; i++ ) {
            exc[i] = exclusion[i];
        }

        exc[i] = in.getRefBase();

        rndPosInSphere(in.getRefHead(), radius, exc);

        return in;
    }

    @Override
    public FVector rndPosOnSphere(FVector in, double radius, FPoint... exclusion) {

        rndPosOnSphere(in.getRefBase(), radius, exclusion);

        FPoint[] exc = new FPoint[exclusion.length + 1];

        int i = 0;
        for (; i < exclusion.length ; i++ ) {
            exc[i] = exclusion[i];
        }

        exc[i] = in.getRefBase();

        rndPosOnSphere(in.getRefHead(), radius, exc);

        return in;
    }
}
