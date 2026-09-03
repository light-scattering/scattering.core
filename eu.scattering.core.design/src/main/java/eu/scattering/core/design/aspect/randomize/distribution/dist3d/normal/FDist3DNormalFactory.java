package eu.scattering.core.design.aspect.randomize.distribution.dist3d.normal;

public interface FDist3DNormalFactory {

    FDist3DNormal normal(double avg, double std);

    // -------------------------------------------------------------------------------------------------

    default FDist3DNormal normal() {

        return normal(0, 1);
    }
}
