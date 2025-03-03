package eu.scattering.core.test;

import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.component.number.complex.FComplex;
import eu.scattering.core.design.component.number.quaternion.FQuaternion;
import eu.scattering.core.transfer.container.position.FPairPos2D.FPairPos2D;
import eu.scattering.core.transfer.container.position.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.container.position.FPairPos4D.FPairPos4D;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static eu.scattering.core.test.Config.factory;

public class TestHelper {

    public static final double range = 10000;
    public static final FPairPos4D range4D = factory.getFPositionHelper().getFPairPos4DWithRange(range);
    public static final FPairPos3D range3D = factory.getFPositionHelper().getFPairPos3DWithRange(range);
    public static final FPairPos2D range2D = factory.getFPositionHelper().getFPairPos2DWithRange(range);

    public static FPoint getRandFPoint(FPoint... exc) {

        return factory.getFRandEngine().rndPos(factory.getFPoint(), range3D, exc);
    }

    public static FVector getRandFVector(FVector... exc) {
        List<FPoint> parsedBaseList = Arrays.stream(exc).map(FVector::getRefBase).collect(Collectors.toList());
        List<FPoint> parsedHeadList = Arrays.stream(exc).map(FVector::getRefHead).collect(Collectors.toList());

        FPoint base = getRandFPoint(parsedBaseList.toArray(FPoint[]::new));

        parsedHeadList.add(base);

        FPoint head = getRandFPoint(parsedHeadList.toArray(FPoint[]::new));

        return factory.getFVector(base, head);
    }

    public static FComplex getRandFComplex(FComplex... exc) {

        return factory.getFRandEngine().rndPos(factory.getFComplex(), range2D, exc);
    }

    public static FQuaternion getRandFQuaternion(FQuaternion... exc) {

        return factory.getFRandEngine().rndPos(factory.getFQuaternion(), range4D, exc);
    }

    public static FSphere getRandFSphere(FSphere... exc) {
        List<FPoint> parsedCenterList = Arrays.stream(exc).map(FSphere::getRefCenter).collect(Collectors.toList());

        FPoint center = getRandFPoint(parsedCenterList.toArray(FPoint[]::new));

        return factory.getRefFSphere(center, factory.getFRandProcessor().nextDouble(0, range, 0));
    }
}
