package eu.scattering.core.design.aspect.randomize.generator;

import eu.scattering.core.design.aspect.randomize.generator.core.FRandCore;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos2D;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos3D;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos4D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos2D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos4D;

import java.util.List;

public interface FRandGenerator extends FRandCore {

    FPos2D nextDouble2D(FPairPos2D range);
    FPos3D nextDouble3D(FPairPos3D range);
    FPos4D nextDouble4D(FPairPos4D range);

    FPos2D nextDoubleOnCircle(double radius);
    FPos2D nextDoubleInCircle(double radius);

    FPos3D nextDoubleOnSphere(double radius);
    FPos3D nextDoubleInSphere(double radius);

    FPos3D nextDoubleInShell(double radiusMin, double radiusMax);

    <T> T getElement(List<T> in, boolean remove);
}
