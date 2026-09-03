package eu.scattering.core.design.component.geometry.base.point;

import eu.scattering.core.design.aspect.randomize.FRandAspect;
import eu.scattering.core.design.aspect.randomize.distribution.dist3d.FDist3D;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos3D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import eu.scattering.core.design.utility.type.option.Location;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface FPointFactory {

    FPointProducer getFPointProducer();

    FPointHelper getFPointHelper();

    //--------------------------------------------------

    FPoint getFPoint();

    //--------------------------------------------------

    default FPoint getFPoint(double x, double y, double z) {

        return getFPoint().setX(x).setY(y).setZ(z);
    }

    default FPoint getFPoint(double x, double y) {

        return getFPoint().setX(x).setY(y);
    }

    default FPoint getFPoint(double x) {

        return getFPoint().setX(x);
    }

    default FPoint getFPoint(FPos3D position) {

        return getFPoint().set(position);
    }

    // -------------------------------------------------------------------------------------------------

    default FPointProducer getFPointProducer(Function<FPointFactory, FPoint> function) {

        return getFPointProducer().withCustomRule(function);
    }

    default FPointProducer getFPointProducer(BiFunction<FPointFactory, FRandAspect, FPoint> function) {

        return getFPointProducer().withCustomRule(function);
    }

    default FPointProducer getFPointProducer(FDist3D dist) {

        return getFPointProducer().withDist(dist);
    }

    default FPointProducer getFPointProducer(FPairPos3D range) {

        return getFPointProducer().withInRange(range);
    }

    default FPointProducer getFPointProducer(double radius, Location type) {

        if (type == Location.IN_SPHERE) {
            return getFPointProducer().withInSphere(radius);
        }

        if (type == Location.ON_SPHERE) {
            return getFPointProducer().withOnSphere(radius);
        }

        throw new IllegalArgumentException("Unsupported producer type");
    }

    default FPointProducer getFPointProducer(double radiusMin, double radiusMax) {

        return getFPointProducer().withInShell(radiusMin, radiusMax);
    }
}
