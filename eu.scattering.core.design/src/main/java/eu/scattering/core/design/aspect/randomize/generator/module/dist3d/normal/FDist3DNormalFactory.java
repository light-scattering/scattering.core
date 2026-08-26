package eu.scattering.core.design.aspect.randomize.generator.module.dist3d.normal;

public interface FDist3DNormalFactory {

    FDist3DNormal getFDist3DNormal(double avg, double std);

    // -------------------------------------------------------------------------------------------------

    default FDist3DNormal getFDist3DNormal() {

        return getFDist3DNormal(0, 1);
    }
}
