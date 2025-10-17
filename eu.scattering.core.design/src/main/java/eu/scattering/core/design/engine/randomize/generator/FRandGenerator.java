package eu.scattering.core.design.engine.randomize.generator;

import eu.scattering.core.design.engine.randomize.generator.core.FRandCore;
import eu.scattering.core.design.engine.randomize.generator.module.ModuleFactory;
import eu.scattering.core.design.transfer.primitive.*;

import java.util.List;

public interface FRandGenerator extends FRandCore, ModuleFactory {

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
