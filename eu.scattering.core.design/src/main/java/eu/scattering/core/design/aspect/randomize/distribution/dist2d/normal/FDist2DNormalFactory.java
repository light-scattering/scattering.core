package eu.scattering.core.design.aspect.randomize.distribution.dist2d.normal;

public interface FDist2DNormalFactory {

    FDist2DNormal normal(double avg, double std);

    // -------------------------------------------------------------------------------------------------

    default FDist2DNormal normal() {

        return normal(0, 1);
    }
}
