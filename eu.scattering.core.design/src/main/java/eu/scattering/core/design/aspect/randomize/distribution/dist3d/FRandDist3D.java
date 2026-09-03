package eu.scattering.core.design.aspect.randomize.distribution.dist3d;

import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;

public interface FRandDist3D {

    FPos3D produce();

    void produce(double[] in);
}
