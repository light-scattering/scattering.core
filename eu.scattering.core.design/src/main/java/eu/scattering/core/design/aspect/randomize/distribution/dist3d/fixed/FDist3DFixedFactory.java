package eu.scattering.core.design.aspect.randomize.distribution.dist3d.fixed;

import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;

public interface FDist3DFixedFactory {

    FDist3DFixed fixed(double d0, double d1, double d2);

    FDist3DFixed fixed(FPos3D val);
}
