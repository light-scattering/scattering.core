package eu.scattering.core.impl.production.support.helper;

import eu.scattering.core.design.elements.algebra.geometry.primitive.point.FPoint;
import eu.scattering.core.design.elements.algebra.geometry.primitive.vector.FVector;
import eu.scattering.core.design.elements.algebra.number.complex.FComplex;
import eu.scattering.core.design.elements.algebra.number.quaternion.FQuaternion;
import eu.scattering.core.design.elements.engine.random.FRandom;
import eu.scattering.core.design.helpers.engine.FRandomHelper;
import eu.scattering.core.transfer.containers.position.FPairPos2D.FPairPos2D;
import eu.scattering.core.transfer.containers.position.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.containers.position.FPairPos4D.FPairPos4D;
import eu.scattering.core.transfer.containers.position.FPos3D.FPos3D;

import java.util.Arrays;

public class FRandomHelperProd implements FRandomHelper {
    private final FRandom fRandom;

    private FRandomHelperProd(FRandom random) {

        this.fRandom = random;
    }

    public static FRandomHelper create(FRandom random) {

        return new FRandomHelperProd(random);
    }

    //--------------------------------------------------

    @Override
    public FComplex rndPosition(FComplex origin, FPairPos2D range, FComplex... exclusion) {
        return null;
    }

    @Override
    public FComplex rndPosition(FComplex origin, double radius, FComplex... exclusion) {
        return null;
    }

    @Override
    public FQuaternion rndPosition(FQuaternion origin, FPairPos4D range, FQuaternion... exclusion) {
        return null;
    }

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
        FVector fCopyLocal = origin.copy().moveBase();

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
