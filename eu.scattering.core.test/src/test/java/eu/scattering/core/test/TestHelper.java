package eu.scattering.core.test;

import eu.scattering.core.design.elements.algebra.geometry.primitive.point.FPoint;
import eu.scattering.core.design.elements.algebra.geometry.primitive.vector.FVector;
import eu.scattering.core.design.elements.algebra.number.complex.FComplex;
import eu.scattering.core.design.elements.algebra.number.quaternion.FQuaternion;
import eu.scattering.core.transfer.containers.position.FPairPos3D.FPairPos3D;

import java.util.Arrays;

import static eu.scattering.core.test.Configuration.factory;
import static eu.scattering.core.test.Configuration.random;

public class TestHelper {

    public static final double range = 10000;
    public static final FPairPos3D range3D = factory.getFPositionHelper().getFPairPos3DWithRange(range);

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
        Double[] parsedRe = Arrays.stream(exc).map(FComplex::getRe).toArray(Double[]::new);
        Double[] parsedIm = Arrays.stream(exc).map(FComplex::getIm).toArray(Double[]::new);

        double re = random.nextDouble(-range, range);
        double im = random.nextDouble(-range, range);

        return factory.getFComplex(re, im);
    }

    public static FQuaternion getRandomFQuaternion(FQuaternion... exc) {
        double re = random.nextDouble(-range, range);
        double i = random.nextDouble(-range, range);
        double j = random.nextDouble(-range, range);
        double k = random.nextDouble(-range, range);

        return factory.getFQuaternion(re, i, j, k);
    }
}
