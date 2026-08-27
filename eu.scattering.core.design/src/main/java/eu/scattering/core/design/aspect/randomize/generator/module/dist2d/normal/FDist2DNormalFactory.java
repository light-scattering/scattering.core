package eu.scattering.core.design.aspect.randomize.generator.module.dist2d.normal;

public interface FDist2DNormalFactory {

    FDist2DNormal getFDist2DNormal(double avg, double std);

    // -------------------------------------------------------------------------------------------------

    default FDist2DNormal getFDist2DNormal() {

        return getFDist2DNormal(0, 1);
    }
}
