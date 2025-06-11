package eu.scattering.core.design.engine.randomize.generator.module.dist2d;

import eu.scattering.core.transfer.container.storage.FPos2D.FPos2D;

public interface FDist2D {

    FPos2D produce();

    void produce(double[] in);
}
