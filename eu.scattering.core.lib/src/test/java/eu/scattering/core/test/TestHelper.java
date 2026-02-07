package eu.scattering.core.test;

import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.component.number.complex.FComplex;
import eu.scattering.core.design.component.number.quaternion.FQuaternion;
import eu.scattering.core.design.storage.transfer.pair.variants.FPairPos2D;
import eu.scattering.core.design.storage.transfer.pair.variants.FPairPos3D;
import eu.scattering.core.design.storage.transfer.pair.variants.FPairPos4D;

import static eu.scattering.core.impl.ConfigDef.EPSILON;
import static eu.scattering.core.test.Config.factory;

public class TestHelper {

    public static final double range = 10000;
    public static final FPairPos4D range4D = factory.getFTransferHelper().getFPairPos4DWithRange(range);
    public static final FPairPos3D range3D = factory.getFTransferHelper().getFPairPos3DWithRange(range);
    public static final FPairPos2D range2D = factory.getFTransferHelper().getFPairPos2DWithRange(range);

    public static FPoint getRandFPoint(FPoint... exc) {

        main:
        while (true) {
            var candidate =  factory.getRandAspect().inRange(factory.getFPoint(), range3D);

            for (FPoint fPoint : exc) {
                if (candidate.isSimilar(fPoint)) {
                    continue main;
                }
            }

            return candidate;
        }
    }

    public static FVector getRandFVector(FVector... exc) {

        main:
        while (true) {
            var base = getRandFPoint();
            var head = getRandFPoint();
            var candidate = factory.getFVector(base, head);

            for (FVector fVector : exc) {
                if (candidate.isSimilar(fVector)) {
                    continue main;
                }
            }

            return candidate;
        }
    }

    public static FComplex getRandFComplex(FComplex... exc) {

        main:
        while (true) {
            var candidate =  factory.getRandAspect().inRange(factory.getFComplex(), range2D);

            for (FComplex fComplex : exc) {
                if (candidate.isSimilar(fComplex)) {
                    continue main;
                }
            }

            return candidate;
        }
    }

    public static FQuaternion getRandFQuaternion(FQuaternion... exc) {

        main:
        while (true) {
            var candidate =  factory.getRandAspect().inRange(factory.getFQuaternion(), range4D);

            for (FQuaternion fQuaternion : exc) {
                if (candidate.isSimilar(fQuaternion)) {
                    continue main;
                }
            }

            return candidate;
        }
    }

    public static FSphere getRandFSphere(FSphere... exc) {

        main:
        while (true) {
            var candidate =  factory.getRefFSphere(
                    getRandFPoint(),
                    factory.getFRand().nextDouble(EPSILON, range)
            );

            for (FSphere fSphere : exc) {
                if (candidate.isSimilar(fSphere)) {
                    continue main;
                }
            }

            return candidate;
        }
    }
}
