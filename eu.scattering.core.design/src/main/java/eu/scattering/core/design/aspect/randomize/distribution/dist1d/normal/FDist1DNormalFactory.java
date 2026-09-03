package eu.scattering.core.design.aspect.randomize.distribution.dist1d.normal;

public interface FDist1DNormalFactory {

    FDist1DNormal normal(double mean, double std);

    // -------------------------------------------------------------------------------------------------

    default FDist1DNormal normal() {

        return normal(0, 1);
    }
}
