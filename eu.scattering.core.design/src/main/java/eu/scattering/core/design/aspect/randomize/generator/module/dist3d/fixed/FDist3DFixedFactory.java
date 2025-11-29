package eu.scattering.core.design.aspect.randomize.generator.module.dist3d.fixed;

import eu.scattering.core.design.transfer.primitive.FPos3D;

public interface FDist3DFixedFactory {

    FDist3DFixed getFDist3DFixed(double x, double y, double z);

    FDist3DFixed getFDist3DFixed(FPos3D val);
}
