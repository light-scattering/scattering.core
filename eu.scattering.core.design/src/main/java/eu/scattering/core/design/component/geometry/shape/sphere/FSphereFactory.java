package eu.scattering.core.design.component.geometry.shape.sphere;

import eu.scattering.core.design.aspect.randomize.FRandAspect;
import eu.scattering.core.design.aspect.randomize.generator.module.dist1d.FDist1D;
import eu.scattering.core.design.aspect.randomize.generator.module.dist3d.FDist3D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import eu.scattering.core.design.utility.annotation.Modificator;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.functionality.Producer;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface FSphereFactory {

    FSphereProducer getFSphereProducer();

    FSphereHelper getFSphereHelper();

    //--------------------------------------------------

    FSphere getFSphere();

    @Modificator
    FSphere getRefFSphere(FPoint refCenter);

    //--------------------------------------------------

    default FSphere getFSphere(double radius) {

        return (FSphere) getFSphere().setRadius(radius);
    }

    default FSphere getFSphere(double x, double y, double z) {

        return (FSphere) getFSphere().setCenter(x, y, z);
    }

    default FSphere getFSphere(double x, double y, double z, double radius) {

        return (FSphere) getFSphere().setCenter(x, y, z).setRadius(radius);
    }

    default FSphere getFSphere(FPos3D center) {

        return (FSphere) getFSphere().setCenter(center);
    }

    default FSphere getFSphere(FPos3D center, double radius) {

        return (FSphere) getFSphere().setCenter(center).setRadius(radius);
    }

    @Modificator
    default FSphere getRefFSphere(FPoint refCenter, double radius) {

        return (FSphere) getRefFSphere(refCenter).setRadius(radius);
    }

    // -------------------------------------------------------------------------------------------------

    default FSphereProducer getFSphereProducer(Function<FSphereFactory, FSphere> function) {

        return getFSphereProducer().withCustomRule(function);
    }

    default FSphereProducer getFSphereProducer(BiFunction<FSphereFactory, FRandAspect, FSphere> function) {

        return getFSphereProducer().withCustomRule(function);
    }

    default FSphereProducer getFSphereProducer(double radius) {

        return getFSphereProducer().withFixRadius(radius);
    }

    default FSphereProducer getFSphereProducer(FDist1D radius) {

        return getFSphereProducer().withDistRadius(radius);
    }

    default FSphereProducer getFSphereProducer(FDist3D dCenter, double radius) {

        return getFSphereProducer().withDistCenterAndFixRadius(dCenter, radius);
    }

    default FSphereProducer getFSphereProducer(FDist3D dCenter, FDist1D radius) {

        return getFSphereProducer().withDistCenterAndDistRadius(dCenter, radius);
    }

    default FSphereProducer getFSphereProducer(Producer<FPoint> pCenter, double radius) {

        return getFSphereProducer().withProdCenterAndFixRadius(pCenter, radius);
    }

    default FSphereProducer getFSphereProducer(Producer<FPoint> pCenter, FDist1D radius) {

        return getFSphereProducer().withProdCenterAndDistRadius(pCenter, radius);
    }
}
