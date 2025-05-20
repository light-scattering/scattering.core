package eu.scattering.core.test;

import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.component.number.complex.FComplex;
import eu.scattering.core.design.component.number.quaternion.FQuaternion;
import eu.scattering.core.transfer.container.storage.FPairPos2D.FPairPos2D;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.container.storage.FPairPos4D.FPairPos4D;

import static eu.scattering.core.test.Config.factory;

public class TestHelper {

    public static final double range = 10000;
    public static final FPairPos4D range4D = factory.getFPositionHelper().getFPairPos4DWithRange(range);
    public static final FPairPos3D range3D = factory.getFPositionHelper().getFPairPos3DWithRange(range);
    public static final FPairPos2D range2D = factory.getFPositionHelper().getFPairPos2DWithRange(range);

    public static FPoint getRandFPoint(FPoint... exc) {

        return factory.getFRandEngine().rndPosInRange(factory.getFPoint(), range3D);
    }

    public static FVector getRandFVector(FVector... exc) {

        FPoint base = getRandFPoint();
        FPoint head = getRandFPoint();

        return factory.getFVector(base, head);
    }

    public static FComplex getRandFComplex(FComplex... exc) {

        return factory.getFRandEngine().rndPos(factory.getFComplex(), range2D);
    }

    public static FQuaternion getRandFQuaternion(FQuaternion... exc) {

        return factory.getFRandEngine().rndPos(factory.getFQuaternion(), range4D);
    }

    public static FSphere getRandFSphere(FSphere... exc) {

        FPoint center = getRandFPoint();

        return factory.getRefFSphere(center, factory.getFRandGenerator().nextDouble(0, range, 0));
    }
}
