package eu.scattering.core.impl.engines.random;

import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;
import eu.scattering.core.design.mutables.geometry.primitive.vector.FVector;
import eu.scattering.core.design.mutables.number.complex.FComplex;
import eu.scattering.core.design.mutables.number.quaternion.FQuaternion;
import eu.scattering.core.design.engines.random.processor.FRandomProcessor;
import eu.scattering.core.design.engines.random.FRandomEngine;
import eu.scattering.core.transfer.containers.position.FPairPos2D.FPairPos2D;
import eu.scattering.core.transfer.containers.position.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.containers.position.FPairPos4D.FPairPos4D;
import eu.scattering.core.transfer.containers.position.FPos2D.FPos2D;
import eu.scattering.core.transfer.containers.position.FPos3D.FPos3D;
import eu.scattering.core.transfer.containers.position.FPos4D.FPos4D;

import java.util.Arrays;

public class FRandomEngineDef implements FRandomEngine {
    private final FRandomProcessor fRandom;

    private FRandomEngineDef(FRandomProcessor random) {

        this.fRandom = random;
    }

    public static FRandomEngine create(FRandomProcessor random) {

        return new FRandomEngineDef(random);
    }

    //--------------------------------------------------

    @Override
    public FComplex rndPosition(FComplex origin, FPairPos2D range, FComplex... exclusion) {
        FPos2D[] exc = Arrays.stream(exclusion).map(FComplex::toFPos2D).toArray(FPos2D[]::new);

        origin.set(fRandom.nextDouble2D(range, exc));

        return origin;
    }

    @Override
    public FComplex rndPosition(FComplex origin, double radius, FComplex... exclusion) {
        FPos2D[] exc = Arrays.stream(exclusion).map(FComplex::toFPos2D).toArray(FPos2D[]::new);

        origin.set(fRandom.nextDoubleOnCircle(radius, exc));

        return origin;
    }

    @Override
    public FQuaternion rndPosition(FQuaternion origin, FPairPos4D range, FQuaternion... exclusion) {
        FPos4D[] exc = Arrays.stream(exclusion).map(FQuaternion::toFPos4D).toArray(FPos4D[]::new);

        origin.set(fRandom.nextDouble4D(range, exc));

        return origin;
    }

    // TODO - Not implemented
    @Override
    public FQuaternion rndPosition(FQuaternion origin, double radius, FQuaternion... exclusion) {
        return null;
    }

    //--------------------------------------------------

    @Override
    public FPoint rndAngle(FPoint origin, FPoint... exclusion) {
        double radius = origin.getLength();

        origin.set(fRandom.nextDoubleOnSphere(radius));

        return origin;
    }

    @Override
    public FPoint rndPosition(FPoint origin, FPairPos3D range, FPoint... exclusion) {
        FPos3D[] exc = Arrays.stream(exclusion).map(FPoint::toFPos3D).toArray(FPos3D[]::new);

        origin.set(fRandom.nextDouble3D(range, exc));

        return origin;
    }

    @Override
    public FPoint rndPosition(FPoint origin, double radius, FPoint... exclusion) {
        FPos3D[] exc = Arrays.stream(exclusion).map(FPoint::toFPos3D).toArray(FPos3D[]::new);

        origin.set(fRandom.nextDoubleInSphere(radius, exc));

        return origin;
    }

    @Override
    public FVector rndAngle(FVector origin, FPoint... exclusion) {
        FVector fCopyLocal = origin.copy().moveBaseToCenter();

        FPoint[] exc = new FPoint[exclusion.length];

        for (int i = 0; i < exclusion.length ; i++ ) {
            exc[i] = exclusion[i].copy().sub(origin.getRefBase());
        }

        rndAngle(fCopyLocal.getRefHead(), exc);

        fCopyLocal.moveBase(origin.getRefBase());

        origin.applyStateFrom(fCopyLocal);

        return origin;
    }

    @Override
    public FVector rndPosition(FVector origin, FPairPos3D range, FPoint... exclusion) {

        rndPosition(origin.getRefBase(), range, exclusion);

        FPoint[] exc = new FPoint[exclusion.length + 1];

        int i = 0;
        for (; i < exclusion.length ; i++ ) {
            exc[i] = exclusion[i];
        }

        exc[i] = origin.getRefBase();

        rndPosition(origin.getRefHead(), range, exc);

        return origin;
    }

    @Override
    public FVector rndPosition(FVector origin, double radius, FPoint... exclusion) {

        rndPosition(origin.getRefBase(), radius, exclusion);

        FPoint[] exc = new FPoint[exclusion.length + 1];

        int i = 0;
        for (; i < exclusion.length ; i++ ) {
            exc[i] = exclusion[i];
        }

        exc[i] = origin.getRefBase();

        rndPosition(origin.getRefHead(), radius, exc);

        return origin;
    }
}
