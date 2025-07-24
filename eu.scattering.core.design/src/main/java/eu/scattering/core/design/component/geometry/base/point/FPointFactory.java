package eu.scattering.core.design.component.geometry.base.point;

import eu.scattering.core.design.engine.randomize.FRandEngine;
import eu.scattering.core.design.engine.randomize.generator.module.dist3d.FDist3D;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface FPointFactory {

    FPointProducer getFPointProducer();

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

        return getFPoint().applyStateFrom(position);
    }

    // -------------------------------------------------------------------------------------------------
    // Producer facades.
    // -------------------------------------------------------------------------------------------------

    default FPointProducer getFPointProducer(Function<FPointFactory, FPoint> function) {

        return getFPointProducer().withCustomRule(function);
    }

    default FPointProducer getFPointProducer(BiFunction<FPointFactory, FRandEngine, FPoint> function) {

        return getFPointProducer().withCustomRule(function);
    }

    default FPointProducer getFPointProducer(FDist3D dist) {

        return getFPointProducer().withDist(dist);
    }

    default FPointProducer getFPointProducer(FPairPos3D range) {

        return getFPointProducer().withInRange(range);
    }

    default FPointProducer getFPointProducer(double radius, FPointProducer.Type type) {

        if (type == FPointProducer.Type.IN_SPHERE) {
            return getFPointProducer().withInSphere(radius);
        }

        if (type == FPointProducer.Type.ON_SPHERE) {
            return getFPointProducer().withRadius(radius);
        }

        throw new IllegalArgumentException("Unsupported producer type");
    }
}
