package eu.scattering.core.design.aspect.randomize.generator.module.dist2d;

import eu.scattering.core.design.transfer.primitive.FPos2D;

public interface FDist2D {

    FPos2D produce();

    void produce(double[] in);
}
