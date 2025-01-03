package eu.scattering.core.test;

import eu.scattering.core.design.mutables.algebra.geometry.primitive.point.FPoint;
import eu.scattering.core.design.mutables.algebra.geometry.primitive.vector.FVector;
import eu.scattering.core.design.mutables.algebra.number.complex.FComplex;
import eu.scattering.core.design.mutables.algebra.number.quaternion.FQuaternion;
import eu.scattering.core.transfer.containers.position.FPairPos2D.FPairPos2D;
import eu.scattering.core.transfer.containers.position.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.containers.position.FPairPos4D.FPairPos4D;

import java.util.Arrays;

import static eu.scattering.core.test.Configuration.factory;

public class TestHelper {

    public static final double range = 10000;
    public static final FPairPos4D range4D = factory.getFPositionHelper().getFPairPos4DWithRange(range);
    public static final FPairPos3D range3D = factory.getFPositionHelper().getFPairPos3DWithRange(range);
    public static final FPairPos2D range2D = factory.getFPositionHelper().getFPairPos2DWithRange(range);

    public static FPoint getRandomFPoint(FPoint... exc) {

        return factory.getFRandomHelper().rndPosition(factory.getFPoint(), range3D, exc);
    }

    public static FVector getRandomFVector(FVector... exc) {

        FPoint[] parsedBase = Arrays.stream(exc).map(FVector::getRefBase).toArray(FPoint[]::new);
        FPoint[] parsedHead = Arrays.stream(exc).map(FVector::getRefHead).toArray(FPoint[]::new);

        FPoint base = getRandomFPoint(parsedBase);
        FPoint head = getRandomFPoint(parsedHead);

        return factory.getFVector(base, head);
    }

    public static FComplex getRandomFComplex(FComplex... exc) {

        return factory.getFRandomHelper().rndPosition(factory.getFComplex(), range2D, exc);
    }

    public static FQuaternion getRandomFQuaternion(FQuaternion... exc) {

        return factory.getFRandomHelper().rndPosition(factory.getFQuaternion(), range4D, exc);
    }
}
