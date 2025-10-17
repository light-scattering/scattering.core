package eu.scattering.core.design.engine.randomize.generator.module.dist3d;

import eu.scattering.core.design.transfer.primitive.FPos3D;

public interface FDist3D {

    FPos3D produce();

    void produce(double[] in);
}
