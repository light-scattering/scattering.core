package eu.scattering.core.design.component.geometry.base.point;

import eu.scattering.core.design.aspect.randomize.FRandAspect;
import eu.scattering.core.design.aspect.randomize.generator.module.dist3d.FDist3D;
import eu.scattering.core.design.transfer.primitive.FPairPos3D;
import eu.scattering.core.design.transfer.primitive.FPos3D;
import eu.scattering.core.design.type.PointLocation;

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

    default FPointProducer getFPointProducer(double radius, PointLocation type) {

        if (type == PointLocation.IN_SPHERE) {
            return getFPointProducer().withInSphere(radius);
        }

        if (type == PointLocation.ON_SPHERE) {
            return getFPointProducer().withOnSphere(radius);
        }

        throw new IllegalArgumentException("Unsupported producer type");
    }

    default FPointProducer getFPointProducer(double radiusMin, double radiusMax) {

        return getFPointProducer().withInShell(radiusMin, radiusMax);
    }
}
