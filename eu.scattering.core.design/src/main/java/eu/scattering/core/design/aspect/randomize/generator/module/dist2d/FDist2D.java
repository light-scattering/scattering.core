package eu.scattering.core.design.aspect.randomize.generator.module.dist2d;

import eu.scattering.core.design.storage.transfer.position.p1.variants.FPos2D;

public interface FDist2D {

    FPos2D produce();

    void produce(double[] in);
}
