package eu.scattering.core.design.aspect.randomize.distribution.dist2d.normal;

public interface FRandDist2DNormalFactory {

    FRandDist2DNormal normal(double avg, double std);

    // -------------------------------------------------------------------------------------------------

    default FRandDist2DNormal normal() {

        return normal(0, 1);
    }
}
