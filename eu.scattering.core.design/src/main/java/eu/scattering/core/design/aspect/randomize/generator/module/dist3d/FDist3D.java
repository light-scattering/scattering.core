package eu.scattering.core.design.aspect.randomize.generator.module.dist3d;

import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;

public interface FDist3D {

    FPos3D produce();

    void produce(double[] in);
}
