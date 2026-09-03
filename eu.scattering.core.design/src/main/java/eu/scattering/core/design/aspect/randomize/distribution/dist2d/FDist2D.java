package eu.scattering.core.design.aspect.randomize.distribution.dist2d;

import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos2D;

public interface FDist2D {

    FPos2D produce();

    void produce(double[] in);
}
