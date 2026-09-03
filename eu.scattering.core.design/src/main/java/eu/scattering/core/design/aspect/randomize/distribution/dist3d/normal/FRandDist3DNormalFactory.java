package eu.scattering.core.design.aspect.randomize.distribution.dist3d.normal;

public interface FRandDist3DNormalFactory {

    FRandDist3DNormal normal(double avg, double std);

    // -------------------------------------------------------------------------------------------------

    default FRandDist3DNormal normal() {

        return normal(0, 1);
    }
}
